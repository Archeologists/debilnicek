package sk.elct.java.user_management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class GroupResultSetExtractor implements ResultSetExtractor<List<Group>>{

	@Override
	public List<Group> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Group> zoznam = new ArrayList<>();
		Group skupina = null;
		while(rs.next()) {
			long id = rs.getLong("id");
			if (skupina == null || skupina.getId() != id) {
				skupina = new Group();
				skupina.setId(id);
				skupina.setName(rs.getString("name"));
				zoznam.add(skupina);
			}
			skupina.addPrivilege(rs.getString("privilege"));
		}
		return zoznam;
	}
}
