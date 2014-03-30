package thething.one.services;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.client.RestTemplate;

import thething.one.dataobjects.ExternalUser;
import thething.one.dataobjects.LegacyGoogleProfile;
import thething.one.dataobjects.ExternalUser.ExternalLoginType;
import thething.one.dataobjects.User;
import thething.one.dbmapping.ExternalUserDao;
import thething.one.dbmapping.UserDao;


public class ExternalUserService {
	
	final static String FACEBOOK_CLIENT_ID = "342473172446483";
	final static String FACEBOOK_CLIENT_SECRET = "afea34faff8db8ac3c00bfea5dbecaae";
	final static String GOOGLE_CLIENT_ID = "308886292225.apps.googleusercontent.com";
	final static String GOOGLE_CLIENT_SECRET = "f8EUJJZIRQ356d9-NWJQZxkF";

	protected Log logger = LogFactory.getLog(getClass());
	
	public ExternalUser initializeExternalUser(ExternalUser externalUser){
		String accessToken = externalUser.getAccessToken();
		String refreshToken = externalUser.getRefreshToken();
		Map<String, String> tokens = new HashMap<String, String>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		return this.initializeExternalUser(tokens, externalUser.getLoginType());
	}
	
	public ExternalUser initializeExternalUser(Map<String, String> tokens, ExternalLoginType loginType){
		User user = null;
		ExternalUser externalUser = null;
		switch(loginType){
		case FACEBOOK:
			externalUser = generateFromFacebook(tokens);	
			break;
		case GOOGLE:
			externalUser = generateFromGoogle(tokens);
			
			break;
		}
		
		if(externalUser != null){
			if(externalUser.getUser() == null){
				user = insertNewUser(externalUser);
				externalUser.setUser(user);
			}else{
				user = externalUser.getUser();
			}
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication a = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
			context.setAuthentication(a);
		}
		return externalUser;
		
	}
	
	private User insertNewUser(ExternalUser externalUser){
		User user = new User();
		user.setConfirmed(true);
		user.setEmail(externalUser.getEmail());
		user.setEnabled(true);
		user.setExternalUser(externalUser);
		user.setRegisteredDate(new Date());
		user.setRole("ROLE_USER");
		Long id = userDao.insertUser(user);
		user.setId(id);
		return user;
	}
	
