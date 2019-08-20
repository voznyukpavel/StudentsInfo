package studentsinfo3;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import studentsinfo3.action.OpenStudentAction;
import studentsinfo3.listeners.EntityListener;
import studentsinfo3.managers.DataManager;
import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;
import studentsinfo3.storage.Storage;

public class StudentsView extends ViewPart implements EntityListener {

	private final Logger logger = Logger.getLogger(OpenStudentAction.class.getName());

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
					//createMenu();
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if (e.button == 1) {
					IWorkbench workbench = PlatformUI.getWorkbench();
					IWorkbenchWindow activeWindow = workbench.getActiveWorkbenchWindow();
					IWorkbenchPage activePage = activeWindow.getActivePage();

					ISelection selection = treeViewer.getSelection();
					IStructuredSelection structSelection = (IStructuredSelection) selection;
					Object selectedObject = structSelection.getFirstElement();
					if (selectedObject instanceof Student) {
						openStudentInEditor(selectedObject, activePage);
					} else if (selectedObject instanceof Group) {
						openGroupInEditor(selectedObject, activePage);
					}
				}
			}
			
		});
		createMenu();
	}

	private void createMenu() {
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(treeViewer.getTree());
	
		treeViewer.getTree().setMenu(menu);
	
		getSite().registerContextMenu(menuManager, treeViewer);
	//	MenuItem[]menuItem= menu.getItems();
	//	menuItem[0].getText();
	//	menuItem[0].setEnabled(false);
		getSite().setSelectionProvider(treeViewer);

	}

	private void openGroupInEditor(Object selectedObject, IWorkbenchPage activePage) {
		Group group = (Group) selectedObject;
		Entity[] entitys = group.getEntries();
		for (int i = 0; i < entitys.length; i++) {
			selectedObject = entitys[i];
			openStudentInEditor(selectedObject, activePage);
		}
	}

	private void openStudentInEditor(Object selectedObject, IWorkbenchPage activePage) {
		Student student = (Student) selectedObject;
		StudentEditorInput sei = new StudentEditorInput(student);
		try {
			activePage.openEditor(sei, StudentEditor.ID).setFocus();
		} catch (PartInitException ex) {
			logger.log(Level.SEVERE, ErrorMessageTextFinals.STUDENT_CANNOT_BE_ADDED, ex);
		}
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
