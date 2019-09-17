package studentsinfo3.dnd;

import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import studentsinfo3.ErrorMessageTextFinals;
import studentsinfo3.StudentEditor;
import studentsinfo3.StudentEditorInput;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;
import studentsinfo3.storage.Storage;

public class EditorAreaDropAdapter extends DropTargetAdapter {

    private final IWorkbenchWindow window;

    private final Logger logger = Logger.getLogger(EditorAreaDropAdapter.class.getName());

    public EditorAreaDropAdapter(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
    public void dragEnter(DropTargetEvent event) {
        event.detail = DND.DROP_COPY;
    }

    @Override
    public void drop(DropTargetEvent event) {
        final IWorkbenchPage page = window.getActivePage();
        if (event.data != "|") {
            String data = (String) event.data;
            StringTokenizer st = new StringTokenizer(data, "|");
            while (st.hasMoreTokens()) {
                int id = Integer.parseInt(st.nextToken());
                String name = st.nextToken();
                String group = st.nextToken();
                String address = st.nextToken();
                String city = st.nextToken();
                int result = Integer.parseInt(st.nextToken());
                boolean male = Boolean.parseBoolean(st.nextToken());
                String imageWay = st.nextToken();
                Student entry = new Student(name, new Group(Storage.getRoot(), group), address, city, result, male);
                entry.setId(id);
                if (!imageWay.equals("null")) {
                    entry.getPhotoData().setPhoto(imageWay);
                }
                StudentEditorInput input = new StudentEditorInput(entry);
                try {
                    page.openEditor(input, StudentEditor.ID).setFocus();
                } catch (PartInitException e) {
                    logger.log(Level.SEVERE, ErrorMessageTextFinals.STUDENT_CANNOT_BE_ADDED, e);
                }
            }
        }
    }
}