package studentsinfo3;

public enum ImageWayKeysEnum {
    
    ADD_GROUP       ("icons/add_group.gif"),
    ADD_STUDENT     ("icons/add_student.jpg"), 
    GROUP           ("icons/students.gif"), 
    OPEN            ("icons/open.png"),
    REMOVE_GROUP    ("icons/remove_group.gif"),
    REMOVE_STUDENT  ("icons/remove_student.png"),
    SAVE            ("icons/save.jpg"), 
    STUDENT         ("icons/student.gif"),
    STUDENT_INSTEAD ("icons/student_image.gif");
    
    private String way;

    ImageWayKeysEnum(String way) {
        this.way = way;
    }

    public String getWay() {
        return way;
    }
}
