package studentsinfo3;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;
import studentsinfo3.model.Student;

public class AdapterFactory implements IAdapterFactory {
    
    private IWorkbenchAdapter groupAdapter = new IWorkbenchAdapter() {

        public Object getParent(Object o) {
            return ((Group) o).getParent();
        }

        public String getLabel(Object o) {
            Group group = ((Group) o);
            return group.getName() + " (" + group.getEntries().length + ")";
        }

        public ImageDescriptor getImageDescriptor(Object object) {
            return AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeysEnum.GROUP.getWay());
        }

        public Object[] getChildren(Object o) {
      //      return  ArrayContentProvider.getInstance().getElements(o);
            return ((Group) o).getEntries();
        }
    };

    private IWorkbenchAdapter entryAdapter = new IWorkbenchAdapter() {

        public Object getParent(Object o) {
            return ((Student) o).getParent();
        }

        public String getLabel(Object o) {
            Student entry = ((Student) o);
            return entry.getName();
        }

        public ImageDescriptor getImageDescriptor(Object object) {
            return AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, ImageWayKeysEnum.STUDENT.getWay());

        }

        public Object[] getChildren(Object o) {
            return new Object[0];
        }
    };

    @Override
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        Entity entity=(Entity)adaptableObject;
        if (adapterType == IWorkbenchAdapter.class &&entity.isGroupEntity())
            return groupAdapter;
        if (adapterType == IWorkbenchAdapter.class &&!entity.isGroupEntity())
            return entryAdapter;
        return null;
    }

    @Override
    public Class[] getAdapterList() {
        return new Class[] { IWorkbenchAdapter.class };
    }
}
