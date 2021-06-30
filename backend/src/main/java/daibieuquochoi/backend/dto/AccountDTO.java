package daibieuquochoi.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import daibieuquochoi.backend.api.validation.AccountNameFormat;

public class AccountDTO {
    @NotBlank(message = "{AccountName.NotBlank}")
    @AccountNameFormat(message = "{AccountName.Valid}")
    @Size(min = 3, max = 50, message = "{AccountName.Size}")
    private String accountName;

//    @NotBlank(message = "{Password.NotBlank}")
//    @Size(min = 6, max = 50, message = "{Password.Size}")
//    private String password;

    @NotBlank(message = "{Fullname.NotBlank}")
    @Size(min = 2, max = 50, message = "{Fullname.Size}")
    private String fullname;

    @NotBlank(message = "{DateOfBirth.NotBlank}")
    private String dateOfBirth;

    @NotBlank(message = "{Position.NotBlank}")
    private String position;

    @NotBlank(message = "{Candidateplace.NotBlank}")
    private String candidateplace;

    @NotBlank(message = "{Status.NotBlank}")
    private String status;

    @NotBlank(message = "{Agency.NotBlank}")
    private String agency;

    @NotBlank(message = "{Role.NotBlank}")
    private String role;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
