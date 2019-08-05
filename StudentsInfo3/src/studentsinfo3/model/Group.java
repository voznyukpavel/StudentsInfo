package studentsinfo3.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;

public class Group extends AbstractStudent{
    private List<AbstractStudent> entries;

    private Group parent;
    private String name;

    private ListenerList listeners;

    public Group(Group parent, String name) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Group getParent() {
        return parent;
    }

    public void rename(String newName) {
        this.name = newName;
        studentChanged(null);
    }

    public void addEntry(AbstractStudent entry) {
        if (entries == null)
            entries = new ArrayList<AbstractStudent>(5);
        entries.add(entry);
        studentChanged(null);
    }

    public void removeEntry(AbstractStudent entry) {
        if (entries != null) {
            entries.remove(entry);
            if (entries.isEmpty())
                entries = null;
        }
        studentChanged(null);
    }

    public AbstractStudent[] getEntries() {
        if (entries != null)
            return (AbstractStudent[]) entries.toArray(new AbstractStudent[entries.size()]);
        return new AbstractStudent[0];
    }

    public void addStudentsListener(StudentsListener listener) {
        if (parent != null)
            parent.addStudentsListener(listener);
        else {
            if (listeners == null)
                listeners = new ListenerList();
            listeners.add(listener);
        }
    }

    public void removeStudentsListener(StudentsListener listener) {
        if (parent != null)
            parent.removeStudentsListener(listener);
        else {
            if (listeners != null) {
                listeners.remove(listener);
                if (listeners.isEmpty())
                    listeners = null;
            }
        }
    }

    protected void studentChanged(Student entry) {
        if (parent != null)
            parent.studentChanged(entry);
        else {
            if (listeners == null)
                return;
            Object[] rls = listeners.getListeners();
            for (int i = 0; i < rls.length; i++) {
                StudentsListener listener = (StudentsListener) rls[i];
                listener.updateStudent(this, entry);
            }
        }
    }
}
