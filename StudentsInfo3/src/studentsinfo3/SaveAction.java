package studentsinfo3;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;



public class SaveAction extends Action{
    public final static String ID = "studentsinfo3.save";
    public SaveAction(IWorkbenchWindow window) {
 
        setId(ID);
        setText("&Save");
        setToolTipText("Save");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.SAVE.getWay()));
        
    }

    public void run() {

    }
}
