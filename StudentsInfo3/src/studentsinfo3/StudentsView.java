package studentsinfo3;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import studentsinfo3.model.AbstractStudent;
import studentsinfo3.model.Group;
import studentsinfo3.model.GroupRoot;
import studentsinfo3.model.Student;
import studentsinfo3.model.StudentsListener;

public class StudentsView extends ViewPart {
    public static final String ID = "studentsInfo3.view.students";

    private TreeViewer treeViewer;
    private GroupRoot groupRoor;

    public GroupRoot getGroupRoor() {
        return groupRoor;
    }

    private IAdapterFactory adapterFactory = new AdapterFactory();
    
    public StudentsView() {

    }

    @Override
    public void createPartControl(Composite parent) {
        groupRoor = new GroupRoot();
        AddGroupAction.getGroupRoot(groupRoor);
        treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        Platform.getAdapterManager().registerAdapters(adapterFactory, AbstractStudent.class); 
        getSite().setSelectionProvider(treeViewer);
        treeViewer.setLabelProvider(new WorkbenchLabelProvider());
        treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
        treeViewer.setInput(groupRoor.getRoot());
        groupRoor.getRoot().addStudentsListener(new StudentsListener() {
            public void updateStudent(Group contacts, Student entry) {
                treeViewer.refresh();
            }
        });
    }

    public void dispose() {
         Platform.getAdapterManager().unregisterAdapters(adapterFactory);
        super.dispose();
    }

    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }

}
