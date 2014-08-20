package enterpriseapp.example.container;

import java.util.Collection;
import java.util.List;

import enterpriseapp.example.dto.Department;
import enterpriseapp.example.dto.Person;
import enterpriseapp.hibernate.DefaultHbnContainer;

@SuppressWarnings("unchecked")
public class PersonContainer extends DefaultHbnContainer<Person> {
	
	private static final long serialVersionUID = 1L;

	public PersonContainer() {
		super(Person.class);
	}
	
	public Collection<?> getCallReportData(Department department, String[] properties, Class<?>[] classes) {
		List<?> result = specialQuery(
			"select p.firstName, p.lastName, c.city, c.phoneNumber" +
			" from Person p" +
			" join p.contactInfo c" +
			" where ? is null or p.department = ?",
			new Object[] {department, department}
		);
		
		return parseSpecialQueryResult(
			result,
			properties,
			classes
		);
	}

}
