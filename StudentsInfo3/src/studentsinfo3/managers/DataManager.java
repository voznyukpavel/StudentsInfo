package studentsinfo3.managers;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;

import studentsinfo3.listeners.EntityListener;
import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;
import studentsinfo3.storage.Storage;

public class DataManager {

    private static DataManager instance;
    private ArrayList<EntityListener> observers;
    private int counter = 0;

    private DataManager() {
        observers = new ArrayList<EntityListener>();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void registerObserver(EntityListener observer) {
        observers.add(observer);
    }

    public boolean isStudentExist(String groupName, String studentName, String address, String city, int result,
            boolean male, Image photo, String imageWay) {
        
        Group group =getGroupByName(groupName);
        Student student = new Student(studentName, group, address, city, result, male);
        if (photo != null) {
            student.setPhotoData(photo, imageWay);
        }
        student.setPhotoData(photo, imageWay);
        
        return isStudentExist(group, student);
    }

    public boolean isStudentExist(Group group, Student student) {
        Entity[] students = group.getEntries();
        for (int i = 0; i < students.length; i++) {
            Student stud = (Student) students[i];
            if (stud.equals(student)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGroupExist(String name) {
        Entity[] groups = Storage.getRoot().getEntries();
        for (int i = 0; i < groups.length; i++) {
            Group group = (Group) groups[i];
            if (group.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public Group getGroupByName(String name) {
        Entity[] groups = Storage.getRoot().getEntries();
        for (int i = 0; i < groups.length; i++) {
            Group group = (Group) groups[i];
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }


    public void addStudent(Group group, Student student) {
        counter++;
        student.setId(counter);
        group.addEntry(student);
        notifyObserversUpdate();
    }

    public void removeStudent(Student student) {
        Group group = getGroup(student.getGroup().getName());
        group.removeEntry(student);
        notifyObserversUpdate();
    }

    public void updateStudent(Student student) {
        Group group = student.getGroup();
        if (isGroupExist(group.getName())) {
            Entity[] students = group.getEntries();
            boolean isfounded = false;
            for (int i = 0; i < students.length; i++) {
                Student stud = (Student) students[i];
                if (stud.getId() == student.getId()) {
                    group.update(i, student);
                    isfounded = true;
                    break;
                }
            }
            if (!isfounded) {
                findStudent(student);
            }
        } else {
            findStudent(student);
        }
        notifyObserversUpdate();
    }

    public void addGroup(String groupName) {
        Storage.getRoot().addEntry(new Group(Storage.getRoot(), groupName));
        notifyObserversUpdate();
    }

    public void removeGroup(Group group) {
        Storage.getRoot().removeEntry(group);
        notifyObserversUpdate();
    }

    public void renameGroup(String oldName, String newName) {
        Group group = getGroup(oldName);
        group.setName(newName);
        notifyObserversUpdate();
    }

    private void findStudent(Student student) {
        Entity[] groups = Storage.getRoot().getEntries();
        for (int i = 0; i < groups.length; i++) {
            Group group = (Group) groups[i];
            getStudent(group, student);
        }
    }

    private void getStudent(Group group, Student student) {
        Entity[] students = group.getEntries();
        for (int i = 0; i < students.length; i++) {
            Student stud = (Student) students[i];
            if (stud.getId() == student.getId()) {
                group.removeEntry(student);
                String groupName = student.getGroup().getName();
                if (!isGroupExist(groupName)) {
                    Group newGroup = new Group(Storage.getRoot(), groupName);
                    Storage.getRoot().addEntry(newGroup);
                    newGroup.addEntry(student);
                } else {
                    group = getGroup(groupName);
                    group.addEntry(student);
                }
            }
        }
    }

    private Group getGroup(String name) {
        Entity[] groups = Storage.getRoot().getEntries();
        for (int i = 0; i < groups.length; i++) {
            Group group = (Group) groups[i];
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    private void notifyObserversUpdate() {
        for (int i = 0; i < observers.size(); i++) {
            EntityListener observer = (EntityListener) observers.get(i);
            observer.updateData();
        }
    }
}
