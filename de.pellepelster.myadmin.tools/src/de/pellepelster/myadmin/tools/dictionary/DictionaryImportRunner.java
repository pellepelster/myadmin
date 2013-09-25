/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.tools.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.springframework.context.event.ApplicationEventMulticaster;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_CONTAINER_TYPE;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryDatatypeVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryResultVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleDefinitionVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.dsl.MyAdminDslStandaloneSetup;
import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEditor;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryFilter;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryResult;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionarySearch;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.myAdminDsl.Module;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModuleDefinition;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModuleParameter;
import de.pellepelster.myadmin.dsl.myAdminDsl.NavigationNode;
import de.pellepelster.myadmin.server.services.events.DictionaryEvent;
import de.pellepelster.myadmin.tools.SpringModelUtils;

public class DictionaryImportRunner {

	public static <T extends EObject> List<T> filter(EObject eObject, Class<T> clazz) {
		return filter(eObject, clazz, null);
	}

	public static <T extends EObject> List<T> filter(EObject eObject, Class<T> clazz, Predicate<T> filter) {
		ResourceSet resourceSet = eObject.eResource().getResourceSet();

		Iterator<T> iterator = Iterators.filter(EcoreUtil.getAllContents(resourceSet, true), clazz);

		Iterator<T> result = iterator;

		if (filter != null) {
			result = Iterators.filter(iterator, filter);
		}

		return Lists.newArrayList(result);

	}

	private final Map<String, ModuleVO> moduleVOs = new HashMap<String, ModuleVO>();

	private final Map<String, ModuleDefinitionVO> moduleDefinitionVOs = new HashMap<String, ModuleDefinitionVO>();

	public final static Logger LOG = Logger.getLogger("DictionaryImport");

	private final IBaseEntityService baseEntityService;

	private Model model;

	private final org.springframework.core.io.Resource modelResource;

	private final List<org.springframework.core.io.Resource> modelResources = new ArrayList<org.springframework.core.io.Resource>();

	private ApplicationEventMulticaster applicationEventMulticaster;

	/**
	 * Constructor for DictionaryImportRunner
	 * 
	 * @param baseEntityService
	 * @param modelFile
	 */
	public DictionaryImportRunner(IBaseEntityService baseEntityService, ApplicationEventMulticaster applicationEventMulticaster, List<org.springframework.core.io.Resource> modelResources, org.springframework.core.io.Resource modelResource) {

		this.baseEntityService = baseEntityService;
		this.applicationEventMulticaster = applicationEventMulticaster;

		List<String> currentModelFileNames = new ArrayList<String>();
		for (org.springframework.core.io.Resource resource : modelResources) {
			if (!currentModelFileNames.contains(resource.getFilename()) && !modelResource.getFilename().equals(resource.getFilename())) {
				this.modelResources.add(resource);
				currentModelFileNames.add(resource.getFilename());
			}
		}

		this.modelResource = modelResource;
	}

