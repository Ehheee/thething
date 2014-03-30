package thething.one.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import thething.one.dataobjects.Comment;
import thething.one.dataobjects.User;


@Controller
@RequestMapping(value = "/comment")
public class CommentController extends BaseController{

	
	@RequestMapping(method = RequestMethod.POST)
	public Long postComment(@ModelAttribute("comment") Comment comment){
		Long id = comment.getId();
		
		if(id == null){
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(user != null){
				comment.setUserId(user.getId());
			}
			id = this.commentDao.insertComment(comment);
		}else{
			this.commentDao.updateComment(comment);
		}
		
		return id;
		
	}
	
	@RequestMapping(value = "/delete/{id}")
	public int deleteComment(@PathVariable("id") Long id){
		Comment c = this.commentDao.getComment(id);
		if(c != null){
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long commentUserId = c.getUserId();
			if(user != null){
				if(user.getId() == commentUserId || "ROLE_ADMIN".equals(user.getRole())){
					this.commentDao.deleteComment(id);
					return 1;
				}
			}
		}
		
		return 0;
	}
	

}
