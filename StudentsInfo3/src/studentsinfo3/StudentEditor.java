package studentsinfo3;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
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

import studentsinfo3.model.Student;

public class StudentEditor extends EditorPart {
    public static final String ID="StudentsInfo3.editors.studentseditor";
    
    private Text nameText;
    private Text groupText;
    private Text addressText;
    private Text cityText;
    private Text resultText;
    private Canvas photoCanvas;
    private Image photoImage;
    
    public StudentEditor() {
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        // TODO Auto-generated method stub

    }

    @Override
    public void doSaveAs() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(IEditorSite site, IEditorInput input) throws PartInitException {
        setSite(site);    
        setInput(input);   
        setPartName(getUser().getName());
    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public void createPartControl(Composite parent) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        parent.setLayout(gridLayout);
        
        Label name= new Label(parent,SWT.NULL);
        name.setText(FieldsNames.NAME.getText());
        
        nameText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        
        photoCanvas = new Canvas(parent, SWT.BORDER);
        GridData gridData = new GridData();
        gridData.widthHint = 120;
        gridData.heightHint = 120;
        gridData.verticalSpan = 5;
        photoCanvas.setLayoutData(gridData);
        photoCanvas.addPaintListener(new PaintListener() {
           public void paintControl(final PaintEvent event) {
               if (photoImage != null) {
                  event.gc.drawImage(photoImage, 0, 0);
               }
           }
        });
        
        Label group= new Label(parent,SWT.NULL);
        group.setText(FieldsNames.GROUP_NAME.getText());
        
        groupText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        
        Label address= new Label(parent,SWT.NULL);
        address.setText(FieldsNames.ADDRESS.getText());
        
        addressText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        
        Label city= new Label(parent,SWT.NULL);
        city.setText(FieldsNames.CITY.getText());
        
        cityText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        
        Label result= new Label(parent,SWT.NULL);
        result.setText(FieldsNames.RESULT.getText());
        
        resultText = new Text(parent, SWT.SINGLE | SWT.BORDER);
        
    }
    private Student getUser() {
        return ((StudentEditorInput) getEditorInput()).getStudent();
      }
    @Override
    public void setFocus() {
        // TODO Auto-generated method stub

    }

}
