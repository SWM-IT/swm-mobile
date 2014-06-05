package de.swm.gwt.client;

import com.google.gwt.user.client.Command;
import de.swm.gwt.client.interfaces.ITypedAction;

/**
 * Adaptiert eine Klasse vom Typ IAction auf den Typ Command.
 * @author Steiner.Christian<br>
 * copyright 2013 SWM Service GmbH 
 */
public class CommandActionAdapter implements Command {

	private ITypedAction<Object> action;



	/**
	 * default constructor.
	 * @param action die action, die adaptiert wird.
	 */
	public CommandActionAdapter(ITypedAction<Object> action) {
		this.action = action;
	}



	@Override
	public void execute() {
		action.execute(null);
	}
}
