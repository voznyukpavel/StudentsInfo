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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.model.Student;
import studentsinfo3.util.ResizedImage;

public class StudentEditor extends EditorPart {
    public static final String ID = "StudentsInfo3.editors.studentseditor";

    private static final int PHOTO_SIZE = 120;

    private Text nameText;
    private Text groupText;
    private Text addressText;
    private Text cityText;
    private Text resultText;
    private Canvas photoCanvas;
    private Image photoImage;

    private Student currentStudent;

    public StudentEditor() {
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        // TODO Auto-generated method stub
         System.out.println("dfd");
    }
    
    @Override
    public void dispose() {
  //      if(isDirty()) {
       //     MessageDialog.openQuestion(new Shell(), "df", "dff");
     //   }
        
     //   MessageDialog.openError(new Shell(), title, message);
     //   System.out.println("dfde");
    }

    @Override
    public void doSaveAs() {
        // TODO Auto-generated method stub
     //    System.out.println("df1d");
    }

    @Override
    public void init(IEditorSite site, IEditorInput input) throws PartInitException {
        setSite(site);
        setInput(input);
    }

    @Override
    public boolean isDirty() {
        if (!currentStudent.getName().equals(nameText.getText())) {
            return true;
        } else if (!currentStudent.getAddress().equals(addressText.getText())) {
            return true;
        } else if (!currentStudent.getCity().equals(cityText.getText())) {
            return true;
        } else if (!currentStudent.getGroup().getName().equals(groupText.getText())) {
            return true;
        } else if (!((Integer) currentStudent.getResult()).toString().equals(resultText.getText())) {
            return true;
        } 
        return false;
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public void createPartControl(Composite parent) {
        currentStudent = getStudent();
        setPartName(currentStudent.getName() + " " + currentStudent.getGroup().getName());

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        parent.setLayout(gridLayout);

        Label name = new Label(parent, SWT.NULL);
        name.setText(FieldsNamesEnum.NAME.getText());

        nameText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        GridData textGridData = new GridData();
        textGridData.widthHint = 150;
        nameText.setLayoutData(textGridData);
        nameText.setText(currentStudent.getName());
        nameText.addModifyListener(new TextModifyListener());

        photoCanvas = new Canvas(parent, SWT.BORDER);
        GridData gridData = new GridData();
        gridData.widthHint = PHOTO_SIZE;
        gridData.heightHint = PHOTO_SIZE;
        gridData.verticalSpan = 5;
        photoImage = currentStudent.getPhoto();
        photoCanvas.setLayoutData(gridData);
        photoCanvas.addPaintListener(new PaintListener() {
            public void paintControl(final PaintEvent event) {
                // photoImage=new
                // Image(Display.getCurrent(),"C:\\Users\\h239267\\git\\repository\\StudentsInfo3\\icons\\eclipse128.png");
                if (photoImage != null) {
                    photoImage = ResizedImage.resize(photoImage, PHOTO_SIZE);
                    event.gc.drawImage(photoImage, 0, 0);
                } else {
                    event.gc.drawImage(AbstractUIPlugin
                            .imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeysEnum.STUDENT_INSTEAD.getWay())
                            .createImage(), 0, 0);
                }
            }
        });

        Label group = new Label(parent, SWT.NULL);
        group.setText(FieldsNamesEnum.GROUP_NAME.getText());

        groupText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        groupText.setLayoutData(textGridData);
        groupText.setText(currentStudent.getGroup().getName());
        groupText.addModifyListener(new TextModifyListener());

        Label address = new Label(parent, SWT.NULL);
        address.setText(FieldsNamesEnum.ADDRESS.getText());

        addressText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        addressText.setLayoutData(textGridData);
        addressText.setText(currentStudent.getAddress());
        addressText.addModifyListener(new TextModifyListener());

        Label city = new Label(parent, SWT.NULL);
        city.setText(FieldsNamesEnum.CITY.getText());

        cityText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        cityText.setLayoutData(textGridData);
        cityText.setText(currentStudent.getCity());
        cityText.addModifyListener(new TextModifyListener());

        Label result = new Label(parent, SWT.NULL);
        result.setText(FieldsNamesEnum.RESULT.getText());

        resultText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        GridData resultGridData = new GridData();
        resultGridData.widthHint = 5;
        resultText.setLayoutData(resultGridData);
        resultText.setText(((Integer) currentStudent.getResult()).toString());
        resultText.addModifyListener(new TextModifyListener());

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
            isDirty();
        }
      }
}
