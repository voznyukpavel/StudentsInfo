package studentsinfo3;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class OpenAction extends Action{
    public final static String ID = "studentsinfo3.open";
    public OpenAction(IWorkbenchWindow window) {
 
        setId(ID);
        setText("&Open");
        setToolTipText("Open");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.OPEN.getWay()));
        
    }

    public void run() {

    }
}
