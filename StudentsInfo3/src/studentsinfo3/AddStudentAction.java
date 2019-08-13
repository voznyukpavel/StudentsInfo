package studentsinfo3;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class AddStudentAction extends Action implements ISelectionListener, ActionFactory.IWorkbenchAction {

    private IStructuredSelection selection;
    private final IWorkbenchWindow window;
    public final static String ID = "studentsinfo3.addStudent";

    public AddStudentAction(IWorkbenchWindow window) {
        this.window = window;
        setId(ID);
        setText("&Add Student");
        setToolTipText("Add student in this group.");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,
                ImageWayKeysEnum.ADD_STUDENT.getWay()));
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;
            Entity entity = (Entity) selection.getFirstElement();
            setEnabled(selection.size() == 1 && entity.isGroupEntity());
        } else {
            setEnabled(false);
        }
    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);

    }

    public void run() {
        AddStudentDialog dialog = new AddStudentDialog(window.getShell());
        int code = dialog.open();
        if (code == Window.OK) {
            Object item = selection.getFirstElement();
            Group group = (Group) item;
            Student student = new Student(dialog.getName(), group, dialog.getAddress(), dialog.getCity(),
                    dialog.getResult());
            if (DataManager.getInstance().isStudentExist(group, student)) {
                MessageDialog.openError(window.getShell(), ErrorMessageTextEnum.STUDENT_CANNOT_BE_ADDED.getMessage(),
                        ErrorMessageTextEnum.STUDENT_IS_ALLREADY_EXIST.getMessage());
            } else {
                DataManager.getInstance().addStudent(group, student);
            }
        }

    }

}
