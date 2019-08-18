package studentsinfo3;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.listeners.SaveListener;
import studentsinfo3.managers.EditorIsDirtydManager;
import studentsinfo3.managers.SaveDataManager;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;
import studentsinfo3.storage.Storage;
import studentsinfo3.util.FieldsChecker;
import studentsinfo3.util.ResizedImage;

public class StudentEditor extends EditorPart implements SaveListener {
	public static final String ID = "StudentsInfo3.editors.studentseditor";

	private static final int PHOTO_SIZE = 120;

	private boolean isDirty;

	private Text nameText;
	private Text groupText;
	private Text addressText;
	private Text cityText;
	private Text resultText;

	private String name;
	private String groupName;
	private String address;
	private String city;

	private Canvas photoCanvas;
	private Image photoImage;
	private Composite parent;

	private Student currentStudent;
	private StudentEditor studentEditor;

	public StudentEditor() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	public StudentEditor getStudentEditor() {
		return studentEditor;
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty() {
		if (!currentStudent.getName().equals(nameText.getText())) {
			isDirty = true;
		} else if (!currentStudent.getAddress().equals(addressText.getText())) {
			isDirty = true;
		} else if (!currentStudent.getCity().equals(cityText.getText())) {
			isDirty = true;
		} else if (!currentStudent.getGroup().getName().equals(groupText.getText())) {
			isDirty = true;
		} else if (!((Integer) currentStudent.getResult()).toString().equals(resultText.getText())) {
			isDirty = true;
		} else {
			isDirty = false;
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		studentEditor = this;
		this.parent = parent;
		signUp();
		currentStudent = getStudent();
		setPartName(currentStudent.getName() + " " + currentStudent.getGroup().getName());

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		parent.setLayout(gridLayout);

		Label name = new Label(parent, SWT.NULL);
		name.setText(FieldsNamesEnum.NAME.getText());

		TextModifyListener textModifyListener = new TextModifyListener();
		nameText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData textGridData = new GridData();
		textGridData.widthHint = 150;
		nameText.setLayoutData(textGridData);
		nameText.setText(currentStudent.getName());
		nameText.addModifyListener(textModifyListener);

		photoCanvas = new Canvas(parent, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = PHOTO_SIZE;
		gridData.heightHint = PHOTO_SIZE;
		gridData.verticalSpan = 5;
		photoCanvas.setLayoutData(gridData);
		photoCanvas.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				photoImage = currentStudent.getPhotoData().getPhoto();
				// photoImage=new
				// Image(Display.getCurrent(),"C:\\Users\\h239267\\git\\repository\\StudentsInfo3\\icons\\eclipse128.png");
				if (photoImage != null) {
					photoImage = ResizedImage.resize(photoImage, PHOTO_SIZE);
					event.gc.drawImage(photoImage, 0, 0);
				} else {
					event.gc.drawImage(AbstractUIPlugin
							.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.STUDENT_INSTEAD)
							.createImage(), 0, 0);
				}
			}
		});

		Label group = new Label(parent, SWT.NULL);
		group.setText(FieldsNamesEnum.GROUP_NAME.getText());

		groupText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		groupText.setLayoutData(textGridData);
		groupText.setText(currentStudent.getGroup().getName());
		groupText.addModifyListener(textModifyListener);

		Label address = new Label(parent, SWT.NULL);
		address.setText(FieldsNamesEnum.ADDRESS.getText());

		addressText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		addressText.setLayoutData(textGridData);
		addressText.setText(currentStudent.getAddress());
		addressText.addModifyListener(textModifyListener);

		Label city = new Label(parent, SWT.NULL);
		city.setText(FieldsNamesEnum.CITY.getText());

		cityText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		cityText.setLayoutData(textGridData);
		cityText.setText(currentStudent.getCity());
		cityText.addModifyListener(textModifyListener);

		Label result = new Label(parent, SWT.NULL);
		result.setText(FieldsNamesEnum.RESULT.getText());

