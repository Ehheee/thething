package thething.one.dbmapping.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import thething.one.dataobjects.ExternalUser;
import thething.one.dataobjects.User;
import thething.one.dataobjects.ExternalUser.ExternalLoginType;

public class AbstractUserMapper {

	/*
	 * Long id;
ExternalLoginType loginType;
String externalId;
String fullName;
String email;
String refreshToken;
String accessToken;
Date tokenExpireDate;
User user;




Long id, String userName, Boolean enabled,
		String eMail, Boolean confirmed,
		Date registeredDate, String role, String password, String salt, ExternalUser externalUser
	 * 
	 * 
	 * 
	 */
	protected Log logger = LogFactory.getLog(getClass());
	ExternalLoginType[] types;
	
	public AbstractUserMapper(){
		this.types = ExternalLoginType.values();
	}
	
	protected Object mapUser(ResultSet rs, Class returnType) throws SQLException{
		ExternalUser externalUser = null;
		User user = null;
		Long externalUserId = rs.getLong("eu.id");
		Long userId = rs.getLong("u.id");
		if(externalUserId != null){
			externalUser = new ExternalUser();
			externalUser.setLoginType(types[rs.getInt("eu.loginType")]);
			externalUser.setExternalId(rs.getString("eu.externalId"));
			externalUser.setFullName(rs.getString("eu.fullName"));
			externalUser.setEmail(rs.getString("eu.email"));
			externalUser.setAccessToken(rs.getString("eu.accessToken"));
			externalUser.setRefreshToken(rs.getString("eu.refreshToken"));
			externalUser.setTokenExpireDate(rs.getDate("eu.tokenExpireDate"));
			
						
		}
		if(userId != null){
			user = new User();
			
			user.setId(userId);
			user.setConfirmed(rs.getBoolean("u.confirmed"));
			user.setEmail(rs.getString("u.email"));
			user.setEnabled(rs.getBoolean("u.enabled"));
			user.setPassword(rs.getString("u.password"));
			user.setRegisteredDate(rs.getDate("u.registeredDate"));
			user.setRole(rs.getString("u.role"));
			user.setUserName(rs.getString("u.userName"));
			user.setNickName(rs.getString("u.nickName"));
			if(externalUser != null && rs.getLong("eu.userId") == userId){
				user.setExternalUser(externalUser);
				externalUser.setUser(user);
			}
		}
		if(returnType.equals(User.class)){
			return user;
		}else if(returnType.equals(ExternalUser.class)){
			return externalUser;
		}else{
			logger.warn("AbstractUserMapper - invalid class requested");
		}
		return null;
		
		
	}
		
		
		
		
		
	
}
