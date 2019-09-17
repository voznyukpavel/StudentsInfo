package studentsinfo3.events;

import java.util.EventObject;

import studentsinfo3.StudentEditor;

@SuppressWarnings("serial")
public class DirtyEvent extends EventObject{
    
    private boolean isDirty;
    private StudentEditor imDirty;
    
    public DirtyEvent(Object source,boolean isDirty, StudentEditor imDirty) {
        super(source);
        this.isDirty=isDirty;
        this.imDirty=imDirty;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public StudentEditor getImDirty() {
        return imDirty;
    }
}
