package sk.elct.java.user_management;


import java.util.ArrayList;
import java.util.List;

public class MemoryUserDao implements UserDao {
	private List<User> users = new ArrayList<>();
			
	//C-REATE
	@Override
	public void add(User user) {
		if (user != null) {
			users.add(user.clone());
		}
	}
	
	//R-EAD
	@Override
	public List<User> getAll() {
		List<User> result = new ArrayList<>();
		for(User u: users) {
			result.add(u.clone());
		}
		return result;
	}
	
	//U-PDATE
	@Override
	public void update(User user) {
		for(int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == user.getId()) {
				users.set(i, user.clone());
				break;
			}
		}
	}
	
	//D-ELETE
	@Override
	public void delete(long id) {
		for(User u : users) {
			if (u.getId() == id) {
				users.remove(u);
				break;
			}
		}
	}

	@Override
	public void addToGroup(User user, Group group) {
		// TODO Auto-generated method stub
		
	}


}

