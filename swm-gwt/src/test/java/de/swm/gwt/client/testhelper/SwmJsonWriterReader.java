package de.swm.gwt.client.testhelper;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.thoughtworks.xstream.XStream;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Hilfsklasse um Json serialisierung / deserialisierung zu simulieren.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public class SwmJsonWriterReader<T> implements JsonReader<T>, JsonWriter<T> {

	private final XStream xstream = new XStream();
	private Class<?>[] ift;

	/**
	 * Erzeugt einen JSON Writer fuer das Testen.
	 *
	 * @param forClass der zu testende JsonWriter
	 * @param <T>      der type des DTO'S
	 * @return der writer
	 */
	public static <T extends JsonWriter> T createWriter(Class<T> forClass) {
		final SwmJsonWriterReader<Object> instance = new SwmJsonWriterReader<Object>();
		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getName().equals("toJson")) {
					return instance.toJson(args[0]);
				}

				return null;
			}
		};

		Class<?>[] ift = { forClass };
		T result = (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), ift, handler);
		return result;
	}

	/**
	 * Erzeugt einen JSON reader fuer das Testen.
	 *
	 * @param forClass der zu testende JsonReader
	 * @param <T>      der type des DTO'S
	 * @return der reader
	 */
	public static <T extends JsonReader> T createReader(Class<T> forClass) {
		final SwmJsonWriterReader<Object> instance = new SwmJsonWriterReader<Object>();
		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getName().equals("read")) {
					return instance.read((String) args[0]);
				}

				return null;
			}
		};

		Class<?>[] ift = { forClass };
		T result = (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), ift, handler);
		return result;
	}


	@Override
	public T read(String s) throws JSONException {
		return deSerialize(s);
	}

	@Override
	public T read(JSONObject jsonObject) throws JSONException {
		return deSerialize(jsonObject.toString());
	}

	@Override
	public String toJson(T t) {
		return serialize(t);
	}

	@Override
	public List<T> readList(String s) throws JSONException {
		throw new IllegalArgumentException("Not supported");
	}

	@Override
	public List<T> readList(String s, String s1) throws JSONException {
		throw new IllegalArgumentException("Not supported");
	}

	@Override
	public List<T> readList(JSONObject jsonObject) throws JSONException {
		throw new IllegalArgumentException("Not supported");
	}

	@Override
	public List<T> readList(JSONObject jsonObject, String s) throws JSONException {
		throw new IllegalArgumentException("Not supported");
	}

	@Override
	public List<T> readList(JSONArray jsonArray) throws JSONException {
		throw new IllegalArgumentException("Not supported");
	}

	@Override
	public String toJson(List<T> ts, String s) {
		throw new IllegalArgumentException("Not supported");
	}

	private String serialize(Object toSerialize) {
		final String xml = xstream.toXML(toSerialize);
		return xml;
	}

	private T deSerialize(String toDeSerialize) {
		final T result = (T) xstream.fromXML(toDeSerialize);
		return result;
	}
}
