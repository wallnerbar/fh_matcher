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
import javax.persistence.OrderBy;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "Matches")

public class MatchesModel implements java.io.Serializable {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 30)
	private String matchOne;
	
	@Column(nullable = false, length = 30)
	private String matchTwo;
	
	@Column(nullable = false, precision=6, scale=2)
	private float percentage;
	
	@OneToMany(mappedBy= "matches", fetch=FetchType.EAGER)
	@OrderBy("firstname, lastname")
	private Set<ProfilesModel> profiles;
	
	
	@Version
	long version;
	
	public MatchesModel() {
		// TODO Auto-generated constructor stub
	}

	public MatchesModel(int id, String matchOne, String matchTwo, float percentage) {
		super();
		this.id = id;
		this.matchOne = matchOne;
		this.matchTwo = matchTwo;
		this.percentage = percentage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatchOne() {
		return matchOne;
	}

	public void setMatchOne(String matchOne) {
		this.matchOne = matchOne;
	}

	public String getMatchTwo() {
		return matchTwo;
	}

	public void setMatchTwo(String matchTwo) {
		this.matchTwo = matchTwo;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	
	public Set<ProfilesModel> getProfiles() {
		return profiles;
	}
 
	public void setProfiles(Set<ProfilesModel> profiles) {
		this.profiles = profiles;
	}
 
	public void addProfile(ProfilesModel profile) {
		if (profiles==null) {
			profiles= new HashSet<ProfilesModel>();
		}
		profiles.add(profile);
	}
	

}
