package sk.elct.java.user_management;

import java.util.List;

public interface UserDao {

	// C-REATE
	void add(User user);

	// R-EAD
	List<User> getAll();

	//U-DATE
	void update(User user);
	void addToGroup(User user, Group group);
	
	
	//D-ELETE
	void delete(long id);

}