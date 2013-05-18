package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerLayoutFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.DiagramUtil;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.EntityShapeUtil;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.query.EntityAttributeQuery;

public class EntityLayoutFeature extends BaseContainerLayoutFeature<Entity>
{
	public EntityLayoutFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class);
	}

	@Override
	public boolean layout(ILayoutContext context)
	{
		ContainerShape entityContainerShape = (ContainerShape) context.getPictogramElement();

		for (ContainerShape entityAttributeContainerShape : getAttributeContainerShapes(entityContainerShape))
		{
			Object attributeBusinessObject = getBusinessObjectForPictogramElement(entityAttributeContainerShape);

			if (attributeBusinessObject instanceof EntityAttribute)
			{
				EntityAttribute entityAttribute = (EntityAttribute) attributeBusinessObject;
				if (EntityAttributeQuery.create(entityAttribute).hasEntity())
				{
					Entity entity = EntityAttributeQuery.create(entityAttribute).getEntity();

					for (Shape entityShape : EntityShapeUtil.getEntityShape(getDiagram(), entity))
					{

						Anchor sourceAnchor = DiagramUtil.getOrCreateChopboxAnchor(entityAttributeContainerShape);
						Anchor targetAnchor = DiagramUtil.getOrCreateChopboxAnchor(entityShape);

						Connection connection = Graphiti.getPeCreateService().createFreeFormConnection(getDiagram());
						link(connection, entity);

						connection.setStart(sourceAnchor);
						connection.setEnd(targetAnchor);

						Polyline polyline = Graphiti.getGaService().createPolyline(connection);
						polyline.setBackground(manageColor(IColorConstant.BLACK));
						polyline.setForeground(manageColor(IColorConstant.BLACK));
						polyline.setLineWidth(5);

					}

				}
			}
		}

		// // add Text decorator for the reference name
		// // ConnectionDecorator textDecorator =
		// // peCreateService.createConnectionDecorator(connection, true,
		// // 0.5, true);
		// // Text text = gaService.createDefaultText(targetDiagram,
		// // textDecorator);
		// // StyleUtil.setStyleForTransition(targetDiagram, text);

		return super.layout(context);
	}
}