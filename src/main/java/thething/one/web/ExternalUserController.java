package thething.one.web;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import thething.one.dataobjects.ExternalUser;
import thething.one.dataobjects.ExternalUser.ExternalLoginType;
import thething.one.dbmapping.ExternalUserDao;
import thething.one.dbmapping.ThingFilter.ThingType;
import thething.one.services.ExternalUserService;

@Controller
@RequestMapping("/user")
public class ExternalUserController extends BaseController {

	
	
	
	@RequestMapping(value = "/ext1", method = RequestMethod.GET)
	public void checkExternal(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		String externalLogin = request.getParameter("externalLogin");
		if(externalLogin != null){
			ExternalLoginType loginType = ExternalLoginType.fromString(externalLogin);
			StringBuffer hostName = new StringBuffer();
			hostName.append(request.getScheme()).append("://").append(request.getServerName());
			session.setAttribute("redirectTo", request.getHeader("Referer"));
			
			this.externalUserService.processExternal(loginType, hostName.toString(), response);
				
		}
	}
	
	@RequestMapping(value="/ext2/{loginType}", method = RequestMethod.GET)
	public void ReceiveExternal (@PathVariable("loginType") String externalLogin,HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		ExternalLoginType loginType = ExternalLoginType.fromString(externalLogin);
		String code = request.getParameter("code");
		StringBuffer hostName = new StringBuffer();
		hostName.append(request.getScheme()).append("://").append(request.getServerName());
		Map<String, String> tokens = this.externalUserService.getTokens(code, loginType, hostName.toString());
		ExternalUser externalUser = this.externalUserService.initializeExternalUser(tokens, loginType);
		String redirect = (String) session.getAttribute("redirectTo");
		Cookie cookie = new Cookie("code", externalUser.getId().toString());
		response.addCookie(cookie);
		try{
			if(redirect != null){
				response.sendRedirect(redirect);
			}else{
				response.sendRedirect("/");
			}
		}catch(Exception e){
			logger.warn(e.getStackTrace());
		}
		
	}
	
	@RequestMapping(value="/ajax/checkExternal", method = RequestMethod.POST)
	public ExternalUser ajaxCheckExternal(HttpServletRequest request){
		ExternalUser externalUser = null;
		Long id = Long.valueOf(request.getParameter("code"));
		externalUser = externalUserDao.getUser(id, false);
		if(externalUser != null){
			externalUser = externalUserService.initializeExternalUser(externalUser);
		}
		
		return externalUser;
	}
	
	
	
	@Autowired
	ExternalUserDao externalUserDao;
	public void setExternalUserDao(ExternalUserDao externalUserDao){
		this.externalUserDao = externalUserDao;
	}
	
	@Autowired
	ExternalUserService externalUserService;
	public void setExternalUserService(ExternalUserService externalUserService){
		this.externalUserService = externalUserService;
	}
}
