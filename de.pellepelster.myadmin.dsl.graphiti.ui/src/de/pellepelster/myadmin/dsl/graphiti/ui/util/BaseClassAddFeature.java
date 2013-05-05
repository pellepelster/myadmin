package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.query.AddContextQuery;

public abstract class BaseClassAddFeature<T extends EObject> extends AbstractAddShapeFeature
{
	private Class<T> businessObjectClass;

	public BaseClassAddFeature(IFeatureProvider fp, Class<T> businessObjectClass)
	{
		super(fp);
		this.businessObjectClass = businessObjectClass;
	}

	public static final String HEADER_LINE_ID = "entity.header.line";

	public static final String NAME_TEXT_ID = "entity.name.text";

	@Override
	public boolean canAdd(IAddContext context)
	{
		return AddContextQuery.create(context).newObjectTypeIs(this.businessObjectClass).targetContainerIs(Diagram.class).result();
	}

	private int[] getHeaderLinePoints(GraphicsAlgorithm graphicsAlgorithm)
	{
		return new int[] { 0, 20, graphicsAlgorithm.getWidth(), 20 };
	}

	@SuppressWarnings("unchecked")
	public PictogramElement addInternal(IAddContext context)
	{
		T addedBusinessObjecty = (T) context.getNewObject();

		Diagram targetDiagram = (Diagram) context.getTargetContainer();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();

		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);

		IGaService gaService = Graphiti.getGaService();

		RoundedRectangle roundedRectangle; // need to access it later

		{
			// create and set graphics algorithm
			roundedRectangle = gaService.createRoundedRectangle(containerShape, 5, 5);
			roundedRectangle.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			roundedRectangle.setBackground(manageColor(MyAdminGraphitiConstants.CLASS_BACKGROUND));
			roundedRectangle.setLineWidth(2);
			gaService.setLocationAndSize(roundedRectangle, context.getX(), context.getY(), MyAdminGraphitiConstants.CLASS_DEFAULT_WIDTH,
					MyAdminGraphitiConstants.CLASS_DEFAULT_HEIGHT);

			// create link and wire it
			link(containerShape, addedBusinessObjecty);
		}

		// header line
		{
			Shape shape = peCreateService.createShape(containerShape, false);

			Polyline polyline = gaService.createPolyline(shape, getHeaderLinePoints(roundedRectangle));
			polyline.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			polyline.setLineWidth(2);
			GraphitiProperties.set(polyline, MyAdminGraphitiConstants.ELEMENT_ID_KEY, HEADER_LINE_ID);
		}

		// SHAPE WITH TEXT
		{
			// create shape for text
			Shape shape = peCreateService.createShape(containerShape, false);

			// create and set text graphics algorithm
			Text text = gaService.createText(shape, getNameText(addedBusinessObjecty));
			text.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_TEXT_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);

			// vertical alignment has as default value "center"
			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			gaService.setLocationAndSize(text, 0, 0, MyAdminGraphitiConstants.CLASS_DEFAULT_WIDTH, 20);

			GraphitiProperties.set(text, MyAdminGraphitiConstants.ELEMENT_ID_KEY, NAME_TEXT_ID);

			// create link and wire it
			link(shape, addedBusinessObjecty);
		}

		return containerShape;
	}

	protected abstract String getNameText(T businessObject);

}
