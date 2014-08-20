package enterpriseapp.example.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import enterpriseapp.EnterpriseApplication;
import enterpriseapp.example.container.ExampleContainerFactory;
import enterpriseapp.example.container.UserContainer;
import enterpriseapp.example.dto.User;
import enterpriseapp.ui.window.AuthWindow;

/**
 * Simple login window.
 * 
 * @author Alejandro Duarte
 *
 */
public class LoginWindow extends AuthWindow {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(LoginWindow.class);

	public LoginWindow() {
		super(Constants.uiLogin, Constants.uiLogin, Constants.uiUserLogin, Constants.uiUserPassword, "admin", "admin");
		setClosable(false);
		getLoginTf().focus();
	}

	@Override
	public void buttonClicked() {
		// this method is called when the login button is clicked (you don't say?)
		UserContainer userContainer = (UserContainer) ExampleContainerFactory.getInstance().getContainer(User.class);
		User user = userContainer.getByLoginAndPassword(getLoginTf().getValue().toString(), getPasswordTf().getValue().toString());
		
		if(user != null) {
			login(user);
		} else {
			showError();
		}
	}

	private void login(User user) {
		logger.info("User logged: " + getLoginTf().getValue());
		EnterpriseApplication.getInstance().setUser(user);
		EnterpriseApplication.getInstance().init();
	}

	private void showError() {
		logger.info("Invalid credentials for login " + getLoginTf().getValue());
		label.setCaption(Constants.uiInvalidCredentials);
		panel.setVisible(true);
	}
	
}
