package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.BaseDatatypeAddFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;

public class ReferenceDatatypeAddFeature extends BaseDatatypeAddFeature<ReferenceDatatype>
{

	public ReferenceDatatypeAddFeature(IFeatureProvider fp)
	{
		super(fp, ReferenceDatatype.class);
	}

}
