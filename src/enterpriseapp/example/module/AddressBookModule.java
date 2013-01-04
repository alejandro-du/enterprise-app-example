package enterpriseapp.example.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import enterpriseapp.example.crud.DepartmentCrud;
import enterpriseapp.example.dto.Department;
import enterpriseapp.example.dto.Person;
import enterpriseapp.example.dto.User;
import enterpriseapp.example.fieldfactory.PersonFieldFactory;
import enterpriseapp.example.formatter.PersonFormatter;
import enterpriseapp.example.ui.Constants;
import enterpriseapp.ui.crud.CrudBuilder;
import enterpriseapp.ui.crud.CrudComponent;
import enterpriseapp.ui.window.MDIWindow;
import enterpriseapp.ui.window.Module;

/**
 * Our Address Book module with options to edit Departments and Persons.
 * 
 * @author Alejandro Duarte
 *
 */
public class AddressBookModule implements Module, Command {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(AddressBookModule.class);

	private MDIWindow mdiWindow;
	
	private MenuItem peopleItem;
	private MenuItem departmentsItem;

	private User user;
	
	@Override
	public void init() {
		// not used
	}

	@Override
	public boolean userCanAccess(enterpriseapp.hibernate.dto.User user) {
		this.user = (User) user;
		// we must return true if the given user has access to this module
		return this.user.getReadAccess() || this.user.getModifyAccess();
	}

	@Override
	public void add(MDIWindow mdiWindow, enterpriseapp.hibernate.dto.User user) {
		this.mdiWindow = mdiWindow; // save reference for further use
		// this will be called when the module is added to the window
		// first, we get the MDI window menu bar, then we add some items to it
		MenuItem ab= mdiWindow.getMenuBar().addItem(Constants.uiAddressBook, null);
		peopleItem = ab.addItem(Constants.uiPeople, this);
		departmentsItem = ab.addItem(Constants.uiDepartments, this);
	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		if(peopleItem.equals(selectedItem)) {
			logger.info("People menu item selected.");
			// "People" menu item is selected
			// let's create a CRUD with the helper class CrudBuilder (you can also instantiate a crud directly)
			CrudComponent<Person> crud = new CrudBuilder<Person>(Person.class)
				.setShowDeleteButton(user.getModifyAccess())
				.setShowNewButton(user.getModifyAccess())
				.setShowUpdateButton(user.getModifyAccess())
				.setFieldFactory(new PersonFieldFactory()) // we can use our own FieldFactory to customize fields
				.build();
			
			crud.setReadOnly(!user.getModifyAccess());
			crud.getSplit().setSplitPosition(50);
			crud.getTable().setPropertyFormatter(new PersonFormatter()); // use a PropertyFormatter for our table
			crud.getTable().updateTable(); // this is needed as we have added a PropertyFormatter after CrudComponent creation
			
			// now, we add it to the MDI window
			mdiWindow.addWorkbenchContent(crud, Constants.uiPeople, null, true, true);
		}
		else if(departmentsItem.equals(selectedItem)) {
			logger.info("Departments menu item selected.");
			// "Departments" menu item is selected
			// let's create a CRUD instantiating it directly
			CrudComponent<Department> crud = new DepartmentCrud(user.getModifyAccess() && !Constants.dbUseCloudFoundryDatabase);
			
			// now, we add it to the MDI window
			mdiWindow.addWorkbenchContent(crud, Constants.uiDepartments, null, true, false);
		}
	}

}
