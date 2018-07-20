package sk.elct.java.user_management;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {
	public static void main(String[] args) {
		User jano = new User();
		jano.setName("Jano");
		jano.setPassword("haha");
		jano.setLastLogin(LocalDateTime.now());
		jano.setEmail("jano@haha.sk");

		User anka = new User();
		anka.setName("Anka");
		anka.setPassword("haha");
		anka.setLastLogin(LocalDateTime.now());
		anka.setEmail("anka@haha.sk");

		UserDao userDao = new MemoryUserDao();
		userDao.add(jano);
		userDao.add(anka);

		System.out.println(userDao.getAll());

		UserDao userDao2 = DaoFactory.INSTANCE.getUserDao();
		userDao2.add(anka);
		anka.setName("Anička");
		userDao2.update(anka);
		System.out.println(userDao2.getAll());
		userDao2.delete(anka.getId());

		GroupDao groupDao = DaoFactory.INSTANCE.getGroupDao();
		Group informatici = new Group();
		informatici.setName("informatici");
		informatici.addPrivilege("stazovat sa");

		try {
			groupDao.add(informatici);
			groupDao.addPrivilege(informatici, "pytat si vyssi plat");

			List<Group> groups = groupDao.getAll();
			for (Group group : groups) {
				System.out.println(group);
			}
			groupDao.delete(informatici.getId());
		} catch (DuplicateGroupNameException e) {
			// ak sa nepodari vlozit, nerobim nic
		}

		List<Group> groups = groupDao.getAll();
		Group prvaSkupina = groups.get(0);
		User peto = new User();
		peto.setName("Peter");
		List<Group> petoveSkupiny = new ArrayList<>();
		petoveSkupiny.add(prvaSkupina);
		peto.setGroups(petoveSkupiny);
		userDao2.add(peto);
		for (User user : userDao2.getAll()) {
			System.out.println(user);

		}

	}
}
