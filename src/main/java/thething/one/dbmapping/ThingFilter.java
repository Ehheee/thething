package thething.one.dbmapping;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import thething.one.dataobjects.Article;
import thething.one.dataobjects.Comment;
import thething.one.dataobjects.Photo;
import thething.one.dataobjects.PhotoArticle;

public class ThingFilter {
	
	protected Log logger = LogFactory.getLog(getClass());

	public enum ThingType{
		ARTICLE("a", "article", Article.class), 
		PHOTO("p", "photo", Photo.class), 
		PHOTO_ARTICLE("pa", "photoArticle", PhotoArticle.class);
		
		
		private String identifier;
		private String description;
		private Class<?> clazz;
		private static List<ThingType> allValues = Arrays.asList(ThingType.values());
		
		ThingType(String identifier, String description, Class<?> clazz) {
			this.identifier = identifier;
			this.description = description;
			this.clazz = clazz;
		}

		public String getIdentifier() {
			return this.identifier;
		}
		public String getDescription(){
			return this.description;
		}
		public Class<?> getThingClass(){
			return this.clazz;
		}
		
		public String toString(){
			return this.identifier;
		}
		
		public static List<ThingType> getAllValues(){
			return allValues;
		}

		public static ThingType fromString(String text) {
			if (text != null) {
				for (ThingType b : allValues) {
					if (text.equalsIgnoreCase(b.identifier)) {
						return b;
					}else if(text.equalsIgnoreCase(b.description)){
						return b;
					}
				}
			}
			return null;
		}
	}
	
	MapSqlParameterSource bindParams;
	Date startDate;
	Date endDate;
	Integer authorId;
	Set<ThingType> types;
	Boolean published;
	Boolean inBlog;
	Set<String> tags;
	Long userId;
	String orderBy;
	String orderHow;
	Integer page;
	Integer pageSize;
	Long thingId;
	
	
	/*select t.*, tags.tag from things t left join thingTags tags on tags.thingId = t.id left join comments c on t.id = c.thingId left join userTags ut on ut.tag = tags.tag where t.type in (1, 2, 3)  AND t.published = true and t.priv != 1 or ut.userId=2 */
	
	final private String a = " AND ";
	final private String selectThings = "select t.*, tags.*, c.* from things t left join thingTags tags on tags.thingId = t.id left join comments c on t.id = c.thingId left join userTags ut on ut.tag = tags.tag where t.priv != 1 OR ut.userId = :userId";
	final private String byStartDate = "t.date >= :startDate";
	final private String byEndDate = "t.date <= :endDate";
	final private String byAuthor = "t.authorId = :authorId";
	final private String byType = "t.type in (:types)";
	final private String byPublished = "t.published = :published";
	final private String byInBlog = "t.inBlog = :inBlog";
	final private String byTags = "tags.tag in (:tags)";
	final private String byId = "id = :id";
	
	
	

	
	private String getTypeQuery(MapSqlParameterSource bindParams){
		String q = selectThings;
		q = q + a + byType;
		if(types == null){
			types = new HashSet<ThingType>();
			types.addAll(ThingType.getAllValues());
		}else if(types.isEmpty()){
			types.addAll(Arrays.asList(ThingType.values()));
		}
		bindParams.addValue("types", types);
		logger.debug("getTypeQuery result: " + q);
		return q;
	}
	
	
		
	@SuppressWarnings("unchecked")
	public String createQuery(){
		if(bindParams == null){
			bindParams = new MapSqlParameterSource();
			bindParams.registerSqlType("types", Types.VARCHAR);
		}
		String q = getTypeQuery(bindParams);

		
		if(userId != null){
			bindParams.addValue("userId", userId);
		}else{
			bindParams.addValue("userId", null);
		}
		
		if(thingId != null){
			q = q + a + byId + ";";
			bindParams.addValue("id", thingId);
			return q;
		}
		
		if(startDate != null){
			q = q + a + byStartDate;
			bindParams.addValue("startDate", startDate);
		}
		if(endDate != null){
			q = q + a + byEndDate;
			bindParams.addValue("endDate", endDate);
		}
		if(authorId != null){
			q = q + a + byAuthor;
			bindParams.addValue("authorId", authorId);
		}
		if(published != null){
			q = q + a + byPublished;
			bindParams.addValue("published", published);
		}
		
		if(inBlog != null){
			q = q + a + byInBlog;
			bindParams.addValue("inBlog", inBlog);
		}
		
		
		if(tags != null && !tags.isEmpty()){
			q = q + a + byTags;
			bindParams.addValue("tags", tags);
		}
		
		
		/*
		if(userId != null){
			q = q.replace("select_userTags", " left join userTags ut on ut.tag = tags.tag ");
			q = q + a + byUserTags;
			bindParams.put("userId", userId);
		}		else{
			q = q.replace("select_userTags", " ");
		}
		*/
		
		if(orderBy != null){
			q = q + " order by :orderBy " + orderHow + ", c.date";
			bindParams.addValue("orderBy", orderBy);
		}else{
			q = q + " order by t.order " + orderHow + ", c.date";
		}
		
		if(page != null && pageSize != null){
			q = q + " limit " + (page*pageSize) + "," + (page*pageSize+pageSize) ;
		}
		if(page == null && pageSize != null){
			q = q + " limit 0," + pageSize;
		}
		
		
		q = q + ";";
		return q;
		
	}
	
	
	public Long getThingId() {
		return thingId;
	}



	public void setThingId(Long thingId) {
		this.thingId = thingId;
	}



	public void setType(ThingType newType){
		this.types = new HashSet<ThingType>();
		this.types.add(newType);
	}
	
	public void addType(ThingType newType){
		this.types.add(newType);
	}
	
	
	public ThingFilter(){
		types = new HashSet<ThingType>();
	}


	

	public Set<String> getTags() {
		return tags;
	}



	public void setTags(Set<String> tags) {
		this.tags = tags;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	
	
	public String getOrderHow() {
		return orderHow;
	}



	public void setOrderHow(String orderHow) {
		this.orderHow = orderHow;
	}



	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}



	public String getOrderBy() {
		return orderBy;
	}


	
	



	public Integer getPage() {
		return page;
	}



	public void setPage(Integer page) {
		this.page = page;
	}



	public Integer getPageSize() {
		return pageSize;
	}



	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public Integer getAuthorId() {
		return authorId;
	}



	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}





	public Boolean getPublished() {
		return published;
	}



	public void setPublished(Boolean published) {
		this.published = published;
	}



	public Set<ThingType> getTypes() {
		return types;
	}



	public void setTypes(HashSet<ThingType> types) {
		this.types = types;
	}

	public MapSqlParameterSource getBindParams() {
		return bindParams;
	}
	public void setBindParams(MapSqlParameterSource bindParams) {
		this.bindParams = bindParams;
	}



	public String toString(){
		Map<String, Object> bp = bindParams.getValues();
		String s = "bindParams{";
		for(Entry<String, Object> e: bp.entrySet()){
			s = s + "KV" + "(" + e.getKey() + ", " + e.getValue() + ")";
			
		}
		s = s + "}";
		return s;
		
	}


	
	
	
}
