package daibieuquochoi.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accountid")
	private Long id;

	@Column(name = "accountname")
	private String accountName;

	@Column(name = "password")
	private String password;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "dateofbirth")
	private Date dateOfBirth;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "position")
	private String position;

	@Column(name = "candidateplace")
	private String candidateplace;

	@Column(name = "status")
	private String status;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<FeedbackEntity> feedback = new HashSet<FeedbackEntity>();

	@ManyToOne
	@JoinColumn(name = "agencyid")
	private AgencyEntity agency;

	@ManyToOne
	@JoinColumn(name = "roleid")
	private RoleEntity role;

	public AccountEntity() {
		super();
	}

	public AccountEntity(Long id, String accountName, String password, String fullname, Date dateOfBirth, String avatar,
			String position, String candidateplace, String status, Set<FeedbackEntity> feedback, AgencyEntity agency,
			RoleEntity role) {
		super();
		this.id = id;
		this.accountName = accountName;
		this.password = password;
		this.fullname = fullname;
		this.dateOfBirth = dateOfBirth;
		this.avatar = avatar;
		this.position = position;
		this.candidateplace = candidateplace;
		this.status = status;
		this.feedback = feedback;
		this.agency = agency;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCandidateplace() {
		return candidateplace;
	}

	public void setCandidateplace(String candidateplace) {
		this.candidateplace = candidateplace;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<FeedbackEntity> getFeedback() {
		return feedback;
	}

	public void setFeedback(Set<FeedbackEntity> feedback) {
		this.feedback = feedback;
	}

	public AgencyEntity getAgency() {
		return agency;
	}

	public void setAgency(AgencyEntity agency) {
		this.agency = agency;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

}
