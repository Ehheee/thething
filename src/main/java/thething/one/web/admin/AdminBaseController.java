package thething.one.web.admin;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import thething.one.dataobjects.AbstractThing;
import thething.one.dataobjects.Article;
import thething.one.dataobjects.Comment;
import thething.one.dataobjects.Photo;
import thething.one.dataobjects.PhotoArticle;
import thething.one.dbmapping.ThingFilter.ThingType;
import thething.one.web.BaseController;

public class AdminBaseController extends BaseController{

	
	protected Long saveThing(MultipartHttpServletRequest request){
		Long id = Long.valueOf(request.getParameter("id"));
		ThingType type = ThingType.fromString(request.getParameter("type"));
		String title = request.getParameter("title");
		Date date;
		try {
			date  = dateFormat.parse(request.getParameter("date"));
		} catch (ParseException e) {
			logger.error(e.getStackTrace());
			date = new Date();
		}
		
		String description = request.getParameter("description");
		
		Long authorId = Long.valueOf(request.getParameter("authorId"));
		Set<String> tags = new HashSet<String>(Arrays.asList(request.getParameterValues("tags")));
		Boolean priv = Boolean.valueOf(request.getParameter("priv"));
		Boolean published = Boolean.valueOf(request.getParameter("published"));
		Integer order = Integer.valueOf(request.getParameter("order"));
		String text = request.getParameter("text");
		Boolean inBlog = Boolean.valueOf(request.getParameter("inBlog"));
		AbstractThing thing = null;
		String fileName = this.saveFile(request, type, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
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
		if(id != null){
			thingDao.updateThing(thing);
		}else{
			id = thingDao.insertThing(thing);
		}
		
		
		return id;
	}
	
	
	
}