	private ExternalUser generateFromGoogle(Map<String, String> tokens){
		ExternalUser externalUser = null;
		RestTemplate restTemplate = new RestTemplate();
		Boolean attemptRefreshToken = false;
		LegacyGoogleProfile profile = null;
		GitHubTokenResponse oAuthResponse = null;
		try{
			profile = restTemplate.getForObject("https://www.googleapis.com/oauth2/v2/userinfo?access_token={acessToken}", LegacyGoogleProfile.class, tokens.get("accessToken"));
		}
		catch(Exception e){
			logger.info(e.getStackTrace());
			attemptRefreshToken = true;
		}
		try{
			if(attemptRefreshToken){
				oAuthResponse = attemptRefreshToken(tokens.get("refreshToken"));
				profile = restTemplate.getForObject("https://www.googleapis.com/oauth2/v2/userinfo?access_token={acessToken}", LegacyGoogleProfile.class, oAuthResponse.getAccessToken());
			}
		}catch(Exception e){
			logger.info(e.getStackTrace());
		}
		
		if(profile != null && profile.getEmail() != null && profile.getId() != null){
			ExternalUser oldUser = externalUserDao.getUser(profile.getId(), ExternalLoginType.GOOGLE);
			if(oldUser != null){
				externalUser = oldUser;
			}else{
				externalUser = new ExternalUser();
			}
			if(oAuthResponse != null){
				externalUser.setAccessToken(oAuthResponse.getAccessToken());
				externalUser.setRefreshToken(oAuthResponse.getRefreshToken());
				externalUser.setTokenExpireDate(new Date(System.currentTimeMillis() + oAuthResponse.getExpiresIn()*1000));
			}
			externalUser.setFullName(profile.getName());
			if(oldUser != null){
				externalUserDao.update(externalUser);
			}else{
				externalUserDao.insert(externalUser);
			}
		}
		return externalUser;
	}
	
	
	public GitHubTokenResponse attemptRefreshToken(String refreshToken) throws Exception{
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthClientRequest request = OAuthClientRequest
		.tokenProvider(OAuthProviderType.GOOGLE)
		.setRefreshToken(refreshToken)
		.setGrantType(GrantType.REFRESH_TOKEN)
		.setClientId("308886292225.apps.googleusercontent.com")
		.setClientSecret("f8EUJJZIRQ356d9-NWJQZxkF")
		.buildQueryMessage();
		GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);
		return oAuthResponse;
	}
	
	
	public ExternalUser generateFromFacebook(Map<String, String> tokens){
		ExternalUser externalUser = null;
		
		FacebookTemplate facebookTemplate = new FacebookTemplate(tokens.get("accessToken"));
		UserOperations userOperations = facebookTemplate.userOperations();
		FacebookProfile profile = userOperations.getUserProfile();
		if(profile != null && profile.getId() != null){
			ExternalUser oldUser = externalUserDao.getUser(userOperations.getUserProfile().getId(), ExternalLoginType.FACEBOOK);
			if(oldUser != null){
				externalUser = oldUser;
			}
			externalUser.setAccessToken(tokens.get("accessToken"));
			externalUser.setFullName(profile.getName());
			if(oldUser != null){
				externalUserDao.update(externalUser);
			}else{
				externalUserDao.insert(externalUser);
			}
		}
		return externalUser;
	}
	
	
	public GitHubTokenResponse getLongLivedToken(String accessToken) throws Exception{
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthClientRequest request = OAuthClientRequest
		.tokenProvider(OAuthProviderType.FACEBOOK)
		.setClientId(FACEBOOK_CLIENT_ID)
		.setClientSecret(FACEBOOK_CLIENT_SECRET)
		.setParameter("grant_type", "fb_exchange_token")
		.setParameter("fb_exchange_token", accessToken)
		.buildQueryMessage();
		GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);
		return oAuthResponse;
		
	}
	
	
	public Map<String, String> getTokens(String code, ExternalLoginType loginType,String hostName){
		OAuthClientRequest request = null;
		String accessToken = null;
		String refreshToken = null;
		Map<String, String> tokens = Collections.emptyMap();
		try{
			switch(loginType){
			case FACEBOOK:
				request = OAuthClientRequest
                .tokenProvider(OAuthProviderType.FACEBOOK)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(FACEBOOK_CLIENT_ID)
                .setClientSecret(FACEBOOK_CLIENT_SECRET)
                .setRedirectURI(hostName)
                .setCode(code)
                .buildQueryMessage();
				break;
			case GOOGLE:
				request = OAuthClientRequest
				.tokenProvider(OAuthProviderType.GOOGLE)
				.setGrantType(GrantType.AUTHORIZATION_CODE)
				.setClientId(GOOGLE_CLIENT_ID)
				.setClientSecret(GOOGLE_CLIENT_SECRET)
				.setCode(code)
				.buildQueryMessage();
				break;
			}
			
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request,
					GitHubTokenResponse.class);
			accessToken = oAuthResponse.getAccessToken();
			refreshToken = oAuthResponse.getRefreshToken();
			if(loginType.equals(ExternalLoginType.FACEBOOK)){
				oAuthResponse = this.getLongLivedToken(accessToken);
				accessToken = oAuthResponse.getAccessToken();
				refreshToken = oAuthResponse.getRefreshToken();
			}
		}catch(Exception e){
			logger.error(e.getStackTrace());
		}
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		return tokens;
	}
	
	
	
	
	
	public void processExternal(ExternalLoginType loginType, String hostName, HttpServletResponse response){
		if(loginType == null){
			return;
		}
		OAuthClientRequest request = null;
		try {
			switch(loginType){
			case FACEBOOK:
				request = OAuthClientRequest
				   .authorizationProvider(OAuthProviderType.FACEBOOK)
				   .setClientId(FACEBOOK_CLIENT_ID)
				   .setRedirectURI(hostName + "/user/ext2/f")
				   .buildQueryMessage();
				break;
			case GOOGLE:
				request = OAuthClientRequest
				   .authorizationProvider(OAuthProviderType.GOOGLE)
				   .setScope("https://www.googleapis.com/auth/userinfo.profile")
				   .setClientId(GOOGLE_CLIENT_ID)
				   .setRedirectURI(hostName + "/user/ext2/g")
				   .buildQueryMessage();
				break;
			}
			if(request != null){
				response.sendRedirect(request.getLocationUri());
			}

		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
		
	}
	
	@Autowired
	ExternalUserDao externalUserDao;
	public void setExternalUserDao(ExternalUserDao externalUserDao){
		this.externalUserDao = externalUserDao;
	}
	
	@Autowired
	UserDao userDao;
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
}
