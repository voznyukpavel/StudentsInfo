package studentsinfo3.action;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ImageWayKeys;
import studentsinfo3.StudentEditorInput;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.EntityType;
import studentsinfo3.model.Student;

public class DeleteStudentAction extends AbstractAction implements ActionFactory.IWorkbenchAction {
    private final IWorkbenchWindow window;
    public final static String ID = "studentsinfo3.deleteStudent";

    public DeleteStudentAction(IWorkbenchWindow window) {
        this.window = window;
        this.enableIfType = EntityType.STUDENT;
        setId(ID);
        setText("&Delete Student");
        setToolTipText("Delete student from this group.");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,
                ImageWayKeys.REMOVE_STUDENT));
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

    public void run() {
        Student student = (Student) selection.getFirstElement();
        if (MessageDialog.openConfirm(window.getShell(), "Delete Student",
                "Do you want to delete the student: " + student.getName() + " ?")) {
            closeStudentsEditor(student);
            DataManager.getInstance().removeStudent(student);
        }
    }

    private void closeStudentsEditor(Student student) {
        IWorkbenchPage page = window.getActivePage();
        StudentEditorInput input = new StudentEditorInput(student);
        IEditorPart editorpart=page.findEditor(input);
        if (editorpart!= null) {
            page.closeEditor(editorpart, true);
        }
    }
}
