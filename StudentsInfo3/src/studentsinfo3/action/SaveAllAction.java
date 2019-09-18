package studentsinfo3.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ImageWayKeys;
import studentsinfo3.events.DirtyEvent;
import studentsinfo3.events.UpdateEvent;
import studentsinfo3.listeners.EditorListener;
import studentsinfo3.managers.DataManager;
import studentsinfo3.managers.EditorIsDirtydManager;
import studentsinfo3.managers.SaveDataManager;

public class SaveAllAction extends Action implements EditorListener, ActionFactory.IWorkbenchAction {

    public final static String ID = "studentsinfo3.saveAll";

    public SaveAllAction(IWorkbenchWindow window) {
        setId(ID);
        setText("&Save All");
        setToolTipText("Save All");
        setImageDescriptor(
                AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.SAVE_ALL));
        IWorkbenchPage page = window.getActivePage();
        page.addPartListener(new EditorChangedListener());
        setEnabled(false);
        signUp();
    }

    public void run() { 
       SaveDataManager.getInstance().startSaveAll();
    }
    
    @Override
    public void isDataDirty(DirtyEvent e) {
        setEnabled(e.isDirty());
    }

    @Override
    public void updateStudent(UpdateEvent e) {
        DataManager.getInstance().updateStudent(e.getStudent());
        setEnabled(false);
    }

    @Override
    public void dispose() {
    }

    private void signUp() {
        EditorIsDirtydManager.getInstance().registerObserver(this);
    }
    
    private class EditorChangedListener implements IPartListener2 {

        @Override
        public void partActivated(IWorkbenchPartReference partRef) {
        }
        
        @Override
        public void partInputChanged(IWorkbenchPartReference partRef) {
        }
        
        @Override
        public void partClosed(IWorkbenchPartReference partRef) {
            setEnabled(false);
        }

        @Override
        public void partDeactivated(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partBroughtToTop(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partOpened(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partHidden(IWorkbenchPartReference partRef) {
        }

        @Override
        public void partVisible(IWorkbenchPartReference partRef) {
        }
    }
}

