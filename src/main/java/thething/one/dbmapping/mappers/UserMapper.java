package thething.one.dbmapping.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.one.dataobjects.ExternalUser;
import thething.one.dataobjects.User;

public class UserMapper  extends AbstractUserMapper implements RowMapper<User>{

	public UserMapper(){
		super();
	}
	
	public User mapRow(ResultSet rs, int i) throws SQLException{
		User user = null;
		Object o = this.mapUser(rs, user.getClass());
		if(o != null && o instanceof User){
			user = (User)o;
		}
		return user;
	}
}
