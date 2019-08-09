package studentsinfo3.managers;

import java.util.ArrayList;

import studentsinfo3.listeners.EntityListener;
import studentsinfo3.model.Group;
import studentsinfo3.storage.IDAO;
import studentsinfo3.storage.Storage;

public class GroupDataManager {
    private static GroupDataManager instance;
    private ArrayList<EntityListener> observers;
    private IDAO storage;

    private GroupDataManager() {
        observers = new ArrayList<EntityListener>();
        storage = Storage.getInstance();
    }

    public static GroupDataManager getInstance() {
        if (instance == null) {
            instance= new GroupDataManager();
        }
        return instance;
    }

    public void registerObserver(EntityListener observer) {
        observers.add(observer);
    }

    public void addGroup(Group group) {
        storage.addGroup(group);
        notifyObserversUpdate();
    }
    public void removeGroup(Group group) {
        storage.deleteGroup(group);
        notifyObserversUpdate();
    }

    public void notifyObserversUpdate() {
        for (int i = 0; i < observers.size(); i++) {
            EntityListener observer = (EntityListener) observers.get(i);
            observer.updateData();
        }
    }

}
