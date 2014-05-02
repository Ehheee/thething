package thething.one.dataobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import thething.one.dbmapping.ThingFilter.ThingType;

public abstract class AbstractThing {

	
	Long id;
	String title;
	Date date;
	String description;
	Long authorId;
	Set<String> tags;
	List<Comment> comments;		//Can be null
	Boolean priv;
	Boolean published;
	Boolean inBlog;
	Integer commentCount;
	Integer order;
	ThingType type;
	
	
	
	public AbstractThing(){
		this.comments = new ArrayList<Comment>();
	}
	
	
	public AbstractThing(Long id, String title, Date date, String description,
			Long authorId, Set<String> tags, List<Comment> comments, Boolean priv, Boolean published, Boolean inBlog, Integer commentCount, Integer order) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.description = description;
		this.authorId = authorId;
		this.tags = tags;
		if(comments != null){
			this.comments = comments;
		}else{
			this.comments = new ArrayList<Comment>();
		}
		this.priv = priv;
		this.published = published;
		this.inBlog = inBlog;
		this.commentCount = commentCount;
		this.order = order;
	}
	
	
	public String getDescription() {
		return description;
	}
	public AbstractThing setDescription(String description) {
		this.description = description;
		return this;
	}
	
	
	public Long getId() {
		return id;
	}
	public AbstractThing setId(Long id) {
		this.id = id;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public AbstractThing setTitle(String title) {
		this.title = title;
		return this;
	}
	public Date getDate() {
		return date;
	}
	public AbstractThing setDate(Date date) {
		this.date = date;
		return this;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public AbstractThing setAuthorId(Long authorId) {
		this.authorId = authorId;
		return this;
	}
	public Set<String> getTags() {
		return tags;
	}
	public AbstractThing setTags(Set<String> tags) {
		this.tags = tags;
		return this;
	}
	
	public List<Comment> getComments() {
		return comments;
	}


	public AbstractThing setComments(List<Comment> comments) {
		this.comments = comments;
		return this;
	}


	public Boolean getPriv() {
		return priv;
	}
	public AbstractThing setPriv(Boolean priv) {
		this.priv = priv;
		return this;
	}
	public Boolean getPublished() {
		return published;
	}
	public AbstractThing setPublished(Boolean published) {
		this.published = published;
		return this;
	}
	public Boolean getInBlog() {
		return inBlog;
	}
	public AbstractThing setInBlog(Boolean inBlog) {
		this.inBlog = inBlog;
		return this;
	}
	public Integer getCommentCount() {
		return commentCount;
	}


	public AbstractThing setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
		return this;
	}
	
	
	public Integer getOrder() {
		return order;
	}
	public AbstractThing setOrder(Integer order) {
		this.order = order;
		return this;
	}
	
	
	
	
	public ThingType getType() {
		return type;
	}
	public AbstractThing setType(ThingType type) {
		this.type = type;
		return this;
	}


	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append(getType().getDescription() + "{")
		.append("id= " + getId() + ", ")
		.append("authorId=" + this.authorId + ", ")
		.append("commentCount=" + this.commentCount + ", ")
		.append("date=" + this.date + ", ")
		.append("title=" + this.title + ", ");
		
		switch(getType()){
		case ARTICLE:
			Article a = (Article)this;
			result.append("text=" + a.getText() + "}");
			break;
		case PHOTO:
			Photo p = (Photo)this;
			result.append("fileName=" + p.getFileName() + "}");
			break;
			
		case PHOTO_ARTICLE:
			PhotoArticle pa = (PhotoArticle)this;
			result.append("text=" + pa.getText() + "}");
			result.append("fileName=" + pa.getFileName() + "}");
			break;
		}
		
		return result.toString();
	}
	
}
