package de.swm.gwt.client;

import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import de.swm.gwt.client.IForm;
import de.swm.gwt.client.authorization.IAccessRight;
import de.swm.gwt.client.authorization.IRightsDependentUIUpdater;
import de.swm.gwt.client.eventbus.ICustomData;
import de.swm.gwt.client.interfaces.ILocation;
import de.swm.gwt.client.utils.ConstraintViolationEditorErrorAdapter;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstrakte Basisklasse fuer Formulare.
 *
 * @param <DTO> Das DTO, das in diesem Formular gepflegt wird
 * @author Florian Rodler, Daniel Wiese
 * @copyright SWM Service GmbH, 2013-2014
 */
public abstract class AbstractDisabledForm<DTO> extends AbstractBaseForm<DTO> implements IDisabledForm<DTO> {



}
