package studentsinfo3;

public enum FieldsNames {

    NAME("Name:"),
    GROUP_NAME("Group name:"),
    ADDRESS("Address:"),
    CITY("City:"),
    RESULT("Result:");

    private String name;

    FieldsNames(String name) {
        this.name = name;
    }

    public String getText() {
        return name;
    }
}
