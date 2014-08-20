package enterpriseapp.example.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import enterpriseapp.Utils;
import enterpriseapp.example.dto.Audit;
import enterpriseapp.example.dto.User;
import enterpriseapp.example.ui.Constants;
import enterpriseapp.ui.FilesViewerComponent;
import enterpriseapp.ui.crud.CrudBuilder;
import enterpriseapp.ui.crud.CrudComponent;
import enterpriseapp.ui.reports.HqlQueryBrowser;
import enterpriseapp.ui.window.MDIWindow;
import enterpriseapp.ui.window.Module;

/**
 * Our configuration module.
 * 
 * @author Alejandro Duarte
 *
 */
public class ConfigurationModule implements Module, Command {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(ConfigurationModule.class);
	
	private MDIWindow mdiWindow;
	private MenuItem usersItem;
	private MenuItem hqlQueriesItem;
	private MenuItem auditLogItem;
	private MenuItem serverLogItem;

	@Override
	public void init() {
		// not used
	}

	@Override
	public boolean userCanAccess(enterpriseapp.hibernate.dto.User user) {
		// we must return true if the given user has access to this module
		return ((User) user).isConfigurationAccess();
	}

	@Override
	public void add(MDIWindow mdiWindow, enterpriseapp.hibernate.dto.User user) {
		this.mdiWindow = mdiWindow; // save reference for further use
		// this will be called when the module is added to the window
		// first, we get the MDI window menu bar, then we add some items to it
		MenuItem configurationItem = mdiWindow.getMenuBar().addItem(Constants.uiConfiguration, null);
		usersItem = configurationItem.addItem(Constants.uiUsers, this);
		hqlQueriesItem = configurationItem.addItem(Constants.uiHqlQueries, this);
		auditLogItem = configurationItem.addItem(Constants.uiAuditLog, this);
		serverLogItem = configurationItem.addItem(Constants.uiServerLog, this);
	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		if(usersItem.equals(selectedItem)) {
			logger.info("Users menu item selected.");
			CrudComponent<User> crud = null;
			
			if(Constants.dbUseCloudFoundryDatabase) {
				// CloudFoundry is in beta, so we need to disable some features to avoid problems
				// let's create a CRUD with the helper class CrudBuilder (you can also instantiate a crud directly)
				crud = new CrudBuilder<User>(User.class)
						.setShowDeleteButton(false)
						.setShowUpdateButton(false)
						.build();
			} else {
				// let's create a CRUD instantiating it directly (you can also use the CrudBuilder helper class)
				crud = new CrudComponent<User>(User.class);
			}
			
			// now, we add it to the MDI window
			mdiWindow.addWorkbenchContent(crud, Constants.uiUsers, null, true, false);
			
		} else if(hqlQueriesItem.equals(selectedItem)) {
			logger.info("HQL queries menu item selected.");
			
			HqlQueryBrowser hqlReport = new HqlQueryBrowser();
			mdiWindow.addWorkbenchContent(hqlReport, Constants.uiHqlQueries, null, true, false);
			
		} else if(auditLogItem.equals(selectedItem)) {
			logger.info("Audit log menu item selected.");
			CrudComponent<Audit> crud = new CrudBuilder<Audit>(Audit.class)
				.setShowForm(false)
				.build();
			
			crud.setReadOnly(true);
			mdiWindow.addWorkbenchContent(crud, Constants.uiAuditLog, null, true, false);
			
		} else if(serverLogItem.equals(selectedItem)) {
			logger.info("Server log menu item selected.");
			FilesViewerComponent filesViewerComponent = new FilesViewerComponent(Utils.getServerLogsDirectory(), false, true);
			filesViewerComponent.setSizeFull();
			mdiWindow.addWorkbenchContent(filesViewerComponent, Constants.uiServerLog, null, true, false);
		}
	}

}
