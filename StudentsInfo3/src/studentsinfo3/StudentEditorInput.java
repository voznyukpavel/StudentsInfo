package studentsinfo3;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import studentsinfo3.model.Student;

public class StudentEditorInput implements IEditorInput {

    private Student currentStudent;

    public StudentEditorInput(Student currentStudent) {
        super();
        Assert.isNotNull(currentStudent);
        this.currentStudent = currentStudent;
    }

    public Student getStudent() {
        return currentStudent;
    }
    
    
    @Override
    public IPersistableElement getPersistable() {
        return null;
    }

    @Override
    public String getToolTipText() {
        return currentStudent.getName();
    }

    public boolean equals(Object obj) {
        if (super.equals(obj))
            return true;
        if (!(obj instanceof StudentEditorInput))
            return false;
        StudentEditorInput other = (StudentEditorInput) obj;
        return this.currentStudent.equals(other.currentStudent);
    }

    public int hashCode() {
        return currentStudent.hashCode();
    }

    @Override
    public String getName() {
        return null;
    }
    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return null;
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return null;
    }
}
