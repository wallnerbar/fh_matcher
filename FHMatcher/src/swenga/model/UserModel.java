package swenga.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table (name = "users")
public class UserModel implements java.io.Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "userName", nullable = false, length = 30)
	private String userName;

	@Column(name = "password", nullable = false, length = 60)
	private String password;
 
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private ProfilesModel profile;
	
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	private Set<UserRoleModel> userRoles;
	
	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	public UserModel(String userName, String password, boolean enabled) {
		super();
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setUserRoles(Set<UserRoleModel> userRoles) {
		this.userRoles = userRoles;
	}
 
	public void addUserRole(UserRoleModel userRole) {
		if (userRoles==null) userRoles = new HashSet<UserRoleModel>();
		userRoles.add(userRole);
	}
 
	public Set<UserRoleModel> getUserRoles() {
		return userRoles;
	}
 
	public void encryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);		
	}

	public ProfilesModel getProfile() {
		return profile;
	}

	public void setProfile(ProfilesModel profile) {
		this.profile = profile;
	}
}
