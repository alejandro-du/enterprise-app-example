package enterpriseapp.example.container;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import enterpriseapp.example.dto.ContactInfo;
import enterpriseapp.example.dto.Department;
import enterpriseapp.example.dto.Person;
import enterpriseapp.example.dto.User;
import enterpriseapp.hibernate.DefaultHbnContainer;

public class DummyDataCreator {
	
	private static Logger logger = LoggerFactory.getLogger(DummyDataCreator.class);
	
	private static final int minByDepartment = 20;
	private static final int maxByDepartment = 40;
	private static final int maxContactInfo = 5;
	private static final int fewPeople = 9 * 20;

	public static void bootstrap() {
		// if there are no users in the database, create one
		UserContainer uc = (UserContainer) ExampleContainerFactory.getInstance().getContainer(User.class);
		
		if(uc.count() == 0) {
			logger.info("Creating admin user...");
			User user = new User();
			user.setLogin("admin");
			user.setPassword("admin");
			user.setConfigurationAccess(true);
			user.setModifyAccess(true);
			user.setReportsAccess(true);
			uc.saveEntity(user);
		}
		
		createNonExistentDepartments();
		
		// if there are few people, create some
		if(ExampleContainerFactory.getInstance().getContainer(Person.class).count() < fewPeople) {
			logger.info("Creating people...");
			createPeople();
		}
	}
	
	public static void createNonExistentDepartments() {
		String[] departmentNames = { "Corporate Development", "Human Resources", "Legal", "Environment", "Quality Assurance", "Research and Development", "Production", "Sales", "Marketing" };
		DepartmentContainer dc = (DepartmentContainer) ExampleContainerFactory.getInstance().getContainer(Department.class);
		
		for(String name : departmentNames) {
			Department department = dc.getByName(name);
			
			if(department == null) {
				department = new Department();
				department.setName(name);
				dc.saveEntity(department);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void createPeople() {
		DefaultHbnContainer<Department> dc = ExampleContainerFactory.getInstance().getContainer(Department.class);
		DefaultHbnContainer<Person> pc = ExampleContainerFactory.getInstance().getContainer(Person.class);
		
		// adapted from http://dev.vaadin.com/browser/svn/addons/JPAContainer/trunk/jpacontainer-addressbook-demo/src/main/java/com/vaadin/demo/jpaaddressbook/DemoDataGenerator.java?format=txt
		String[] fnames = { "John", "Peter", "Alice", "Joshua", "Mike", "Olivia", "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene", "Lisa", "Marge" };
		String[] lnames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel", "Simons", "Verne", "Scott", "Allison", "Gates", "Rowling", "Barks", "Ross", "Schneider", "Tate" };
		String cities[] = { "Amsterdam", "Berlin", "Helsinki", "Hong Kong", "London", "Luxemburg", "New York", "Oslo", "Paris", "Rome", "Stockholm", "Tokyo", "Turku" };
		String streets[] = { "4215 Blandit Av.", "452-8121 Sem Ave", "279-4475 Tellus Road", "4062 Libero. Av.", "7081 Pede. Ave", "6800 Aliquet St.", "P.O. Box 298, 9401 Mauris St.", "161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.", "448-8295 Mi Avenue", "6419 Non Av.", "659-2538 Elementum Street", "2205 Quis St.", "252-5213 Tincidunt St.", "P.O. Box 175, 4049 Adipiscing Rd.", "3217 Nam Ave", "P.O. Box 859, 7661 Auctor St.", "2873 Nonummy Av.", "7342 Mi, Avenue", "539-3914 Dignissim. Rd.", "539-3675 Magna Avenue", "Ap #357-5640 Pharetra Avenue", "416-2983 Posuere Rd.", "141-1287 Adipiscing Avenue", "Ap #781-3145 Gravida St.", "6897 Suscipit Rd.", "8336 Purus Avenue", "2603 Bibendum. Av.", "2870 Vestibulum St.", "Ap #722 Aenean Avenue", "446-968 Augue Ave", "1141 Ultricies Street", "Ap #992-5769 Nunc Street", "6690 Porttitor Avenue", "Ap #105-1700 Risus Street", "P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street", "414-1417 Fringilla Street", "Ap #183-928 Scelerisque Road", "561-9262 Iaculis Avenue" };
		
		Random r = new Random(0);
		
		List<Department> departments = dc.listAll();
		
		for(Department department : departments) {
			int amount = r.nextInt(maxByDepartment - minByDepartment) + minByDepartment;
			
			for(int i = 0; i < amount; i++) {
				Person person = new Person();
				person.setFirstName(fnames[r.nextInt(fnames.length)]);
				person.setLastName(lnames[r.nextInt(lnames.length)]);
				person.setDepartment(department);
				
				HashSet<ContactInfo> contactInfoSet = new HashSet<ContactInfo>();
				int phones = r.nextInt(maxContactInfo) + 1;
				
				for(int j = 0; j < phones; j++) {
					ContactInfo contactInfo = new ContactInfo();
					
					contactInfo.setCity(cities[r.nextInt(cities.length)]);
					contactInfo.setPhoneNumber("+358 02 555 " + r.nextInt(10) + r.nextInt(10) + r.nextInt(10) + r.nextInt(10));
					
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, r.nextInt(30) + 1970);
					cal.set(Calendar.MONTH, r.nextInt(11));
					cal.set(Calendar.DAY_OF_MONTH, r.nextInt(28));
					
					person.setBirthday(cal.getTime());
					
					int n = r.nextInt(100000);
					
					if (n < 10000) {
						n += 10000;
					}
					
					contactInfo.setZipCode("" + n);
					contactInfo.setStreet(streets[r.nextInt(streets.length)]);
					contactInfoSet.add(contactInfo);
				}
				
				person.setContactInfo(contactInfoSet);
				pc.saveEntity(person);
			}
		}
	}
}
