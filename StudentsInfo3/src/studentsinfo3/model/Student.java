package studentsinfo3.model;

import java.util.Date;

import org.eclipse.swt.graphics.Image;

public class Student extends Entity {

	private int id;
	private Group group;
	private String address;
	private String city;
	private PhotoData photoData;
	private int result;
	private Date dateOfChange;
	private boolean male;


	public Student(String name, Group group, String address, String city, int result, boolean male) {
		super();
		this.name = name;
		this.group = group;
		this.address = address;
		this.city = city;
		this.result = result;
		this.male = male;
		type = EntityType.STUDENT;
		photoData = new PhotoData();
		this.dateOfChange = new Date(System.currentTimeMillis());

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.dateOfChange = new Date(System.currentTimeMillis());
		this.group = group;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.dateOfChange = new Date(System.currentTimeMillis());
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.dateOfChange = new Date(System.currentTimeMillis());
		this.city = city;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.dateOfChange = new Date(System.currentTimeMillis());
		this.result = result;
	}

	@Override
	public Group getParent() {
		return group;
	}

	public PhotoData getPhotoData() {
		return photoData;
	}

	public void setPhotoData(Image photo, String way) {
		this.dateOfChange = new Date(System.currentTimeMillis());
		this.photoData = new PhotoData(photo, way);
	}

	public Date getDate() {
		return dateOfChange;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.dateOfChange = new Date(System.currentTimeMillis());
		this.male = male;
	}

	@Override
	public int hashCode() {
		final int prime = 13;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result += prime * result + ((group == null) ? 0 : group.hashCode());
		result += prime * result + ((address == null) ? 0 : address.hashCode());
		result += prime * result + ((city == null) ? 0 : city.hashCode());
		result += prime + id;
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
		if (id == other.id)
			return true;
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
		return id + " " + name + " " + group + " " + address + " " + city + " " + result;
	}

}
