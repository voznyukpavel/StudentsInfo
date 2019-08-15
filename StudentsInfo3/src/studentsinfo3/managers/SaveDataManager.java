package studentsinfo3.managers;

import java.util.ArrayList;

import studentsinfo3.listeners.SaveListener;

public class SaveDataManager {
    
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

    public void startSave() {
        notifyObserversIsDirty();
    }
    
    public void registerObserver(SaveListener observer) {
        observers.add(observer);
    }

    public void notifyObserversIsDirty() {
        for (int i = 0; i < observers.size(); i++) {
            SaveListener observer = (SaveListener) observers.get(i);
            observer.saveStart();
        }
    }
}
