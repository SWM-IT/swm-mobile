package de.swm.mobile.kitchensink.client;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotations used in {@link de.swm.mobile.kitchensink.client.Application}.
 */
public class ShowcaseAnnotations {
	/**
	 * Indicates that a class variable should be included as source data in the
	 * example. All data must have a JavaDoc style comment.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface ShowcaseData {
	}

	/**
	 * Indicates that a method or inner class should be included as source code in
	 * the example. All source must have a JavaDoc style comment.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE, ElementType.METHOD})
	public @interface ShowcaseSource {
	}

	/**
	 * Indicates the raw files that be included as raw source in a Showcase
	 * example.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ShowcaseRaw {
		String[] value();
	}

	/**
	 * Indicates the prefix of a style attribute used in a Showcase example.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ShowcaseStyle {
		String[] value();
	}
}