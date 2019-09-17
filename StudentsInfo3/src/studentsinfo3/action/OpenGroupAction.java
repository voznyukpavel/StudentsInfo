package studentsinfo3.action;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ErrorMessageTextFinals;
import studentsinfo3.ImageWayKeys;
import studentsinfo3.StudentEditor;
import studentsinfo3.StudentEditorInput;
import studentsinfo3.model.Entity;
import studentsinfo3.model.EntityType;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class OpenGroupAction extends AbstractAction implements ActionFactory.IWorkbenchAction {

    public final static String ID = "studentsinfo3.open";
    private final Logger logger = Logger.getLogger(OpenGroupAction.class.getName());

    private final IWorkbenchWindow window;

    public OpenGroupAction(IWorkbenchWindow window) {
        this.window = window;
        this.enableIfType = EntityType.GROUP;
        setId(ID);
        setText("&Open");
        setToolTipText("Open");
        setImageDescriptor(
                AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.OPEN));
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

    public void run() {
        Object item = selection.getFirstElement();
        Group group = (Group) item;
        IWorkbenchPage page = window.getActivePage();
        Entity[] entitys = group.getEntries();
        addStudentsToEditor(page, entitys);
    }

    private void addStudentsToEditor(IWorkbenchPage page, Entity[] entitys) {
        for (int i = 0; i < entitys.length; i++) {
            Student student = (Student) entitys[i];
            StudentEditorInput input = new StudentEditorInput(student);
            try {
                page.openEditor(input, StudentEditor.ID).setFocus();
            } catch (PartInitException e) {
                logger.log(Level.SEVERE, ErrorMessageTextFinals.STUDENT_CANNOT_BE_ADDED, e);
            }
        }
    }
}
