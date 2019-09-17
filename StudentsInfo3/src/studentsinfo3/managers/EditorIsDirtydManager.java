package studentsinfo3.managers;

import java.util.ArrayList;

import studentsinfo3.StudentEditor;
import studentsinfo3.events.DirtyEvent;
import studentsinfo3.events.UpdateEvent;
import studentsinfo3.listeners.EditorListener;
import studentsinfo3.model.Student;


public class EditorIsDirtydManager {

    private static EditorIsDirtydManager instance;
    private ArrayList<EditorListener> observers;
    private boolean isDataDirty;
    private Student student;
    private StudentEditor dirtyStudentEditor;

    private EditorIsDirtydManager() {
        observers = new ArrayList<EditorListener>();
    }
    
    public static EditorIsDirtydManager getInstance() {
        if (instance == null) {
            instance = new EditorIsDirtydManager();
        }
        return instance;
    }

    public void dataIsDirty(boolean isDataDirty,StudentEditor studentEditor) {
        this.dirtyStudentEditor=studentEditor;
        this.isDataDirty = isDataDirty;
        notifyObserversIsDirty();
    }
    
    public void dataChenged(Student student) {
        this.student=student;
        notifyObserversUpdate();
    }

    public void registerObserver(EditorListener observer) {
        observers.add(observer);
    }

    public void notifyObserversIsDirty() {
        for (int i = 0; i < observers.size(); i++) {
            EditorListener observer = (EditorListener) observers.get(i);
            observer.isDataDirty(new DirtyEvent(this,isDataDirty,dirtyStudentEditor));
        }
    }
 
    public void notifyObserversUpdate() {
        for (int i = 0; i < observers.size(); i++) {
            EditorListener observer = (EditorListener) observers.get(i);
            observer.updateStudent(new UpdateEvent(this,student));
        }
    }
}
