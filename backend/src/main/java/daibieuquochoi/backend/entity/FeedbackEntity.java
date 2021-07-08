package daibieuquochoi.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "accountid")
    private AccountEntity account;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "recommendationsid")
    private RecommendationsEntity recommendations;

    public FeedbackEntity() {
        super();
    }

    public FeedbackEntity(Long id, String title, String contents, String files, AccountEntity account, RecommendationsEntity recommendations) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.files = files;
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

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public RecommendationsEntity getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(RecommendationsEntity recommendations) {
        this.recommendations = recommendations;
    }
}
