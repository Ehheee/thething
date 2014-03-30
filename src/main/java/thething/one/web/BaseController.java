package thething.one.web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;


import thething.one.dataobjects.AbstractThing;
import thething.one.dbmapping.CommentDao;
import thething.one.dbmapping.TagDao;
import thething.one.dbmapping.ThingDao;
import thething.one.dbmapping.ThingFilter;
import thething.one.dbmapping.ThingFilter.ThingType;
import thething.one.services.SettingsService;

public class BaseController {

	protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	protected Log logger = LogFactory.getLog(getClass());
	protected List<ThingType> thingTypes = Arrays.asList(ThingType.values());
	
	public ThingFilter processRequest(HttpServletRequest request, HttpSession session,  List<ThingType> types){
		Map<String, String[]> params = request.getParameterMap();
		printRequestParams(params);
		ThingFilter tf = checkFilter(params, session);
		
		if(types != null){
			tf.setTypes(new HashSet<ThingType>(types));
		}
		session.setAttribute("thingFilter", tf);
		return tf;
	}
	
	public ThingFilter checkFilter(Map<String, String[]> params, HttpSession session){
		Object  o = session.getAttribute("thingFilter");
		ThingFilter thingFilter;
		if(o != null && o instanceof ThingFilter){
			thingFilter = (ThingFilter)o;
		}else{
			thingFilter = new ThingFilter();
		}
		
		try{

				
			if(params.get("startDate") != null){
				Date startDate = dateFormat.parse(params.get("startDate")[0]);
				thingFilter.setStartDate(startDate);
			}else{
				thingFilter.setStartDate(null);
			}
			
			if(params.get("endDate") != null){
				Date endDate = dateFormat.parse(params.get("endDate")[0]);
				thingFilter.setEndDate(endDate);
			}else{
				thingFilter.setEndDate(null);
			}
			
			if(params.get("authorId") != null){
				Integer authorId = Integer.parseInt(params.get("authorId")[0]);
				thingFilter.setAuthorId(authorId);
			}else{
				thingFilter.setAuthorId(null);
			}
			
			if(params.get("published") != null){
				Boolean published = Boolean.parseBoolean(params.get("published")[0]);
				thingFilter.setPublished(published);
			}else{
				thingFilter.setPublished(null);
			}
			
			if(params.get("tags") != null){
				Set<String> tags = new HashSet<String>(Arrays.asList(params.get("tags")));
				thingFilter.setTags(tags);
			}else{
				thingFilter.setTags(null);
			}
			
			if(params.get("userId") != null){
				Long userId = Long.valueOf(params.get("userId")[0]);
				thingFilter.setUserId(userId);
			}else{
				thingFilter.setUserId(null);
			}
			
			if(params.get("orderBy") != null){
				//TODO validate here?
				thingFilter.setOrderBy(params.get("orderBy")[0]);
				
			}else{
				thingFilter.setOrderBy(null);
			}
			
			if(params.get("orderHow") != null){
				thingFilter.setOrderHow(params.get("orderHow")[0]);
			}else{
				thingFilter.setOrderHow(null);
			}
			
			
			logger.info("thingFilter processed from request :" + thingFilter);
			
		}catch(Exception e){
			logger.error(e.getStackTrace());
		}
		return thingFilter;
	}
	
	
	public void thingsToModel(ThingFilter filter, Model model){
		List<AbstractThing> things = thingDao.getThings(filter);
		model.addAttribute("things",	 things);
	}
	
	
	private void printRequestParams(Map<String, String[]> params){
		StringBuilder sb =  new StringBuilder("Received requestParams: {");
		for(Entry<String, String[]> e: params.entrySet()){
			String key = e.getKey();
			String[] value = e.getValue();
			sb.append("(K: " + key + ", V: " + value + ")");
		}
		sb.append("}");
		logger.info(sb.toString());
	}
	
	
	@Autowired 
	protected CommentDao commentDao;
	
	@Autowired
	protected SettingsService settingsService;
	
	@Autowired
	protected ThingDao thingDao;
	
	@Autowired 
	protected TagDao tagDao;
}
