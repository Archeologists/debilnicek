package sk.elct.java.user_management;

import java.util.List;


public interface GroupDao {
	void add(Group group) throws DuplicateGroupNameException;
	void addPrivilege(Group group, String privilege);
	List<Group> getAll();
	List<Group> getByUser(User user);
	void delete(long id);
}
