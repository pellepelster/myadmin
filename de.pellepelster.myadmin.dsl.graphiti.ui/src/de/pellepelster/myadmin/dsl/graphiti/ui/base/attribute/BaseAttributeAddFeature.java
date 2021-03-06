package de.pellepelster.myadmin.dsl.graphiti.ui.base.attribute;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.container.BaseContainerAddFeature;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.sizeandlocation.PrecompiledSizeAndLocationHandler;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.sizeandlocation.SizeAndLocationHandler;

public abstract class BaseAttributeAddFeature<T extends EObject> extends AbstractAddShapeFeature
{

	public static final String ATTRIBUTE_NAME_TEXT_ID = "attribute.name.text";

	public static final String ATTRIBUTE_TYPE_TEXT_ID = "attribute.type";

	public static final String ATTRIBUTE_ENTITY_LINK_ID = "attribute.entity.link";

	public static final String ATTRIBUTE_CONTAINER_ID = "attribute";

	public static final PrecompiledSizeAndLocationHandler NAME_TEXT_SIZE_AND_LOCATION_HANDLER = PrecompiledSizeAndLocationHandler.create().setColumn(2, 0);

	public static final PrecompiledSizeAndLocationHandler TYPE_TEXT_SIZE_AND_LOCATION_HANDLER = PrecompiledSizeAndLocationHandler.create().setColumn(2, 1);

	public BaseAttributeAddFeature(IFeatureProvider fp, Class<T> attributeClass)
	{
		super(fp);
	}

	@SuppressWarnings("unchecked")
	public PictogramElement addInternal(IAddContext context)
	{
		T addedBusinessObject = (T) context.getNewObject();
		ContainerShape entityContainer = context.getTargetContainer();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		ContainerShape attributeContainerShape = peCreateService.createContainerShape(entityContainer, true);
		GraphitiProperties.set(attributeContainerShape, MyAdminGraphitiConstants.ELEMENT_ID_KEY, ATTRIBUTE_CONTAINER_ID);
		RoundedRectangle roundedRectangle;

		{
			roundedRectangle = gaService.createRoundedRectangle(attributeContainerShape, 5, 5);
			roundedRectangle.setForeground(manageColor(MyAdminGraphitiConstants.ATTRIBUTE_FOREGROUND));
			roundedRectangle.setBackground(manageColor(MyAdminGraphitiConstants.ATTRIBUTE_BACKGROUND));
			roundedRectangle.setTransparency(MyAdminGraphitiConstants.ATTRIBUTE_TRANSPARENCY);
			roundedRectangle.setLineWidth(2);
			link(attributeContainerShape, addedBusinessObject);

			SizeAndLocationHandler.create(entityContainer).addWidthOffset(MyAdminGraphitiConstants.MARGIN * -2)
					.setHeight(MyAdminGraphitiConstants.ATTRIBUTE_HEIGHT)
					.setY(BaseContainerAddFeature.CONTAINER_NAME_TEXT_HEIGHT + MyAdminGraphitiConstants.MARGIN).centerHorizontal()
					.setSizeAndLocation(roundedRectangle);
		}

		// attribute name text
		{
			Shape shape = peCreateService.createShape(attributeContainerShape, false);

			Text text = gaService.createText(shape, getNameText(addedBusinessObject));
			text.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_TEXT_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			GraphitiProperties.set(text, MyAdminGraphitiConstants.ELEMENT_ID_KEY, ATTRIBUTE_NAME_TEXT_ID);
			link(shape, addedBusinessObject);

			IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
			directEditingInfo.setMainPictogramElement(attributeContainerShape);
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);

			NAME_TEXT_SIZE_AND_LOCATION_HANDLER.setSizeAndLocation(roundedRectangle, text);
		}

		// attribute type text
		{
			Shape shape = peCreateService.createShape(attributeContainerShape, false);

			Text text = gaService.createText(shape, getTypeText(addedBusinessObject));
			text.setForeground(manageColor(MyAdminGraphitiConstants.CLASS_TEXT_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			GraphitiProperties.set(text, MyAdminGraphitiConstants.ELEMENT_ID_KEY, ATTRIBUTE_TYPE_TEXT_ID);
			link(shape, addedBusinessObject);

			IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
			directEditingInfo.setMainPictogramElement(attributeContainerShape);
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);

			TYPE_TEXT_SIZE_AND_LOCATION_HANDLER.setSizeAndLocation(roundedRectangle, text);
		}

		layoutPictogramElement(entityContainer);

		return attributeContainerShape;
	}

	protected abstract String getNameText(T businessObject);

	protected abstract String getTypeText(T businessObject);

}
