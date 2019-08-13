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

import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;

public class DeleteGroupAction extends Action implements ISelectionListener, ActionFactory.IWorkbenchAction {
    private final IWorkbenchWindow window;
    public final static String ID = "studentsinfo3.deleteGroup";
    private IStructuredSelection selection;

    public DeleteGroupAction(IWorkbenchWindow window) {
        this.window = window;
     
        setId(ID);

        setText("&Delete group");
        setToolTipText("Delete group");
        setImageDescriptor(
                AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeysEnum.REMOVE_GROUP.getWay()));
        window.getSelectionService().addSelectionListener(this);
    }

    public void run() {
        Object item = selection.getFirstElement();
        Group group = (Group) item;
        if (MessageDialog.openConfirm(window.getShell(), "Delete Group",
                "Do you want to delete the group: " + group.getName() + " ?")) {
            DataManager.getInstance().removeGroup(group);
        }
    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
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
}
