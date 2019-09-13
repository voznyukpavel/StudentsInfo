package studentsinfo3.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import studentsinfo3.ErrorMessageTextFinals;
import studentsinfo3.dialogs.AddStudentDialog;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class AddHandler implements IHandler {

	private IWorkbenchWindow window;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			window = HandlerUtil.getActiveWorkbenchWindow(event);
			IStructuredSelection structSelection = (IStructuredSelection) selection;
			Object item = structSelection.getFirstElement();
			Group group = (Group) item;
			createStudent(group);
		}
		return null;
	}

	private void createStudent(Group group) {
		AddStudentDialog dialog = new AddStudentDialog(window.getShell());
		int code = dialog.open();
		if (code == Window.OK) {
			Student student = new Student(dialog.getName(), group, dialog.getAddress(), dialog.getCity(),
					dialog.getResult(),dialog.isMale());
			if (DataManager.getInstance().isStudentExist(group, student)) {
				MessageDialog.openError(window.getShell(), ErrorMessageTextFinals.STUDENT_CANNOT_BE_ADDED,
						ErrorMessageTextFinals.STUDENT_IS_ALLREADY_EXIST);
			} else {
				DataManager.getInstance().addStudent(group, student);
			}
		}
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
