package thething.one.dbmapping.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import thething.one.dataobjects.ExternalUser;

public class ExternalUserMapper extends AbstractUserMapper implements RowMapper<ExternalUser> {

	public ExternalUserMapper(){
		super();
	}
	
	public ExternalUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExternalUser externalUser = null;
		Object o = this.mapUser(rs, externalUser.getClass());
		if(o != null && o instanceof ExternalUser){
			externalUser = (ExternalUser)o;
		}
		return externalUser;
	}

}
