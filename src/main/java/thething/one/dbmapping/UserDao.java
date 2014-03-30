package thething.one.dbmapping;

import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import thething.one.dataobjects.User;
import thething.one.dbmapping.BaseDao.ActionType;
import thething.one.dbmapping.BaseDao.ObjectType;

public class UserDao extends BaseDao{

	
	
	public User getUser(String userName){
		User u = null;
		this.jdbcTemplate.queryForRowSet(Statements.Select_users_By_userName, userName);
		return u;
	}
	
	public Long insertUser(User user){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource pm = getParamSource(user);
		this.namedParameterJdbcTemplate.update(Statements.Insert_user, pm, keyHolder);
		Long newId = (Long)keyHolder.getKey();
		tagDao.performAction(user.getTags(), newId, ActionType.INSERT, ObjectType.USER);
		return newId;
	}
	
	public void updateUser(User user){
		SqlParameterSource pm = getParamSource(user);
		this.jdbcTemplate.queryForRowSet(Statements.Select_users_For_Update, user.getId());
		this.namedParameterJdbcTemplate.update(Statements.Update_users_By_userName_And_id, pm);
		tagDao.updateTagReferences(user.getTags(), user.getId(), ObjectType.USER);
	}
	
	public void deleteUser(Long id){
		this.jdbcTemplate.update(Statements.Delete_users_By_id, id);
		tagDao.updateTagReferences(null, id, ObjectType.USER);
	}
	
	private MapSqlParameterSource getParamSource(User user){
		MapSqlParameterSource source = new MapSqlParameterSource();
		source
		.addValue("confirmed", user.getConfirmed())
		.addValue("email", user.getEmail())
		.addValue("enabled", user.getEnabled())
		.addValue("id", user.getId())
		.addValue("password", user.getPassword())
		.addValue("registeredDate", user.getRegisteredDate())
		.addValue("role", user.getRole())
		.addValue("userName", user.getUserName());
		if(user.getExternalUser() != null){
			source.addValue("externalUserId", user.getExternalUser().getId());
		}
		return source;
	}
	/*
	public void deleteUser(String userName){
		this.jdbcTemplate.update(Statements.Delete_users_By_userName, userName);
	}
	*/

	
}
