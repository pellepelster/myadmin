package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.text;

import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPictogramElementContext;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerUpdateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

public class TextDatatypeUpdateFeature extends BaseContainerUpdateFeature<TextDatatype, TextDatatype>
{

	public TextDatatypeUpdateFeature(IFeatureProvider fp)
	{
		super(fp, TextDatatype.class, MyAdminDslPackage.Literals.DATATYPE__NAME);
	}

	@Override
	protected List<TextDatatype> getAttributes(IPictogramElementContext context)
	{
		return null;
	}

}