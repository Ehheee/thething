package thething.one.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import thething.one.dataobjects.AbstractThing;
import thething.one.dataobjects.Settings;
import thething.one.dataobjects.User;
import thething.one.dbmapping.ThingFilter;
import thething.one.dbmapping.ThingFilter.ThingType;

@Controller
@RequestMapping(value="/")
public class MainController extends BaseController{

	
	@RequestMapping(method = RequestMethod.GET)
	public String main(Model model, HttpServletRequest request, HttpSession session){
		Settings s = this.settingsService.getSettings("main");
		this.processStartPage(s.getStartPage(), request, session, model);
		
		
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/b/{page}")
	public String blog(@PathVariable Integer page, HttpServletRequest request, HttpSession session, Model model){
		ThingFilter filter = this.processRequest(request, session, thingTypes);
		filter.setPage(page);
		this.thingsToModel(filter, model);
		
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/b")
	public String mainBlog(HttpServletRequest request, HttpSession session, Model model){
		ThingFilter filter = this.processRequest(request, session, thingTypes);
		this.thingsToModel(filter, model);
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{identifier:[a-zA-Z]+}")
	public String byTypeOrTag(@PathVariable("identifier") String identifier, HttpSession session, HttpServletRequest request, Model model){
		checkTypeOrTag(identifier, session);
		ThingFilter filter = this.processRequest(request, session, null);
		this.thingsToModel(filter, model);
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{identifier}/{page}")
	public String byTypeOrTagPaged(@PathVariable("identifier") String identifier, @PathVariable("page") Integer page, HttpSession session, HttpServletRequest request, Model model){
		request.setAttribute("page", page);
		checkTypeOrTag(identifier, session);
		ThingFilter filter = this.processRequest(request, session, null);
		this.thingsToModel(filter, model);
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id:[0-9]+}")
	public String byId(@PathVariable("id") Long id){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AbstractThing thing;
		if(user != null){
			thing  = this.thingDao.getThingForUser(id, user.getId());
		}else{
			thing = this.thingDao.getThingForUser(id, null);
		}
		
		return null;
	}
	
	
	
	private ThingFilter checkTypeOrTag(String identifier, HttpSession session){
		ThingType type = ThingType.fromString(identifier);
		ThingFilter filter = null;
		if(type != null){
			filter = new ThingFilter();
			filter.setType(type);
		}else{
			if(this.tagDao.getTags().contains(identifier)){
				filter = new ThingFilter();
				Set<String> tags = new HashSet<String>();
				tags.add(identifier);
				filter.setTags(tags);
			}
		}
		return filter;
		
	}
	
	
	private void processBlog(HttpServletRequest request, HttpSession session, Model model){
		ThingFilter filter = this.processRequest(request, session,  null);
		this.thingsToModel(filter, model);
	}
	
	
	
	private void processStartPage(String startPage, HttpServletRequest request, HttpSession session, Model model){
		if(startPage.contains("tag:")){
			this.processRequest(request, session,  null);
			Object o = session.getAttribute("thingFilter");
			if(o != null && o instanceof ThingFilter){
				startPage = startPage.substring(4);
				String[] t = startPage.split(",");
				Set<String> tags = new HashSet<String>(Arrays.asList(t));
				ThingFilter tf = (ThingFilter)o;
				tf.setTags(tags);
				this.thingsToModel(tf, model);
			}
		}
		else if(startPage.contains("blog")){
			this.processBlog(request, session, model);
		}
		else if(startPage.contains("url:")){
			startPage = startPage.substring(4);
			startPage = "redirect:" + startPage;
		}
		else if(startPage.contains("type:")){
			ThingFilter tf = new ThingFilter();
			startPage = startPage.substring(5);
			for(String type: startPage.split(",")){
				tf.addType(ThingType.fromString(type));
				
			}
			tf.setPageSize(30);
			this.thingsToModel(tf, model);
		}
		else if(startPage.contains("thing:")){
			startPage = startPage.substring(6);
			Long id = null;
			try{
				id = Long.valueOf(startPage);
			}catch(Exception e){
				logger.error(e.getStackTrace());
			}
			this.thingDao.getThingForUser(id, null);
		logger.warn("StartPage not defined correctly: " + startPage);
		
		}
		
	}
	
}
