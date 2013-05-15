package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.BaseDatatypeDirectEditFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;

public class ReferenceDatatypeDirectEditFeature extends BaseDatatypeDirectEditFeature<ReferenceDatatype>
{

	public ReferenceDatatypeDirectEditFeature(IFeatureProvider fp)
	{
		super(fp, ReferenceDatatype.class);
	}

}
