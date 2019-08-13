package studentsinfo3.action;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ErrorMessageTextFinals;
import studentsinfo3.ImageWayKeysEnum;
import studentsinfo3.StudentEditor;
import studentsinfo3.StudentEditorInput;
import studentsinfo3.model.EntityType;
import studentsinfo3.model.Student;

public class OpenStudentAction extends AbstractAction implements IWorkbenchAction {
    public final static String ID = "studentsinfo3.studentSelection";

    private final Logger logger = Logger.getLogger(OpenStudentAction.class.getName());

    private final IWorkbenchWindow window;

    public OpenStudentAction(IWorkbenchWindow window) {
        this.window = window;
        this.enableIfType = EntityType.STUDENT;
        setId(ID);
        setText("&Student");
        setToolTipText("Current student");
        setImageDescriptor(
                AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeysEnum.STUDENT.getWay()));
        window.getSelectionService().addSelectionListener(this);
    }

    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

    public void run() {
        Object item = selection.getFirstElement();
        Student entry = (Student) item;
        IWorkbenchPage page = window.getActivePage();
        StudentEditorInput input = new StudentEditorInput(entry);
        try {
            page.openEditor(input, StudentEditor.ID).setFocus();

        } catch (PartInitException e) {
            logger.log(Level.SEVERE, ErrorMessageTextFinals.STUDENT_CANNOT_BE_ADDED, e);
        }
    }
}
