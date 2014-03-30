package thething.one.dbmapping;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.one.dataobjects.Settings;
import thething.one.dbmapping.BaseDao;

public class SettingsDao extends BaseDao {

	

	/**
	 * Return all different settings that are in database
	 * 
	 * @return List of Settings
	 */
	public List<Settings> getSettings(){
		List<Settings> settings = null;
		SqlRowSet rs = this.jdbcTemplate.queryForRowSet(Statements.Select_Settings);
		return settings;
	}
	
	
	
	
	/**
	 * 
	 * @param id
	 */
	public void deleteSettings(Integer id){
		this.jdbcTemplate.update(Statements.Delete_Settings_By_id, id);
	}
	
	public Settings getSettings(String tag){
		this.jdbcTemplate.queryForRowSet(Statements.Select_Settings_By_tag, tag);
		return null;
	}
}
