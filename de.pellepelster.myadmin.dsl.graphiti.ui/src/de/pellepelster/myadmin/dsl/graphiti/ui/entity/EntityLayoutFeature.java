package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import java.util.List;
import java.util.Map;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.util.IColorConstant;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute.BaseAttributeAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerLayoutFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.ContainerShapeQuery;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.DiagramUtil;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.EntityShapeUtil;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;
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

				Map<Connection, List<Entity>> connectionsToDelete = ContainerShapeQuery.create(entityAttributeContainerShape).getOutgoingConnections()
						.filterConnectionsByLinkedBusinessObjectType(Entity.class).getLinkedBusinessObjectType(Entity.class);

				if (EntityAttributeQuery.create(entityAttribute).hasEntity())
				{
					Entity entity = EntityAttributeQuery.create(entityAttribute).getEntity();

					boolean hasLinkToEntity = false;

					for (Map.Entry<Connection, List<Entity>> connection2Entities : connectionsToDelete.entrySet())
					{
						if (connection2Entities.getValue().contains(entity))
						{
							hasLinkToEntity = true;
						}
						else
						{
							GraphitiUi.getPeService().deletePictogramElement(connection2Entities.getKey());
						}
					}

					if (!hasLinkToEntity)
					{
						for (Shape entityShape : EntityShapeUtil.getEntityShape(getDiagram(), entity))
						{
							Anchor sourceAnchor = DiagramUtil.getOrCreateBoxRelativeAnchor(getDiagram(), entityAttributeContainerShape, 1.0, 0.5);
							Anchor targetAnchor = DiagramUtil.getOrCreateChopboxAnchor(entityShape);

							Connection connection = Graphiti.getPeCreateService().createFreeFormConnection(getDiagram());
							GraphitiProperties.set(connection, MyAdminGraphitiConstants.ELEMENT_ID_KEY, BaseAttributeAddFeature.ATTRIBUTE_ENTITY_LINK_ID);

							link(connection, entityAttribute);

							connection.setStart(sourceAnchor);
							connection.setEnd(targetAnchor);

							Polyline polyline = Graphiti.getGaService().createPolyline(connection);
							polyline.setBackground(manageColor(IColorConstant.BLACK));
							polyline.setForeground(manageColor(IColorConstant.BLACK));
							polyline.setLineWidth(1);
							GraphitiProperties.set(polyline, MyAdminGraphitiConstants.ELEMENT_ID_KEY, BaseAttributeAddFeature.ATTRIBUTE_ENTITY_LINK_ID);

						}
					}
				}
				else
				{
					for (Map.Entry<Connection, List<Entity>> connection2Entities : connectionsToDelete.entrySet())
					{
						GraphitiUi.getPeService().deletePictogramElement(connection2Entities.getKey());
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