package de.swm.mobile.kitchensink.client.showcase.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.validation.IValidator;
import de.swm.commons.mobile.client.widgets.*;
import de.swm.mobile.kitchensink.client.components.EnumRenderer;
import de.swm.mobile.kitchensink.client.components.Person;
import org.gwtbootstrap3.client.ui.ButtonGroup;

import java.util.Date;

//TODO: Refactoring neue Struktur
public class EditorFormPage extends SimplePage implements Editor<Person> {

	@UiField @Path("name") 
	TextBox nameTextBox;
	@UiField @Path("email")
	EmailTextBox mailTextBox;
	@UiField @Path("birthdate") 
	DateTextBox birthdateTextBox;
	@UiField @Path("name")
	TextArea description;
	@UiField @Path("job")
	DropDownList<String> jobList;
	@UiField @Path("option")
	ButtonGroup radioButtonGroup;
	@UiField @Path("genOption")
	GenericRadioButtonGroup<EnumRenderer.ExampleEnum> radioButtonGroupGeneric;
	@UiField @Ignore
	DateTextBox timeTextBox;
	@UiField @Ignore
	DateTextBox scheduleTextBox;
	@UiField @Ignore
	Button saveButton;
	@UiField @Ignore
	Button cancelButton;



	private static EditorPageUiBinder uiBinder = GWT.create(EditorPageUiBinder.class);
	private Driver driver = GWT.create(Driver.class);

	interface EditorPageUiBinder extends UiBinder<Widget, EditorFormPage> {}
	
	interface Driver extends SimpleBeanEditorDriver<Person, EditorFormPage> {}
	
	@SuppressWarnings("deprecation")
	public EditorFormPage() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		Person p = new Person();
		p.setName("Alex");
		p.setEmail("alex@swm.de");
		Date date = new Date();
		date.setYear(68);
		p.setBirthdate(date);
		driver.edit(p);

		this.timeTextBox.setValue(date);
		this.scheduleTextBox.setValue(date);

		// test enum based radio group
		radioButtonGroupGeneric.setRenderer(new EnumRenderer());
		radioButtonGroupGeneric.setKeyValueProvider(new EnumRenderer());
		EnumRenderer.setValues(radioButtonGroupGeneric);
		radioButtonGroupGeneric.setValue(EnumRenderer.ExampleEnum.GENERIC_OPTION_3);

		// test text area validation
		description.addValidator(new IValidator<String>() {
			@Override
			public String validate(HasValue<String> field) {
				String value = field.getValue();
				if (value == null || value.length() == 0) {
					return "Bitte einen Wert eingeben.";
				}
				return null;
			}
		});

	}

    @UiHandler("saveButton")
    void onSaveButtonClicked(ClickEvent e) {

        Person edited = driver.flush();
        if (driver.hasErrors()) {
        	Utils.console(driver.getErrors().toString());
        }
        Window.alert(edited.toString());
    }

	@Override
	public String getName() {
		return "EditorFormPage";
	}

}
