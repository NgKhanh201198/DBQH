package daibieuquochoi.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class LocationsEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "locationsid")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "parentid")
	private Long parentid;

	public LocationsEntity() {
		super();
	}

	public LocationsEntity(Long id, String name, Long parentid) {
		super();
		this.id = id;
		this.name = name;
		this.parentid = parentid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

}
