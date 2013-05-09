package de.pellepelster.myadmin.dsl.graphiti.ui.base.container;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
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
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.SizeAndLocation;

public abstract class BaseContainerAddFeature<BO extends EObject> extends AbstractAddShapeFeature
{
	public static final int CONTAINER_NAME_TEXT_HEIGHT = 25;

	public static final String CONTAINER_HEADER_LINE_ID = "container.header.line";

	public static final String CONTAINER_NAME_TEXT_ID = "container.name.text";

	public static final String CONTAINER_ID = "container.container";

	private Class<BO> businessObjectClass;

	public BaseContainerAddFeature(IFeatureProvider fp, Class<BO> businessObjectClass)
	{
		super(fp);
		this.businessObjectClass = businessObjectClass;
	}

	@Override
	public boolean canAdd(IAddContext context)
	{
		return AddContextQuery.create(context).newObjectTypeIs(this.businessObjectClass).targetContainerTypeIs(Diagram.class).result();
	}

	@SuppressWarnings("unchecked")
	public PictogramElement addInternal(IAddContext context)
	{
		BO addedBusinessObject = (BO) context.getNewObject();
		Diagram targetDiagram = (Diagram) context.getTargetContainer();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);
		GraphitiProperties.set(containerShape, MyAdminGraphitiConstants.ELEMENT_ID_KEY, CONTAINER_ID);
		link(containerShape, addedBusinessObject);

		RoundedRectangle roundedRectangle;

		{
			roundedRectangle = gaService.createRoundedRectangle(containerShape, 5, 5);
			roundedRectangle.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			roundedRectangle.setBackground(manageColor(MyAdminGraphitiConstants.CLASS_BACKGROUND));
			roundedRectangle.setTransparency(MyAdminGraphitiConstants.CLASS_TRANSPARENCY);
			roundedRectangle.setLineWidth(2);

			SizeAndLocation.create(context, MyAdminGraphitiConstants.CLASS_DEFAULT_WIDTH, MyAdminGraphitiConstants.CLASS_DEFAULT_HEIGHT).setLocationAndSize(
					roundedRectangle);
		}

		// header line
		{
			Shape shape = peCreateService.createShape(containerShape, false);

			Polyline polyline = gaService.createPolyline(shape, SizeAndLocation.create(roundedRectangle).setYAndHeight(this.CONTAINER_NAME_TEXT_HEIGHT)
					.getPoints());
			polyline.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			polyline.setLineWidth(2);
			GraphitiProperties.set(polyline, MyAdminGraphitiConstants.ELEMENT_ID_KEY, CONTAINER_HEADER_LINE_ID);

		}

		// name text
		{
			Shape shape = peCreateService.createShape(containerShape, false);

			Text text = gaService.createText(shape, getNameText(addedBusinessObject));
			text.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_TEXT_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			GraphitiProperties.set(text, MyAdminGraphitiConstants.ELEMENT_ID_KEY, CONTAINER_NAME_TEXT_ID);
			link(shape, addedBusinessObject);

			IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
			directEditingInfo.setMainPictogramElement(containerShape);
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);

			SizeAndLocation.create(roundedRectangle).setHeight(this.CONTAINER_NAME_TEXT_HEIGHT).setLocationAndSize(text);

		}

		return containerShape;
	}

	protected abstract String getNameText(BO businessObject);

}
