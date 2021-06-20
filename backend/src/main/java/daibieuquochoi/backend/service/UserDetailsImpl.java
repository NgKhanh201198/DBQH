package daibieuquochoi.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.entity.AgencyEntity;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String accountName;
    private String password;
    private String fullname;
    private Date dateOfBirth;
    private String avatar;
    private String position;
    private String candidateplace;
    private String status;
    private AgencyEntity agency;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(AccountEntity accountEntity) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(accountEntity.getRole().getKeyName()));

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(accountEntity.getId(), accountEntity.getAccountName(),
                accountEntity.getPassword(), accountEntity.getFullname(), accountEntity.getDateOfBirth(),
                accountEntity.getAvatar(), accountEntity.getPosition(), accountEntity.getCandidateplace(),
                accountEntity.getStatus(), accountEntity.getAgency(), authorities);
        return userDetailsImpl;
    }

    public UserDetailsImpl(Long id, String accountName, String password, String fullname, Date dateOfBirth,
                           String avatar, String position, String candidateplace, String status, AgencyEntity agency,
                           Collection<? extends GrantedAuthority> authorities) {
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
        this.agency = agency;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.accountName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(this.status.equals("LOCKED"));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status.equals("ACTIVE");
    }

    // get
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
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

    public AgencyEntity getAgency() {
        return agency;
    }

    public void setAgency(AgencyEntity agency) {
        this.agency = agency;
    }
}
