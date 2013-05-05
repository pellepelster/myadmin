package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IDirectEditingInfo;
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
	private int HEADER_LINE_Y = 20;

	private Class<T> businessObjectClass;

	public BaseClassAddFeature(IFeatureProvider fp, Class<T> businessObjectClass)
	{
		super(fp);
		this.businessObjectClass = businessObjectClass;
	}

	public static final String HEADER_LINE_ID = "entity.header.line";

	public static final String NAME_TEXT_ID = "entity.name.text";

	public static final String ATTRIBUTES_CONTAINER_ID = "attributes.container";

	@Override
	public boolean canAdd(IAddContext context)
	{
		return AddContextQuery.create(context).newObjectTypeIs(this.businessObjectClass).targetContainerIs(Diagram.class).result();
	}

	private int[] getHeaderLinePoints(GraphicsAlgorithm graphicsAlgorithm)
	{
		return new int[] { 0, this.HEADER_LINE_Y, graphicsAlgorithm.getWidth(), this.HEADER_LINE_Y };
	}

	@SuppressWarnings("unchecked")
	public PictogramElement addInternal(IAddContext context)
	{
		T addedBusinessObject = (T) context.getNewObject();

		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);
		IGaService gaService = Graphiti.getGaService();

		RoundedRectangle roundedRectangle; // need to access it later

		{
			roundedRectangle = gaService.createRoundedRectangle(containerShape, 5, 5);
			roundedRectangle.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			roundedRectangle.setBackground(manageColor(MyAdminGraphitiConstants.CLASS_BACKGROUND));
			roundedRectangle.setLineWidth(2);
			gaService.setLocationAndSize(roundedRectangle, context.getX(), context.getY(), MyAdminGraphitiConstants.CLASS_DEFAULT_WIDTH,
					MyAdminGraphitiConstants.CLASS_DEFAULT_HEIGHT);

			link(containerShape, addedBusinessObject);
		}

		// header line
		{
			Shape shape = peCreateService.createShape(containerShape, false);

			Polyline polyline = gaService.createPolyline(shape, getHeaderLinePoints(roundedRectangle));
			polyline.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			polyline.setLineWidth(2);
			GraphitiProperties.set(polyline, MyAdminGraphitiConstants.ELEMENT_ID_KEY, HEADER_LINE_ID);
		}

		// name text
		{
			Shape shape = peCreateService.createShape(containerShape, false);

			Text text = gaService.createText(shape, getNameText(addedBusinessObject));
			text.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_TEXT_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);

			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			gaService.setLocationAndSize(text, 0, 0, MyAdminGraphitiConstants.CLASS_DEFAULT_WIDTH, this.HEADER_LINE_Y);

			GraphitiProperties.set(text, MyAdminGraphitiConstants.ELEMENT_ID_KEY, NAME_TEXT_ID);
			link(shape, addedBusinessObject);

			IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
			directEditingInfo.setMainPictogramElement(containerShape);
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);
		}

		// attributes container
		{
			ContainerShape attributesContainerShape = peCreateService.createContainerShape(containerShape, false);

			RoundedRectangle attributesRoundedRectangle = gaService.createRoundedRectangle(attributesContainerShape, 5, 5);
			attributesRoundedRectangle.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			attributesRoundedRectangle.setBackground(manageColor(MyAdminGraphitiConstants.CLASS_ATTRIBUTES_BACKGROUND));
			gaService.setLocationAndSize(attributesRoundedRectangle, context.getX(), this.HEADER_LINE_Y, MyAdminGraphitiConstants.CLASS_DEFAULT_WIDTH,
					MyAdminGraphitiConstants.CLASS_DEFAULT_HEIGHT);

			GraphitiProperties.set(attributesContainerShape, MyAdminGraphitiConstants.ELEMENT_ID_KEY, ATTRIBUTES_CONTAINER_ID);
			link(attributesContainerShape, addedBusinessObject);

		}

		return containerShape;
	}

	protected abstract String getNameText(T businessObject);

}
