package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.CreateContextQuery;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.BaseClassCreateFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;

public class EntityCreateFeature extends BaseClassCreateFeature
{
	public EntityCreateFeature(IFeatureProvider fp)
	{
		super(fp, Messages.Entity, Messages.EntityCreate);
	}

	@Override
	public boolean canCreate(ICreateContext context)
	{
		return CreateContextQuery.create(context).targetContainerIs(Diagram.class).result();
	}

	@Override
	public Object[] create(ICreateContext context)
	{
		// ask user for EClass name
		String newClassName = "abc"; // ExampleUtil.askString(TITLE,
										// USER_QUESTION, "");
		if (newClassName == null || newClassName.trim().length() == 0)
		{
			return EMPTY;
		}

		Entity newEntity = MyAdminDslFactory.eINSTANCE.createEntity();
		newEntity.setName(newClassName);

		return baseCreate(context, newEntity);
	}
}