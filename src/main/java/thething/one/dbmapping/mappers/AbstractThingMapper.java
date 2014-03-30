package thething.one.dbmapping.mappers;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.RowSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.one.dataobjects.AbstractThing;
import thething.one.dataobjects.Article;
import thething.one.dataobjects.Comment;
import thething.one.dataobjects.Photo;
import thething.one.dataobjects.PhotoArticle;
import thething.one.dbmapping.ThingFilter;
import thething.one.dbmapping.ThingFilter.ThingType;

public class AbstractThingMapper{
	
	protected Log logger = LogFactory.getLog(getClass());

	List<AbstractThing> thingsList;
	Map<Long, Integer> mappedThings;
	Set<Long> mappedComments;
	
	ThingType[] types;
	
	public AbstractThingMapper(){
		types = ThingType.values();
	}
	
	public void mapRow(ResultSet rs) throws SQLException{
		
		List<Comment> comments = new ArrayList<Comment>();
		ThingType type = types[rs.getInt("type")];
		Long thingId = rs.getLong("t.id");
		Long tagThingId = rs.getLong("tags.thingId");
		Long commentId = rs.getLong("c.id");
		Long commentThingId = rs.getLong("c.thingId");
		if(thingId != null && type != null && (mappedThings.get(thingId) == null)){
			
			
			String title = rs.getString("t.title");
			Date date = rs.getDate("t.date");
			String description = rs.getString("t.description");
			Long authorId = rs.getLong("t.authorId");
			//Integer commentCount = rs.getInt("t.commentCount");
			Set<String> tags = new HashSet<String>();   
			Boolean priv = rs.getBoolean("t.priv");
			Boolean published = rs.getBoolean("t.published");
			Boolean inBlog = rs.getBoolean("t.inBlog");
			Integer order = rs.getInt("t.order");
			
			logger.info(type);
			switch(type){
			
				case ARTICLE:
					String text = rs.getString("t.text");
					Article article = new Article(thingId, title, date, description, authorId, tags, comments, priv, published, inBlog, null, text, order);
					logger.info("mapped Row: " + article);
					mappedThings.put(thingId, addToList(article));
					break;
				case PHOTO:
				case PHOTO_ARTICLE:
					String fileName = rs.getString("t.fileName");
					Photo photo = new Photo(thingId, title, date, description, authorId, tags, comments, priv, published, inBlog, null, fileName, order);
					logger.info("mapped Row: " + photo);
					if(type == ThingType.PHOTO_ARTICLE){
						String text2 = rs.getString("t.text");
						PhotoArticle pa = new PhotoArticle(photo, text2);
						mappedThings.put(thingId, addToList(pa));
					}else{
						mappedThings.put(thingId, addToList(photo));
					}
				}
					
			}
		Integer thingIndex;
		
		if(tagThingId != null && (thingIndex = mappedThings.get(tagThingId)) != null){
			thingsList.get(thingIndex).getTags().add(rs.getString("tags.tag"));
		}
		
		
		if(commentThingId != null && commentId != null && (thingIndex = mappedThings.get(commentThingId)) != null){
			if(!mappedComments.contains(commentId)){
				String text = rs.getString("c.text");
				Date date = rs.getDate("c.date");
				String author = rs.getString("c.author");
				Long userId = rs.getLong("c.userId");
				Comment c = new Comment(commentId, thingId, text, date , author, userId);
				thingsList.get(thingIndex).getComments().add(c);
				mappedComments.add(commentId);
				
			}
		}
		
		//logger.warn("AbstractThingMapper - null row mapped. Shouldn't happen");
	}
			
		
		
	
	
	public Integer addToList(AbstractThing thing){
		thingsList.add(thing);
		
		return this.thingsList.size() -1;
	}

	public List<AbstractThing> mapRows(ResultSet rs) {
		this.thingsList = new ArrayList<AbstractThing>();
		this.mappedThings = new HashMap<Long, Integer>();
		this.mappedComments = new HashSet<Long>();
		
		try {
			while(rs.next()){
				try {
					mapRow(rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this.thingsList;
		
		
	}

}
