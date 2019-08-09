package studentsinfo3.storage;

import java.util.List;

import studentsinfo3.model.Group;

public interface IDAO {

   public void addGroup(Group group);
   public void deleteGroup(Group group);
   public List getData();
 //  public Group getRoot();

}
