package de.swm.gwt.client;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.gwt.editor.client.EditorError;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;

import de.swm.gwt.client.authorization.IAccessRight;
import de.swm.gwt.client.authorization.IRightsDependentUIUpdater;
import de.swm.gwt.client.utils.ConstraintViolationEditorErrorAdapter;

/**
 * Abstrakte Basisklasse fuer Formulare.
 *
 * @param <DTO> Das DTO, das in diesem Formular gepflegt wird
 * @author Florian Rodler, Daniel Wiese
 * @copyright SWM Service GmbH, 2013-2014
 */
public abstract class AbstractForm<DTO> extends AbstractBaseForm<DTO> implements IForm<DTO> {

	/**
	 * Der default style fuer ein FehlerFeld (message). *
	 */
	private static final String ERROR_FIELD_STYLE = "errorField";

	private IRightsDependentUIUpdater rightsDependentUIUpdater;

	private Validator validator = null;

	
	/**
	 * Erzeugt ein abstraktes Form.
	 */
	public AbstractForm() {
		this(null);
	}

	/**
	 * Erzeugt ein abstraktes Form.
	 *
	 * @param rightsDependentUiUpdater der in das konkrete Form injizierte rightsDependentUiUpdater
	 */
	public AbstractForm(IRightsDependentUIUpdater rightsDependentUiUpdater) {
		this.rightsDependentUIUpdater = rightsDependentUiUpdater;
	}


	/**
	 * Liefert den Validator aus dem konkreten Formular.
	 *
	 * @return der Validator.
	 */
	public Validator getValidator() {
		// Initialisierung nur wenn tatsächlich benötigt
		if (validator == null) {
			validator = Validation.buildDefaultValidatorFactory().getValidator();
		}
		
		return validator;
	}

	/**
	 * Registriert ein UI-Objekt mit den Ausfuehrungsrechten.
	 * <p/>
	 * Registrierte UI-Objekte werden entsprechend den Ausfuehrungsrechten aktiviert oder deaktiviert.
	 *
	 * @param uiObject       das UI-Objekt
	 * @param executionRight die Ausfuehrungsrechte.
	 */
	protected void registerAccessRight(UIObject uiObject, IAccessRight executionRight) {
		if (rightsDependentUIUpdater == null) {
			throw new IllegalArgumentException("Aufruf registerAccessRights ohne gesetzten accessRightsChecker.");
		}
		rightsDependentUIUpdater.register(uiObject, executionRight);
	}
	

	@Override
	protected void onLoad() {
		if (rightsDependentUIUpdater != null) {
			rightsDependentUIUpdater.updateComponents();
		}
	}

	/**
	 * Diese Methode kann ueberscheiben wenn men einen eigenen CC Stil fuer Fehler-Meldungen bebötigt.
	 *
	 * @return der Stil fuer Fehlermeldungen.
	 */
	protected String getErrorFieldStyle() {
		return (SWMGwt.getTheme() != null) ? SWMGwt.getTheme().getCssBundle().getErrorCss().errorField() :
				ERROR_FIELD_STYLE;
	}

	/**
	 * Validiert das Formular und zeigt Fehlermeldungen ggf. direkt an.
	 *
	 * @return true wenn das Formular valide ist.
	 */
	public boolean validateWithErrorContentArea(Panel errorArea) {
		final List<ConstraintViolation<?>> violationsToDisplay = getConstraintViolations();
		if (errorArea != null) {
			errorArea.clear();
			for (ConstraintViolation<?> constraintViolation : violationsToDisplay) {
				//zz.B. <g:HTML styleName="errorField" HTML="Fehler 2"/>
				final HTML error = new HTML(constraintViolation.getMessage());
				error.setStyleName(getErrorFieldStyle());
				errorArea.add(error);
			}
		}
		//und am Schluss Violations anzeigen.
		if (!violationsToDisplay.isEmpty()) {
			this.dtoEditor.setConstraintViolations(violationsToDisplay);
			return false;
		}

		return true;
	}


	/**
	 * Validiert das Formular und zeigt Fehlermeldungen ggf. direkt an.
	 *
	 * @return true wenn das Formular valide ist.
	 */
	public boolean validate() {
		List<ConstraintViolation<?>> violationsToDisplay = getConstraintViolations();
		//und am Schluss Violations anzeigen.
		if (!violationsToDisplay.isEmpty()) {
			this.dtoEditor.setConstraintViolations(violationsToDisplay);
			return false;
		}

		return true;
	}

	/**
	 * Liefert die Liste der der Validierungsfehler.
	 *
	 * @return die Liste der Validierungsfehler.
	 */
	public List<ConstraintViolation<?>> getConstraintViolations() {
		DTO entity = this.getDisplayedDTO();
		if (entity != null && this.dtoEditor != null) {
			List<ConstraintViolation<?>> violationsToDisplay = new ArrayList<ConstraintViolation<?>>();
			//Zuerst Violations des Validators sammeln
			violationsToDisplay.addAll(getValidator().validate(entity));
			//Dann die Errors vom Editor sammeln (falsche Eingabewerte)
			if (this.dtoEditor.hasErrors()) {
				for (EditorError ee : this.dtoEditor.getErrors()) {
					violationsToDisplay.add(new ConstraintViolationEditorErrorAdapter<DTO>(ee, entity));
				}
			}
			return violationsToDisplay;
		}
		return new ArrayList<ConstraintViolation<?>>();
	}

}
