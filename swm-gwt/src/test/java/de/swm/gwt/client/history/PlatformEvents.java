package de.swm.gwt.client.history;

import de.swm.gwt.client.eventbus.IEvent;



/**
 * Hier werden die nicht vom Generator erzeugten event eingefuegt.
 * 
 * @author wiese.daniel
 * @author Tschischka.Andre
 * @author Neeb.Hana <br>
 * <br>
 *         copyright (C) 2010, SWM Services GmbH
 * 
 */
public enum PlatformEvents implements IEvent {

	/** Wenn die Anwendung gestartet wird. **/
	INIT("INIT"),

	/** Um den Start-Bildschirm anzuzeigen. **/
	DISPLAY_START_SCREEN("DISPLAY_START_SCREEN"),

	/** Wenn der Benutzer sich einlogged. */
	USER_RECOGNIZED("USER_RECOGNIZED"),
	/** Wenn der Benutzer sich einlogged. */
	USER_LOGIN("USER_LOGIN"),
	/** Wenn der Benutzer sich auslogged. */
	USER_LOGOUT("USER_LOGOUT"),
	
	/** MESSAGE EVENTS - START **/
	/** Default-Event fuer den MessageFormPresenter zum Anzeigen einer Nachricht ohne buttons. */
	SHOW_MESSAGE_FORM_DEFAULT("SHOW_MESSAGE_FORM_DEFAULT"),

	/** Zeigt das Formular fuer Message an: Use Case Registrirung. **/
	SHOW_MESSAGE_FORM_FOR_NEW_REGISTRATION("SHOW_MESSAGE_FORM_FOR_NEW_REGISTRATION"),

	/** Zeigt das Formular fuer Message an: Use Case Zahlerstande Erfassen. **/
	SHOW_MESSAGE_FORM_NO_ZAHELER("SHOW_MESSAGE_FORM_NO_ZAHELER"),

	/** Zeigt das Formular fuer Message an: Use Case Online Stromantrag. **/
	SHOW_MESSAGE_FORM_NO_TARIFBERATUNGS_RESULT("SHOW_MESSAGE_FORM_NO_TARIFBERATUNGS_RESULT"),

	/** Zeigt das Formular fuer Message an: Use Case Zahlerstande Erfassen. **/
	SHOW_MESSAGE_FORM_ZAEHLER_STAENDE_ERFOLGREICH_ERFASST("SHOW_MESSAGE_FORM_ZAEHLER_STAENDE_ERFOLGREICH_ERFASST"),

	/** Zeigt das Formular fuer Message an: Use Case Zahlerstande/Zwischenabrechnung Erfassen. **/
	SHOW_MESSAGE_FORM_ZAEHLER_STAENDE_ZWISCHENABRECHNUNG_ERFOLGREICH_ERFASST(
			"SHOW_MESSAGE_FORM_ZAEHLER_STAENDE_ZWISCHENABRECHNUNG_ERFOLGREICH_ERFASST"),

	/** Zeigt das Formular fuer Message an: Use Case Tarifberatung ohne umstellbare Vertraege. **/
	SHOW_MESSAGE_FORM_NO_UMSTELLBARE_VERTRAEGE("SHOW_MESSAGE_FORM_NO_UMSTELLBARE_VERTRAEGE"),

	/**
	 * Zeigt das Formular fuer Message an: Use Case Das Vertragskonto vorher auf Online rechung umstellen.
	 **/
	SHOW_MESSAGE_FORM_FOR_VK_AUF_ONLINE_RECHNUNG_UMSTELLEN("SHOW_MESSAGE_FORM_FOR_VK_AUF_ONLINE_RECHNUNG_UMSTELLEN"),

	/**
	 * Zeigt das Formular fuer Message an: Use Case Das Vertragskonto vorher auf Online rechung umstellen.
	 **/
	SHOW_MESSAGE_FORM_ONLINE_RECHNUNG_UMSTELLEN_SUCCESS("SHOW_MESSAGE_FORM_ONLINE_RECHNUNG_UMSTELLEN_SUCCESS"),

