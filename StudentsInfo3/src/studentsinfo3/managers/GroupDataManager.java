package studentsinfo3.managers;

import java.util.ArrayList;

import studentsinfo3.listeners.GroupListener;
import studentsinfo3.model.Group;
import studentsinfo3.storage.IDAO;
import studentsinfo3.storage.Storage;

public class GroupDataManager {
    private static GroupDataManager instance;
    private ArrayList<GroupListener> observers;
    private IDAO storage;

    private GroupDataManager() {
        observers = new ArrayList<GroupListener>();
        storage = Storage.getInstance();
    }

    public static GroupDataManager getInstance() {
        if (instance == null) {
            instance= new GroupDataManager();
        }
        return instance;
    }

    public void registerObserver(GroupListener observer) {
        observers.add(observer);
    }

    public void addGroup(Group group) {
        storage.addGroup(group);
        notifyObserversUpdate();
    }

    public void notifyObserversUpdate() {
        for (int i = 0; i < observers.size(); i++) {
            GroupListener observer = (GroupListener) observers.get(i);
            observer.updateData();
        }
    }

}
