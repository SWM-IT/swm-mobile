package de.swm.gwt.client;

import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.Widget;
import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.interfaces.ILocation;

/**
 * Abstrakte Basisklasse fuer Formulare.
 *
 * @param <DTO> Das DTO, das in diesem Formular gepflegt wird
 * @copyright SWM Service GmbH, 20xx
 */
public abstract class AbstractBaseForm<DTO> implements IBaseForm<DTO> {

	protected Widget root;
	protected ICustomData customData;


	/**
	 * The editor is might null for forms without data binding.
	 */
	protected SimpleBeanEditorDriver<DTO, ?> dtoEditor;


	/**
	 * Erzeugt ein abstraktes Form.
	 */
	public AbstractBaseForm() {
	}


	/**
	 * Will set the dto Edito if the form is using data binding.
	 *
	 * @param dtoEditor the dto editor
	 */
	public void setDtoEditor(SimpleBeanEditorDriver<DTO, ?> dtoEditor) {
		this.dtoEditor = dtoEditor;
	}


	/**
	 * onLoad funktioniert nur, wenn ein AttachEvent.Handler auf
	 * root registriert wird.
	 */
	protected void onLoad() {

	}


	private void attachOnLoadHandler() {
		getRoot().addAttachHandler(new AttachEvent.Handler() {
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				if (event.isAttached()) {
					onLoad();
				}
			}
		});
	}


	/**
	 * {@inheritDoc}
	 */
	public void displayDTO(DTO toDisplay) {
		if (this.dtoEditor != null) {
			this.dtoEditor.edit(toDisplay);
			this.dtoEditor.setConstraintViolations(null);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public DTO getDisplayedDTO() {
		if (this.dtoEditor != null) {
			return this.dtoEditor.flush();
		}
		return null;
	}


	/**
	 * Rendert das Formular in die entsprechende Location.
	 *
	 * @param location   die Location, in die das Formular gerendert werden soll.
	 * @param customData die CustomData oder null, falls nicht vorhanden.
	 */
	public void render(ILocation location, ICustomData customData) {
		setCustomData(customData);
		final Widget widget = this.getWidget();
		widget.setVisible(true);
		location.add(widget);
		location.render();
	}


	/**
	 * Entfernt das Formular aus der Location.
	 *
	 * @param location Location, aus der das Formular entfernt werden soll
	 */
	public void remove(ILocation location) {
		location.remove(this.getWidget());
	}


	public Widget getRoot() {
		return root;
	}


	public ICustomData getCustomData() {
		return customData;
	}


	/**
	 * Liefert true wenn das Formuar Custom-Daten hat.
	 *
	 * @return true wenn das Formuar Custom-Daten hat.
	 */
	public boolean hasCustomData() {
		return getCustomData() != null;
	}


	/**
	 * {@inheritDoc}
	 */
	public Widget getWidget() {
		return root;
	}


	/**
	 * Initialisiert das Widget, das das gesamte Formular darstellt.
	 *
	 * @param root das im konkreten Form ereugte Widget.
	 */
	public void initalizeWidget(Widget root) {
		this.root = root;
		attachOnLoadHandler();
	}


	/**
	 * Setzt die CustomData und passt das Formular daran an.
	 *
	 * @param customData die CustomData.
	 */
	public void setCustomData(ICustomData customData) {
		this.customData = customData;
		customizeForm(customData);
	}


	/**
	 * Fuehrt die Anpassung des Formulars durch (setzt die Buttons, die Reihenfolge, etc.).
	 *
	 * @param customData vom benutzer ubergebene Daten zur Formularanpassung.
	 */
	protected abstract void customizeForm(ICustomData customData);

}
