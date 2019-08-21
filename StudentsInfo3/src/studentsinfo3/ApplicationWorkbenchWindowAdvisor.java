package studentsinfo3;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.MarkerTransfer;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.dnd.EditorAreaDropAdapter;



public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
    private static final int MINIMUM_WIDTH=500;
    private static final int MINIMUM_HEIGHT=300;
    private static final int INITIAL_WIDTH=600;
    private static final int INITIAL_HEIGHT=300;
    private static final String TOOL_TIP_TEXT="Student info";
    
    private TrayItem trayItem;
    private Image trayImage;
    private ApplicationActionBarAdvisor actionBarAdvisor;

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    @Override
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        actionBarAdvisor = new ApplicationActionBarAdvisor(configurer);
        return actionBarAdvisor;
    }

    @Override
    public void preWindowOpen() {
        final IWorkbenchWindow window = getWindowConfigurer().getWindow();
        window.getShell().setMinimumSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(INITIAL_WIDTH, INITIAL_HEIGHT));
        configurer.setShowCoolBar(true);
        configurer.setShowMenuBar(true);
        configurer.setShowStatusLine(false);
        
        configurer.addEditorAreaTransfer(EditorInputTransfer.getInstance());
        configurer.addEditorAreaTransfer(ResourceTransfer.getInstance());
        configurer.addEditorAreaTransfer(FileTransfer.getInstance());
        configurer.addEditorAreaTransfer(MarkerTransfer.getInstance());
      //  configurer.addEditorAreaTransfer(GroupTransfer.getInstance());
        configurer.addEditorAreaTransfer(TextTransfer.getInstance());
        configurer.addEditorAreaTransfer(PluginTransfer.getInstance());
        
		configurer.configureEditorAreaDropListener(new EditorAreaDropAdapter(configurer.getWindow()));
        
    }

    public void postWindowOpen() {
        final IWorkbenchWindow window = getWindowConfigurer().getWindow();
        trayItem = initTaskItem(window);
        if (trayItem != null) {
            hookPopupMenu(window);
            hookMinimize(window);
        }
    }

    private void hookMinimize(final IWorkbenchWindow window) {
        window.getShell().addShellListener(new ShellAdapter() {
            public void shellIconified(ShellEvent e) {
                window.getShell().setVisible(false);
            }
        });
        
        trayItem.addListener(SWT.DefaultSelection, new Listener() {
            public void handleEvent(Event event) {
                Shell shell = window.getShell();
                if (!shell.isVisible()) {
                    shell.setVisible(true);
                    window.getShell().setMinimized(false);
                }
            }
        });
    }

    private void hookPopupMenu(final IWorkbenchWindow window) {
        trayItem.addListener(SWT.MenuDetect, new Listener() {
            public void handleEvent(Event event) {
                MenuManager trayMenu = new MenuManager();
                Menu menu = trayMenu.createContextMenu(window.getShell());
                actionBarAdvisor.fillTrayItem(trayMenu);
                menu.setVisible(true);
            }
        });
    }

    private TrayItem initTaskItem(IWorkbenchWindow window) {
        final Tray tray = window.getShell().getDisplay().getSystemTray();
        if (tray == null)
            return null;
        trayItem = new TrayItem(tray, SWT.NONE);
        trayImage = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeys.STUDENT)
                .createImage();
        trayItem.setImage(trayImage);
        trayItem.setToolTipText(TOOL_TIP_TEXT);
        return trayItem;
    }

}
