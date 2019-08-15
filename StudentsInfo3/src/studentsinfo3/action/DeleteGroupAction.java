package studentsinfo3.action;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.Application;
import studentsinfo3.ImageWayKeys;
import studentsinfo3.StudentEditorInput;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Entity;
import studentsinfo3.model.EntityType;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class DeleteGroupAction extends AbstractAction implements ActionFactory.IWorkbenchAction {
    private final IWorkbenchWindow window;
    public final static String ID = "studentsinfo3.deleteGroup";

    public DeleteGroupAction(IWorkbenchWindow window) {
        this.window = window;
        this.enableIfType = EntityType.GROUP;
        setId(ID);
        setText("&Delete group");
        setToolTipText("Delete group");
        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID,
                ImageWayKeys.REMOVE_GROUP));
        window.getSelectionService().addSelectionListener(this);
    }

    @Override
    public void run() {
        Group group = (Group) selection.getFirstElement();
        if (MessageDialog.openConfirm(window.getShell(), "Delete Group",
                "Do you want to delete the group: " + group.getName() + " ?")) {
            IWorkbenchPage page = window.getActivePage();
            Entity[] students = group.getEntries();
            for (int i = 0; i < students.length; i++) {
                Student student = (Student) students[i];
                StudentEditorInput input = new StudentEditorInput(student);
                IEditorPart editorpart=page.findEditor(input);
                if (editorpart != null) {
                    page.closeEditor(editorpart, true);
                }
            }
            DataManager.getInstance().removeGroup(group);
        }

    }

    @Override
    public void dispose() {
        window.getSelectionService().removeSelectionListener(this);
    }

}
