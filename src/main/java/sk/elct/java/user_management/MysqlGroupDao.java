package sk.elct.java.user_management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlGroupDao implements GroupDao {

	private JdbcTemplate jdbcTemplate;
	
	public MysqlGroupDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(Group group) throws DuplicateGroupNameException {
		try {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("`group`");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			simpleJdbcInsert.usingColumns("name");
			
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("name", group.getName());
			
			// tu sa vyhadzuje vynimka DuplicateKeyException
			long noveId = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			
			// tieto riadky sa vykonaju iba ak je to nova skupina
			group.setId(noveId);
			for (String privilege: group.getPrivileges()) {
				addPrivilege(group, privilege);
			}
		} catch (DuplicateKeyException e) {
			// v databaze uz je rovnomenna skupina
			DuplicateGroupNameException mojaVynimka 
				= new DuplicateGroupNameException(
						"rovnomenna skupina \"" + group.getName() + "\" uz je v systeme", 
						e);
			throw mojaVynimka;
		}
		
	}

	@Override
	public void addPrivilege(Group group, String privilege) {
		String sql = "INSERT INTO `privileges` (id_group, privilege) VALUES (?,?)";
		jdbcTemplate.update(sql, group.getId(), privilege);
		if (!group.getPrivileges().contains(privilege))
			group.addPrivilege(privilege);
	}

	@Override
	public List<Group> getAll() {
		String sql = "SELECT `group`.id, `group`.`name`, privilege FROM `group` " + 
				"JOIN `privileges` ON `group`.id = `privileges`.id_group " + 
				"ORDER BY `group`.id;";
		return jdbcTemplate.query(sql, new GroupResultSetExtractor());
	}

	@Override
	public List<Group> getByUser(User user) {
		String sql = "SELECT `group`.id, `group`.`name`, privilege FROM `group` " + 
				"JOIN `privileges` ON `group`.id = `privileges`.id_group " +
				"JOIN user_group ON `group`.id = user_group.id_group " +
				"WHERE user_group.id_user = " + user.getId() + " " +
				"ORDER BY `group`.id";
		return jdbcTemplate.query(sql, new GroupResultSetExtractor());	
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update("DELETE FROM `privileges` WHERE id_group = " + id);
		jdbcTemplate.update("DELETE FROM `user_group` WHERE id_group = " + id);
		jdbcTemplate.update("DELETE FROM `group` WHERE id = " + id);
	}

}
