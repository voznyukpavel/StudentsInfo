package studentsinfo3;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class Perspective implements IPerspectiveFactory {
    @Override
    public void createInitialLayout(IPageLayout layout) {
        layout.setEditorAreaVisible(true);
        layout.addStandaloneView(StudentsView.ID, false, IPageLayout.LEFT, .20f, layout.getEditorArea());
    }
}
