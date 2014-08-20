package enterpriseapp.example.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.dto.AuditLog;
import enterpriseapp.hibernate.dto.Dto;

@Entity
@CrudTable(filteringPropertyName="id")
public class Audit extends Dto implements AuditLog {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable=false, length=255)
	private String entityType;
	
	@Column(nullable=false)
	private Date date;
	
	@Column(nullable=false, length=255)
	private String action;
	
	@Column
	private String dtoId;
	
	@Column(nullable=false, length=1023)
	private String details;
	
	@Column(nullable=false, length=255)
	private String user;

	@Column(nullable=false, length=255)
	private String ip;

	@Override
	public String toString() {
		return "" + id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Object id) {
		this.id = (Long) id;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDtoId() {
		return dtoId;
	}

	public void setDtoId(String dtoId) {
		this.dtoId = dtoId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
