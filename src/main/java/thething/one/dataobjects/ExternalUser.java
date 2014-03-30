package thething.one.dataobjects;

import java.util.Date;


public class ExternalUser {

	public enum ExternalLoginType{
		FACEBOOK("f", "facebook"), GOOGLE("g", "google");
		
		
		private String text;
		private String description;
		
		ExternalLoginType(String text, String description) {
			this.text = text;
			this.description = description;
		}

		public String getText() {
			return this.text;
		}
		public String getDescription(){
			return this.description;
		}

		public static ExternalLoginType fromString(String text) {
			if (text != null) {
				for (ExternalLoginType b : ExternalLoginType.values()) {
					if (text.equalsIgnoreCase(b.text)) {
						return b;
					}else if(text.equalsIgnoreCase(b.description)){
						return b;
					}
				}
			}
			return null;
		}
	
	}
	
	
	Long id;
	ExternalLoginType loginType;
	String externalId;
	String fullName;
	String email;
	String refreshToken;
	String accessToken;
	Date tokenExpireDate;
	User user;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ExternalLoginType getLoginType() {
		return loginType;
	}
	public void setLoginType(ExternalLoginType loginType) {
		this.loginType = loginType;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Date getTokenExpireDate() {
		return tokenExpireDate;
	}
	public void setTokenExpireDate(Date tokenExpireDate) {
		this.tokenExpireDate = tokenExpireDate;
	}
	
	
	
	
}
