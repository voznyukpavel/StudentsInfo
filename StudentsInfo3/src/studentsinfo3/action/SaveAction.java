package studentsinfo3.action;

import org.eclipse.jface.action.Action;
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
import studentsinfo3.events.DirtyEvent;
import studentsinfo3.events.UpdateEvent;
import studentsinfo3.listeners.EditorListener;
import studentsinfo3.managers.DataManager;
import studentsinfo3.managers.EditorIsDirtydManager;
import studentsinfo3.managers.SaveDataManager;

public class SaveAction extends Action implements EditorListener, ActionFactory.IWorkbenchAction {

    private IWorkbenchPage page;
    private StudentEditor studentEditor;

    public final static String ID = "studentsinfo3.save";

    public SaveAction(IWorkbenchWindow window) {
        setId(ID);
        setText("&Save");
        setToolTipText("Save");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.SAVE));
        page = window.getActivePage();
        page.addPartListener(new EditorChangedListener());
        setEnabled(false);
        signUp();
    }

    public void run() {
        SaveDataManager.getInstance().startSave(studentEditor);
    }

    @Override
    public void dispose() {
    }

    private void signUp() {
        EditorIsDirtydManager.getInstance().registerObserver(this);
    }

    @Override
    public void isDataDirty(DirtyEvent e) {
        setEnabled(e.isDirty());
        studentEditor = e.getImDirty();
    }

    @Override
    public void updateStudent(UpdateEvent e) {
        DataManager.getInstance().updateStudent(e.getStudent());
        setEnabled(false);
    }

    private class EditorChangedListener implements IPartListener2 {

        @Override
        public void partActivated(IWorkbenchPartReference partRef) {
            IWorkbenchPart part = partRef.getPart(true);
            if (part instanceof StudentEditor) {
                setEnabled(((StudentEditor) part).isDirty());
                studentEditor = ((StudentEditor) part).getStudentEditor();
            }
        }

        @Override
        public void partInputChanged(IWorkbenchPartReference partRef) {
            IWorkbenchPart part = partRef.getPart(true);
            if (part instanceof StudentEditor) {
                setEnabled(((StudentEditor) part).isDirty());
                studentEditor = ((StudentEditor) part).getStudentEditor();
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
