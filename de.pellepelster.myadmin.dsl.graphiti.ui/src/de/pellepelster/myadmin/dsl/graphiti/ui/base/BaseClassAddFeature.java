package de.pellepelster.myadmin.dsl.graphiti.ui.base;

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

public abstract class BaseClassAddFeature<T extends EObject> extends AbstractAddShapeFeature
{
	public final static int HEADER_LINE_Y = 25;

	public static final String HEADER_LINE_ID = "entity.header.line";

	public static final String NAME_TEXT_ID = "entity.name.text";

	public static final String ENTITY_CONTAINER_ID = "entity.container";

	public static final String ATTRIBUTES_SHAPE_ID = "attributes.shape";

	private Class<T> businessObjectClass;

	public BaseClassAddFeature(IFeatureProvider fp, Class<T> businessObjectClass)
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
		T addedBusinessObject = (T) context.getNewObject();
		Diagram targetDiagram = (Diagram) context.getTargetContainer();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		ContainerShape entityContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		GraphitiProperties.set(entityContainerShape, MyAdminGraphitiConstants.ELEMENT_ID_KEY, ENTITY_CONTAINER_ID);
		link(entityContainerShape, addedBusinessObject);

		RoundedRectangle roundedRectangle;

		{
			roundedRectangle = gaService.createRoundedRectangle(entityContainerShape, 5, 5);
			roundedRectangle.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			roundedRectangle.setBackground(manageColor(MyAdminGraphitiConstants.CLASS_BACKGROUND));
			roundedRectangle.setTransparency(MyAdminGraphitiConstants.CLASS_TRANSPARENCY);
			roundedRectangle.setLineWidth(2);

			SizeAndLocation.create(context, MyAdminGraphitiConstants.CLASS_DEFAULT_WIDTH, MyAdminGraphitiConstants.CLASS_DEFAULT_HEIGHT).setLocationAndSize(
					roundedRectangle);
		}

		// header line
		{
			Shape shape = peCreateService.createShape(entityContainerShape, false);

			Polyline polyline = gaService.createPolyline(shape, SizeAndLocation.create(roundedRectangle).setYAndHeight(this.HEADER_LINE_Y).getPoints());
			polyline.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			polyline.setLineWidth(2);
			GraphitiProperties.set(polyline, MyAdminGraphitiConstants.ELEMENT_ID_KEY, HEADER_LINE_ID);

		}

		// name text
		{
			Shape shape = peCreateService.createShape(entityContainerShape, false);

			Text text = gaService.createText(shape, getNameText(addedBusinessObject));
			text.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_TEXT_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			GraphitiProperties.set(text, MyAdminGraphitiConstants.ELEMENT_ID_KEY, NAME_TEXT_ID);
			link(shape, addedBusinessObject);

			IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
			directEditingInfo.setMainPictogramElement(entityContainerShape);
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);

			SizeAndLocation.create(roundedRectangle).setHeight(this.HEADER_LINE_Y).setLocationAndSize(text);

		}

		// attributes container
		{
			Shape shape = peCreateService.createShape(entityContainerShape, false);

			RoundedRectangle attributesRoundedRectangle = gaService.createRoundedRectangle(shape, 5, 5);
			attributesRoundedRectangle.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_FOREGROUND));
			attributesRoundedRectangle.setBackground(manageColor(MyAdminGraphitiConstants.CLASS_ATTRIBUTES_BACKGROUND));

			GraphitiProperties.set(attributesRoundedRectangle, MyAdminGraphitiConstants.ELEMENT_ID_KEY, ATTRIBUTES_SHAPE_ID);

			SizeAndLocation.create(0, 0, roundedRectangle.getWidth(), roundedRectangle.getHeight()).setYAndHeight(HEADER_LINE_Y)
					.setLocationAndSize(attributesRoundedRectangle);
		}

		return entityContainerShape;
	}

	protected abstract String getNameText(T businessObject);

}
