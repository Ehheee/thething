package thething.one.dbmapping;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.one.dataobjects.AbstractThing;
import thething.one.dataobjects.User;

public class TagDao extends BaseDao{

	private Set<String> allTags;
	
	public Set<String> getTags(){
		if(allTags == null){
			allTags = new HashSet<String>();
		}
		if(allTags.isEmpty()){
			SqlRowSet rs = this.jdbcTemplate.queryForRowSet(Statements.Select_Distinct_Tags);
			while(rs.next()){
				allTags.add(rs.getString("tag"));
			}
		}
		return allTags;
	}

	public void onInsert(Set<String> tags){
		allTags.addAll(tags);
	}
	
	public void onRemove(){
		allTags = null;
	}
	
	
	
	public void performAction(Set<String> tags, final Long referenceId, ActionType actionType, ObjectType o){
		final List<String> tagsList = new ArrayList<String>(tags);
		String statement = null;
		switch(actionType){
		case INSERT:
			if(o.equals(ObjectType.THING)){
				statement = Statements.Insert_thingTags;
			}
			if(o.equals(ObjectType.USER)){
				statement = Statements.Insert_userTags;
			}
			break;
		case DELETE:
			if(o.equals(ObjectType.THING)){
				statement = Statements.Delete_thingTags;
			}
			if(o.equals(ObjectType.USER)){
				statement = Statements.Delete_userTags;
			}
			break;
		}
		logger.info(statement);
		if(statement != null) {
			
		
			this.jdbcTemplate.batchUpdate(statement, new BatchPreparedStatementSetter() {
	
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					ps.setString(1, tagsList.get(i));
					logger.info(tagsList.get(i) + "   " + referenceId);
					ps.setLong(2, referenceId);
					
				}
	
				public int getBatchSize() {
					return tagsList.size();
				}
				
				
				
			});
		}else{
			logger.warn("TagDao.performAction - statement is null, illegal situation");
		}
		
		
	}
	
	public void updateTagReferences(Set<String> tagsToInsert, Long referenceId, ObjectType o){
		String select = null;
		Set<String> tagsToRemove = new HashSet<String>();
		if(o.equals(ObjectType.THING)){
			select = Statements.Select_thingTags;
		}
		if(o.equals(ObjectType.USER)){
			select = Statements.Select_userTags;
		}

		if(select != null){
			SqlRowSet rs = this.jdbcTemplate.queryForRowSet(select, referenceId);
			while(rs.next()){
				
				String t = rs.getString("tag");
				logger.info(t);
				if(tagsToInsert != null && tagsToInsert.contains(t)){
					tagsToInsert.remove(t);
				}else{
					tagsToRemove.add(t);
				}
			}
			this.logCollection(tagsToRemove);
			if(tagsToInsert != null && !tagsToInsert.isEmpty()){
				performAction(tagsToInsert, referenceId, ActionType.INSERT, o);
			}
			if(tagsToRemove != null && !tagsToRemove.isEmpty()){
				performAction(tagsToRemove, referenceId, ActionType.DELETE, o);
			}
		}else{
			logger.warn("TagDao.updateTagReferences select statement was null. No tags updated.");
		}
	}
	

	
	
	
	
	
	
	
	
	
}
