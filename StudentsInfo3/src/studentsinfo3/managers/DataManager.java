package studentsinfo3.managers;

import java.util.ArrayList;

import studentsinfo3.listeners.EntityListener;
import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;
import studentsinfo3.storage.Storage;

public class DataManager {
    
    private static DataManager instance;
    private ArrayList<EntityListener> observers;

    private DataManager() {
        observers = new ArrayList<EntityListener>();

    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance= new DataManager();
        }
        return instance;
    }

    public void registerObserver(EntityListener observer) {
        observers.add(observer);
    }
    
    public boolean isStudentExist(Group group, Student student) {
        Entity [] students=group.getEntries();
        for(int i=0;i<students.length;i++) {
            Student stud=(Student)students[i];
            if(stud.equals(student)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isGroupExist(String name) {
        Entity [] groups= Storage.getRoot().getEntries();
        for(int i=0;i<groups.length;i++) {
            Group group=(Group)groups[i];
            if(group.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public void addStudent(Group group, Student student) {
        group.addEntry(student);
        notifyObserversUpdate();
    }
    
    public void removeStudent(Student student) {
        Group group = student.getGroup();
        group.removeEntry(student);
        notifyObserversUpdate();
    }

    public void addGroup(String groupName) {
        Storage.getRoot().addEntry(new Group(Storage.getRoot(),groupName));
        notifyObserversUpdate();
    }
    
    public void removeGroup(Group group) {
        Storage.getRoot().removeEntry(group); 
        notifyObserversUpdate();
    }

    public void notifyObserversUpdate() {
        for (int i = 0; i < observers.size(); i++) {
            EntityListener observer = (EntityListener) observers.get(i);
            observer.updateData();
        }
    }

}
