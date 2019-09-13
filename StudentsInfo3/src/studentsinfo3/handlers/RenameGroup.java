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
import studentsinfo3.dialogs.AddGroupDialog;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Group;

public class RenameGroup implements IHandler {
	
	private IWorkbenchWindow window;
	
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			window = HandlerUtil.getActiveWorkbenchWindow(event);
			IStructuredSelection structSelection = (IStructuredSelection) selection;
			Object item = structSelection.getFirstElement();
			Group group = (Group) item;
			renameGroup(group);
		}
		return null;
	}
	
	private void renameGroup(Group group) {
		 AddGroupDialog dialog = new AddGroupDialog(window.getShell());
	        int code = dialog.open();
	        if (code == Window.OK) {
	            String newGroupName = dialog.getName();
	            if (DataManager.getInstance().isGroupExist(newGroupName)) {
	                MessageDialog.openError(window.getShell(), ErrorMessageTextFinals.GROUP_CANNOT_BE_ADDED,
	                        ErrorMessageTextFinals.GROUP_IS_ALLREADY_EXIST);
	            } else {
	                DataManager.getInstance().renameGroup(group.getName(), newGroupName);;
	            }
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
