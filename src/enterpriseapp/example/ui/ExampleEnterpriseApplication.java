package enterpriseapp.example.ui;

import java.util.ArrayList;

import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

import enterpriseapp.EnterpriseApplication;
import enterpriseapp.example.container.DummyDataCreator;
import enterpriseapp.example.container.ExampleContainerFactory;
import enterpriseapp.example.dto.User;
import enterpriseapp.example.job.DummyDataCreationJob;
import enterpriseapp.example.module.AddressBookModule;
import enterpriseapp.example.module.ConfigurationModule;
import enterpriseapp.example.module.ReportsModule;
import enterpriseapp.hibernate.ContainerFactory;
import enterpriseapp.ui.window.MDIWindow;
import enterpriseapp.ui.window.Module;

/**
 * An example Vaadin application demonstrating Enterprise App for Vaadin features.
 * See http://alejandroduarte.weebly.com/enterpriseapp for further information.
 * 
 * @author Alejandro Duarte
 *
 */
public class ExampleEnterpriseApplication extends EnterpriseApplication {

	private static final long serialVersionUID = 1L;
	
	private static boolean firstInit = true;

	@Override
	public void init() {
		super.init();
		
		if(firstInit) {
			firstInit = false;
			
			// we will use a custom ContainerFactory
			ContainerFactory.init(new ExampleContainerFactory());
			
			// create dummy data if necesary
			DummyDataCreator.bootstrap();
			
			// Schedule dummy data creation job
			DummyDataCreationJob.schedule();
		}
		
		// show content according to the state of getUser()
		if(getUser() == null) {
			showPublicContent();
		} else {
			showPrivateContent();
		}
	}

	private void showPublicContent() {
		removeWindow(getMainWindow());
		
		// we are gonna create an empty window and add a new LoginWindow to it
		LoginWindow emptyMainWindow = new LoginWindow();
		Window mainWindow = new Window(Constants.uiAppName);
		
		mainWindow.addWindow(emptyMainWindow);
		setMainWindow(mainWindow);
	}

	private void showPrivateContent() {
		removeWindow(getMainWindow());
		
		// we will use the Enterprise Application modules feature that allows us to implement rol based access per module
		ArrayList<Module> modules = getModules();
		
		// now, we create an instance of MDIWindow, initialize its content and set it to be the main window for the app
		MDIWindow mainWindow = new MDIWindow(Constants.uiAppName, modules);
		mainWindow.initWorkbenchContent((User) getUser(), null);
		setMainWindow(mainWindow);
		
		// we need to add a welcome tab
		Label label = new Label(Constants.uiWelcomeMessage, Label.CONTENT_XHTML);
		label.setSizeFull();
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.addComponent(label);
		mainWindow.addWorkbenchContent(panel, Constants.uiWelcome, null, false, false);
		
		// finally, let's add a "close session" option
		mainWindow.getMenuBar().addItem(Constants.uiLogout, new MenuBar.Command() {
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				setUser(null); // remove user from session
				init(); // reload app
			}
		});
	}

	private ArrayList<Module> getModules() {
		// let's define the modules
		ConfigurationModule configurationModule = new ConfigurationModule();
		AddressBookModule addressBookModule = new AddressBookModule();
		ReportsModule reportsModule = new ReportsModule();
		
		// add them to a new ArrayList so we can pass them to the MDIWindow constructor
		ArrayList<Module> modules = new ArrayList<Module>();
		modules.add(configurationModule);
		modules.add(addressBookModule);
		modules.add(reportsModule);
		
		return modules;
	}

}
