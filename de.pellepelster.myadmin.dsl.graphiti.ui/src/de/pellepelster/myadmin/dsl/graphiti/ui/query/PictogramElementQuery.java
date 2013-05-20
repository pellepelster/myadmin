package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;

public class PictogramElementQuery extends BaseQuery
{
	private PictogramElement pictogramElement;

	public static PictogramElementQuery create(PictogramElement pictogramElement)
	{
		return new PictogramElementQuery(pictogramElement);
	}

	private PictogramElementQuery(PictogramElement pictogramElement)
	{
		super();
		this.pictogramElement = pictogramElement;
	}

	public PictogramElementQuery gaElementIdIs(String elementId)
	{
		getResults().add(elementId.equals(GraphitiProperties.get(this.pictogramElement.getGraphicsAlgorithm(), MyAdminGraphitiConstants.ELEMENT_ID_KEY)));
		return this;
	}

	public Shape getParenShapeWithId(String elementId)
	{
		if (this.pictogramElement instanceof Shape)
		{
			Shape shape = (Shape) this.pictogramElement;

			while (shape != null)
			{
				if (elementId.equals(GraphitiProperties.get(shape, MyAdminGraphitiConstants.ELEMENT_ID_KEY)))
				{
					return shape;
				}

				shape = shape.getContainer();
			}
		}

		return null;
	}

}
