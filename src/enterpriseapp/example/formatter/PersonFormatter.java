package enterpriseapp.example.formatter;

import com.vaadin.data.Property;

import enterpriseapp.hibernate.annotation.CrudField;
import enterpriseapp.ui.crud.CrudTable;
import enterpriseapp.ui.crud.DefaultPropertyFormatter;

public class PersonFormatter extends DefaultPropertyFormatter {

	@Override
	public String formatPropertyValue(Object rowId, Object colId, Property property, Object bean, Object propertyObject, Class<?> returnType, CrudField crudFieldAnnotation, CrudTable<?> crudTable) {
		
		if("firstName".equals(colId) || "lastName".equals(colId)) {
			return propertyObject.toString().toUpperCase();
		}
		
		// this is needed if we want to use Enterprise App default formatting
		return super.formatPropertyValue(rowId, colId, property, bean, propertyObject, returnType, crudFieldAnnotation, crudTable);
	}
	
}
