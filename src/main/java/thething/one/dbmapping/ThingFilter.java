package thething.one.dbmapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import thething.one.dataobjects.Comment;

public class ThingFilter {
	
	protected Log logger = LogFactory.getLog(getClass());

	public enum ThingType{
		ARTICLE("a", "article"), PHOTO("p", "photo"), PHOTO_ARTICLE("pa", "photoArticle");
		
		
		private String text;
		private String description;
		
		ThingType(String text, String description) {
			this.text = text;
			this.description = description;
		}

		public String getText() {
			return this.text;
		}
		public String getDescription(){
			return this.description;
		}

		public static ThingType fromString(String text) {
			if (text != null) {
				for (ThingType b : ThingType.values()) {
					if (text.equalsIgnoreCase(b.text)) {
						return b;
					}else if(text.equalsIgnoreCase(b.description)){
						return b;
					}
				}
			}
			return null;
		}
	}
	
	Map<String, Object> bindParams;
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
	
	
	

	
	private String getTypeQuery(Map<String, Object> bindParams){
		String q = selectThings;
		q = q + a + byType;
		if(types == null){
			logger.warn("ThingFilter.types is null. Why?");
			types = new HashSet<ThingType>();
			types.addAll(Arrays.asList(ThingType.values()));
		}
		if(types.isEmpty()){
			types.addAll(Arrays.asList(ThingType.values()));
		}
		bindParams.put("types", types);
		logger.debug("getTypeQuery result: " + q);
		return q;
	}
	
	
		
	@SuppressWarnings("unchecked")
	public String createQuery(){
		if(bindParams == null){
			bindParams = new HashMap<String, Object>();
		}
		String q = getTypeQuery(bindParams);
		//logger.debug(((Set<ThingType>)bindParams.get("types")).toArray());

		
		if(userId != null){
			bindParams.put("userId", userId);
		}else{
			bindParams.put("userId", null);
		}
		
		if(thingId != null){
			q = q + a + byId + ";";
			bindParams.put("id", thingId);
			return q;
		}
		
		if(startDate != null){
			q = q + a + byStartDate;
			bindParams.put("startDate", startDate);
		}
		if(endDate != null){
			q = q + a + byEndDate;
			bindParams.put("endDate", endDate);
		}
		if(authorId != null){
			q = q + a + byAuthor;
			bindParams.put("authorId", authorId);
		}
		if(published != null){
			q = q + a + byPublished;
			bindParams.put("published", published);
		}
		
		if(inBlog != null){
			q = q + a + byInBlog;
			bindParams.put("inBlog", inBlog);
		}
		
		
		if(tags != null && !tags.isEmpty()){
			q = q + a + byTags;
			bindParams.put("tags", tags);
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
			bindParams.put("orderBy", orderBy);
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



	public Map<String, Object> getBindParams() {
		return bindParams;
	}



	public void setBindParams(Map<String, Object> bindParams) {
		this.bindParams = bindParams;
	}

	public String toString(){
		Set<ThingType> thingTypes = (HashSet<ThingType>)bindParams.get("types");
		Set<Entry<String, Object>> bp = bindParams.entrySet();
		String s = "bindParams{";
		for(Entry<String, Object> e: bp){
			s = s + "KV" + "(" + e.getKey() + ", " + e.getValue() + ")";
			
		}
		s = s + "}";
		return s;
		
	}


	
	
	
}
