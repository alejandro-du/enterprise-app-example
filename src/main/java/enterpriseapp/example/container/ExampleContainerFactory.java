package enterpriseapp.example.container;

import enterpriseapp.example.dto.Audit;
import enterpriseapp.example.dto.Department;
import enterpriseapp.example.dto.Person;
import enterpriseapp.example.dto.User;
import enterpriseapp.hibernate.ContainerFactory;
import enterpriseapp.hibernate.DefaultHbnContainer;
import enterpriseapp.hibernate.dto.AuditLog;

/**
 * Our custom container factory.
 * 
 * @author Alejandro Duarte
 *
 */
public class ExampleContainerFactory extends ContainerFactory {

	@SuppressWarnings("rawtypes")
	@Override
	public DefaultHbnContainer getContainer(Class<?> clazz) {
		
		if(Department.class.isAssignableFrom(clazz)) {
			return new DepartmentContainer();
			
		} else if(Person.class.isAssignableFrom(clazz)) {
			return new PersonContainer();
			
		} else if(User.class.isAssignableFrom(clazz)) {
			return new UserContainer();
			
		} else if(AuditLog.class.isAssignableFrom(clazz)) { // you must do this in order to make AuditInterceptor work properly
			return getDefaultFactory().getContainer(Audit.class); 
		}
		
		return getDefaultFactory().getContainer(clazz);
	}

}
