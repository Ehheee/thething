package thething.one.dbmapping;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import thething.one.dataobjects.ExternalUser;
import thething.one.dataobjects.User;
import thething.one.dataobjects.ExternalUser.ExternalLoginType;
import thething.one.dbmapping.mappers.ExternalUserMapper;

public class ExternalUserDao extends BaseDao {

	
	public ExternalUser getUser(String externalId, ExternalLoginType loginType){
		Map<String, Object> bindParams = Collections.emptyMap();
		bindParams.put("externalId", externalId);
		bindParams.put("loginType", loginType.getText());
		ExternalUser externalUser = this.namedParameterJdbcTemplate.queryForObject(Statements.Select_externalUsers_By_externalId_And_type, bindParams, externalUserMapper);
		return externalUser;
	}
	
	public ExternalUser getUser(Long id, boolean forUpdate){
		String statement = null;
		if(forUpdate){
			statement = Statements.Select_externalUsers_By_id_For_Update; 
		}else{
			statement = Statements.Select_externalUsers_By_id;
		}
		return this.jdbcTemplate.queryForObject(statement, externalUserMapper, id);
	}
	
	
	public void update(ExternalUser externalUser){
		SqlParameterSource pm = getParamSource(externalUser);
		this.jdbcTemplate.queryForRowSet(Statements.Select_externalUsers_By_id_For_Update, externalUser.getId());
		this.namedParameterJdbcTemplate.update(Statements.Update_externalUsers_By_id, pm);
		if(externalUser.getUser() != null){
			this.tagDao.updateTagReferences(externalUser.getUser().getTags(), externalUser.getUser().getId(), ObjectType.USER);
		}else{
			logger.warn("externalUser updated without having user associated");
		}
	}
	
	public Long insert(ExternalUser externalUser){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource pm = getParamSource(externalUser);
		this.namedParameterJdbcTemplate.update(Statements.Insert_externalUser, pm, keyHolder);
		return (Long)keyHolder.getKey();
		
	}
	
	private MapSqlParameterSource getParamSource(ExternalUser externalUser){
		MapSqlParameterSource source = new MapSqlParameterSource();
		source
		.addValue("accessToken", externalUser.getAccessToken())
		.addValue("email", externalUser.getEmail())
		.addValue("externalId", externalUser.getExternalId())
		.addValue("fullName", externalUser.getFullName())
		.addValue("id", externalUser.getId())
		.addValue("loginType", externalUser.getLoginType())
		.addValue("refreshToken", externalUser.getRefreshToken());
		if(externalUser.getUser() != null){
			source.addValue("userId", externalUser.getUser().getId());
		}
		return source;
	}
	
	



	

	@Autowired
	private ExternalUserMapper externalUserMapper;
	public void setExternalUserMapper(ExternalUserMapper externalUserMapper) {
		this.externalUserMapper = externalUserMapper;
	}
}
