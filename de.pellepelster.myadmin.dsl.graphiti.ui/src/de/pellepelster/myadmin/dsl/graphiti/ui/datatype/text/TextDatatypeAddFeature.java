package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.BaseDatatypeAddFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

public class TextDatatypeAddFeature extends BaseDatatypeAddFeature<TextDatatype>
{

	public TextDatatypeAddFeature(IFeatureProvider fp)
	{
		super(fp, TextDatatype.class);
	}

}
