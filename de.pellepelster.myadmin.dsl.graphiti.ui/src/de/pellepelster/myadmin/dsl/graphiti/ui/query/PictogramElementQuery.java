package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;

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
}
