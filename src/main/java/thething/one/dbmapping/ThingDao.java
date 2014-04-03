package thething.one.dbmapping;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.sql.RowSet;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import thething.one.dataobjects.AbstractThing;
import thething.one.dataobjects.Comment;
import thething.one.dataobjects.Photo;
import thething.one.dbmapping.BaseDao.ObjectType;
import thething.one.dbmapping.mappers.AbstractThingExtractor;
import thething.one.dbmapping.mappers.AbstractThingMapper;

public class ThingDao extends BaseDao{

	
	
	protected Log logger = LogFactory.getLog(getClass());
	
	
	public List<AbstractThing> getThings(ThingFilter filter){
	
		List<AbstractThing> things = this.namedParameterJdbcTemplate.query(filter.createQuery(), filter.getBindParams(), extractor);
		
		return things;
	}
	
	public AbstractThing getThingForUser(Long id, Long userId){
		AbstractThing thing = null;
	
			ThingFilter filter = new ThingFilter();
			filter.setThingId(id);
			filter.setUserId(userId);
			
			List<AbstractThing> things = this.namedParameterJdbcTemplate.query(filter.createQuery(), filter.getBindParams(), extractor);
			thing = things.get(0);
			if(things.size()> 1){
				logger.error("Single thing id returns more rows");
			}

		
		return thing;
	}
	
	
	
	public Long insertThing(AbstractThing thing){
		//TODO got to extend BeanPropertySqlParameter for not existing fields to work
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource pm = new BeanPropertySqlParameterSource(thing);
		pm.registerSqlType("type", Types.VARCHAR);
		this.namedParameterJdbcTemplate.update(Statements.Insert_thing, pm, keyHolder);
		Long newId = (Long)keyHolder.getKey();
		tagDao.performAction(thing.getTags(), newId, ActionType.INSERT, ObjectType.THING);
		return newId;
		
	}
	
	public AbstractThing getThingForUpdate(Long id){
		Object o = this.jdbcTemplate.query(Statements.Select_things_For_Update, new Long[] {id}, extractor);
		List<AbstractThing> things = this.checkObject(o);
		AbstractThing thing = things.get(0);
		if(things.get(1) != null){
			logger.error("Single thing id returns 2 rows");
		}
		
		return thing;
	}
	
	public void updateThing(AbstractThing thing){
		BeanPropertySqlParameterSource pm = new BeanPropertySqlParameterSource(thing);
		pm.registerSqlType("type", Types.VARCHAR);
		this.jdbcTemplate.queryForRowSet(Statements.Select_things_For_Update, thing.getId());
		this.namedParameterJdbcTemplate.update(Statements.Update_things_By_id, pm);
		tagDao.updateTagReferences(thing.getTags(), thing.getId(), ObjectType.THING);
	}
	
	
	public void deleteThing(long id){
		this.jdbcTemplate.update(Statements.Delete_things_By_id, id);
		tagDao.updateTagReferences(null, id, ObjectType.THING);
		
	}
	
	
	/**
	 * 
	 * @param o Object from namedParameterJdbcTemplate query
	 * @return List of Things or null if o doesn't match
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<AbstractThing> checkObject(Object o){
		List<AbstractThing> things = new ArrayList<AbstractThing>();
		if(o instanceof List){
			if(((List) o).get(0) != null) {
				if(((List) o).get(0) instanceof AbstractThing){
					things = (List<AbstractThing>)o;
				}
			}
		}
		
		return things;
	}
	
	@Autowired
	private AbstractThingExtractor extractor;
	public void setExtractor(AbstractThingExtractor extractor) {
		this.extractor = extractor;
	}
	
	
	
	
}
