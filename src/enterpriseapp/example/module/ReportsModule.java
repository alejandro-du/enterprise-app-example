package enterpriseapp.example.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import enterpriseapp.example.dto.User;
import enterpriseapp.example.report.CallingReport;
import enterpriseapp.example.ui.Constants;
import enterpriseapp.ui.window.MDIWindow;
import enterpriseapp.ui.window.Module;

/**
 * A report module with just one report for now.
 * 
 * @author Alejandro Duarte
 *
 */
public class ReportsModule implements Module, Command {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ReportsModule.class);
	
	private MDIWindow mdiWindow;
	
	private MenuItem callingReportItem;

	@Override
	public void init() {
		// not used
	}

	@Override
	public boolean userCanAccess(enterpriseapp.hibernate.dto.User user) {
		// we must return true if the given user has access to this module
		return ((User) user).getReportsAccess();
	}

	@Override
	public void add(MDIWindow mdiWindow, enterpriseapp.hibernate.dto.User user) {
		this.mdiWindow = mdiWindow; // save reference for further use
		// this will be called when the module is added to the window
		// first, we get the MDI window menu bar, then we add some items to it
		callingReportItem = mdiWindow.getMenuBar().addItem(Constants.uiReports, null).addItem(Constants.uiCallingReport, this);
	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		if(callingReportItem.equals(selectedItem)) {
			logger.info("Calling report menu item selected.");
			// "Calling report" menu item is selected, let's create a new report instance
			CallingReport report = new CallingReport();
			
			// now, we add it to the MDI window
			mdiWindow.addWorkbenchContent(report, Constants.uiCallingReport, null, true, false);
		}
	}

}
