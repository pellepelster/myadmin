package de.pellepelster.myadmin.dsl.graphiti.ui.entity.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseCreateFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.CreateContextQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttributeOptions;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleType;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleTypes;

public class EntityAttributeCreateFeature extends BaseCreateFeature<EntityAttribute>
{
	public EntityAttributeCreateFeature(IFeatureProvider fp)
	{
		super(fp, EntityAttribute.class, Messages.EntityAttribute, Messages.EntityAttributeCreate);
	}

	@Override
	public boolean canCreate(ICreateContext context)
	{
		return CreateContextQuery.create(context).targetContainerElementIdIs(BaseContainerAddFeature.CONTAINER_ID).result();
	}

	@Override
	public EntityAttribute createAndAddToModelInternal(ICreateContext context, String className)
	{
		Entity entity = (Entity) getBusinessObjectForPictogramElement(context.getTargetContainer());

		EntityAttribute newEntityAttribute = MyAdminDslFactory.eINSTANCE.createEntityAttribute();
		newEntityAttribute.setName(className);

		SimpleType simpleType = MyAdminDslFactory.eINSTANCE.createSimpleType();
		simpleType.setType(SimpleTypes.STRING);
		newEntityAttribute.setType(simpleType);

		EntityAttributeOptions entityAttributeOptions = MyAdminDslFactory.eINSTANCE.createEntityAttributeOptions();
		newEntityAttribute.setEntityAttributeOptions(entityAttributeOptions);

		entity.getAttributes().add(newEntityAttribute);

		return newEntityAttribute;
	}

}