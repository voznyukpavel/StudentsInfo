package studentsinfo3.model;

public class GroupRoot {

    private Group rootGroup;
    
    public Group getRoot() {
        if (rootGroup == null)
            rootGroup = new Group(null, "RootGroup");
        return rootGroup;
    }
}