package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public interface MyAdminGraphitiConstants
{
	static final String ELEMENT_ID_KEY = "element.id";

	static final String ORGANISATION_NAME_KEY = "organisation.name";

	static final String PROJECT_NAME_KEY = "project.name";

	static final String PACKAGE_NAME_KEY = "package.name";

	static final IColorConstant CLASS_BACKGROUND = new ColorConstant(187, 218, 247);

	static final IColorConstant CLASS_FOREGROUND = new ColorConstant(98, 131, 167);

	static final IColorConstant CLASS_TEXT_FOREGROUND = IColorConstant.BLACK;

	static final IColorConstant CLASS_ATTRIBUTES_BACKGROUND = new ColorConstant(255, 0, 0);

	static final IColorConstant PACKAGE_TEXT_FOREGROUND = CLASS_TEXT_FOREGROUND;

	static final IColorConstant ATTRIBUTE_BACKGROUND = CLASS_BACKGROUND;

	static final IColorConstant ATTRIBUTE_FOREGROUND = CLASS_FOREGROUND;

	static final double CLASS_TRANSPARENCY = 0.5;

	static final double ATTRIBUTE_TRANSPARENCY = 0.5;

	static final int CLASS_DEFAULT_WIDTH = 200;

	static final int CLASS_DEFAULT_HEIGHT = 100;

	static final int CLASS_MIN_HEIGHT = 100;

	static final int CLASS_MIN_WIDTH = 200;

	static final int MARGIN = 10;

	static final int ATTRIBUTE_HEIGHT = 25;

	static final IColorConstant ANCHOR_BACKGROUND = new ColorConstant(98, 131, 167);

}
