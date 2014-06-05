package de.swm.gwt.client.utils;

import com.google.gwt.editor.client.EditorError;
import com.google.gwt.validation.client.impl.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * Adapter, der eine Instanz von EditorError auf eine ConstraintViolation mappt.
 * Implementiert die Klasse  ConstraintViolation aber nur so weit, wie es GWT benoetigt.
 * @param <T> der Typ der zugehoerigen DTOs.
 * @author Steiner.Christian<br>
 * copyright 2014 SWM Service GmbH 
 */
//Gehoert zum AbstractForm!
public class ConstraintViolationEditorErrorAdapter<T> implements ConstraintViolation<T> {

	private final EditorError toAdapt;
	private final T bean;



	/**
	 * Constructor.
	 * @param toAdapt der EditorError, der adaptiert werden soll
	 * @param bean   das Bean, zu dem der Error gehoert.
	 */
	public ConstraintViolationEditorErrorAdapter(EditorError toAdapt, T bean) {
		this.toAdapt = toAdapt;
		this.bean = bean;
	}



	@Override
	public String getMessage() {
		return toAdapt.getMessage();
	}



	@Override
	public String getMessageTemplate() {
		return toAdapt.getMessage();
	}



	@Override
	public T getRootBean() {
		return bean;
	}



	@Override
	public Class<T> getRootBeanClass() {
		return (Class<T>) bean.getClass();
	}



	@Override
	public Object getLeafBean() {
		return null;
	}



	@Override
	public Path getPropertyPath() {
		return new PathImpl().append(toAdapt.getAbsolutePath());
	}



	@Override
	public Object getInvalidValue() {
		return toAdapt.getValue();
	}



	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return null;
	}
}
