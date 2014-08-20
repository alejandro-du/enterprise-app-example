package enterpriseapp.example.ui;

import java.util.ArrayList;

import com.vaadin.ui.Label;
import com.vaadin.ui.LegacyWindow;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
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
		
		if(getMainWindow() == null) {
			// create main window
			LegacyWindow mainWindow = new LegacyWindow(Constants.uiAppName);
			setMainWindow(mainWindow);
		} else {
			getMainWindow().removeAllComponents();
		}
		
		// show content according to the state of getUser()
		if(getUser() == null) {
			showPublicContent();
		} else {
			showPrivateContent();
		}
	}

	private void showPublicContent() {
		// add a new LoginWindow to it
		LoginWindow loginWindow = new LoginWindow();
		
		getMainWindow().addWindow(loginWindow);
	}

	private void showPrivateContent() {
		getMainWindow().removeWindow((Window) getMainWindow().getWindows().toArray()[0]);
		
		// we will use the Enterprise Application modules feature that allows us to implement rol based access per module
		ArrayList<Module> modules = getModules();
		
		// now, we create an instance of MDIWindow, initialize its content and set it to be the main window for the app
		MDIWindow mdiWindow = new MDIWindow(modules);
		mdiWindow.initWorkbenchContent((User) getUser(), null);
		getMainWindow().setContent(mdiWindow);
		
		// we need to add a welcome tab
		Label label = new Label(Constants.uiWelcomeMessage, Label.CONTENT_XHTML);
		label.setSizeFull();
		VerticalLayout welcome = new VerticalLayout();
		welcome.setSizeFull();
		welcome.setMargin(true);
		welcome.addComponent(label);
		
		mdiWindow.addWorkbenchContent(welcome, Constants.uiWelcome, null, false, false);
		
		// finally, let's add a "close session" option
		mdiWindow.getMenuBar().addItem(Constants.uiLogout, new MenuBar.Command() {
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
