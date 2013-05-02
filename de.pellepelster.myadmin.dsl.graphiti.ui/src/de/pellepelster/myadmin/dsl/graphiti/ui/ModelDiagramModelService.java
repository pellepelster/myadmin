package de.pellepelster.myadmin.dsl.graphiti.ui;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;

public class ModelDiagramModelService
{

	public static final String FILE_EXTENSION = "msl";

	protected IPeService peService;
	
	protected IDiagramTypeProvider dtp;

	private static Logger LOG = Logger.getLogger(ModelDiagramModelService.class);

	static protected ModelDiagramModelService modelService = null;

	/**
	 * return the model service, create one if it does not exist yet.
	 */
	static public ModelDiagramModelService getModelService(IDiagramTypeProvider dtp)
	{
		modelService = new ModelDiagramModelService(dtp);
		return modelService;
	}

	/**
	 * return the model service. returns null if there is no model service.
	 */
	static public ModelDiagramModelService getModelService()
	{
		return modelService;
	}

	protected ModelDiagramModelService(IDiagramTypeProvider dtp)
	{
		this.dtp = dtp;
		this.peService = Graphiti.getPeService();
	}

	public Model getModel()
	{
		final Diagram diagram = dtp.getDiagram();
		
		Resource resource = diagram.eResource();
		ResourceSet resourceSet = resource.getResourceSet();
		EObject businessObject = (EObject) dtp.getFeatureProvider().getBusinessObjectForPictogramElement(diagram);
		
		Model model = null;
		
		if (businessObject != null)
		{
			// If its a proxy, resolve it
			if (businessObject.eIsProxy())
			{
				if (businessObject instanceof InternalEObject)
				{
					model = (Model) resourceSet.getEObject(((InternalEObject) businessObject).eProxyURI(), true);
				}
			}
			else
			{
				if (businessObject instanceof Model)
				{
					model = (Model) businessObject;
				}
			}
		}

		if (model == null)
		{
			model = createModel();
		}
		
		return model;
	}

	public Object getBusinessObject(PictogramElement pe)
	{
		return dtp.getFeatureProvider().getBusinessObjectForPictogramElement(dtp.getDiagram());
	}

	/**
	 * Creates the domain model element and store it in the file. Overwrite to
	 * set required properties on creation.
	 */
	protected Model createModel()
	{
		final Diagram diagram = dtp.getDiagram();
		
		try
		{
			Model model = MyAdminDslFactory.eINSTANCE.createModel();
			if (StringUtils.isEmpty(model.getName()) && !StringUtils.isEmpty(GraphitiProperties.get(diagram, MyAdminGraphitiConstants.PROJECT_NAME_KEY)))
			{
				model.setName(GraphitiProperties.get(diagram, MyAdminGraphitiConstants.PROJECT_NAME_KEY));
			}

			createModelResourceAndAddModel(model);
			
			//peService.setPropertyValue(diagram, PROPERTY_URI, EcoreUtil.getURI(model).toString());
			
			dtp.getFeatureProvider().link(diagram, model);
			
			return model;
		}
		catch (Exception e)
		{
			LOG.error(String.format("error creating model for diagram '%s'", diagram.getName()), e);
			return null;
		}
	}

	protected void createModelResourceAndAddModel(final Model model) throws CoreException, IOException
	{

		final Diagram diagram = dtp.getDiagram();

		URI uri = diagram.eResource().getURI();
		uri = uri.trimFragment();
		uri = uri.trimFileExtension();
		uri = uri.appendFileExtension(FILE_EXTENSION);

		ResourceSet resourceSet = diagram.eResource().getResourceSet();

		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IResource file = workspaceRoot.findMember(uri.toPlatformString(true));

		if (file == null || !file.exists())
		{
			Resource resource = resourceSet.createResource(uri);
			resource.setTrackingModification(true);
			resource.getContents().add(model);
		}
		else
		{
			final Resource resource = resourceSet.getResource(uri, true);
			resource.getContents().add(model);
		}
	}
}
