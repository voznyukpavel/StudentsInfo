package studentsinfo3.dnd;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;

import studentsinfo3.model.Entity;
import studentsinfo3.model.EntityType;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class EntityDragListener implements DragSourceListener {

	private final TreeViewer viewer;
	private EntityType entityTipe;

	public EntityDragListener(TreeViewer treeViewer) {
		this.viewer = treeViewer;
	}

	@Override
	public void dragFinished(DragSourceEvent event) {

		// if(event.data instanceof Student) {
		// Student student=(Student)event.data;
		// System.out.println(student.toString());
		// }else if(event.data instanceof Group) {
		// Group group=(Group)event.data;
		// System.out.println(group.toString());
		// }
		// System.out.println(event.data.toString());
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = viewer.getStructuredSelection();
		Student entity = (Student) selection.getFirstElement();

		if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = entity.getId() + "|" + entity.getName() + "|" + entity.getGroup().getName() + "|"
					+ entity.getAddress() + "|" + entity.getCity() + "|" + entity.getResult() + "|"
					+ entity.getPhotoData().getImageWay();
		}

	}

	@Override
	public void dragStart(DragSourceEvent event) {
		System.out.println(event);
	}

}