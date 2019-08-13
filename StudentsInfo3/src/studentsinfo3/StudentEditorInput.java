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

    @Override
    public <T> T getAdapter(Class<T> adapter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean exists() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        // TODO Auto-generated method stub
        return null;
    }

    public Student getStudent() {
        return currentStudent;
    }
    
    
    @Override
    public IPersistableElement getPersistable() {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return null;
    }
}
