package enterpriseapp.example.crud;

import enterpriseapp.example.dto.Department;
import enterpriseapp.ui.crud.CrudComponent;

public class DepartmentCrud extends CrudComponent<Department> {
	
	private static final long serialVersionUID = 1L;

	public DepartmentCrud(boolean showForm) {
		super(
			Department.class,
			null,
			null,
			null,
			null,
			showForm,
			true,
			true,
			true,
			true,
			false,
			false,
			true,
			0
		);
		
		// if form is not shown, make the component read only, so "import from clipboard" option, won't be available
		setReadOnly(!showForm);
		
		// we want to hide filters for this component
		filterLayout.setVisible(false);
	}

}
