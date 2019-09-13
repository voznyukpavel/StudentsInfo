package studentsinfo3.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

import studentsinfo3.FieldsNamesEnum;
import studentsinfo3.model.Student;

public class TypeRadioHandler implements IHandler {

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
            IStructuredSelection strucSelection = (IStructuredSelection) selection;
            Student student = (Student) strucSelection.getFirstElement();
            if (event.getParameter(RadioState.PARAMETER_ID).contains(FieldsNamesEnum.BOY.getText()) && !student.isMale()) {
                student.setMale(true);
            } else if (event.getParameter(RadioState.PARAMETER_ID).contains(FieldsNamesEnum.GIRL.getText()) && student.isMale()) {
                student.setMale(false);
            }
        }
        return null;
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
