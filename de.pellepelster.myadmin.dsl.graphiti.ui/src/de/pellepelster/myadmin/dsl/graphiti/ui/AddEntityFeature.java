package de.pellepelster.myadmin.dsl.graphiti.ui;

import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class AddEntityFeature extends AbstractAddShapeFeature
{

	private static final IColorConstant E_CLASS_TEXT_FOREGROUND = IColorConstant.BLACK;

	private static final IColorConstant E_CLASS_FOREGROUND = new ColorConstant(98, 131, 167);

	private static final IColorConstant E_CLASS_BACKGROUND = new ColorConstant(187, 218, 247);

	public AddEntityFeature(IFeatureProvider fp)
	{
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context)
	{
		// check if user wants to add a EClass
		if (context.getNewObject() instanceof Entity)
		{
			// check if user wants to add to a diagram
			if (context.getTargetContainer() instanceof Diagram)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public PictogramElement add(IAddContext context)
	{
		Entity addedEntity = (Entity) context.getNewObject();
		Diagram targetDiagram = (Diagram) context.getTargetContainer();

		List<Entity> referencedEntities = EntityUtil.getLinkedEntities(addedEntity);

		// CONTAINER SHAPE WITH ROUNDED RECTANGLE
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);

		// define a default size for the shape
		int width = 100;
		int height = 50;
		IGaService gaService = Graphiti.getGaService();
		RoundedRectangle roundedRectangle; // need to access it later

		{
			// create and set graphics algorithm
			roundedRectangle = gaService.createRoundedRectangle(containerShape, 5, 5);
			roundedRectangle.setForeground(manageColor(E_CLASS_FOREGROUND));
			roundedRectangle.setBackground(manageColor(E_CLASS_BACKGROUND));
			roundedRectangle.setLineWidth(2);
			gaService.setLocationAndSize(roundedRectangle, context.getX(), context.getY(), width, height);

			// if added Class has no resource we add it to the resource
			// of the diagram in a real scenario the business model would have
			// its own resource
			if (addedEntity.eResource() == null)
			{
				getDiagram().eResource().getContents().add(addedEntity);
			}
			// create link and wire it
			link(containerShape, addedEntity);
		}

		// SHAPE WITH LINE
		{
			// create shape for line
			Shape shape = peCreateService.createShape(containerShape, false);

			// create and set graphics algorithm
			Polyline polyline = gaService.createPolyline(shape, new int[] { 0, 20, width, 20 });
			polyline.setForeground(manageColor(E_CLASS_FOREGROUND));
			polyline.setLineWidth(2);
		}

		// SHAPE WITH TEXT
		{
			// create shape for text
			Shape shape = peCreateService.createShape(containerShape, false);

			// create and set text graphics algorithm
			Text text = gaService.createText(shape, addedEntity.getName());
			text.setForeground(manageColor(E_CLASS_TEXT_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			// vertical alignment has as default value "center"
			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			gaService.setLocationAndSize(text, 0, 0, width, 20);

			// create link and wire it
			link(shape, addedEntity);
		}

		for (Entity entity : referencedEntities)
		{
			for (Shape shape : DiagramUtil.getEntityShape(targetDiagram, entity))
			{
				Anchor sourceAnchor = Graphiti.getPeService().getChopboxAnchor(containerShape);

				Anchor targetAnchor = Graphiti.getPeService().getChopboxAnchor(shape);

				AddConnectionContext context1 = new AddConnectionContext(sourceAnchor, targetAnchor);
				context1.setNewObject(entity);

			}
		}

		return containerShape;
	}
}