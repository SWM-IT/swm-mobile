package de.swm.gwt.client.exception;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import de.swm.gwt.client.widget.InfoDialog;


/**
 * Ein {@link com.google.gwt.core.client.GWT.UncaughtExceptionHandler}, der die Exception loggt,
 * dem User die Fehlermeldung mit allgemeinem Fehler
 * anzeigt und die Anwendung neu laedt. Man kann ihn auch
 * so konfigurieren, dass die Anwendung nicht neu geladen wird.
 *
 * @author Steiner.Christian
 * @author wiese.daniel <br>
 *         copyright (C) 2010, SWM Services GmbH S-IP-AN-EE
 */
public class MessagingUncaughtExceptionHandler implements GWT.UncaughtExceptionHandler {

	/**
	 * Speichert, ob nach einer Exception die Anwendung neu geladen werden soll. *
	 */
	private final boolean reloadApplicationAfterException;

	/**
	 * Speichert, ob ein Reload bereits gestartet wurde --> Damit der User den Fehlerdialog nicht mehrfach bekommt. *
	 */
	private boolean reloadStarted = false;


	/**
	 * Erstellt einen {@link MessagingUncaughtExceptionHandler}. Die Anwendung wird im Fehlerfall neu geladen.
	 */
	public MessagingUncaughtExceptionHandler() {
		this(true);
	}


	/**
	 * Erstellt einen {@link MessagingUncaughtExceptionHandler}.
	 *
	 * @param reloadApplicationAfterException
	 *         Gibt an, ob die Anwendung im Fehlerfall neu geladen werden soll.
	 */
	public MessagingUncaughtExceptionHandler(boolean reloadApplicationAfterException) {
		this.reloadApplicationAfterException = reloadApplicationAfterException;
	}


	/**
	 * Behandelt eine unerwartete Exception. Zeigt Fehlermeldungen an und laedt die Anwendung ggf. neu.
	 *
	 * @param e die Exception
	 */
	public void onUncaughtException(Throwable e) {
		GWT.log("Uncaught exception caught", e);
		if (reloadApplicationAfterException) {
			showReloadDialog();
		} else {
			showErrorDialog();
		}

	}


	/**
	 * Zeigt einen Fehlerdialog mit allgemeiner Fehlermeldung an.
	 */
	public void showErrorDialog() {
		InfoDialog dialog = new InfoDialog("Kritischer Fehler", "Ein kritischer Fehler ist aufgetreten.");
		dialog.center();
	}


	/**
	 * Zeigt einen Fehlerdialog mit allgemeiner Fehlermeldung an. Nach Ok des Users wird die Anwendung neu geladen.
	 */
	public synchronized void showReloadDialog() {
		if (!reloadStarted) {
			reloadStarted = true;
			InfoDialog dialog = new InfoDialog("Kritischer Fehler",
					"Ein kritischer Fehler ist aufgetreten. Die Anwendung wird neu geladen");
			dialog.addCloseHandler(new CloseHandler<PopupPanel>() {
				@Override
				public void onClose(CloseEvent<PopupPanel> event) {
					ApplicationRedirecter.redirectToBase();
				}
			});
			dialog.center();
		}
	}
}
