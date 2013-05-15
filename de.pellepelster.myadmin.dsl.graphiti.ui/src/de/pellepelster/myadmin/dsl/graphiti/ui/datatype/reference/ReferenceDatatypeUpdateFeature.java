package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference;

import java.util.Collections;
import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPictogramElementContext;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerUpdateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;

public class ReferenceDatatypeUpdateFeature extends BaseContainerUpdateFeature<ReferenceDatatype, ReferenceDatatype>
{

	public ReferenceDatatypeUpdateFeature(IFeatureProvider fp)
	{
		super(fp, ReferenceDatatype.class, MyAdminDslPackage.Literals.DATATYPE__NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<ReferenceDatatype> getAttributes(IPictogramElementContext context)
	{
		return Collections.EMPTY_LIST;
	}

}