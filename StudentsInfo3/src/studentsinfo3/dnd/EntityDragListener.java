package studentsinfo3.dnd;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;

import studentsinfo3.model.Entity;
import studentsinfo3.model.EntityType;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class EntityDragListener extends DragSourceAdapter {

	private final TreeViewer viewer;
	private EntityType entityTipe;

	public EntityDragListener(TreeViewer treeViewer) {
		this.viewer = treeViewer;
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = viewer.getStructuredSelection();
		Entity entity = (Entity) selection.getFirstElement();
		// Group entity1 = (Group) selection.getFirstElement();
		// if (GroupTransfer.getInstance().isSupportedType(event.dataType)) {
		// event.data=entity1;
		// System.out.println(event);
		// }
		// System.out.println(event);
		if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
			if (entity.getType().equals(entityTipe.STUDENT)) {
				Student student = (Student) entity;
				writeStudent(student, event);
			} else if (entity.getType().equals(entityTipe.GROUP)) {
				Group group = (Group) entity;
				Entity[] students = group.getEntries();
				for (int i = 0; i < group.getSize(); i++) {
					Student student = (Student) students[i];
					writeStudent(student, event);
				}
			}

		}
	}

	private void writeStudent(Student student, DragSourceEvent event) {
		String data=  student.getId() + "|" + student.getName() + "|" + student.getGroup().getName() + "|"
				+ student.getAddress() + "|" + student.getCity() + "|" + student.getResult() + "|"
				+ student.isMale() + "|" + student.getPhotoData().getImageWay() + "|";
		if (event.data == null) {
			event.data=data;
		} else {
			event.data+= data;
		}
	}

}