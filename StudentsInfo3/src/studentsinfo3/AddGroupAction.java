package studentsinfo3;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.managers.GroupDataManager;
import studentsinfo3.model.Group;
import studentsinfo3.storage.Storage;
//import studentsinfo3.model.GroupRoot;

public class AddGroupAction extends Action {
    private final IWorkbenchWindow window;
 //   private static GroupRoot groupRoot;
    
    public final static String ID = "studentsinfo3.addGroup";


    public AddGroupAction(IWorkbenchWindow window) {

        this.window = window;
        setId(ID);
        setText("&Add Group");
        setToolTipText("Add Group");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.ADD_GROUP.getWay()));
    }

    public void run() {
        AddGroupDialog dialog = new AddGroupDialog(window.getShell());
        int code = dialog.open();
        if (code == Window.OK) {           
           Group root = Storage.getRoot();
           Group group= new Group(root,dialog.getName());
           root.addEntry(group);
           GroupDataManager.getInstance().addGroup(group);
        }
    }

  //  public static void getGroupRoot(GroupRoot root) {
  //      groupRoot=root;
  //  }
}
