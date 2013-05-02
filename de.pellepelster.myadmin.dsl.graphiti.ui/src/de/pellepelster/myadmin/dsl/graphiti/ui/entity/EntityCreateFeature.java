package de.pellepelster.myadmin.dsl.graphiti.ui.entity;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import com.google.inject.Inject;

import de.pellepelster.myadmin.dsl.graphiti.ui.ModelDiagramModelService;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;

public class EntityCreateFeature extends AbstractCreateFeature
{

	private static final String TITLE = "Create class";

	private static final String USER_QUESTION = "Enter new class name";

	@Inject
	private ModelDiagramModelService modelService;

	public EntityCreateFeature(IFeatureProvider fp)
	{
		// set name and description of the creation feature
		super(fp, "Entity", "Create Entity");
		modelService = ModelDiagramModelService.getModelService(fp.getDiagramTypeProvider());
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
		Entity newEntity = MyAdminDslFactory.eINSTANCE.createEntity();
		newEntity.setName(newClassName);

		modelService.getModel().getElements().add(newEntity);

        //getFeatureProvider().getDirectEditingInfo().setActive(true);
		
		addGraphicalRepresentation(context, newEntity);

		return new Object[] { newEntity };
	}
}