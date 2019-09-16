package studentsinfo3.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ImageWayKeys;
import studentsinfo3.dialogs.AddGroupDialog;
import studentsinfo3.managers.DataManager;

public class AddGroupAction extends Action {

    private final IWorkbenchWindow window;

    public final static String ID = "studentsinfo3.addGroup";

    public AddGroupAction(IWorkbenchWindow window) {
        this.window = window;
        setId(ID);
        setText("&Add Group");
        setToolTipText("Add Group");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.ADD_GROUP));
    }

    public void run() {
        AddGroupDialog dialog = new AddGroupDialog(window.getShell());
        createDialog(dialog);
    }

    private void createDialog(AddGroupDialog dialog) {
        int code = dialog.open();
        if (code == Window.OK) {
            String groupName = dialog.getName();
            DataManager.getInstance().addGroup(groupName);
        }
    }
}