	private void createDictionaries(int logIdentiation) {

		List<Dictionary> dictionaries = filter(this.model, Dictionary.class);

		ToolUtils.logInfo(LOG, String.format("importing %d dictionaries", dictionaries.size()), logIdentiation);

		for (Dictionary dictionary : dictionaries) {

			DictionaryVO dictionaryVO = DictionaryFactory.createDictionary(dictionary, logIdentiation + 1);

			// - editor ---------------------------------------------------
			DictionaryEditor dictionaryEditor = dictionary.getDictionaryeditor();

			if (dictionaryEditor != null) {
				DictionaryEditorVO dictionaryEditorVO = DictionaryFactory.createEditor(dictionaryEditor, logIdentiation + 1);
				dictionaryVO.setEditor(dictionaryEditorVO);
			}
			// - /editor --------------------------------------------------

			// - search ---------------------------------------------------
			DictionarySearch dictionarySearch = dictionary.getDictionarysearch();
			if (dictionarySearch != null) {
				DictionarySearchVO dictionarySearchVO = createSearch(dictionarySearch, logIdentiation + 1);
				dictionaryVO.setSearch(dictionarySearchVO);

				// - result
				// ---------------------------------------------------
				DictionaryResult dictionaryResult = dictionarySearch.getDictionaryresult();
				if (dictionaryResult == null) {
					throw new RuntimeException("no result defined");
				}
				DictionaryResultVO dictionaryResultVO = new DictionaryResultVO();
				dictionaryResultVO.setName(dictionaryResult.getName());

				for (DictionaryControl dictionaryControl : dictionaryResult.getResultcolumns()) {
					DictionaryControlVO dictionaryControlVO = ControlFactory.getInstance().createDictionaryControlVO(dictionaryControl, logIdentiation + 1);
					dictionaryResultVO.getControls().add(dictionaryControlVO);
				}

				ToolUtils.logInfo(LOG, String.format("adding dictionary result '%s'", dictionaryResultVO.getName()), logIdentiation);
				dictionarySearchVO.setResult(dictionaryResultVO);
				// - /result
				// --------------------------------------------------

				// - filter
				// ---------------------------------------------------
				for (DictionaryFilter dictionaryfilter : dictionarySearch.getDictionaryfilters()) {
					DictionaryFilterVO dictionaryFilterVO = new DictionaryFilterVO();
					dictionaryFilterVO.setName(dictionaryfilter.getName());
					ToolUtils.logInfo(LOG, String.format("adding dictionary filter '%s'", dictionaryFilterVO.getName()), logIdentiation);
					dictionarySearchVO.getFilter().add(dictionaryFilterVO);

					DictionaryContainerVO filterRootCompositeVO = new DictionaryContainerVO();
					filterRootCompositeVO.setType(DICTIONARY_CONTAINER_TYPE.COMPOSITE);

					ContainerFactory.getInstance().createContainer(filterRootCompositeVO, dictionaryfilter.getContainercontents(), logIdentiation + 1);

					dictionaryFilterVO.setContainer(filterRootCompositeVO);

				}
				// -/ filter
				// --------------------------------------------------
			}
			// - /search --------------------------------------------------

			ToolUtils.logInfo(LOG, String.format("persisting dictionary '%s'", dictionaryVO.getName()), logIdentiation);
			this.baseEntityService.create(dictionaryVO);

		}
	}

	private void createModuleDefinition(ModuleDefinition moduleDefinition, int logIdentiation) {
		ToolUtils.logInfo(LOG, String.format("importing module definition '%s'", moduleDefinition.getName()), logIdentiation);

		ModuleDefinitionVO moduleDefinitionVO = new ModuleDefinitionVO();
		moduleDefinitionVO.setName(moduleDefinition.getName());

		this.moduleDefinitionVOs.put(moduleDefinition.getName(), this.baseEntityService.create(moduleDefinitionVO));
	}

	private void createModuleDefinitions(int logIdentiation) {
		List<ModuleDefinition> moduleDefinitions = filter(this.model, ModuleDefinition.class);

		ToolUtils.logInfo(LOG, String.format("importing %d module definitions", moduleDefinitions.size()), logIdentiation);

		for (ModuleDefinition moduleDefinition : moduleDefinitions) {
			createModuleDefinition(moduleDefinition, logIdentiation + 1);
		}

	}

	private void createModules(int logIdentation) {

		ToolUtils.logInfo(LOG, String.format("importing modules"), logIdentation);

		for (Module module : filter(this.model, Module.class)) {
			ModuleVO moduleVO = new ModuleVO();
			moduleVO.setName(module.getName());

			moduleVO.setModuleDefinition(this.moduleDefinitionVOs.get(module.getModuledefinition().getName()));

			ToolUtils.logInfo(LOG, String.format("importing module '%s'", moduleVO.getName()), logIdentation);

			for (ModuleParameter moduleParameter : module.getModuleParameters()) {

				ToolUtils.logInfo(LOG, String.format("setting property '%s' with value '%s'", moduleParameter.getModuleDefinitionParameter().getName(), moduleParameter.getValue()), logIdentation + 1);
				moduleVO.getProperties().put(moduleParameter.getModuleDefinitionParameter().getName(), moduleParameter.getValue());
			}

			this.moduleVOs.put(moduleVO.getName(), this.baseEntityService.create(moduleVO));
		}

	}

