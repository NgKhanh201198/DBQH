package daibieuquochoi.backend.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feedbackid")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "contents")
	private String contents;

	@Column(name = "files")
	private String files;

	@Column(name = "dateofbirth")
	private Date dateOfBirth;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "position")
	private String position;

	@Column(name = "candidateplace")
	private String candidateplace;

	@ManyToOne
	@JoinColumn(name = "accountid")
	private AccountEntity account;

	@ManyToOne
	@JoinColumn(name = "recommendationsid")
	private RecommendationsEntity recommendations;

	public FeedbackEntity() {
		super();
	}

	public FeedbackEntity(Long id, String title, String contents, String files, Date dateOfBirth, String avatar,
			String position, String candidateplace, AccountEntity account, RecommendationsEntity recommendations) {
		super();
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.files = files;
		this.dateOfBirth = dateOfBirth;
		this.avatar = avatar;
		this.position = position;
		this.candidateplace = candidateplace;
		this.account = account;
		this.recommendations = recommendations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
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

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

	public RecommendationsEntity getRecommendation() {
		return recommendations;
	}

	public void setRecommendation(RecommendationsEntity recommendations) {
		this.recommendations = recommendations;
	}

}
