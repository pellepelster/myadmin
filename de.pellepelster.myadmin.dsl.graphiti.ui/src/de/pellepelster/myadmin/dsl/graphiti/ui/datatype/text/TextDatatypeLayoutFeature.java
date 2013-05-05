package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.util.BaseClassLayoutFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

public class TextDatatypeLayoutFeature extends BaseClassLayoutFeature<TextDatatype>
{
	public TextDatatypeLayoutFeature(IFeatureProvider fp)
	{
		super(fp, TextDatatype.class);
	}
}