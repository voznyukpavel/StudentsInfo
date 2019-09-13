package studentsinfo3.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import studentsinfo3.StudentEditorInput;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Student;

public class RemoveHandler implements IHandler {
	private IWorkbenchWindow window;

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		window = HandlerUtil.getActiveWorkbenchWindow(event);
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			Student student = (Student) strucSelection.getFirstElement();
			deleteStudent(student);
		}
		return null;
	}

	private void deleteStudent(Student student) {
		if (MessageDialog.openConfirm(window.getShell(), "Delete Student",
				"Do you want to delete the student: " + student.getName() + " ?")) {
			IWorkbenchPage page = window.getActivePage();
			StudentEditorInput input = new StudentEditorInput(student);
			IEditorPart editorpart = page.findEditor(input);
			if (editorpart != null) {
				page.closeEditor(editorpart, true);
			}
			DataManager.getInstance().removeStudent(student);
		}
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