	/**
	 * Zeigt das Formular fuer Message an: Meldung, wenn keine Or.Rechnungen vorliegen.
	 **/
	SHOW_MESSAGE_FORM_FOR_VK_HAS_NO_ONLINE_RECHNUNGEN("SHOW_MESSAGE_FORM_FOR_VK_HAS_NO_ONLINE_RECHNUNGEN"),

	/**
	 * Zeigt das Formular fuer Message an: Meldung um eine Online Rechnung downzuloaden.
	 **/
	SHOW_MESSAGE_FORM_FOR_DOWNLOAD_OR_RECHNUNGEN("SHOW_MESSAGE_FORM_FOR_DOWNLOAD_OR_RECHNUNGEN"),

	/**
	 * Zeigt das Formular fuer Message an: Meldung, falls zum Vertragskonto bereits ein Auszugsauftrag gemeldet wurde.
	 **/
	SHOW_MESSAGE_FORM_AUSZUG_ALREADY_REPORTED_FOR_VERTRAGSKONTO(
			"SHOW_MESSAGE_FORM_AUSZUG_ALREADY_REPORTED_FOR_VERTRAGSKONTO"),

	/**
	 * Zeigt das Formular fuer Message an: Meldung, falls unter dem Vertragskonto ein Vertrag mit Abrechnungsklasse 03 -
	 * Liefervertrag SWM Geschäftskd. (Vertrag.Abrechnungsklasse) und gleichzeitig Sparte Strom oder Wasser
	 * (Vertrag.Sparte) existiert.
	 **/
	SHOW_MESSAGE_FORM_AUSZUG_NOT_ALLOWED_CONTRACT("SHOW_MESSAGE_FORM_AUSZUG_NOT_ALLOWED_CONTRACT"),

	/**
	 * Zeigt an dass keine Kontonummern angezeigt werden koenen, weil das VK einen Abweichenden Rechnungsempfaneger hat.
	 **/
	SHOW_MESSAGE_FORM_DISPLAY_KONTOVERBINDUNGEN_DISALLOWED("SHOW_MESSAGE_FORM_DISPLAY_KONTOVERBINDUNGEN_DISALLOWED"),

	/**
	 * Zeigt das Formular fuer Message wegen keine Daten an: Use Case Verbrauchshistorie.
	 **/
	SHOW_MESSAGE_FORM_NO_VERBRAUCHSHISTORIE("SHOW_MESSAGE_FORM_NO_VERBRAUCHSHISTORIE"),
	

	/** Zeigt ein nicht editierbares Formular der Message an. **/
	/** Schliesst das Formular fuer Message. **/
	HIDE_MESSAGE_FORM("HIDE_MESSAGE_FORM"),

	/** Wird gefuert nachdem ein Vertragskonto erfolgreich auf Online-Rechnung umgestellt wurde. **/
	VERTRAGSKONTO_AUF_ONLINERECHNUNG_UMGESTELLT("VERTRAGSKONTO_AUF_ONLINERECHNUNG_UMGESTELLT"),

	/** Wird gefuert wenn sich das ausgewahlte Vertragskonte geaendert hat. */
	VERTAGSKONTO_SELECTION_CHANGED("VERTAGSKONTO_SELECTION_CHANGED"),

	/** Wenn der benutzer seine Registrierung beenden mochte (Email-Link). */
	COMPLETE_REGISTRATION("COMPLETE_REGISTRATION"),

	/** Wenn der Regirtrierungs-Key fehlerhaft ist. */
	SHOW_REGISTRATION_LINK_ERROR("SHOW_REGISTRATION_LINK_ERROR"),

	/**
	 * Wenn der benutzer seine Registrierung beenden mochte (Email-Link) - Uberblick am Ende.
	 */
	COMPLETE_REGISTRATION_SUMMARY("COMPLETE_REGISTRATION_SUMMARY"),

	/** Adresse für Adresse. */
	SHOW_DISABLED_ADRESSE_FORM_ADRESSE("SHOW_DISABLED_ADRESSE_FORM_ADRESSE"),

