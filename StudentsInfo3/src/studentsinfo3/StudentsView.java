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
import studentsinfo3.model.Session;
import studentsinfo3.model.Student;
import studentsinfo3.model.StudentsListener;

public class StudentsView extends ViewPart {
    public static final String ID = "studentsInfo3.view.students";

    private TreeViewer treeViewer;
    private Session session;
    private IAdapterFactory adapterFactory = new AdapterFactory();
    
    public StudentsView() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void createPartControl(Composite parent) {
        initializeSession(); // temporary tweak to build a fake model
        treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        Platform.getAdapterManager().registerAdapters(adapterFactory, AbstractStudent.class); 
        getSite().setSelectionProvider(treeViewer);
        treeViewer.setLabelProvider(new WorkbenchLabelProvider());
        treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
        treeViewer.setInput(session.getRoot());
        session.getRoot().addStudentsListener(new StudentsListener() {
            public void updateStudent(Group contacts, Student entry) {
                treeViewer.refresh();
            }
        });

    }

    private void initializeSession() {
        session = new Session();
        Group root = session.getRoot();
        Group friendsGroup = new Group(root, "1");
        root.addEntry(friendsGroup);
        friendsGroup.addEntry(new Student("Alize", friendsGroup, "aliz", "localhost", 5));
        friendsGroup.addEntry(new Student("Sydney", friendsGroup, "syd", "localhost", 4));
        Group otherGroup = new Group(root, "Other");
        root.addEntry(otherGroup);
        otherGroup.addEntry(new Student("Nadine", otherGroup, "nad", "localhost", 3));
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
