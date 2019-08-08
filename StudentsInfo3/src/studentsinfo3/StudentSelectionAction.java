package studentsinfo3;

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

import studentsinfo3.model.Student;

public class StudentSelectionAction extends Action implements ISelectionListener, IWorkbenchAction {

    private final IWorkbenchWindow window;
    private IStructuredSelection selection;
    public final static String ID = "studentsinfo3.studentSelection";



    public StudentSelectionAction(IWorkbenchWindow window) {
        this.window = window;
        setId(ID);
        setText("&Student");
        // System.out.println("StudentSelectionConstr");
        setToolTipText("Current student");
        setImageDescriptor(
                AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.STUDENT.getWay()));
        window.getSelectionService().addSelectionListener(this);
    }

    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;
            boolean isSelectionValid = selection.size() == 1 && selection.getFirstElement() instanceof Student;
            if (isSelectionValid) {
                run();
            }
        } else {
      //      setEnabled(false);
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
        }
    }
}
