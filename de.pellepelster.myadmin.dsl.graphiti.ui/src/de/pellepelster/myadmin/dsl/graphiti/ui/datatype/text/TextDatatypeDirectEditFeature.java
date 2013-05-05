package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.BaseDatatypeDirectEditFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

public class TextDatatypeDirectEditFeature extends BaseDatatypeDirectEditFeature<TextDatatype>
{

	public TextDatatypeDirectEditFeature(IFeatureProvider fp)
	{
		super(fp, TextDatatype.class);
	}

}
