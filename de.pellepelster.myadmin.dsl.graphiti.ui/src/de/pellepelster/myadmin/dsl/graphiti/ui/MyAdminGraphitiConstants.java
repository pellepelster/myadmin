package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public interface MyAdminGraphitiConstants
{
	static final String ELEMENT_ID_KEY = "element.id";

	static final String ORGANISATION_NAME_KEY = "organisation.name";

	static final String PROJECT_NAME_KEY = "project.name";

	static final String PACKAGE_NAME_KEY = "package.name";

	static final IColorConstant CLASS_TEXT_FOREGROUND = IColorConstant.BLACK;

	static final IColorConstant CLASS_FOREGROUND = new ColorConstant(98, 131, 167);

	static final IColorConstant CLASS_BACKGROUND = new ColorConstant(187, 218, 247);

	static final IColorConstant PACKAGE_TEXT_FOREGROUND = CLASS_TEXT_FOREGROUND;
}
