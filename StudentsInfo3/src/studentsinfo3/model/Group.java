package studentsinfo3.model;

import java.util.ArrayList;
import java.util.List;

public class Group extends Entity {

	private List<Entity> entries;

	private Group parent;

	public Group(Group parent, String name) {
		this.name = name;
		this.parent = parent;
		type = EntityType.GROUP;
	}

	public int getSize() {
		if (entries == null) {
			return 0;
		}
		return entries.size();
	}

	public Group getParent() {
		return parent;
	}

	public void rename(String newName) {
		this.name = newName;

	}

	public void update(int place, Entity entry) {
		if (entries == null) {
			entries = new ArrayList<Entity>(5);
			entries.add(entry);
		} else {
			entries.set(place, entry);
		}

	}

	public void addEntry(Entity entry) {
		if (entries == null)
			entries = new ArrayList<Entity>(5);
		entries.add(entry);
	}

	public void removeEntry(Entity entry) {
		if (entries != null) {
			entries.remove(entry);
			if (entries.isEmpty())
				entries = null;
		}
	}

	public Entity[] getEntries() {

		if (entries != null) {
			return (Entity[]) entries.toArray(new Entity[entries.size()]);
		}
		return new Entity[0];
	}

	@Override
	public int hashCode() {
		final int prime = 13;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Group other = (Group) obj;
		if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
