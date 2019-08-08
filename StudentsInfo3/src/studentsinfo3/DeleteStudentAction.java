package studentsinfo3;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class DeleteStudentAction extends Action implements ISelectionListener, ActionFactory.IWorkbenchAction {
    private final IWorkbenchWindow window;
    public final static String ID = "studentsinfo3.deleteStudent";
    private IStructuredSelection selection;

    public DeleteStudentAction(IWorkbenchWindow window) {
        this.window = window;
        setId(ID);
        setText("&Delete Student");
        setToolTipText("Delete student from this group.");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.REMOVE_STUDENT.getWay()));
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;
            setEnabled(selection.size() == 1 && selection.getFirstElement() instanceof Student);
        } else {
            setEnabled(false);
        }
    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }
    
    public void run() {
        Object item = selection.getFirstElement();
        Student student = (Student) item;
        if (MessageDialog.openConfirm(window.getShell(), "Delete Student",
                "Do you want to delete the student: " + student.getName() + " ?")) {
            Group group = student.getGroup();
            group.removeEntry(student);
        }
    }
}
