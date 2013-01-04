package enterpriseapp.example.container;

import enterpriseapp.example.dto.User;
import enterpriseapp.hibernate.DefaultHbnContainer;

/**
 * A custom container for users.
 * 
 * @author Alejandro Duarte
 *
 */
@SuppressWarnings("unchecked")
public class UserContainer extends DefaultHbnContainer<User> {

	private static final long serialVersionUID = 1L;

	public UserContainer() {
		super(User.class);
	}
	
	public User getByLoginAndPassword(String login, String password) {
		// there are plenty of usefull methods for querying the database using HQL
		// here we use "simpleQuery" wich returns a single instance of User
		return (User) singleQuery("from User where login = ? and password = ?", new Object[] {login, password});
	}

}
