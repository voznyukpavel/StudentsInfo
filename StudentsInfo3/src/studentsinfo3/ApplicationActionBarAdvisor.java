package studentsinfo3;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.plugin.AbstractUIPlugin;


public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;

    private AddStudentAction addStudentAction;
    private SaveAction saveAction;
    private OpenAction openAction;
    private DeleteStudentAction deleteStudentAction;

    private AddGroupAction addGroupAction;
    private DeleteGroupAction deleteGroupAction;
    
    private StudentSelectionAction studentSelectionAction;


    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }
    
 

    @Override
    protected void makeActions(IWorkbenchWindow window) {
        exitAction = ActionFactory.QUIT.create(window);
        exitAction.setText("&Exit@Alt+X");
        register(exitAction);
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);

        addStudentAction = new AddStudentAction(window);
        register(addStudentAction);
        deleteStudentAction = new DeleteStudentAction(window);
        register(deleteStudentAction);
        saveAction = new SaveAction(window);
        register(saveAction);
        openAction = new OpenAction(window);
        register(openAction);

        addGroupAction = new AddGroupAction(window);
        register(addGroupAction);
        deleteGroupAction= new DeleteGroupAction(window);
        register(deleteGroupAction);
        
        studentSelectionAction =new StudentSelectionAction(window);
        register(studentSelectionAction);
    }

    @Override
    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("&File", "File");
        fileMenu.add(exitAction);

        MenuManager editMenu = new MenuManager("&Edit", "Edit");
        editMenu.add(openAction);
        editMenu.add(saveAction);
        editMenu.add(new Separator());
        editMenu.add(addStudentAction);
        editMenu.add(deleteStudentAction);
        editMenu.add(new Separator());
        editMenu.add(addGroupAction);
        editMenu.add(deleteGroupAction);

        MenuManager helpMenu = new MenuManager("&Help", "help");
        helpMenu.add(aboutAction);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
    }

    @Override
    protected void fillCoolBar(ICoolBarManager coolBar) {
        IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle());
        coolBar.add(toolbar);
        toolbar.add(openAction);
        toolbar.add(saveAction);
        toolbar.add(deleteStudentAction);
        toolbar.add(addStudentAction);
    }

    protected void fillTrayItem(IMenuManager trayItem) {
        trayItem.add(aboutAction);
        trayItem.add(exitAction);
    }
}
