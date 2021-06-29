package daibieuquochoi.backend.entity;

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
@Table(name = "recommendations")
public class RecommendationsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendationsid")
    private Long id;

    @Column(name = "object")
    private String object;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "address")
    private String address;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "commenttype")
    private String commenttype;

    @Column(name = "files")
    private String files;

    @Column(name = "contents")
    private String contents;

    @Column(name = "status")
    private String status;

    @Column(name = "reportingdeadline")
    private Long reportingdeadline;

    @Column(name = "fields")
    private String fields;

    @ManyToOne
    @JoinColumn(name = "agencyid")
    private AgencyEntity agency;

    @OneToMany(mappedBy = "recommendations", cascade = CascadeType.ALL)
    private Set<FeedbackEntity> feedback = new HashSet<FeedbackEntity>();

    public RecommendationsEntity() {
        super();
    }

    public RecommendationsEntity(Long id, String object, String fullname, String address, String phonenumber, String email, String title, String contents, String files, Long reportingdeadline, String commenttype, String fields, String status, AgencyEntity agency, Set<FeedbackEntity> feedback) {
        this.id = id;
        this.object = object;
        this.fullname = fullname;
        this.address = address;
        this.phonenumber = phonenumber;
        this.email = email;
        this.title = title;
        this.contents = contents;
        this.files = files;
        this.reportingdeadline = reportingdeadline;
        this.commenttype = commenttype;
        this.fields = fields;
        this.status = status;
        this.agency = agency;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getReportingdeadline() {
        return reportingdeadline;
    }

    public void setReportingdeadline(Long reportingdeadline) {
        this.reportingdeadline = reportingdeadline;
    }

    public String getCommenttype() {
        return commenttype;
    }

    public void setCommenttype(String commenttype) {
        this.commenttype = commenttype;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AgencyEntity getAgency() {
        return agency;
    }

    public void setAgency(AgencyEntity agency) {
        this.agency = agency;
    }

    public Set<FeedbackEntity> getFeedback() {
        return feedback;
    }

    public void setFeedback(Set<FeedbackEntity> feedback) {
        this.feedback = feedback;
    }
}