	private void createNavigationTree(int logIdentation) {

		ToolUtils.logInfo(LOG, "creating navigation tree", logIdentation);

		// - create navigation table --------------------------------------
		List<ModuleNavigationVO> navigationVOList = new ArrayList<ModuleNavigationVO>();

		Predicate<NavigationNode> filter = new Predicate<NavigationNode>() {
			@Override
			public boolean apply(NavigationNode navigationNode) {
				return !(navigationNode.eContainer() instanceof NavigationNode);
			}
		};

		createNavigationTree(filter(this.model, NavigationNode.class, filter), navigationVOList, logIdentation + 1);

		for (ModuleNavigationVO moduleNavigationVO : navigationVOList) {
			this.baseEntityService.create(moduleNavigationVO);
		}

	}

	private ModuleVO getOrCreateFakeModuleVO(String moduleDefinitionId, String moduleId, String moduleProperty, String moduleValue) {
		if (!this.moduleDefinitionVOs.containsKey(moduleDefinitionId)) {
			ModuleDefinitionVO moduleDefinitionVO = new ModuleDefinitionVO();
			moduleDefinitionVO.setName(moduleDefinitionId);
			this.moduleDefinitionVOs.put(moduleDefinitionId, this.baseEntityService.create(moduleDefinitionVO));
		}

		ModuleDefinitionVO moduleDefinitionVO = this.moduleDefinitionVOs.get(moduleDefinitionId);

		if (!this.moduleVOs.containsKey(moduleId)) {
			ModuleVO moduleVO = new ModuleVO();
			moduleVO.setName(moduleId);
			moduleVO.setModuleDefinition(moduleDefinitionVO);
			moduleVO.getProperties().put(moduleProperty, moduleValue);
			this.moduleVOs.put(moduleId, this.baseEntityService.create(moduleVO));
		}

		ModuleVO moduleVO = this.moduleVOs.get(moduleId);

		return moduleVO;
	}

	private String getParentDictionaryName(EObject eObject) {
		if (eObject.eContainer() instanceof Dictionary) {
			Dictionary dictionary = (Dictionary) eObject.eContainer();
			return dictionary.getName();
		}

		return null;
	}

	private void createNavigationTree(List<NavigationNode> navigationNodes, List<ModuleNavigationVO> parentNavigationList, int logIdentation) {

		for (NavigationNode navigationNode : navigationNodes) {
			ToolUtils.logInfo(LOG, String.format("creating navigation node '%s'", navigationNode.getName()), logIdentation);

			ModuleNavigationVO moduleNavigationVO = new ModuleNavigationVO();
			parentNavigationList.add(moduleNavigationVO);

			if (StringUtils.isEmpty(navigationNode.getTitle())) {
				moduleNavigationVO.setTitle(navigationNode.getName());
			} else {
				moduleNavigationVO.setTitle(navigationNode.getTitle());
			}

			if (navigationNode.getModule() != null && this.moduleVOs.containsKey((navigationNode.getModule().getName()))) {
				moduleNavigationVO.setModule(this.moduleVOs.get(navigationNode.getModule().getName()));
			}

			if (navigationNode.getDictionaryEditor() != null) {
				String dictionaryName = getParentDictionaryName(navigationNode.getDictionaryEditor());
				ModuleVO moduleVO = getOrCreateFakeModuleVO(DictionaryEditorModule.MODULE_ID, String.format("%s_editor", dictionaryName), DictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID, dictionaryName);
				moduleNavigationVO.setModule(moduleVO);
			}

			if (navigationNode.getDictionarySearch() != null) {
				String dictionaryName = getParentDictionaryName(navigationNode.getDictionarySearch());
				ModuleVO moduleVO = getOrCreateFakeModuleVO(DictionarySearchModule.MODULE_ID, String.format("%s_search", dictionaryName), DictionarySearchModule.SEARCHDICTIONARYNAME_PARAMETER_ID, dictionaryName);
				moduleNavigationVO.setModule(moduleVO);
			}

			createNavigationTree(navigationNode.getNavigationNodes(), moduleNavigationVO.getChildren(), logIdentation + 1);
		}

	}

