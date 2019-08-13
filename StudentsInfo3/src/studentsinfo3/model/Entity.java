package studentsinfo3.model;


public abstract class Entity {
    
    private boolean groupEntity=false;
    
    public abstract String getName();  
    public abstract Group getParent() ;
    
    
    public boolean isGroupEntity() {
        return groupEntity;
    }
    
    public void setGroupEntity(boolean groupEntity) {
        this.groupEntity = groupEntity;
    }
}
