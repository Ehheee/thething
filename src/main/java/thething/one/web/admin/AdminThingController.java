package thething.one.web.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
@RequestMapping(value="/thing")
public class AdminThingController extends AdminBaseController{

	
	@RequestMapping(value = "/delete/{id}")
	public int deleteThing(@PathVariable("id") Long id){
		thingDao.deleteThing(id);
		
		return 1;
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public Long insertThing(MultipartHttpServletRequest request){
		
		
		
		return this.saveThing(request);
	}
	
	
}
