package sk.elct.java.user_management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User u = new User();
		u.setId(rs.getLong("id"));
		u.setName(rs.getString("name"));
		u.setPassword(rs.getString("password"));
		u.setEmail(rs.getString("email"));
		Timestamp timestamp = rs.getTimestamp("lastLogin");
		if (timestamp != null) {
			u.setLastLogin(rs.getTimestamp("lastLogin").toLocalDateTime());
		}
		return u;
	}

}
