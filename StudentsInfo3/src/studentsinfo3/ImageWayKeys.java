package studentsinfo3;

public enum ImageWayKeys {

    GROUP("icons/students.gif"), 
    STUDENT("icons/student.gif"),
    ADD_STUDENT("icons/add_student.jpg"), 
    REMOVE_STUDENT("icons/remove_student.png"),
    SAVE("icons/save.jpg"), 
    OPEN("icons/open.png"),
    ADD_GROUP("icons/add_group.gif"),
    REMOVE_GROUP("icons/remove_group.gif");

    private String way;

    ImageWayKeys(String way) {
        this.way = way;
    }

    public String getWay() {
        return way;
    }
}
