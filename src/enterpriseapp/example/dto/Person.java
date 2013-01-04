package enterpriseapp.example.dto;

import static javax.persistence.CascadeType.ALL;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enterpriseapp.hibernate.annotation.CrudField;
import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.dto.Dto;

@Entity
@CrudTable(filteringPropertyName="name")
public class Person extends Dto {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String firstName;
	
	@Column(nullable=false)
	private String lastName;
	
	@Column
	@Temporal(TemporalType.DATE)
	Date birthday;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Department department;
	
	@OneToMany(cascade=ALL, orphanRemoval=true)
	@CrudField(embedded=true, forceRequired=true)
	private Set<ContactInfo> contactInfo;

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
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<ContactInfo> getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(Set<ContactInfo> contactInfo) {
		this.contactInfo = contactInfo;
	}

}
