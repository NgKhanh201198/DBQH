package daibieuquochoi.backend.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "agency")
public class AgencyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agencyid")
    private Long id;

    @Column(name = "agencyname")
    private String agencyName;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "agency")
    @JsonIgnore
    private Set<AccountEntity> accounts = new HashSet<AccountEntity>();

    @OneToMany(mappedBy = "agency")
    @JsonIgnore
    private Set<RecommendationsEntity> recommendations = new HashSet<RecommendationsEntity>();

    public AgencyEntity() {
        super();
    }

    public AgencyEntity(Long id, String agencyName, String note, String status, Set<AccountEntity> accounts,
                        Set<RecommendationsEntity> recommendations) {
        super();
        this.id = id;
        this.agencyName = agencyName;
        this.note = note;
        this.status = status;
        this.accounts = accounts;
        this.recommendations = recommendations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountEntity> accounts) {
        this.accounts = accounts;
    }

    public Set<RecommendationsEntity> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<RecommendationsEntity> recommendations) {
        this.recommendations = recommendations;
    }

}
