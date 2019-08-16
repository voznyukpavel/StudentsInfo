package studentsinfo3.listeners;

import studentsinfo3.events.DirtyEvent;
import studentsinfo3.events.UpdateEvent;


public interface EditorListener {
    void isDataDirty(DirtyEvent e);
    void updateStudent(UpdateEvent e);
}
