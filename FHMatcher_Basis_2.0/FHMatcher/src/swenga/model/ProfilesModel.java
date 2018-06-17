package swenga.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "profiles")

public class ProfilesModel implements java.io.Serializable {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 30)
	private String firstname;
	
	@Column(nullable = false, length = 30)
	private String lastname;

	@Column(nullable = false) 
	private boolean gender;
	
	@Temporal(TemporalType.DATE)
	private Date dayOfBirth;
	
	@OneToOne(mappedBy= "profile")
	private UserModel user;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private MatchesModel matches;
	
	@Version
	long version;
	
	
	
	
	public ProfilesModel() {
		// TODO Auto-generated constructor stub
	}

	public ProfilesModel(String firstname, String lastname, boolean gender, Date dayOfBirth) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.dayOfBirth = dayOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public MatchesModel getMatches() {
		return matches;
	}

	public void setMatches(MatchesModel matches) {
		this.matches = matches;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
}
