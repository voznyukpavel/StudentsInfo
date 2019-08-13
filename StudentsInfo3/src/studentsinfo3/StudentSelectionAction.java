package studentsinfo3;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.model.Entity;
import studentsinfo3.model.Student;

public class StudentSelectionAction extends Action implements ISelectionListener, IWorkbenchAction {
    public final static String ID = "studentsinfo3.studentSelection";

    private final Logger logger = Logger.getLogger(StudentSelectionAction.class.getName());
    
    private final IWorkbenchWindow window;
    private IStructuredSelection selection;

    public StudentSelectionAction(IWorkbenchWindow window) {
        this.window = window;
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

    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;
            Entity entity=(Entity)selection.getFirstElement();
            setEnabled(selection.size() == 1 &&!entity.isGroupEntity());
        } else {
            setEnabled(false);
        }
    }

    public void run() {
        Object item = selection.getFirstElement();
        Student entry = (Student) item;
        IWorkbenchPage page = window.getActivePage();
        StudentEditorInput input = new StudentEditorInput(entry);
        try {
            page.openEditor(input, StudentEditor.ID).setFocus();
            
        } catch (PartInitException e) {
            logger.log(Level.SEVERE, ErrorMessageTextEnum.STUDENT_CANNOT_BE_ADDED.getMessage(), e);
        }
    }
}
