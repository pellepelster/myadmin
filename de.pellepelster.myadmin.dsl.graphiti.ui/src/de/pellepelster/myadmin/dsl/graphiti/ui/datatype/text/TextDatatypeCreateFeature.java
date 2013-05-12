package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.datatype.BaseDatatypeCreateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.BaseDictionaryDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.Labels;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

public class TextDatatypeCreateFeature extends BaseDatatypeCreateFeature<TextDatatype>
{

	public TextDatatypeCreateFeature(IFeatureProvider fp)
	{
		super(fp, Messages.TextDatatype, Messages.TextDatatypeCreate);
	}

	@Override
	public TextDatatype createAndAddToModelInternal(ICreateContext context, String name)
	{
		TextDatatype textDatatype = MyAdminDslFactory.eINSTANCE.createTextDatatype();
		textDatatype.setName(name);

		BaseDictionaryDatatype baseDictionaryDatatype = MyAdminDslFactory.eINSTANCE.createBaseDictionaryDatatype();
		textDatatype.setBaseDatatype(baseDictionaryDatatype);

		Labels labels = MyAdminDslFactory.eINSTANCE.createLabels();
		baseDictionaryDatatype.setLabels(labels);

		getPackage(context).getElements().add(textDatatype);

		return textDatatype;
	}

}
