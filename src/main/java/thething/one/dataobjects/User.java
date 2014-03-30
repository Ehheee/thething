package thething.one.dataobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{

	Long id;
	String userName;
	Boolean enabled;
	String email;
	Boolean confirmed;
	Date registeredDate;
	String role;
	String password;
	String salt;
	String nickName;
	Set<String> tags;
	List<GrantedAuthority> roles;
	ExternalUser externalUser;
	
	
	public User(Long id, String userName, Boolean enabled,
			String eMail, Boolean confirmed,
			Date registeredDate, String role, String password, String salt, String nickName, ExternalUser externalUser)  {
		this.id = id;
		this.userName = userName;
		this.enabled = enabled;
		this.email = eMail;
		this.confirmed = confirmed;
		this.registeredDate = registeredDate;
		this.role = role;
		this.password = password;
		this.salt = salt;
		this.nickName = nickName;
		roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(role));
		this.externalUser = externalUser;
	}
	public User() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String eMail) {
		this.email = eMail;
	}
	public Boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	public List<GrantedAuthority> getRoles() {
		return roles;
	}
	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	public String getUsername() {
		return userName;
		
	}
	public boolean isAccountNonExpired() {
		return true;
	}
	public boolean isAccountNonLocked() {
		return confirmed;
	}
	public boolean isCredentialsNonExpired() {
		return true;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public ExternalUser getExternalUser() {
		return externalUser;
	}
	public void setExternalUser(ExternalUser externalUser) {
		this.externalUser = externalUser;
	}
	public Set<String> getTags() {
		return tags;
	}
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	
	
}
