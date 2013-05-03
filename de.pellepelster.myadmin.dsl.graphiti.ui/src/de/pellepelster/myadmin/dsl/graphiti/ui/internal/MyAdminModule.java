package de.pellepelster.myadmin.dsl.graphiti.ui.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.ui.editor.GlobalURIEditorOpener;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.ui.resource.Storage2UriMapperImpl;

import com.google.inject.Binder;

public class MyAdminModule extends AbstractGenericModule
{
	public Class<? extends IURIEditorOpener> bindIURIEditorOpener()
	{
		return GlobalURIEditorOpener.class;
	}

	@Override
	public void configure(Binder binder)
	{
		binder.bind(IStorage2UriMapper.class).to(Storage2UriMapperImpl.class);
		binder.bind(IWorkbench.class).toInstance(PlatformUI.getWorkbench());
		binder.bind(IWorkspace.class).toInstance(ResourcesPlugin.getWorkspace());
		// binder.bind(String.class).annotatedWith(Names.named("diagramTypeId")).toInstance("MyAdminModelDiagram");
	}
}