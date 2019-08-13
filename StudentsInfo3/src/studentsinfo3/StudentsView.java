package studentsinfo3;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import studentsinfo3.listeners.EntityListener;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Entity;
import studentsinfo3.storage.Storage;

public class StudentsView extends ViewPart implements EntityListener {
   
    public static final String ID = "studentsInfo3.view.students";

    private TreeViewer treeViewer;
    private IAdapterFactory adapterFactory = new AdapterFactory();

    public StudentsView() {

    }

    @Override
    public void createPartControl(Composite parent) {
        signUp();
        treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
        Platform.getAdapterManager().registerAdapters(adapterFactory, Entity.class);
        getSite().setSelectionProvider(treeViewer);
        treeViewer.setLabelProvider(new WorkbenchLabelProvider());
        treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
        treeViewer.setInput(Storage.getRoot());
        treeViewer.getTree().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                if (e.button == 3) {
   
                }
            }
            @Override
            public void mouseDoubleClick(MouseEvent e) {
              
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

    @Override
    public void updateData() {
        treeViewer.refresh();
    }

    private void signUp() {
        DataManager.getInstance().registerObserver(this);
    }

}
