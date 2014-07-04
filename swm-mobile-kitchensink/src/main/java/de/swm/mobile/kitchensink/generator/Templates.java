package de.swm.mobile.kitchensink.generator;

/**
 * Created by wiese.daniel on 04.07.2014.
 */
public class Templates {

    private static String PREFIX_JAVA = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "\n" +
            "    <meta name=\"description\"\n" +
            "          content=\"M-Ticket\"/>\n" +
            "    <meta name=\"author\" content=\"SWM Services GmbH\"/>\n" +
            "    <meta property=\"og:title\" content=\"SWM-Mobile\"/>\n" +
            "    <meta property=\"og:description\" content=\"SWM-Mobile\"/>\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <title>SWM Mobile - Source</title>\n" +
            "\n" +
            " \n" +
            "\n" +
            "    <!-- Bootstrap core CSS -->\n" +
            "    <link href=\"../../css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
            "    <link href=\"../../css/newStyle.css\" rel=\"stylesheet\">\n" +
            "\n" +
            "</head>\n" +
            "<body>\n" +
            " <div class=\"container\">\n" +
            "      <div class=\"page-header\">\n" +
            "        <h1>Source code (Java - GWT UI Binder)</h1>\n" +
            "      </div>\n" +
            "      <div class=\"row\">\n" +
            "        <div class=\"col-md-12\">";

    private static String PREFIX_XML = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "\n" +
            "    <meta name=\"description\"\n" +
            "          content=\"M-Ticket\"/>\n" +
            "    <meta name=\"author\" content=\"SWM Services GmbH\"/>\n" +
            "    <meta property=\"og:title\" content=\"SWM-Mobile\"/>\n" +
            "    <meta property=\"og:description\" content=\"SWM-Mobile\"/>\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <title>SWM Mobile - Source</title>\n" +
            "\n" +
            " \n" +
            "\n" +
            "    <!-- Bootstrap core CSS -->\n" +
            "    <link href=\"../../css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
            "    <link href=\"../../css/newStyle.css\" rel=\"stylesheet\">\n" +
            "\n" +
            "</head>\n" +
            "<body>\n" +
            " <div class=\"container\">\n" +
            "      <div class=\"page-header\">\n" +
            "        <h1>XML Template (GWT UI Binder)</h1>\n" +
            "      </div>\n" +
            "      <div class=\"row\">\n" +
            "        <div class=\"col-md-12\">";


    private static String SUFFIX = "</div></div></div></body></html>";

    public static String getPrefixJava() {
        return PREFIX_JAVA;
    }

    public static String getPrefixXML() {
        return PREFIX_XML;
    }

    public static String getSuffix() {
        return SUFFIX;
    }
}
