package studentsinfo3;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import studentsinfo3.managers.DataManager;


public class AddGroupAction extends Action {

    private final IWorkbenchWindow window;

    public final static String ID = "studentsinfo3.addGroup";

    public AddGroupAction(IWorkbenchWindow window) {
        this.window = window;
        setId(ID);
        setText("&Add Group");
        setToolTipText("Add Group");
        setImageDescriptor(
                AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeysEnum.ADD_GROUP.getWay()));
    }

    public void run() {
        AddGroupDialog dialog = new AddGroupDialog(window.getShell());
        int code = dialog.open();
        if (code == Window.OK) {
            if (DataManager.getInstance().isGroupExist(dialog.getName())) {
                MessageDialog.openError(window.getShell(), ErrorMessageTextEnum.GROUP_CANNOT_BE_ADDED.getMessage(),
                        ErrorMessageTextEnum.GROUP_IS_ALLREADY_EXIST.getMessage());
            } else {
                DataManager.getInstance().addGroup(dialog.getName());
            }
        }
    }
}
