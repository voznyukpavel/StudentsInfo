package studentsinfo3.action;

import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ImageWayKeys;
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
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.ADD_STUDENT));
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

    public void run() {
        Object item = selection.getFirstElement();
        Group group = (Group) item;
        AddStudentDialog dialog = new AddStudentDialog(window.getShell(), group.getName());
        createDialog(dialog, group);
    }

    private void createDialog(AddStudentDialog dialog, Group group) {
        int code = dialog.open();
        if (code == Window.OK) {
            Student student = new Student(dialog.getName(), group, dialog.getAddress(), dialog.getCity(),
                    dialog.getResult(), dialog.isMale());
            if (dialog.getPhoto() != null) {
                student.setPhotoData(dialog.getPhoto(), dialog.getImageWay());
            }
            DataManager.getInstance().addStudent(group, student);
        }
    }
}
