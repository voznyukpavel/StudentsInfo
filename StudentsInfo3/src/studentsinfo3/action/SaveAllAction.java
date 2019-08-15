package studentsinfo3.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ImageWayKeys;
import studentsinfo3.StudentEditor;

import studentsinfo3.listeners.EditorListener;
import studentsinfo3.managers.DataManager;
import studentsinfo3.managers.EditorIsDirtydManager;
import studentsinfo3.managers.SaveDataManager;
import studentsinfo3.model.Student;

public class SaveAllAction extends Action implements EditorListener, ActionFactory.IWorkbenchAction {

    protected IStructuredSelection selection;

    private IWorkbenchPage page;

    public final static String ID = "studentsinfo3.save";

    public SaveAllAction(IWorkbenchWindow window) {
        setId(ID);
        setText("&Save");
        setToolTipText("Save");
        setImageDescriptor(
                AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.SAVE_ALL));
        page = window.getActivePage();
        page.addPartListener(new EditorChangedListener());
        setEnabled(false);
        signUp();
    }

    public void run() { 
        SaveDataManager.getInstance().startSave();
        setEnabled(false);
    }

    @Override
    public void dispose() {
    }

    private void signUp() {
        EditorIsDirtydManager.getInstance().registerObserver(this);
    }

    @Override
    public void isDataDirty(boolean isDirty) {
        setEnabled(isDirty);
    }
    
    @Override
    public void updateStudent(Student student) {
        DataManager.getInstance().updateStudent(student);
    }

    private class EditorChangedListener implements IPartListener2 {

        @Override
        public void partActivated(IWorkbenchPartReference partRef) {
            IWorkbenchPart part = partRef.getPart(true);
            if (part instanceof StudentEditor) {
                setEnabled(((StudentEditor) part).isDirty());
            }
        }
        
        @Override
        public void partInputChanged(IWorkbenchPartReference partRef) {
            IWorkbenchPart part = partRef.getPart(true);
            if (part instanceof StudentEditor) {
                setEnabled(((StudentEditor) part).isDirty());
            }

        }
        @Override
        public void partClosed(IWorkbenchPartReference partRef) {
            setEnabled(false);
        }

        @Override
        public void partDeactivated(IWorkbenchPartReference partRef) {
            setEnabled(false);
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

