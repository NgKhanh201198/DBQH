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
@Table(name = "roles")
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roleid")
	private Long id;

	@Column(name = "rolename")
	private String roleName;

	@Column(name = "keyname")
	private String keyName;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<AccountEntity> account = new HashSet<AccountEntity>();

	public RoleEntity() {
		super();
	}

	public RoleEntity(Long id, String roleName, String keyName, Set<AccountEntity> account) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.keyName = keyName;
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Set<AccountEntity> getAccount() {
		return account;
	}

	public void setAccount(Set<AccountEntity> account) {
		this.account = account;
	}

}
