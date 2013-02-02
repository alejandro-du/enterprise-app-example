package enterpriseapp.example.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import enterpriseapp.hibernate.annotation.CrudField;
import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.dto.Dto;

@Entity
@CrudTable(filteringPropertyName="login")
public class User extends Dto implements enterpriseapp.hibernate.dto.User {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String login;
	
	@Column(nullable=false)
	@CrudField(isPassword=true, showInTable=false)
	private String password;
	
	@Column(nullable=false)
	private boolean configurationAccess;
	
	@Column(nullable=false)
	private boolean readAccess;
	
	@Column(nullable=false)
	private boolean modifyAccess;
	
	@Column(nullable=false)
	private boolean reportsAccess;
	
	@Override
	public String toString() {
		return login;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Object id) {
		this.id = (Long) id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isConfigurationAccess() {
		return configurationAccess;
	}

	public void setConfigurationAccess(boolean configurationAccess) {
		this.configurationAccess = configurationAccess;
	}

	public boolean isReadAccess() {
		return readAccess;
	}

	public void setReadAccess(boolean readAccess) {
		this.readAccess = readAccess;
	}

	public boolean isModifyAccess() {
		return modifyAccess;
	}

	public void setModifyAccess(boolean modifyAccess) {
		this.modifyAccess = modifyAccess;
	}

	public boolean isReportsAccess() {
		return reportsAccess;
	}

	public void setReportsAccess(boolean reportsAccess) {
		this.reportsAccess = reportsAccess;
	}

}
