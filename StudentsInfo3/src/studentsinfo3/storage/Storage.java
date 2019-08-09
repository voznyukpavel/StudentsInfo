package studentsinfo3.storage;

import java.util.ArrayList;
import java.util.List;

import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;

public class Storage implements IDAO {

    private final List<Entity> storage = new ArrayList<>();
    private static Storage instance;
    private static Group rootGroup;
    
    public static Group getRoot() {
        if (rootGroup == null)
            rootGroup = new Group(null, "RootGroup");
        return rootGroup;
    }

    private Storage() {

    }

    @Override
    public void addGroup(Entity group) {
        storage.add(group);

    }

    @Override
    public void deleteGroup(Entity group) {
        storage.remove(group);
    }
   
    @Override
    public List getData() {
        return storage;
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance= new Storage();
        }
        return instance;
    }
    
   // public Group getRoot() {
 //       if(storage.isEmpty()||storage.get(0)==null) {
  //          storage.add(0, new Group("root"));
            
   //     }
   //     return storage.get(0);
   // }

}