	// private void createPermissions(int logIdentiation)
	// {
	// ImportUtil.logInfo(String.format("importing permissions"),
	// logIdentiation);
	//
	// List<MyAdminPermissionVO> oldPermissions =
	// baseEntityService.filter(GenericFilterFactory.createGenericFilter(MyAdminPermissionVO.class));
	// List<MyAdminPermissionVO> oldPermissionsToRetain = new
	// ArrayList<MyAdminPermissionVO>();
	// List<MyAdminPermissionVO> newPermissions = new
	// ArrayList<MyAdminPermissionVO>();
	//
	// // for (Permission permission : model.getPermissions().getPermission())
	// // {
	// //
	// // MyAdminPermissionVO permissionVO = getPermission(oldPermissions,
	// // permission);
	// //
	// // if (permissionVO != null)
	// // {
	// // oldPermissions.remove(permissionVO);
	// // oldPermissionsToRetain.add(permissionVO);
	// // }
	// // else
	// // {
	// // newPermissions.add(createPermissionVO(permission));
	// // }
	// // }
	//
	// ImportUtil.logInfo(String.format("removing %d old permissions",
	// oldPermissions.size()), logIdentiation + 1);
	// for (MyAdminPermissionVO permissionVO : oldPermissions)
	// {
	// baseEntityService.delete(permissionVO);
	// }
	//
	// ImportUtil.logInfo(String.format("creating %d new permissions",
	// newPermissions.size()), logIdentiation + 1);
	// for (MyAdminPermissionVO permissionVO : oldPermissions)
	// {
	// baseEntityService.create(permissionVO);
	// }
	//
	// }

	private DictionarySearchVO createSearch(DictionarySearch dictionarySearch, int logIdentiation) {

		DictionarySearchVO dictionarySearchVO = new DictionarySearchVO();
		dictionarySearchVO.setName(dictionarySearch.getName());
		dictionarySearchVO.setTitle(dictionarySearch.getTitle());

		ToolUtils.logInfo(LOG, String.format("adding dictionary search '%s'", dictionarySearchVO.getName()), logIdentiation);

		return dictionarySearchVO;

	}

	public Model getModel() {
		return this.model;
	}

	private void initDictionaryTables(int logIdentation) {

		ToolUtils.logInfo(LOG, "initializing dictionary tables", logIdentation);

		// - remove all existing dictionaries -----------------------------
		this.baseEntityService.deleteAll(DictionaryVO.class.getName());
		this.baseEntityService.deleteAll(DictionarySearchVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryFilterVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryResultVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryEditorVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryContainerVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryControlVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryDatatypeVO.class.getName());
	}

	/**
	 * Clean up module tables
	 */
	private void initModuleTables(int logIdentation) {

		ToolUtils.logInfo(LOG, "initializing module tables", logIdentation);

		this.baseEntityService.deleteAll(ModuleNavigationVO.class.getName());
		this.baseEntityService.deleteAll(ModuleVO.class.getName());
		this.baseEntityService.deleteAll(ModuleDefinitionVO.class.getName());
	}

	public void run() {

		MyAdminDslStandaloneSetup.doSetup();

		int logIdentiation = 0;

		LOG.info(String.format("starting dictionary import"));

		this.model = SpringModelUtils.getModel(this.modelResource, this.modelResources);

		// modules
		initModuleTables(logIdentiation + 1);
		createModuleDefinitions(logIdentiation + 1);
		createModules(logIdentiation + 1);
		createNavigationTree(logIdentiation + 1);

		initDictionaryTables(logIdentiation + 1);
		createDictionaries(logIdentiation + 1);

		applicationEventMulticaster.multicastEvent(new DictionaryEvent(DictionaryEvent.DICTIONARY_EVENT_TYPE.IMPORT_FINISHED));

		/*
		 * createPermissions(logIdentiation + 1);
		 */
	}
}
