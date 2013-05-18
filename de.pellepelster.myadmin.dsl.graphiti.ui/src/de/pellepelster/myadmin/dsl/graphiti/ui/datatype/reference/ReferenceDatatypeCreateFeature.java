package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.BaseDatatypeCreateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.BaseDictionaryDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.Labels;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;

public class ReferenceDatatypeCreateFeature extends BaseDatatypeCreateFeature<ReferenceDatatype>
{

	public ReferenceDatatypeCreateFeature(IFeatureProvider fp)
	{
		super(fp, ReferenceDatatype.class, Messages.ReferenceDatatype, Messages.ReferenceDatatypeCreate);
	}

	@Override
	public ReferenceDatatype createAndAddToModelInternal(ICreateContext context, String name)
	{
		ReferenceDatatype referenceDatatype = MyAdminDslFactory.eINSTANCE.createReferenceDatatype();
		referenceDatatype.setName(name);

		BaseDictionaryDatatype baseDictionaryDatatype = MyAdminDslFactory.eINSTANCE.createBaseDictionaryDatatype();
		referenceDatatype.setBaseDatatype(baseDictionaryDatatype);

		Labels labels = MyAdminDslFactory.eINSTANCE.createLabels();
		baseDictionaryDatatype.setLabels(labels);

		getPackage(context).getElements().add(referenceDatatype);

		return referenceDatatype;
	}

}
