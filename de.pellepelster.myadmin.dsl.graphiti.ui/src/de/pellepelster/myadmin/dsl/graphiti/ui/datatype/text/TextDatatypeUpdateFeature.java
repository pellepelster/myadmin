package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.util.BaseClassUpdateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

public class TextDatatypeUpdateFeature extends BaseClassUpdateFeature<TextDatatype>
{

	public TextDatatypeUpdateFeature(IFeatureProvider fp)
	{
		super(fp, TextDatatype.class, MyAdminDslPackage.Literals.DATATYPE__NAME);
	}

}