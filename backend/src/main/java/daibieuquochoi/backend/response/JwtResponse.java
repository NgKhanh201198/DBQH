package daibieuquochoi.backend.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class JwtResponse {
    private Long id;
    private String accountName;
    private String fullname;
    private Date dateOfBirth;
    private String avatar;
    private String position;
    private String candidateplace;
    private String status;
    private List<String> role;
    private String agency;
    private String token;
    private String type = "Bearer";

    public JwtResponse() {
        super();
    }

    public JwtResponse(Long id, String accountName, String fullname, Date dateOfBirth, String avatar, String position, String candidateplace, String status, List<String> role, String agency, String token) {
        this.id = id;
        this.accountName = accountName;
        this.fullname = fullname;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.position = position;
        this.candidateplace = candidateplace;
        this.status = status;
        this.role = role;
        this.agency = agency;
        this.token = token;
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

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