		resultText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData resultGridData = new GridData();
		resultGridData.widthHint = 5;
		resultText.setLayoutData(resultGridData);
		resultText.setText(((Integer) currentStudent.getResult()).toString());
		resultText.addModifyListener(textModifyListener);

	}

	private Student getStudent() {
		return ((StudentEditorInput) getEditorInput()).getStudent();
	}

	@Override
	public void setFocus() {
		if (nameText != null && !nameText.isDisposed()) {
			nameText.setFocus();
		}
	}

	private class TextModifyListener implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			setDirty();
			EditorIsDirtydManager.getInstance().dataIsDirty(isDirty, studentEditor);
		}
	}

	@Override
	public void saveStart() {

		groupName = groupText.getText().trim();
		name = nameText.getText().trim();
		address = addressText.getText().trim();
		city = cityText.getText().trim();
		String resultString = resultText.getText().trim();

		if (!isFieldNotOnlyLatersValid(FieldsNamesEnum.GROUP_NAME.getText(), groupName)) {
			return;
		}

		if (!isNumberFieldValid(resultString)) {
			return;
		}

		if (!isFieldValid(FieldsNamesEnum.NAME.getText(), name)) {
			return;
		}

		if (!isFieldNotOnlyLatersValid(FieldsNamesEnum.ADDRESS.getText(), address)) {
			return;
		}

		if (!isFieldValid(FieldsNamesEnum.CITY.getText(), city)) {
			return;
		}

		setPartName(name + " " + groupName);
		currentStudent.setName(name);
		currentStudent.setAddress(address);
		currentStudent.setCity(city);
		currentStudent.setResult(Integer.parseInt(resultString));
		if (!currentStudent.getGroup().getName().equals(groupName)) {
			currentStudent.setGroup(new Group(Storage.getRoot(), groupName));
		}
		EditorIsDirtydManager.getInstance().dataChenged(currentStudent);
		isDirty = false;
	}

	private boolean isNumberFieldValid(String resultString) {
		try {
			if (FieldsChecker.numbersCheck(resultString)) {
				Integer.parseInt(resultString);
			} else
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			sendErrorMessage(ErrorMessageTextFinals.WRONG_RESULT, ErrorMessageTextFinals.RESULT_MUST_BE_INTEGER);
			return false;
		}
		return true;
	}

	private boolean isFieldNotOnlyLatersValid(String fieldsName, String content) {
		return !isFieldEmpty(fieldsName, content) && isFieldDataNotOnlyLatersValid(fieldsName, content);
	}

	private boolean isFieldDataNotOnlyLatersValid(String fieldsName, String content) {
		if (!FieldsChecker.numbersSignsAndLatersCheck(content)) {
			sendErrorMessage(ErrorMessageTextFinals.INVALID + fieldsName,
					fieldsName + ErrorMessageTextFinals.CONTAIN_FORBIDDEN_SYMBOLS);
			return false;
		}
		return true;
	}

	private boolean isFieldValid(String fieldsName, String content) {
		return !isFieldEmpty(fieldsName, content) && isFieldDataValid(fieldsName, content);
	}

	private boolean isFieldDataValid(String fieldsName, String content) {
		if (!FieldsChecker.latersCheck(content)) {
			sendErrorMessage(ErrorMessageTextFinals.INVALID + fieldsName,
					fieldsName + ErrorMessageTextFinals.ONLY_LATERS_ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private boolean isFieldEmpty(String fieldsName, String content) {
		if (content.equals("")) {
			sendErrorMessage(ErrorMessageTextFinals.INVALID + fieldsName,
					fieldsName + ErrorMessageTextFinals.EMPTY_FIELD_ERROR_MESSAGE);
			return true;
		}
		return false;
	}

	private void signUp() {
		SaveDataManager.getInstance().registerObserver(this);
	}

	private void sendErrorMessage(String title, String message) {
		MessageDialog.openError(parent.getShell(), title, message);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void dispose() {
		SaveDataManager.getInstance().unregisterObserver(this);
		super.dispose();
	}

}
