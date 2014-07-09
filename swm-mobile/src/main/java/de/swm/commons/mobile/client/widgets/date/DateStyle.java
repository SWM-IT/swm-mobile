package de.swm.commons.mobile.client.widgets.date;

/**
 * Defines the different date styles.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2012, SWM Services GmbH
 */
public enum DateStyle {

	/**
	 * Date sytle.
	 */
	DATE("date", "dd. MMMM yyyy", "d. MMMM yyyy", "yyyy-MM-dd", "yyyy-MM-dd", "yyyy-MM-dd", "yyyy-MM-dd"),
	/**
	 * time sytle. allways with milliseconds.
	 */
	TIME("time", "HH:mm", "HH:mm", "HH:mm:ss.SS'Z'", "HH:mm:ss.SS'Z'", "HH:mm:ss.SS", "HH:mm:ss.SS"),
	/**
	 * Date time sytle. allways with milliseconds.
	 */
	DATETIME("datetime-local", "dd.MM.yyyy HH:mm", "dd.MM.yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SS'Z'",
			"yyyy-MM-dd'T'HH:mm:ss.SS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SS", "yyyy-MM-dd'T'HH:mm:ss.SS");

	private final String htmlInputType;
	private final String formatPattern;
	private final String parsePattern;
	private final String formatPatternRfc3339;
	private final String parsePatternRfc3339;
	private final String formatPatternRfc3339Local;
	private final String parsePatternRfc3339Local;


	/**
	 * Default constructor. Collect patterns to using with using {@link com.google.gwt.i18n.client.DateTimeFormat}.
	 *
	 * @param htmlInputType             HTML5 input type name
	 * @param formatPattern             the pattern for formatting
	 * @param parsePattern              the pattern for parsing
	 * @param formatPatternRfc3339      the pattern for formatting a RFC-3339 date string
	 * @param parsePatternRfc3339       the pattern for parsing a RFC-3339 date string
	 * @param formatPatternRfc3339Local the pattern for formatting a RFC-3339 date string in local manner
	 * @param parsePatternRfc3339Local  the pattern for parsing a RFC-3339 date string in local manner
	 */
	DateStyle(String htmlInputType, String formatPattern, String parsePattern, String formatPatternRfc3339, String parsePatternRfc3339,
			  String formatPatternRfc3339Local, String parsePatternRfc3339Local) {
		this.htmlInputType = htmlInputType;
		this.formatPattern = formatPattern;
		this.parsePattern = parsePattern;
		this.formatPatternRfc3339 = formatPatternRfc3339;
		this.parsePatternRfc3339 = parsePatternRfc3339;
		this.formatPatternRfc3339Local = formatPatternRfc3339Local;
		this.parsePatternRfc3339Local = parsePatternRfc3339Local;
	}

	/**
	 * Gets the default date format pattern.
	 *
	 * @return date format pattern
	 */
	public String getFormatPattern() {
		return formatPattern;
	}

	/**
	 * Gets the default date parse pattern.
	 *
	 * @return date parse pattern
	 */
	public String getParsePattern() {
		return parsePattern;
	}

	/**
	 * Gets the pattern for formatting a RFC-3339 date string.
	 *
	 * @return date format pattern
	 */
	public String getFormatPatternRfc3339() {
		return formatPatternRfc3339;
	}

	/**
	 * Gets the pattern for formatting a RFC-3339 date string in local manner.
	 *
	 * @return date format pattern
	 */
	public String getFormatPatternRfc3339Local() {
		return formatPatternRfc3339Local;
	}

	/**
	 * Gets the pattern for parsing a RFC-3339 date string.
	 *
	 * @return date parse pattern
	 */
	public String getParsePatternRfc3339() {
		return parsePatternRfc3339;
	}

	/**
	 * Gets the pattern for parsing a RFC-3339 date string in local manner.
	 *
	 * @return date parse pattern
	 */
	public String getParsePatternRfc3339Local() {
		return parsePatternRfc3339Local;
	}

	/**
	 * Gets the HTML5 inputfield type.
	 *
	 * @return inputfield type
	 */
	public String getHtmlInputType() {
		return htmlInputType;
	}
}
