package de.swm.gwt.generator;

import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.PrintWriter;

public class SuperDevModeGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		// get the property oracle
		PropertyOracle propertyOracle = context.getPropertyOracle();
		ConfigurationProperty property = null;
		try {
			// get mgwt.superdevmode variable
			property = propertyOracle.getConfigurationProperty("swm.superdevmode");
		} catch (BadPropertyValueException e) {
			// if we can`t find it die
			logger.log(TreeLogger.ERROR, "can not resolve swm.superdevmode variable", e);
			throw new UnableToCompleteException();
		}

		ConfigurationProperty superDevModeServer = null;
		try {
			// get mgwt.superdevmode variable
			superDevModeServer = propertyOracle.getConfigurationProperty("swm.superdevmode_host");
		} catch (BadPropertyValueException e) {
			// if we can`t find it die
			logger.log(TreeLogger.INFO, "can not resolve swm.superdevmode_host variable - using default - http://<server>:9876", e);
		}


		JClassType classType = null;

		try {
			// get the type we are looking for
			classType = context.getTypeOracle().getType(typeName);
		} catch (NotFoundException e) {
			// if we can`t get it die
			logger.log(TreeLogger.ERROR, "can not find type: '" + typeName + "'", e);
			throw new UnableToCompleteException();
		}

		// get value of mgwt.os
		String mgwtSuperDevmodePropertyValue = property.getValues().get(0);

		if ("on".equals(mgwtSuperDevmodePropertyValue)) {
			return buildOnImplementation(logger, context, classType, typeName, superDevModeServer);
		} else {
			return "de.swm.gwt.client.superdev.SuperDevModeOffImpl";
		}


	}

	private String buildOnImplementation(TreeLogger logger, GeneratorContext context, JClassType classType, String typeName, ConfigurationProperty superDevModeServer) {


		if (superDevModeServer.getValues().get(0) == null) {
			//use default impl!
			return "de.swm.gwt.client.superdev.SuperDevModeHelperOnDefaultImpl";
		}


		// get the package name
		String packageName = classType.getPackage().getName();
		// build name for implementation class
		String simpleName = classType.getSimpleSourceName() + "_generated";
		// combine package name and simple name to full qualified
		String fullName = packageName + "." + simpleName;

		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
		composer.setSuperclass("de.swm.gwt.client.superdev.SuperDevModeHelperOnImpl");
		composer.addImport("de.swm.gwt.client.superdev.SuperDevModeHelperOnImpl");


		PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);

		if (printWriter == null) {
			return fullName;
		}


		// start writing the implementation
		SourceWriter writer = composer.createSourceWriter(context, printWriter);

		writer.println("protected String getServerUrl() {");
		writer.println("return \"" + superDevModeServer.getValues().get(0) + "\";");
		writer.println("}");
		writer.commit(logger);

		return fullName;
	}

}
