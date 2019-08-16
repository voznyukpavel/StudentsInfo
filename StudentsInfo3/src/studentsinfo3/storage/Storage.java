package studentsinfo3.storage;

import studentsinfo3.model.Group;

public class Storage {
    
    private static Group rootGroup;
    
    private Storage() {

    }
    
    public static Group getRoot() {
        if (rootGroup == null)
            rootGroup = new Group(null, "RootGroup");
        return rootGroup;
    }
}
