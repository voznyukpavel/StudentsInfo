package studentsinfo3.model;

public abstract class Entity {

    protected EntityType type;
    protected String name;

    public abstract Group getParent();

    public EntityType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
