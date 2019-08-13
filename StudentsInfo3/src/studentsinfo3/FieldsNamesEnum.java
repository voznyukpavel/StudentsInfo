package studentsinfo3;

public enum FieldsNamesEnum {

    NAME      ("Name:"),
    GROUP_NAME("Group name:"),
    ADDRESS   ("Address:"),
    CITY      ("City:"),
    RESULT    ("Result:");

    private String name;

    FieldsNamesEnum(String name) {
        this.name = name;
    }

    public String getText() {
        return name;
    }
}
