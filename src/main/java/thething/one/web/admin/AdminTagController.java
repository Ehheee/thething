package thething.one.web.admin;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thething.one.dbmapping.BaseDao.ObjectType;

@Controller
@RequestMapping(value = "/tag")
public class AdminTagController extends AdminBaseController{
	
	/*
nxlksanxsa

xsa
use tagdao to delete or add tag references

*/
	
	@RequestMapping(value = "/{type}/{id}")
	public int updateTagReferences(@PathVariable("type") String object, 
									@PathVariable("id") Long id,
									@RequestParam("tags") List<String> tags
									){
		ObjectType o = ObjectType.valueOf(object.toUpperCase());
		this.tagDao.updateTagReferences(new HashSet<String>(tags), id, o);
		return 1;
	}
	
	
}
