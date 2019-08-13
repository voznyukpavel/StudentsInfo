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
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;


import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class GroupSelectionAction extends Action implements ISelectionListener, ActionFactory.IWorkbenchAction {
   
    public final static String ID = "studentsinfo3.open";
    private final Logger logger = Logger.getLogger(GroupSelectionAction.class.getName());
    
    private IStructuredSelection selection;
    private final IWorkbenchWindow window;

    public GroupSelectionAction(IWorkbenchWindow window) {
        this.window = window;
        setId(ID);
        setText("&Open");
        setToolTipText("Open");
        setImageDescriptor(
                AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeysEnum.OPEN.getWay()));
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
        if (incoming instanceof IStructuredSelection) {
            selection = (IStructuredSelection) incoming;
            Entity entity=(Entity)selection.getFirstElement();
            setEnabled(selection.size() == 1 &&entity.isGroupEntity());
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
        Group group = (Group) item;
        IWorkbenchPage page = window.getActivePage();
        Entity[] entitys = group.getEntries();
        for (int i = 0; i < entitys.length; i++) {
            Student student = (Student) entitys[i];
            StudentEditorInput input = new StudentEditorInput(student);
            try {
                page.openEditor(input, StudentEditor.ID).setFocus();
            } catch (PartInitException e) {
                logger.log(Level.SEVERE, ErrorMessageTextEnum.STUDENT_CANNOT_BE_ADDED.getMessage(), e);
            }
        }
    }
}
