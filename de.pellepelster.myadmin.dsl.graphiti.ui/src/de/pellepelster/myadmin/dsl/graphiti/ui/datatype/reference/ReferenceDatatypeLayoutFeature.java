package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerLayoutFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;

public class ReferenceDatatypeLayoutFeature extends BaseContainerLayoutFeature<ReferenceDatatype>
{
	public ReferenceDatatypeLayoutFeature(IFeatureProvider fp)
	{
		super(fp, ReferenceDatatype.class);
	}
}