package studentsinfo3.model;

import org.eclipse.swt.graphics.Image;

public class Student extends Entity {

    private Group group;
    private String address;
    private String city;
    private int result;
    private Image photo;

    public Student(String name, Group group, String address, String city, int result) {
        super();
        this.name = name;
        this.group = group;
        this.address = address;
        this.city = city;
        this.result = result;
        type = EntityType.STUDENT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public Group getParent() {
        return group;
    }
    
    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }
    
    @Override
    public int hashCode() {
      final int prime = 13;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result+= prime * result + ((group == null) ? 0 : group.hashCode());
      result+=prime * result + ((address == null) ? 0 : address.hashCode());
      result+=prime * result + ((city == null) ? 0 : city.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Student other = (Student) obj;
      if (!name.equals(other.name))
        return false;
      if (!group.equals(other.group))
        return false;
      if (!address.equals(address))
        return false;
      if (!city.equals(city))
          return false;
      return true;
    }

    @Override
    public String toString() {
      return name + " " + group+" "+address+" "+city+" "+result;
    }

}
