/*
 * Copyright 2011 SWM Services GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.generator;

import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import de.swm.commons.mobile.client.setup.OsDetection;

import java.io.PrintWriter;


/**
 * The {@link OsDetectionGenerator} creates the implementation for {@link OsDetection} for each platform. Will below up
 * the Permutations! Usage:
 * <p/>
 * <pre>
 * <!-- Generate OS Detection -->
 * 	<generate-with class="de.swm.commons.mobile.generator.OsDetectionGenerator">
 * 		<when-type-assignable class="de.swm.commons.mobile.client.setup.OsDetection" />
 * 	</generate-with>
 * </pre>
 */
public class OsDetectionGenerator extends Generator {

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String typeName)
            throws UnableToCompleteException {

        // get the property oracle
        PropertyOracle propertyOracle = context.getPropertyOracle();
        SelectionProperty property = null;
        try {
            // get mgwt.os variable
            property = propertyOracle.getSelectionProperty(logger, "swmmmobile.os");
        } catch (BadPropertyValueException e) {
            // if we can`t find it die
            logger.log(TreeLogger.ERROR, "can not resolve mgwt.os variable", e);
            throw new UnableToCompleteException();
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

        // get value of mmobile.os
        String mmobileProperty = property.getCurrentValue();
        // get the package name
        String packageName = classType.getPackage().getName();
        // build name for implementation class
        String simpleName = classType.getSimpleSourceName() + "_" + property.getCurrentValue();
        // combine package name and simple name to full qualified
        String fullName = packageName + "." + simpleName;

        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
        composer.addImplementedInterface(typeName);
        composer.addImport(typeName);

        PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);

        if (printWriter == null) {
            return fullName;
        }

        // start writing the implementation
        SourceWriter writer = composer.createSourceWriter(context, printWriter);

        writer.println("public boolean isAndroid() {");
        writer.println("return isAndroidTablet() || isAndroidPhone();");
        writer.println("}");

        writer.println("public boolean isIPhone() {");
        writer.println("return " + mmobileProperty.equals("iphone") + " || " + mmobileProperty.equals("retina") + ";");
        writer.println("}");

        writer.println("public boolean isIPad() {");
        writer.println("return " + mmobileProperty.equals("ipad") + ";");
        writer.println("}");

        writer.println("public boolean isIOs() {");
        writer.println("return isIPhone() || isIPad();");
        writer.println("}");

        writer.println("public boolean isDesktop() {");
        writer.println("return " + mmobileProperty.equals("desktop") + ";");
        writer.println("}");

        writer.println("public boolean isBlackBerry() {");
        writer.println("return " + mmobileProperty.equals("blackberry") + ";");
        writer.println("}");

        writer.println("public boolean isTablet() {");
        writer.println("return isDesktop() || isIPad() || isAndroidTablet();");
        writer.println("}");

        writer.println("public boolean isPhone() {");
        writer.println("return isIPhone() || isAndroidPhone() || isBlackBerry();");
        writer.println("}");

        writer.println("public boolean isAndroidTablet() {");
        writer.println("return " + mmobileProperty.equals("android_tablet") + ";");
        writer.println("}");

        writer.println("public boolean isAndroidPhone() {");
        writer.println("return " + mmobileProperty.equals("android") + ";");
        writer.println("}");

        writer.println("public boolean isRetina() {");
        writer.println("return " + mmobileProperty.equals("retina") + ";");
        writer.println("}");

        writer.commit(logger);

        return fullName;

    }
}
