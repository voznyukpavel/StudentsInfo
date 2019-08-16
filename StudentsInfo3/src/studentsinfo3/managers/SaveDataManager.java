package studentsinfo3.managers;

import java.util.ArrayList;

import studentsinfo3.StudentEditor;
import studentsinfo3.listeners.SaveListener;

public class SaveDataManager {
    private StudentEditor studentEditor;
    private static SaveDataManager instance;
    private ArrayList<SaveListener> observers;

    private SaveDataManager() {
        observers = new ArrayList<SaveListener>();
    }

    public static SaveDataManager getInstance() {
        if (instance == null) {
            instance = new SaveDataManager();
        }
        return instance;
    }

    public void startSaveAll() {
        notifyObserversIsDirtyAll();
    }

    public void startSave(StudentEditor studentEditor) {
        this.studentEditor = studentEditor;
        notifyObserversIsDirty();
    }

    public void registerObserver(SaveListener observer) {
        observers.add(observer);
    }

    public void unregisterObserver(SaveListener observer) {
        observers.remove(observer);
    }

    private void notifyObserversIsDirty() {
        for (int i = 0; i < observers.size(); i++) {
            if (observers.get(i) instanceof StudentEditor) {
                StudentEditor editor = (StudentEditor) observers.get(i);
                if (editor.equals(studentEditor)) {
                    SaveListener observer = (SaveListener) observers.get(i);
                    observer.saveStart();
                }
            }
        }
    }

    private void notifyObserversIsDirtyAll() {
        for (int i = 0; i < observers.size(); i++) {
            SaveListener observer = (SaveListener) observers.get(i);
            observer.saveStart();
        }
    }
}
