package de.swm.commons.mobile.client.utils;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.command.CommandPopup;
import de.swm.gwt.client.interfaces.ITypedAction;

import java.util.List;

/**
 * Util class to create popup dialogs.
 *
 * @author steuer.konstantin
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public interface ISwmPopupUtil {


	/**
	 * Ein Handler zur Steuerung des StatusPopups {@link ISwmPopupUtil#showStatusPopup(String, java.util.List)}.
	 *
	 * @author steuer.konstantin
	 *         <br>
	 *         copyright (C) 2012, Stadtwerke München GmbH
	 */
	public interface StatusPopupHandler {

		/**
		 * Aenderung des Anzeigestatus.
		 *
		 * @param statusDescription .
		 */
		void changeStatus(String statusDescription);

		/**
		 * Beenden des StatusPopups..
		 */
		void cancelStatusPopup();

	}

	/**
	 * Descriptor zur definition von Buttons in {@link ISwmPopupUtil#showMultiButtonPopup(String, java.util.List,
	 * java.util.List)}.
	 *
	 * @author steuer.konstantin
	 *         <br>
	 *         copyright (C) 2012, Stadtwerke München GmbH
	 */
	public class ItemDesciptor<T> implements Comparable<ItemDesciptor> {

		/**
		 * Objekt für die Action. Bei Strings: Buttontyp.
		 * Ist eine beliebige Zeichenkette, welcher den Bottontyp definiert/beschreibt. Der Buttontyp
		 * wird der der Action bei der Ausfuehrung {@link de.swm.gwt.client.interfaces.ITypedAction#execute(Object)} uebergeben.
		 */
		private final T type;

		/**
		 * i18-Text des Buttons.
		 */
		private final String text;

		/**
		 * Reihnfolge des buttons.
		 */
		private final int order;

		/**
		 * Action, die beim betaetigen des Buttons ausgefuehrt wird.
		 */
		private ITypedAction<T> action;

		/**
		 * Itemtyp = Itemtext, gleiche Reihenfolge {@link #order} = 0.
		 *
		 * @param text   {@link #text}
		 * @param action {@link #action}
		 * @throws IllegalArgumentException falls die action <code>null</code> ist.
		 */
		public ItemDesciptor(String text, ITypedAction<T> action) throws IllegalArgumentException {
			this(text, (T) text, 0, action);
		}

		/**
		 * Gleiche Reihenfolge {@link #order} = 0.
		 *
		 * @param text   {@link #text}
		 * @param type   {@link #type}
		 * @param action {@link #action}
		 * @throws IllegalArgumentException falls die action <code>null</code> ist.
		 */
		public ItemDesciptor(String text, T type, ITypedAction<T> action) throws IllegalArgumentException {
			this(text, type, 0, action);
		}

		/**
		 * Itemtyp = Itemtext.
		 *
		 * @param text   {@link #text}
		 * @param order  {@link #order}
		 * @param action {@link #action}
		 * @throws IllegalArgumentException falls die action <code>null</code> ist.
		 */
		public ItemDesciptor(String text, int order, ITypedAction<T> action) throws IllegalArgumentException {
			this(text, (T) text, order, action);
		}

		/**
		 * Hauptkonstruktor.
		 *
		 * @param text   {@link #text}
		 * @param type   {@link #type}
		 * @param order  {@link #order}
		 * @param action {@link #action}
		 * @throws IllegalArgumentException falls die action <code>null</code> ist.
		 */
		public ItemDesciptor(String text, T type, int order, ITypedAction<T> action)
				throws IllegalArgumentException {
			this.text = text;
			this.type = type;
			this.order = order;

			if (action == null) {
				throw new IllegalArgumentException("action is null");
			} else {
				this.action = action;
			}
		}

		public ITypedAction<T> getAction() {
			return action;
		}

		public int getOrder() {
			return order;
		}

		public String getText() {
			return text;
		}

		public T getType() {
			return type;
		}

		@Override
		public int compareTo(ItemDesciptor descriptor) {
			return Integer.valueOf(this.getOrder()).compareTo(Integer.valueOf(descriptor.getOrder()));
		}
	}

	/**
	 * Zeigt eine Infobox an.
	 *
	 * @param hasCancelButton true wenn der Abbruchbutton angezeigt werden soll
	 * @param header          die haupueberschrift.
	 * @param text            der Text
	 * @param okClickHanlder  dier Clik handler fuer den OK button oder null
	 */
	void showPopup(boolean hasCancelButton, String header, String text, ClickHandler okClickHanlder);


	/**
	 * Zeigt eine Infobox an.
	 *
	 * @param header die haupueberschrift.
	 * @param text   der text
	 */
	void showPopup(String header, String text);

	/**
	 * Baut eine Infobox an (ohne diese Anzuzeigen).
	 *
	 * @param header  die haupueberschrift.
	 * @param widgets die listitems im Dialog
	 * @return der popup panel das erzeugt wurde
	 */
	CommandPopup buildMultiWidgetPopup(String header, Widget... widgets);

	/**
	 * Zeigt eine Infobox an.
	 *
	 * @param hasCancelButton true wenn der Abbruchbutton angezeigt werden soll
	 * @param header          die haupueberschrift.
	 * @param texts           eine Menge von Texten
	 * @param okClickHanlder  dier Clik handler fuer den OK button oder null
	 */
	void showPopup(boolean hasCancelButton, String header, List<String> texts, ClickHandler okClickHanlder);

	/**
	 * Zeigt eine Infobox an.
	 *
	 * @param hasCancelButton    true wenn der Abbruchbutton angezeigt werden soll
	 * @param header             die haupueberschrift.
	 * @param text               der text
	 * @param okClickHanlder     dier Clik handler fuer den OK button oder null
	 * @param cancelClickHanlder dier Clik handler fuer den Cancel button oder null
	 */
	void showPopup(boolean hasCancelButton, String header, String text, ClickHandler okClickHanlder,
				   ClickHandler cancelClickHanlder);

	/**
	 * Zeigt eine Infobox an.
	 *
	 * @param hasCancelButton    true wenn der Abbruchbutton angezeigt werden soll
	 * @param header             die haupueberschrift.
	 * @param texts              die Texte
	 * @param okClickHanlder     dier Clik handler fuer den OK button oder null
	 * @param cancelClickHanlder dier Clik handler fuer den Cancel button oder null
	 */
	void showPopup(boolean hasCancelButton, String header, List<String> texts, ClickHandler okClickHanlder,
				   ClickHandler cancelClickHanlder);

	/**
	 * Zeigt eine Infobox an.
	 *
	 * @param hasCancelButton    true wenn der Abbruchbutton angezeigt werden soll
	 * @param header             die haupueberschrift.
	 * @param text               der text
	 * @param okClickHanlder     dier Clik handler fuer den OK button oder null
	 * @param cancelClickHanlder dier Clik handler fuer den Cancel button oder null
	 */
	void showPopupYesNo(boolean hasCancelButton, String header, String text, ClickHandler okClickHanlder,
						ClickHandler cancelClickHanlder);

	/**
	 * Zeigt eine Infobox an.
	 *
	 * @param hasCancelButton    true wenn der Abbruchbutton angezeigt werden soll
	 * @param header             die haupueberschrift.
	 * @param text               der text
	 * @param popupOkText        Text des OK-Buttons.
	 * @param popupCancelText    Text des Cancel-Buttons.
	 * @param okClickHanlder     dier Clik handler fuer den OK button oder null
	 * @param cancelClickHanlder dier Clik handler fuer den Cancel button oder null
	 */
	void showPopup(boolean hasCancelButton, String header, String text, String popupOkText, String popupCancelText,
				   ClickHandler okClickHanlder, ClickHandler cancelClickHanlder);

	/**
	 * This popup retus a hanlder. Using this hndler you can change the status or hide this popup.
	 *
	 * @param header .
	 * @param texts  .
	 * @return .
	 */
	StatusPopupHandler showStatusPopup(String header, List<String> texts);

	/**
	 * Zeigt ein Popup mit beliebig vielen Buttons. Die Reihenfolge der buttons,
	 * kann in den ItemDesciptor festgelegt werden. Wird keine Reihenfolge festgelegt,
	 * werden die buttons beliebig plaziert.
	 *
	 * @param <T>              Typ der ItemDescriptors
	 * @param header           die haupueberschrift.
	 * @param texts            der text
	 * @param buttonDesciptors List von ItemDesciptor.
	 */
	<T> void showMultiButtonPopup(String header, List<String> texts, List<ItemDesciptor<T>> buttonDesciptors);

	/**
	 * Zeigt ein Popup mit einer beliebig Langen Listenansicht. Die Reihenfolge der Items in der Liste,
	 * kann in den ItemDesciptor festgelegt werden. Wird keine Reihenfolge festgelegt,
	 * werden die buttons beliebig platziert.
	 *
	 * @param header             die haupueberschrift.
	 * @param listItemDesciptors List von ItemDesciptor.
	 */
	void showListPopup(String header, List<ItemDesciptor> listItemDesciptors);
}
