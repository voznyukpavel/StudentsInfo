package studentsinfo3.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import studentsinfo3.model.Entity;
import studentsinfo3.model.EntityType;

public abstract class AbstractAction extends Action implements ISelectionListener {

    protected EntityType enableIfType;
    protected IStructuredSelection selection;

    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
       this.selection = (StructuredSelection) selection;
       setEnabled(isTypesEqual(selection));
    }
    
    private boolean isTypesEqual(ISelection selection) {
        return enableIfType == null || enableIfType == getSelectionType(selection);
    }
    
    private EntityType getSelectionType(ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structSelection = (IStructuredSelection) selection;
            Object selectedObject = structSelection.getFirstElement();
            if (selectedObject instanceof Entity) {
                Entity entity = (Entity) selectedObject;
                return entity.getType();
            }
        }
        return null;
    }

}
