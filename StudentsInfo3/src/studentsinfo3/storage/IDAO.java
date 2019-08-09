package studentsinfo3.storage;

import java.util.List;

import studentsinfo3.model.Entity;
import studentsinfo3.model.Group;

public interface IDAO {

   public void addGroup(Entity group);
   public void deleteGroup(Entity group);
   public List getData();
 //  public Group getRoot();

}
