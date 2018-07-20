package sk.elct.java.user_management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sun.org.apache.regexp.internal.recompile;

public class MysqlUserDao implements UserDao {

	private JdbcTemplate jdbcTemplate;

	// public MysqlUserDao() {
	// MysqlDataSource dataSource = new MysqlDataSource();
	// dataSource.setUrl("jdbc:mysql://localhost/user_management?" +
	// "serverTimezone=Europe/Bratislava&nullNamePatternMatchesAll=true");
	// dataSource.setDatabaseName("user_management");
	// dataSource.setUser("um");
	// dataSource.setPassword("wEobMo217OsdYNNX");
	// jdbcTemplate = new JdbcTemplate(dataSource);
	// }

	public MysqlUserDao(MysqlDataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void add(User user) {
		// String sql = "INSERT INTO user "
		// + "(name, `password`, email, lastLogin) "
		// + "VALUES (?,?,?,?)";
		// jdbcTemplate.update(sql, user.getName(), user.getPassword(),
		// user.getEmail(), user.getLastLogin());

		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("user");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("name", "password", "email", "lastLogin");

		Map<String, Object> hodnoty = new HashMap<>();
		hodnoty.put("name", user.getName());
		hodnoty.put("password", user.getPassword());
		hodnoty.put("email", user.getEmail());
		hodnoty.put("lastLogin", user.getLastLogin());

		long noveId = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		user.setId(noveId);

		for (Group group : user.getGroups()) {
			addToGroup(user, group);
		}
	}

	@Override
	public List<User> getAll() {
		String sql = "SELECT * FROM `user`";
		UserRowMapper rowMapper = new UserRowMapper();
		List<User> zoznam = jdbcTemplate.query(sql, rowMapper);
		GroupDao groupDao = DaoFactory.INSTANCE.getGroupDao();
		for (User user : zoznam) {
			user.setGroups(groupDao.getByUser(user));
		}
		return zoznam;
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE `user` SET name=?, `password`=?, " + "email=?, lastLogin=? WHERE id =?";
		jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getEmail(), user.getLastLogin(),
				user.getId());
		
		jdbcTemplate.update("DELETE FROM user_group WHERE id_user = ?" , user.getId());
		

		for (Group group : user.getGroups()) {
			addToGroup(user, group);
		}
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update("DELETE FROM user_group WHERE id_user = " + id );
		jdbcTemplate.update("DELETE FROM user WHERE id = " + id);

	}

	public void addToGroup(User user, Group group) {
		String sql = "INSERT INTO `user_group` (id_group, id_user) VALUES (?,?)";
		jdbcTemplate.update(sql, group.getId(), user.getId()); // parametre ktore nahradzaju otazniky
		if (!user.getGroups().contains(group)) {
			user.getGroups().add(group);
		}

	}

}
