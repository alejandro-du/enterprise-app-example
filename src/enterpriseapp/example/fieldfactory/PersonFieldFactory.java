package enterpriseapp.example.fieldfactory;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import enterpriseapp.ui.crud.DefaultCrudFieldFactory;

public class PersonFieldFactory extends DefaultCrudFieldFactory {

	private static final long serialVersionUID = 1L;
	
	public Field createCustomField(Object bean, Item item, String pid, Component uiContext, Class<?> propertyType) {
		Field field = null;
		
		if("firstName".equals(pid) || "lastName".equals(pid)) {
			TextField textField = new TextField();
			textField.setDescription("This is a customized TextField");
			field = textField;
		}
		
		return field;
	}

}
