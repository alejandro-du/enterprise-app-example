package enterpriseapp.example.report;

import java.awt.Color;
import java.util.Collection;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import enterpriseapp.Utils;
import enterpriseapp.example.container.ExampleContainerFactory;
import enterpriseapp.example.container.PersonContainer;
import enterpriseapp.example.dto.Department;
import enterpriseapp.example.dto.Person;
import enterpriseapp.example.ui.Constants;
import enterpriseapp.ui.crud.EntityField;
import enterpriseapp.ui.reports.AbstractReport;

/**
 * A simple report with all the entries in the address book.
 * 
 * @author Alejandro Duarte
 *
 */
public class CallingReport extends AbstractReport {

	private static final long serialVersionUID = 1L;
	
	private EntityField departmentSelect;

	@Override
	public String[] getColumnProperties() {
		return new String[] {"firstName", "lastName", "city", "phoneNumber"};
	}

	@Override
	public Class<?>[] getColumnClasses() {
		return new Class<?>[] {String.class, String.class, String.class, String.class};
	}

	@Override
	public String[] getColumnTitles() {
		return new String[] {
			Utils.getPropertyLabel("person", "firstName"),
			Utils.getPropertyLabel("person", "lastName"),
			Utils.getPropertyLabel("person", "city"),
			Utils.getPropertyLabel("person", "phoneNumber")
		};
	}

	@Override
	public Collection<?> getData() {
		// here we need to return all the data to show in the report
		// this will be called when the "Refresh" button is clicked
		PersonContainer personContainer = (PersonContainer) ExampleContainerFactory.getInstance().getContainer(Person.class);
		return personContainer.getCallReportData((Department) departmentSelect.getValue(), getColumnProperties(), getColumnClasses());
	}

	@Override
	public Component getParametersComponent() {
		// we will add a component into the "Configuration" section
		departmentSelect = new EntityField(Department.class, ExampleContainerFactory.getInstance().getContainer(Department.class));
		departmentSelect.setCaption(Constants.uiDepartment);
		departmentSelect.setImmediate(true);
		
		VerticalLayout component = new VerticalLayout();
		component.addComponent(departmentSelect);
		
		return component;
	}

	@Override
	public DynamicReportBuilder getReportBuilder() {
		// let's override this method to add some elements to the report
		DynamicReportBuilder reportBuilder = super.getReportBuilder();
		
		Style headerStyle = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM).build();
		
		reportBuilder.addAutoText(Constants.uiForInternalUseOnly, AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT, 200, headerStyle);
		reportBuilder.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 200, 10, headerStyle);
		reportBuilder.addAutoText(Utils.getCurrentTimeAndDate(), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 200, headerStyle);
		
		Style titleStyle = new StyleBuilder(true).setPadding(0).setFont(Font.ARIAL_BIG_BOLD).setHorizontalAlign(HorizontalAlign.CENTER).build();
		reportBuilder.setTitleStyle(titleStyle);
		reportBuilder.setTitleHeight(18);
		String title = Constants.uiCallingReport;
		
		if(departmentSelect.getValue() != null) {
			title += " - " + departmentSelect.getValue();
		}
		
		reportBuilder.setTitle(title);
		
		Style footerStyle = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM).setTextColor(Color.GRAY).build();
		reportBuilder.addAutoText("Powered by Enterprise App for Vaadin", AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT, 200, footerStyle);
		
		return reportBuilder;
	}
}
