/*************************************************************************************
 *
 * Generated on Sat Apr 27 22:34:16 CEST 2013 by Spray CreateShapeFeature.xtend
 * 
 * This file is an extension point: copy to "src" folder to manually add code to this
 * extension point.
 *
 *************************************************************************************/
package de.pellepelster.myadmin.dsl.spray.ui.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;

import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleType;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleTypes;

public class MyAdminModelDiagramCreateTextattributeFeature extends MyAdminModelDiagramCreateTextattributeFeatureBase
{
	public MyAdminModelDiagramCreateTextattributeFeature(final IFeatureProvider fp)
	{
		super(fp);
	}

	@Override
	protected EntityAttribute createTextattribute(ICreateContext context)
	{
		EntityAttribute textEntityAttribute = super.createTextattribute(context);

		SimpleType simpleType = MyAdminDslFactory.eINSTANCE.createSimpleType();
		simpleType.setType(SimpleTypes.STRING);
		textEntityAttribute.setType(simpleType);

		return textEntityAttribute;
	}
}
