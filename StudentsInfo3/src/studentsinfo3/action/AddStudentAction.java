package studentsinfo3.action;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ErrorMessageTextFinals;
import studentsinfo3.ImageWayKeysEnum;
import studentsinfo3.dialogs.AddStudentDialog;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.EntityType;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class AddStudentAction extends AbstractAction implements ActionFactory.IWorkbenchAction {

    private final IWorkbenchWindow window;
    public final static String ID = "studentsinfo3.addStudent";

    public AddStudentAction(IWorkbenchWindow window) {
        this.window = window;
        this.enableIfType = EntityType.GROUP;
        setId(ID);
        setText("&Add Student");
        setToolTipText("Add student in this group.");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,
                ImageWayKeysEnum.ADD_STUDENT.getWay()));
        window.getSelectionService().addSelectionListener(this);
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
                MessageDialog.openError(window.getShell(), ErrorMessageTextFinals.STUDENT_CANNOT_BE_ADDED,
                        ErrorMessageTextFinals.STUDENT_IS_ALLREADY_EXIST);
            } else {
                DataManager.getInstance().addStudent(group, student);
            }
        }

    }

}
