package de.swm.gwt.client.navigation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import de.swm.gwt.client.eventbus.IEventHandler;
import de.swm.gwt.client.interfaces.ITypedAction;
import java_cup.action_part;


/**
 * Definiert einen Unter-Menue Punkt in der Navigationslieste.
 *
 * @author wiese.daniel copyright (C) 2010, SWM Services GmbH
 *
 */
public class NavigationContent implements INavigationContent {

	/** Definiert diedefault prioritaet (reihenfolge) eines Menuepunktes. */
	public static final int DEFAULT_PRIORITY = 10;

	private final ClickHandler handler;
	private final String linkText;
	private final INavigationArea targetArea;
	private int priority = DEFAULT_PRIORITY;

	private final String[] allowedroles;


	/**
	 * Default constructor.
	 *
	 * @param inArea
	 *            die Position in der das Navigationsmenue eingefuegt werden soll.
	 * @param handler
	 *            der click handler
	 * @param linkText
	 *            der link text
	 */
	public NavigationContent(INavigationArea inArea, ClickHandler handler, String linkText) {
		this(inArea, handler, linkText, (String[]) null);
	}


	/**
	 * Default constructor.
	 *
	 * @param inArea
	 *            die Position in der das Navigationsmenue eingefuegt werden soll.
	 * @param handler
	 *            der click handler
	 * @param linkText
	 *            der link text
	 * @param allowedroles
	 *            die liste der erlaubte rollen, die diesen menuepunkt aufrufen koennen.
	 */
	public NavigationContent(INavigationArea inArea, ClickHandler handler, String linkText, String... allowedroles) {
		this.targetArea = inArea;
		this.handler = handler;
		this.linkText = linkText;
		this.allowedroles = allowedroles;
	}

	/**
	 * Default constructor.
	 *
	 * @param inArea
	 *            die Position in der das Navigationsmenue eingefuegt werden soll.
	 * @param eventHandler
	 *            der Eventhandler. Wird auf ClickHandler umgewandelt.
	 * @param linkText
	 *            der link text
	 */
	public NavigationContent(INavigationArea inArea, IEventHandler eventHandler, String linkText) {
		this(inArea, eventHandler, linkText, (String[]) null);
	}

	/**
	 * Default constructor.
	 *
	 * @param inArea
	 *            die Position in der das Navigationsmenue eingefuegt werden soll.
	 * @param eventHandler
	 *            der Eventhandler. Wird auf ClickHandler umgewandelt.
	 * @param linkText
	 *            der link text
	 * @param allowedroles
	 *            die liste der erlaubte rollen, die diesen menuepunkt aufrufen koennen.
	 */
	public NavigationContent(INavigationArea inArea, final IEventHandler eventHandler, String linkText, String... allowedroles) {
		this(inArea, wrapEventHandler(eventHandler), linkText, allowedroles);
	}

	/**
	 * Default constructor.
	 *
	 * @param inArea
	 *            die Position in der das Navigationsmenue eingefuegt werden soll.
	 * @param action
	 *            die Action. Wird auf ClickHandler umgewandelt.
	 * @param linkText
	 *            der link text
	 */
	public NavigationContent(INavigationArea inArea, ITypedAction action, String linkText) {
		this(inArea, action, linkText, (String[]) null);
	}

	/**
	 * Default constructor.
	 *
	 * @param inArea
	 *            die Position in der das Navigationsmenue eingefuegt werden soll.
	 * @param action
	 *            die Action. Wird auf ClickHandler umgewandelt.
	 * @param linkText
	 *            der link text
	 * @param allowedroles
	 *            die liste der erlaubte rollen, die diesen menuepunkt aufrufen koennen.
	 */
	public NavigationContent(INavigationArea inArea, final ITypedAction action, String linkText, String... allowedroles) {
		this(inArea, wrapTypedAction(action), linkText, allowedroles);
	}

	/**
	 * Wandelt die Action auf ein ClickHandler um.
	 *
	 * @param action die Action. Wird auf ClickHandler umgewandelt.
	 * @return ClickHandler
	 */
	private static ClickHandler wrapTypedAction(final ITypedAction action) {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				action.execute(null);
			}
		};
	}


	/**
	 * Wandelt die EventHandler auf ein ClickHandler um.
	 *
	 * @param eventHandler der Eventhandler. Wird auf ClickHandler umgewandelt.
	 * @return ClickHandler
	 */
	private static ClickHandler wrapEventHandler(final IEventHandler eventHandler) {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventHandler.handleEvent(null, null, null);
			}
		};
	}


	public ClickHandler getHandler() {
		return handler;
	}


	public String getLinkText() {
		return linkText;
	}


	public INavigationArea getTargetArea() {
		return targetArea;
	}


	@Override
	public int priority() {
		return priority;
	}


	@Override
	public void setPriority(int priority) {
		this.priority = priority;

	}


	@Override
	public String[] allowedRoles() {
		return allowedroles;
	}

}