	/** Adresse für Rechnungsanschrift. */
	SHOW_DISABLED_ADRESSE_FORM_RECHNUNGS_ADRESSE("SHOW_DISABLED_ADRESSE_FORM_RECHNUNGS_ADRESSE"),

	/**
	 * Zeigt das Formular fuer Message wegen keine Daten an: Use Case Verbrauchshistorie.
	 **/
	SHOW_MESSAGE_FORM_NO_DATA("SHOW_MESSAGE_FORM_NO_DATA"),

	/** FORWARD EVENTS - START */
	/** Forward to Tarifberatung Strom. - AsycJs-Kompatible. */
	FORWARD_TO_TARIFBERATUNG("FORWARD_TO_TARIFBERATUNG"),
	
	/** Forward to Tarifberatung Strom - user ist eingelogged. - AsycJs-Kompatible. */
	FORWARD_TO_TARIFBERATUNG_INTERN("FORWARD_TO_TARIFBERATUNG_INTERN"),

	/** Forward to Tarifberatung Business- Strom. - AsycJs-Kompatible. */
	FORWARD_TO_TARIFBERATUNG_MSB("FORWARD_TO_TARIFBERATUNG_MSB"),

	/** FORWARD EVENTS - START */
	/** Forward to Tarifberatung Gas. - AsycJs-Kompatible. */
	FORWARD_TO_TARIFBERATUNG_GAS("FORWARD_TO_TARIFBERATUNG_GAS"),
	
	/** Forward to Tarifberatung Gas - Intern. - AsycJs-Kompatible. */
	FORWARD_TO_TARIFBERATUNG_GAS_INTERN("FORWARD_TO_TARIFBERATUNG_GAS_INTERN"),

	/** Forward to Ablesung. - AsycJs-Kompatible. */
	FORWARD_TO_ABLESUNG("FORWARD_TO_ABLESUNG"),

	/** Forward zum Login. - AsycJs-Kompatible. */
	FORWARD_TO_LOGIN("FORWARD_TO_LOGIN"),

	/** Forward zur Registrierung. - AsycJs-Kompatible. */
	FORWARD_TO_REGISTRATION("FORWARD_TO_REGISTRATION"),

	/** Forward zum vergessenen Password. - AsycJs-Kompatible. */
	FORWARD_TO_FORGOTTEN_PW("FORWARD_TO_FORGOTTEN_PW"),
	/** FORWARD EVENTS - ENDE */

	/**
	 * UC14. Dieser Event stoesst die Vorbereitung und die Anzeige der Zaehlerstanderfassung in dem Usecase
	 * Zwischenabrechnung anfordern - AsycJs-Kompatible..
	 */
	FORWARD_TO_ZWISCHENABRECHNUNG_ANFORDERN_ZAEHLERSTAND_ERFASSUNG(
			"FORWARD_TO_ZWISCHENABRECHNUNG_ANFORDERN_ZAEHLERSTAND_ERFASSUNG"),
	/**
	 * Als custom date wird diesem event die etr tracking code seinte mtgegeben. Diese muss in war/tracking liegen.
	 */
	TACKING_EVENT("TACKING_EVENT"),

	/**
	 * UC14. Dieser Event stoesst den Wechsel des Vertragskontos auf Onlinerechnung.
	 */
	FORWARD_TO_SWITCH_VERTRAG_TO_ONLINERECHNUNG("FORWARD_TO_SWITCH_VERTRAG_TO_ONLINERECHNUNG"),

	/**
	 * UC14. Dieser Event wird nach erfolgreichem Wechsel auf die Onlinerechnung gesendet.
	 */
	SWITCH_VERTRAG_TO_ONLINERECHNUNG_SUCCESSFUL("SWITCH_VERTRAG_TO_ONLINERECHNUNG_SUCCESSFUL");

	private String eventName;



	/**
	 * Default constructor.
	 * 
	 * @param eventName
	 *            der eindeutigen evt name
	 */
	private PlatformEvents(String eventName) {
		this.eventName = eventName;
	}



	@Override
	public String eventName() {
		return eventName;
	}

}
