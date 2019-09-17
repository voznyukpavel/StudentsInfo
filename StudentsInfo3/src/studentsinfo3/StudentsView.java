package studentsinfo3;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import studentsinfo3.dnd.EntityDragListener;
import studentsinfo3.listeners.EntityListener;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;
import studentsinfo3.storage.Storage;

public class StudentsView extends ViewPart implements EntityListener {
    public static final String ID = "studentsInfo3.view.students";

    private final Logger logger = Logger.getLogger(StudentsView.class.getName());

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
        initDragSupport();
        treeViewer.setComparator(new ViewerComparator());
        treeViewer.getTree().addMouseListener(new TreeMouseAdapter());
        createMenu();
    }

    private void initDragSupport() {
        int operations = DND.DROP_COPY | DND.DROP_MOVE;
        Transfer[] transferTypes = new Transfer[] { TextTransfer.getInstance() };
        treeViewer.addDragSupport(operations, transferTypes, new EntityDragListener(treeViewer));
        // treeViewer.addDropSupport(operations, transferTypes, new EntityDropListener(treeViewer));
    }

    private void createMenu() {
        MenuManager menuManager = new MenuManager();
        Menu menu = menuManager.createContextMenu(treeViewer.getTree());
        treeViewer.getTree().setMenu(menu);
        getSite().registerContextMenu(menuManager, treeViewer);
        getSite().setSelectionProvider(treeViewer);
        menu.addMenuListener(new PopUpMenuAdapter(menu));
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
        treeViewer.getControl().setFocus();
    }

    private void signUp() {
        DataManager.getInstance().registerObserver(this);
    }

    private class TreeMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
            if (e.button == 1) {
                IWorkbenchPage activePage = getWorkbenchPage();
                Object selectedObject = getSelection();
                if (selectedObject instanceof Student) {
                    openStudentInEditor(selectedObject, activePage);
                } else if (selectedObject instanceof Group) {
                    openGroupInEditor(selectedObject, activePage);
                }
            }
        }
        
        @Override
        public void mouseDown(MouseEvent e) {
            IWorkbenchPage activePage = getWorkbenchPage();
            Object selectedObject = getSelection();
            if (selectedObject instanceof Student) {
                Student student = (Student) selectedObject;
                StudentEditorInput sei = new StudentEditorInput(student);
                if(activePage.findEditor(sei)!=null) {
                    openEditor(activePage, sei);
                }
            }
        }

        private void openEditor(IWorkbenchPage activePage, StudentEditorInput sei) {
            try {
                activePage.openEditor(sei, StudentEditor.ID).setFocus();
            } catch (PartInitException ex) {
                logger.log(Level.SEVERE, ErrorMessageTextFinals.STUDENT_CANNOT_BE_ADDED, ex);
            }
        }

        private Object getSelection() {
            ISelection selection = treeViewer.getSelection();
            IStructuredSelection structSelection = (IStructuredSelection) selection;
            Object selectedObject = structSelection.getFirstElement();
            return selectedObject;
        }

        private IWorkbenchPage getWorkbenchPage() {
            IWorkbench workbench = PlatformUI.getWorkbench();
            IWorkbenchWindow activeWindow = workbench.getActiveWorkbenchWindow();
            IWorkbenchPage activePage = activeWindow.getActivePage();
            return activePage;
        }

        private void openGroupInEditor(Object selectedObject, IWorkbenchPage activePage) {
            Group group = (Group) selectedObject;
            Entity[] entities = group.getEntries();
            for (int i = 0; i < entities.length; i++) {
                selectedObject = entities[i];
                openStudentInEditor(selectedObject, activePage);
            }
        }

        private void openStudentInEditor(Object selectedObject, IWorkbenchPage activePage) {
            Student student = (Student) selectedObject;
            StudentEditorInput sei = new StudentEditorInput(student);
            openEditor(activePage, sei);
        }
    }

    private class PopUpMenuAdapter extends MenuAdapter {

        private Menu menu;

        public PopUpMenuAdapter(Menu menu) {
            this.menu = menu;
        }

        @Override
        public void menuShown(MenuEvent e) {
            ISelection selection = treeViewer.getSelection();
            IStructuredSelection structSelection = (IStructuredSelection) selection;
            Object selectedObject = structSelection.getFirstElement();
            MenuItem boy = menu.getItem(4);
            MenuItem girl = menu.getItem(5);
            if (selectedObject instanceof Student) {
                Student student = (Student) selectedObject;
                if (student.isMale()) {
                    boy.setSelection(true);
                    girl.setSelection(false);
                } else {
                    boy.setSelection(false);
                    girl.setSelection(true);
                }
            } else {
                boy.setSelection(false);
                girl.setSelection(false);
            }
        }
    }
}
