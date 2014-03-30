package thething.one.web.admin;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import thething.one.dataobjects.AbstractThing;
import thething.one.dataobjects.Article;
import thething.one.dataobjects.Comment;
import thething.one.dataobjects.Photo;
import thething.one.dataobjects.PhotoArticle;
import thething.one.dbmapping.ThingFilter.ThingType;
import thething.one.web.BaseController;

public class AdminBaseController extends BaseController{

	
	protected Long saveThing(HttpServletRequest request){
		Long id = Long.valueOf(request.getParameter("id"));
		ThingType type = ThingType.fromString(request.getParameter("type"));
		String title = request.getParameter("title");
		Date date;
		try {
			date  = dateFormat.parse(request.getParameter("date"));
		} catch (ParseException e) {
			logger.error(e.getStackTrace());
			date = null;
		}
		
		String description = request.getParameter("description");
		
		Long authorId = Long.valueOf(request.getParameter("authorId"));
		Set<String> tags = new HashSet<String>(Arrays.asList(request.getParameterValues("tags")));
		Boolean priv = Boolean.valueOf(request.getParameter("priv"));
		Boolean published = Boolean.valueOf(request.getParameter("published"));
		Integer order = Integer.valueOf(request.getParameter("order"));
		String text = request.getParameter("text");
		String fileName = request.getParameter("fileName");
		Boolean inBlog = Boolean.valueOf(request.getParameter("inBlog"));
		AbstractThing thing = null;
		if(id != null){
			thing = thingDao.getThingForUpdate(id);
			thing
			.setAuthorId(authorId)
			.setDate(date)
			.setDescription(description)
			.setOrder(order)
			.setPriv(priv)
			.setPublished(published)
			.setInBlog(inBlog)
			.setTags(tags)
			.setTitle(title);
			
			switch(type){
			case ARTICLE:
				if(!(thing instanceof Article)){
					thing = new Article(id, title, date, description, authorId, tags, null, priv, published, inBlog, null, text, order);
				}else{
					((Article)thing).setText(text);
				}
				break;
			case PHOTO:
			case PHOTO_ARTICLE:
				if(thing instanceof Photo && type.equals(ThingType.PHOTO)){
					((Photo)thing).setFileName(fileName);
				}else if(thing instanceof PhotoArticle && type.equals(ThingType.PHOTO_ARTICLE)){
					((PhotoArticle)thing).setText(text).setFileName(fileName);
				}else if(type.equals(ThingType.PHOTO)){
					thing = new Photo(id, title, date, description, authorId, tags, null, priv, published, inBlog, null, fileName, order);
				}else if(type.equals(ThingType.PHOTO_ARTICLE)){
					thing = new PhotoArticle(id, title, date, description, authorId, tags, null, priv, published, inBlog, null, fileName, text, order);
				}
			}
			thingDao.updateThing(thing);
		}else{
			switch(type){
			case ARTICLE:
				thing = new Article(id, title, date, description, authorId, tags, null, priv, published, inBlog, null, text, order);
				break;
			
			case PHOTO:
				thing = new Photo(id, title, date, description, authorId, tags, null, priv, published, inBlog, null, fileName, order);
				break;
			case PHOTO_ARTICLE:
				thing = new PhotoArticle(id, title, date, description, authorId, tags, null, priv, published, inBlog, null, fileName, text, order);
				break;
			}
		id = thingDao.insertThing(thing);
		}
		
		
		return id;
	}
	
	
	
}



