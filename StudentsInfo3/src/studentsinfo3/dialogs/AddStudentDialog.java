package studentsinfo3.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import studentsinfo3.ErrorMessageTextFinals;
import studentsinfo3.FieldsNamesEnum;
import studentsinfo3.util.FieldsChecker;

public class AddStudentDialog extends Dialog {

    private Text nameText;
    private Text addressText;
    private Text cityText;
    private Text resultText;

    private String name;
    private String address;
    private String city;
    private int result;

    public AddStudentDialog(Shell shell) {
        super(shell);
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Add Student");
    }

    protected Control createDialogArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        composite.setLayout(layout);

        GridData labelGridData = new GridData(GridData.END, GridData.CENTER, false, false);
        GridData textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);

        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText("&Name:");
        nameLabel.setLayoutData(labelGridData);

        nameText = new Text(composite, SWT.BORDER);
        nameText.setLayoutData(textGridData);

        Label addressLabel = new Label(composite, SWT.NONE);
        addressLabel.setText("&Address:");
        addressLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

        addressText = new Text(composite, SWT.BORDER);
        addressText.setLayoutData(textGridData);

        Label cityLabel = new Label(composite, SWT.NONE);
        cityLabel.setText("&City:");
        cityLabel.setLayoutData(labelGridData);

        cityText = new Text(composite, SWT.BORDER);
        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        gridData.widthHint = convertHeightInCharsToPixels(20);
        cityText.setLayoutData(gridData);

        Label resultLabel = new Label(composite, SWT.NONE);
        resultLabel.setText("&Result:");
        resultLabel.setLayoutData(labelGridData);

        resultText = new Text(composite, SWT.BORDER);
        GridData resultGridData = new GridData(GridData.BEGINNING, GridData.FILL, true, false);
        resultGridData.widthHint = convertHeightInCharsToPixels(1);
        resultText.setLayoutData(resultGridData);

        return composite;
    }

    protected void okPressed() {
        name = nameText.getText().trim();
        address = addressText.getText().trim();
        city = cityText.getText().trim();
        String resultString = resultText.getText().trim();

        if (resultString.equals("")) {
            result = 0;
        } else if (!isNumberFieldValid(resultString)) {
            return;
        }

        if (!isFieldValid(FieldsNamesEnum.NAME.getText(), name)) {
            return;
        }
        
        String fieldAddress = FieldsNamesEnum.ADDRESS.getText();
        if (isFieldEmpty(fieldAddress, address)) {
            return;
        }

        if (!FieldsChecker.numbersSignsAndLatersCheck(address)) {
            sendErrorMessage(ErrorMessageTextFinals.INVALID_ADDRESS,
                    ErrorMessageTextFinals.ADDRESS_CONTAIN_FORBIDDEN_SYMBOLS);
            return;
        }

        if (!isFieldValid(FieldsNamesEnum.CITY.getText(), city)) {
            return;
        }
        super.okPressed();
    }

    private boolean isNumberFieldValid(String resultString) {
        try {
            if (FieldsChecker.numbersCheck(resultString)) {
                result = Integer.parseInt(resultString);
            } else
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            sendErrorMessage(ErrorMessageTextFinals.WRONG_RESULT,
                    ErrorMessageTextFinals.RESULT_MUST_BE_INTEGER);
            return false;
        }
        return true;
    }

    private boolean isFieldValid(String fieldsName, String content) {
        return !isFieldEmpty(fieldsName, content) && isFieldDataValid(fieldsName, content);
    }

    private boolean isFieldEmpty(String fieldsName, String content) {
        if (content.equals("")) {
            sendErrorMessage(ErrorMessageTextFinals.INVALID + fieldsName,
                    fieldsName + ErrorMessageTextFinals.EMPTY_FIELD_ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    private boolean isFieldDataValid(String fieldsName, String content) {
        if (!FieldsChecker.latersCheck(content)) {
            sendErrorMessage(ErrorMessageTextFinals.INVALID + fieldsName,
                    fieldsName + ErrorMessageTextFinals.ONLY_LATERS_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getResult() {
        return result;
    }

    private void sendErrorMessage(String title, String message) {
        MessageDialog.openError(getShell(), title, message);
    }

}
