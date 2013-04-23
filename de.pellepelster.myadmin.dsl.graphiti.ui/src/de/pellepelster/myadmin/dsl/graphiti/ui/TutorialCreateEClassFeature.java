package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;

public class TutorialCreateEClassFeature extends AbstractCreateFeature
{

	private static final String TITLE = "Create class";

	private static final String USER_QUESTION = "Enter new class name";

	public TutorialCreateEClassFeature(IFeatureProvider fp)
	{
		// set name and description of the creation feature
		super(fp, "Entity", "Create Entity");
	}

	@Override
	public boolean canCreate(ICreateContext context)
	{
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context)
	{
		// ask user for EClass name
		String newClassName = "abc"; // ExampleUtil.askString(TITLE,
										// USER_QUESTION, "");
		if (newClassName == null || newClassName.trim().length() == 0)
		{
			return EMPTY;
		}

		// create EClass
		Entity newClass = MyAdminDslFactory.eINSTANCE.createEntity();
		// Add model element to resource.
		// We add the model element to the resource of the diagram for
		// simplicity's sake. Normally, a customer would use its own
		// model persistence layer for storing the business model separately.
		getDiagram().eResource().getContents().add(newClass);
		newClass.setName(newClassName);

		// do the add
		addGraphicalRepresentation(context, newClass);

		// return newly created business object(s)
		return new Object[] { newClass };
	}
}