package studentsinfo3.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import studentsinfo3.FieldsNamesEnum;
import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class AboutStudentHandler implements IHandler {

    private IWorkbenchWindow window;

    private static final String CONTAINS = "contains: ";
    private static final String DOESNT_HAVE_PHOTO = " Dosen`t have a photo";
    private static final String LAST_CHANGES = "Last data changes: ";
    private static final String ID = "ID:";
    private static final String PHOTO = "Photo: ";
    private static final String STUDENTS = " students";

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
        window = HandlerUtil.getActiveWorkbenchWindow(event);
        if (selection != null & selection instanceof IStructuredSelection) {
            IStructuredSelection structSelection = (IStructuredSelection) selection;
            Object selectedObject = structSelection.getFirstElement();
            if (selectedObject instanceof Entity) {
                Entity entity = (Entity) selectedObject;
                defineType(entity);
            }
        }
        return null;
    }

    private void defineType(Entity entity) {
        switch (entity.getType()) {
        case GROUP:
            Group group = (Group) entity;
            makeMessage(group);
            break;
        case STUDENT:
            Student student = (Student) entity;
            makeMessage(student);
            break;
        default:
            throw new RuntimeException();
        }
    }

    @SuppressWarnings("deprecation")
    private void makeMessage(Student student) {
        String lineSeparator = System.lineSeparator();
        String info = "";
        info += ID + " " + student.getId() + lineSeparator;
        info += FieldsNamesEnum.NAME.getText() + " " + student.getName() + lineSeparator;
        info += FieldsNamesEnum.GROUP_NAME.getText() + " " + student.getGroup().getName() + lineSeparator;
        info += FieldsNamesEnum.ADDRESS.getText() + " " + student.getAddress() + lineSeparator;
        info += FieldsNamesEnum.CITY.getText() + " " + student.getCity() + lineSeparator;
        info += FieldsNamesEnum.RESULT.getText() + " " + Integer.toString(student.getResult()) + lineSeparator;
        if (student.isMale()) {
            info += FieldsNamesEnum.SEX.getText() + " " + FieldsNamesEnum.BOY.getText() + lineSeparator;
        } else {
            info += FieldsNamesEnum.SEX.getText() + " " + FieldsNamesEnum.GIRL.getText() + lineSeparator;
        }
        if (student.getPhotoData().getPhoto() == null) {
            info += PHOTO + DOESNT_HAVE_PHOTO + lineSeparator;
        } else {
            info += PHOTO + student.getPhotoData().getImageWay() + lineSeparator;
        }
        info += lineSeparator + lineSeparator + lineSeparator;
        info += LAST_CHANGES + " " + student.getDate().toLocaleString();
        MessageDialog.openInformation(window.getShell(), student.getName(), info);
    }

    private void makeMessage(Group group) {
        String info = "";
        info += FieldsNamesEnum.GROUP_NAME.getText() + " " + group.getName() + System.lineSeparator();
        info += CONTAINS;
        info += group.getSize();
        info += STUDENTS;
        MessageDialog.openInformation(window.getShell(), group.getName(), info);
    }

    @Override
    public void addHandlerListener(IHandlerListener handlerListener) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isHandled() {
        return true;
    }

    @Override
    public void removeHandlerListener(IHandlerListener handlerListener) {
    }

}
