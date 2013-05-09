package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.AddContextQuery;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class EntityAddFeature extends BaseContainerAddFeature<Entity>
{

	public EntityAddFeature(IFeatureProvider fp)
	{
		super(fp, Entity.class);
	}

	@Override
	public boolean canAdd(IAddContext context)
	{
		return AddContextQuery.create(context).newObjectTypeIs(Entity.class).targetContainerTypeIs(Diagram.class).result();
	}

	@Override
	public PictogramElement add(IAddContext context)
	{
		// for (Entity entity : referencedEntities)
		// {
		// for (Shape shape : EntityShapeUtil.getEntityShape(targetDiagram,
		// entity))
		// {
		// Anchor sourceAnchor =
		// DiagramUtil.getOrCreateChopboxAnchor(containerShape);
		// Anchor targetAnchor = DiagramUtil.getOrCreateChopboxAnchor(shape);
		//
		// Connection connection =
		// peCreateService.createFreeFormConnection(targetDiagram);
		// link(connection, entity);
		//
		// connection.setStart(sourceAnchor);
		// connection.setEnd(targetAnchor);
		//
		// Polyline polyline = gaService.createPolyline(connection);
		// polyline.setBackground(manageColor(IColorConstant.BLACK));
		// polyline.setForeground(manageColor(IColorConstant.BLACK));
		// polyline.setLineWidth(5);
		//
		// // add Text decorator for the reference name
		// // ConnectionDecorator textDecorator =
		// // peCreateService.createConnectionDecorator(connection, true,
		// // 0.5, true);
		// // Text text = gaService.createDefaultText(targetDiagram,
		// // textDecorator);
		// // StyleUtil.setStyleForTransition(targetDiagram, text);
		//
		// }
		// }

		return addInternal(context);
	}

	@Override
	protected String getNameText(Entity businessObject)
	{
		return businessObject.getName();
	}

}