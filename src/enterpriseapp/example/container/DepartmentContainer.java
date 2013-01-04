package enterpriseapp.example.container;

import enterpriseapp.example.dto.Department;
import enterpriseapp.hibernate.DefaultHbnContainer;

@SuppressWarnings("unchecked")
public class DepartmentContainer extends DefaultHbnContainer<Department> {
	
	private static final long serialVersionUID = 1L;

	public DepartmentContainer() {
		super(Department.class);
	}
	
	public Department getByName(String name) {
		return singleQuery("from Department where name = ?", new Object[] {name});
	}

}
