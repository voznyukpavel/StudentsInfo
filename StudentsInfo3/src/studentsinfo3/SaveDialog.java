package studentsinfo3;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class SaveDialog extends Dialog {
    private Button yesButton, noButton, cancelButton;

    protected SaveDialog(IShellProvider parentShell) {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        yesButton=createButton(parent, 0, "Yes", true);
        noButton=createButton(parent, 1, "No", false);
        cancelButton=createButton(parent, 1, "Cancel", false);
    //    composite.setLayout(layout);

   //     GridData labelGridData = new GridData(GridData.END, GridData.CENTER, false, false);
   //     GridData textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
  //      textGridData.widthHint = convertHeightInCharsToPixels(20);

   //     Label nameLabel = new Label(composite, SWT.NONE);
   //     nameLabel.setText("&Group name:");
   //     nameLabel.setLayoutData(labelGridData);

        return composite;
    }

    @Override
    protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
        // TODO Auto-generated method stub
        return super.createButton(parent, id, label, defaultButton);
    }

    @Override
    protected void buttonPressed(int buttonId) {
        super.buttonPressed(buttonId);
    }

}
