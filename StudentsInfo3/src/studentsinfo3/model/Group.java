package studentsinfo3.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;

public class Group extends AbstractStudent{
  
    private List<AbstractStudent> entries;

    private Group parent;
    private String name;
    private ListenerList studentsListeners;

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
            if (studentsListeners == null)
                studentsListeners = new ListenerList();
            studentsListeners.add(listener);
        }
    }

    public void removeStudentsListener(StudentsListener listener) {
        if (parent != null)
            parent.removeStudentsListener(listener);
        else {
            if (studentsListeners != null) {
                studentsListeners.remove(listener);
                if (studentsListeners.isEmpty())
                    studentsListeners = null;
            }
        }
    }

    private void studentChanged(Student entry) {
        if (parent != null)
            parent.studentChanged(entry);
        else {
            if (studentsListeners == null)
                return;
            Object[] rls = studentsListeners.getListeners();
            for (int i = 0; i < rls.length; i++) {
                StudentsListener listener = (StudentsListener) rls[i];
             
                listener.updateStudent();
            }
        }
    }
    
    @Override
    public int hashCode() {
      final int prime = 13;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Group other = (Group) obj;
      if (!name.equals(other.name))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return name ;
    }
}
