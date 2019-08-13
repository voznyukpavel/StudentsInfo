package studentsinfo3;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Entity;
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
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,
                ImageWayKeysEnum.REMOVE_STUDENT.getWay()));
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;

            Entity entity = (Entity) selection.getFirstElement();
            setEnabled(selection.size() == 1 && !entity.isGroupEntity());
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
            IWorkbenchPage page = window.getActivePage();
            StudentEditorInput input = new StudentEditorInput(student);
            if (page.findEditor(input) != null) {
                page.closeEditor(page.findEditor(input), true);
            }
            DataManager.getInstance().removeStudent(student);
        }
    }
}
