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

public class AddGroupDialog extends Dialog {

    private Text nameText;
    private String groupName;

    public AddGroupDialog(Shell parentShell) {
        super(parentShell);

    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Add Group");
    }

    protected Control createDialogArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        composite.setLayout(layout);

        GridData labelGridData = new GridData(GridData.END, GridData.CENTER, false, false);
        GridData textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textGridData.widthHint = convertHeightInCharsToPixels(20);

        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText("&Group name:");
        nameLabel.setLayoutData(labelGridData);

        nameText = new Text(composite, SWT.BORDER);
        nameText.setLayoutData(textGridData);

        return composite;
    }

    protected void okPressed() {
        groupName = nameText.getText().trim();
        String fieldName = FieldsNamesEnum.GROUP_NAME.getText();
        if (isFieldEmpty(fieldName, groupName)) {
            return;
        }
        if (!FieldsChecker.numbersSignsAndLatersCheck(groupName)) {
            sendErrorMessage(ErrorMessageTextFinals.INVALID + fieldName,
                    fieldName + ErrorMessageTextFinals.ADDRESS_CONTAIN_FORBIDDEN_SYMBOLS);
            return;
        }
        super.okPressed();
    }

    private boolean isFieldEmpty(String fieldsName, String content) {
        if (content.equals("")) {
            sendErrorMessage(ErrorMessageTextFinals.INVALID + fieldsName,
                    fieldsName + ErrorMessageTextFinals.EMPTY_FIELD_ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    public String getName() {
        return groupName;
    }

    private void sendErrorMessage(String title, String message) {
        MessageDialog.openError(getShell(), title, message);
    }
}
