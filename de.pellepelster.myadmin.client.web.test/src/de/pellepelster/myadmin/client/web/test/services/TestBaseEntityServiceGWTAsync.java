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
package de.pellepelster.myadmin.client.web.test.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.jpql.EntityVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.jpql.IConditionalExpressionVO;
import de.pellepelster.myadmin.client.base.jpql.expressions.EntityExpressionObjectVO;
import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.messages.ValidationMessage;
import de.pellepelster.myadmin.client.base.util.CollectionUtils;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleDefinitionVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModule;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.modules.navigation.ModuleNavigationModule;
import de.pellepelster.myadmin.client.web.services.IBaseEntityServiceGWTAsync;
import de.pellepelster.myadmin.client.web.test.vo.BaseHierarchicalTestVO;
import de.pellepelster.myadmin.client.web.test.vo.HierarchicalTest1VO;
import de.pellepelster.myadmin.client.web.test.vo.HierarchicalTest2VO;
import de.pellepelster.myadmin.client.web.test.vo.HierarchicalTest3VO;
import de.pellepelster.myadmin.client.web.test.vo.Test1VO;
import de.pellepelster.myadmin.client.web.test.vo.Test2VO;
import de.pellepelster.myadmin.client.web.test.vo.Test3VO;

@SuppressWarnings("unchecked")
public class TestBaseEntityServiceGWTAsync implements IBaseEntityServiceGWTAsync {

	private final List<Test1VO> test1vos = new ArrayList<Test1VO>();
	private final List<Test2VO> test2vos = new ArrayList<Test2VO>();
	private final List<Test3VO> test3vos = new ArrayList<Test3VO>();

	public final static List<IHierarchicalVO> hierarchicalTest1VOs = new ArrayList<IHierarchicalVO>();
	public final static List<IHierarchicalVO> hierarchicalTest2VOs = new ArrayList<IHierarchicalVO>();
	public final static List<IHierarchicalVO> hierarchicalTest3VOs = new ArrayList<IHierarchicalVO>();

	public TestBaseEntityServiceGWTAsync() {
		super();

		for (int i = 0; i < 100; i++) {
			Test3VO test3VO = getTest3VO(i, "string" + i);
			this.test3vos.add(test3VO);
		}

		for (int i = 0; i < 50; i++) {

			Test1VO test1VO = getTest1VO(i, "string" + i, i);

			for (int n = 0; n < 10; n++) {
				test1VO.getTest2VOs().add(getTest2VO(n, "string" + n, n));
			}

			this.test1vos.add(test1VO);
		}

		for (int i = 0; i < 50; i++) {

			Test2VO test2VO = getTest2VO(i, "string" + i, i);
			this.test2vos.add(test2VO);
		}

	}

	/** {@inheritDoc} */
	@Override
	public <CreateVOType extends IBaseVO> void create(CreateVOType vo, AsyncCallback<CreateVOType> callback) {
	}

	/** {@inheritDoc} */
	@Override
	public <DeleteVOType extends IBaseVO> void delete(DeleteVOType vo, AsyncCallback<Void> callback) {
	}

	/** {@inheritDoc} */
	@Override
	public void deleteAll(String voClassName, AsyncCallback<Void> callback) {
	}

	private <FilterVOType extends IBaseVO> void createHierarchicalVO(BaseHierarchicalTestVO hierarchicalTestVO, BaseHierarchicalTestVO parentVO, GenericFilterVO<FilterVOType> genericFilterVO, AsyncCallback<List<FilterVOType>> callback) {
		List<IHierarchicalVO> result = new ArrayList<IHierarchicalVO>();
		result.add(hierarchicalTestVO);

		Long id = (Long) getCriteria(genericFilterVO.getEntity(), BaseHierarchicalTestVO.FIELD_ID);
		String idString = Long.toString(id);

		hierarchicalTestVO.setId(id);
		hierarchicalTestVO.setString1(Long.toString(id));

		if (idString.length() > 1 && parentVO != null) {
			idString = idString.substring(0, idString.length() - 1);

			parentVO.setString1(idString);
			parentVO.setId(Long.parseLong(idString));
			hierarchicalTestVO.setParent(parentVO);
		}

		callback.onSuccess((List<FilterVOType>) getVOSubList(genericFilterVO, result));
	}

	/** {@iNamenheritDoc} */
	@Override
	public <FilterVOType extends IBaseVO> void filter(GenericFilterVO<FilterVOType> genericFilterVO, AsyncCallback<List<FilterVOType>> callback) {

		if (genericFilterVO.getVOClassName().equals(HierarchicalTest1VO.class.getName())) {
			createHierarchicalVO(new HierarchicalTest1VO(null), null, genericFilterVO, callback);
			return;
		}

		if (genericFilterVO.getVOClassName().equals(HierarchicalTest2VO.class.getName())) {
			createHierarchicalVO(new HierarchicalTest2VO(null), new HierarchicalTest1VO(null), genericFilterVO, callback);
			return;
		}

		if (genericFilterVO.getVOClassName().equals(HierarchicalTest3VO.class.getName())) {
			createHierarchicalVO(new HierarchicalTest3VO(null), new HierarchicalTest2VO(null), genericFilterVO, callback);
			return;
		}

		if (genericFilterVO.getVOClassName().equals(Test1VO.class.getName())) {

			if (hasCriteria(genericFilterVO.getEntity(), Test1VO.FIELD_ID)) {

				int index = Integer.parseInt(getCriteria(genericFilterVO.getEntity(), Test1VO.FIELD_ID).toString());
				callback.onSuccess((List<FilterVOType>) getResultList(this.test1vos.get(index)));

				return;
			} else {
				callback.onSuccess((List<FilterVOType>) getVOSubList(genericFilterVO, this.test1vos));

				return;
			}
		}

		if (genericFilterVO.getVOClassName().equals(Test2VO.class.getName())) {

			if (hasCriteria(genericFilterVO.getEntity(), Test2VO.FIELD_ID)) {

				int index = Integer.parseInt(getCriteria(genericFilterVO.getEntity(), Test2VO.FIELD_ID).toString());
				callback.onSuccess((List<FilterVOType>) getResultList(this.test2vos.get(index)));

				return;
			} else {
				callback.onSuccess((List<FilterVOType>) getVOSubList(genericFilterVO, this.test2vos));

				return;
			}
		}

		if (genericFilterVO.getVOClassName().equals(Test3VO.class.getName())) {
			if (hasCriteria(genericFilterVO.getEntity(), Test3VO.FIELD_ID)) {

				int index = Integer.parseInt(getCriteria(genericFilterVO.getEntity(), Test3VO.FIELD_ID).toString());
				callback.onSuccess((List<FilterVOType>) getResultList(this.test3vos.get(index)));

				return;
			} else {
				callback.onSuccess((List<FilterVOType>) getVOSubList(genericFilterVO, this.test3vos));
				return;
			}
		}

		if (genericFilterVO.getVOClassName().equals(ModuleVO.class.getName())) {

			// navigation module
			if (hasCriteria(genericFilterVO.getEntity(), ModuleVO.FIELD_NAME, ModuleNavigationModule.MODULE_ID)) {

				ModuleVO moduleVO = new ModuleVO();
				moduleVO.setName(ModuleNavigationModule.MODULE_ID);

				ModuleDefinitionVO moduleDefinitionVO = new ModuleDefinitionVO();
				moduleDefinitionVO.setName(ModuleNavigationModule.MODULE_ID);
				moduleVO.setModuleDefinition(moduleDefinitionVO);

				callback.onSuccess((List<FilterVOType>) getResultList(moduleVO));
				return;
			}

			// hierarchical tree module
			if (hasCriteria(genericFilterVO.getEntity(), ModuleVO.FIELD_NAME, HierarchicalTreeModule.MODULE_ID)) {

				ModuleVO moduleVO = new ModuleVO();
				moduleVO.setName(HierarchicalTreeModule.MODULE_ID);

				ModuleDefinitionVO moduleDefinitionVO = new ModuleDefinitionVO();
				moduleDefinitionVO.setName(HierarchicalTreeModule.MODULE_ID);
				moduleVO.setModuleDefinition(moduleDefinitionVO);

				callback.onSuccess((List<FilterVOType>) getResultList(moduleVO));
				return;
			}

			boolean handled = false;

			handled = handled | checkForDictionary(genericFilterVO, callback, TestDictionaryServiceGWTAsync.DICTIONARY1_ID);
			handled = handled | checkForDictionary(genericFilterVO, callback, TestDictionaryServiceGWTAsync.DICTIONARY2_ID);
			handled = handled | checkForDictionary(genericFilterVO, callback, TestDictionaryServiceGWTAsync.DICTIONARY3_ID);
			handled = handled | checkForDictionary(genericFilterVO, callback, TestDictionaryServiceGWTAsync.DICTIONARY_ASSIGNMENT_TEST_ID);
			handled = handled | checkForDictionary(genericFilterVO, callback, TestDictionaryServiceGWTAsync.DICTIONARY_HIERARCHICAL_TEST_ID);

			if (handled) {
				return;
			}

		}

		if (genericFilterVO.getVOClassName().equals(ModuleNavigationVO.class.getName())) {

			List<ModuleNavigationVO> navigationTree = createNavigationTree();
			callback.onSuccess((List<FilterVOType>) navigationTree);

			return;

		}

		callback.onFailure(new RuntimeException("filter for vo '" + genericFilterVO.getVOClassName() + "' or its filter parameters not implemented"));
	}

	private List<ModuleNavigationVO> createNavigationTree() {

		List<ModuleNavigationVO> result = new ArrayList<ModuleNavigationVO>();

		ModuleNavigationVO moduleNavigation1VO = new ModuleNavigationVO();
		moduleNavigation1VO.setTitle("Test");
		result.add(moduleNavigation1VO);

		ModuleNavigationVO moduleNavigation11VO = new ModuleNavigationVO();
		moduleNavigation11VO.setTitle("Dictionaries");
		moduleNavigation1VO.getChildren().add(moduleNavigation11VO);

		createDictionarySearchNavigationNode(moduleNavigation11VO, TestDictionaryServiceGWTAsync.DICTIONARY1_ID);
		createDictionarySearchNavigationNode(moduleNavigation11VO, TestDictionaryServiceGWTAsync.DICTIONARY2_ID);
		createDictionarySearchNavigationNode(moduleNavigation11VO, TestDictionaryServiceGWTAsync.DICTIONARY3_ID);
		createDictionaryEditorNavigationNode(moduleNavigation11VO, TestDictionaryServiceGWTAsync.DICTIONARY_ASSIGNMENT_TEST_ID);
		createDictionaryEditorNavigationNode(moduleNavigation11VO, TestDictionaryServiceGWTAsync.DICTIONARY_HIERARCHICAL_TEST_ID);

		return result;
	}

	private <FilterVOType extends IBaseVO> boolean checkForDictionary(GenericFilterVO<?> genericFilterVO, AsyncCallback<List<FilterVOType>> callback, String dictionaryId) {

		if (hasCriteria(genericFilterVO.getEntity(), ModuleVO.FIELD_NAME, dictionaryId + DictionarySearchModule.MODULE_ID)) {
			ModuleVO moduleVO = getSearchModule(dictionaryId);
			callback.onSuccess((List<FilterVOType>) getResultList(moduleVO));
			return true;
		}

		if (hasCriteria(genericFilterVO.getEntity(), ModuleVO.FIELD_NAME, dictionaryId + DictionaryEditorModule.MODULE_ID)) {
			ModuleVO moduleVO = getEditorModule(dictionaryId);
			callback.onSuccess((List<FilterVOType>) getResultList(moduleVO));
			return true;
		}

		return false;

	}

	private void createDictionarySearchNavigationNode(ModuleNavigationVO parentMNavigationVO, String dictionaryId) {
		ModuleNavigationVO moduleNavigationVO = new ModuleNavigationVO();
		moduleNavigationVO.setTitle(dictionaryId);
		parentMNavigationVO.getChildren().add(moduleNavigationVO);
		moduleNavigationVO.setModule(getSearchModule(dictionaryId));
	}

	private void createDictionaryEditorNavigationNode(ModuleNavigationVO parentMNavigationVO, String dictionaryId) {
		ModuleNavigationVO moduleNavigationVO = new ModuleNavigationVO();
		moduleNavigationVO.setTitle(dictionaryId);
		parentMNavigationVO.getChildren().add(moduleNavigationVO);
		moduleNavigationVO.setModule(getEditorModule(dictionaryId));
	}

	private Object getCriteria(EntityVO entityVO, IAttributeDescriptor<?> field) {

		for (IConditionalExpressionVO conditionalExpressionVO : entityVO.getCriteria()) {

			if (conditionalExpressionVO.getExpressionObject1() instanceof EntityExpressionObjectVO) {
				EntityExpressionObjectVO entityExpressionObjectVO = (EntityExpressionObjectVO) conditionalExpressionVO.getExpressionObject1();

				if (entityExpressionObjectVO.getField().equals(field.getAttributeName())) {
					return conditionalExpressionVO.getExpressionObject2().getValue();
				}
			}
		}

		return null;
	}

	private ModuleVO getEditorModule(String editorDictionaryName) {

		ModuleVO module = new ModuleVO();
		module.setName(editorDictionaryName + DictionaryEditorModule.MODULE_ID);
		module.getProperties().put(DictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID, editorDictionaryName);

		ModuleDefinitionVO dictionaryEditorModuleDefinition = new ModuleDefinitionVO();
		dictionaryEditorModuleDefinition.setName(DictionaryEditorModule.MODULE_ID);
		module.setModuleDefinition(dictionaryEditorModuleDefinition);

		return module;
	}

	/** {@inheritDoc} */
	@Override
	public void getNewVO(String voClassName, HashMap<String, String> parameters, AsyncCallback<IBaseVO> callback) {
		BaseHierarchicalTestVO parent = null;

		if (parameters.containsKey(IHierarchicalVO.FIELD_PARENT_CLASSNAME.getAttributeName()) && parameters.containsKey(IHierarchicalVO.FIELD_PARENT_ID.getAttributeName())) {
			String parentVOClassName = parameters.get(IHierarchicalVO.FIELD_PARENT_CLASSNAME.getAttributeName());
			Long parentVOID = Long.parseLong(parameters.get(IHierarchicalVO.FIELD_PARENT_ID.getAttributeName()));

			if (parentVOClassName.equals(HierarchicalTest1VO.class.getName())) {
				parent = new HierarchicalTest1VO(null);
			} else if (parentVOClassName.equals(HierarchicalTest2VO.class.getName())) {
				parent = new HierarchicalTest2VO(null);
			} else if (parentVOClassName.equals(HierarchicalTest3VO.class.getName())) {
				parent = new HierarchicalTest3VO(null);
			}

			parent.setId(parentVOID);
			parent.setString1(parentVOID.toString());
		}

		if (voClassName.equals(Test1VO.class.getName())) {
			callback.onSuccess(new Test1VO());
		} else if (voClassName.equals(Test2VO.class.getName())) {
			callback.onSuccess(new Test2VO());
		} else if (voClassName.equals(Test3VO.class.getName())) {
			callback.onSuccess(new Test3VO());
		} else if (voClassName.equals(HierarchicalTest1VO.class.getName())) {
			callback.onSuccess(new HierarchicalTest1VO(parent));
		} else if (voClassName.equals(HierarchicalTest2VO.class.getName())) {
			callback.onSuccess(new HierarchicalTest2VO(parent));
		} else if (voClassName.equals(HierarchicalTest3VO.class.getName())) {
			callback.onSuccess(new HierarchicalTest3VO(parent));
		} else {
			callback.onFailure(new RuntimeException("unsupported vo type '" + voClassName + "'"));
		}
	}

	private <T> List<T> getResultList(T resultItem) {
		List<T> resultList = new ArrayList<T>();
		resultList.add(resultItem);
		return resultList;
	}

	private ModuleVO getSearchModule(String searchDictionaryName) {

		ModuleVO module = new ModuleVO();
		module.setName(searchDictionaryName + DictionarySearchModule.MODULE_ID);
		module.getProperties().put(DictionarySearchModule.SEARCHDICTIONARYNAME_PARAMETER_ID, searchDictionaryName);

		ModuleDefinitionVO dictionarySearchModuleDefinition = new ModuleDefinitionVO();
		dictionarySearchModuleDefinition.setName(DictionarySearchModule.MODULE_ID);
		module.setModuleDefinition(dictionarySearchModuleDefinition);

		return module;
	}

	private Test1VO getTest1VO(long id, String string, Integer integer) {
		Test1VO test1vo = new Test1VO();
		test1vo.setId(id);
		test1vo.setString1(string);
		test1vo.setInteger1(integer);
		test1vo.setEnumeration1(Test1VO.ENUM1.ENUM1_VALUE1);
		test1vo.setBigDecimal1(new BigDecimal(integer + 0.1 * integer));
		test1vo.setDate1(new Date());
		test1vo.setBoolean1((integer % 2 == 0));

		test1vo.setTest3VO(this.test3vos.get((int) id));

		return test1vo;
	}

	private Test2VO getTest2VO(long id, String string, Integer integer) {
		Test2VO test2vo = new Test2VO();
		test2vo.setId(id);
		test2vo.setString2(string);
		test2vo.setInteger2(integer);
		test2vo.setEnumeration2(Test2VO.ENUM2.ENUM2_VALUE1);
		test2vo.setBigDecimal2(new BigDecimal(integer + 0.1 * integer));
		test2vo.setDate2(new Date());
		test2vo.setBoolean2((integer % 2 == 0));
		test2vo.setTest3VO(this.test3vos.get((int) id));

		return test2vo;
	}

	private Test3VO getTest3VO(long id, String string) {
		Test3VO test3vo = new Test3VO();
		test3vo.setId(id);
		test3vo.setString3(string);

		return test3vo;
	}

	private <VOType extends IBaseVO> List<VOType> getVOSubList(GenericFilterVO<?> genericFilter, List<VOType> list) {
		int to = (genericFilter.getMaxResults() + genericFilter.getFirstResult() > list.size()) ? list.size() : genericFilter.getMaxResults() + genericFilter.getFirstResult();
		int from = (genericFilter.getFirstResult() > list.size()) ? list.size() : genericFilter.getFirstResult();

		if (to == from) {
			return list;
		} else {
			return list.subList(from, to);
		}

	}

	private boolean hasCriteria(EntityVO entityVO, IAttributeDescriptor<?> field) {
		return getCriteria(entityVO, field) != null;
	}

	private boolean hasCriteria(EntityVO entityVO, IAttributeDescriptor<?> field, Object fieldValue) {

		Object criteriaValue = getCriteria(entityVO, field);

		if (criteriaValue == null) {
			return false;
		} else {
			return criteriaValue.equals(fieldValue);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void read(Long id, String voClassName, AsyncCallback<IBaseVO> callback) {
		if (voClassName.equals(HierarchicalTest1VO.class.getName())) {
			HierarchicalTest1VO hierarchicalTest1VO = new HierarchicalTest1VO(null);
			hierarchicalTest1VO.setString1("xxx");
			callback.onSuccess(hierarchicalTest1VO);
		}

	}

	/** {@inheritDoc} */
	@Override
	public <SaveVOType extends IBaseVO> void save(SaveVOType vo, AsyncCallback<SaveVOType> callback) {
	}

	/** {@inheritDoc} */
	@Override
	public <ValidateVOType extends IBaseVO> void validate(ValidateVOType vo, AsyncCallback<List<IValidationMessage>> callback) {
	}

	/** {@inheritDoc} */
	@Override
	public <ValidateAndCreateVOType extends IBaseVO> void validateAndCreate(ValidateAndCreateVOType vo, AsyncCallback<Result<ValidateAndCreateVOType>> callback) {
		if (vo instanceof Test1VO) {

			Result<Test1VO> result = new Result<Test1VO>();

			Test1VO test1vo = (Test1VO) vo;
			result.setVO(test1vo);

			if (test1vo.getString1() != null && test1vo.getString1().contains("error")) {
				ValidationMessage validationMessage = new ValidationMessage(IMessage.SEVERITY.ERROR, "error", "error", "error", CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY, "string1"));
				result.getValidationMessages().add(validationMessage);
			} else {
				test1vo.setId(this.test1vos.size());
				this.test1vos.add(test1vo);
			}

			callback.onSuccess((Result<ValidateAndCreateVOType>) result);
		} else if (vo instanceof HierarchicalTest1VO) {
			Result<HierarchicalTest1VO> result = new Result<HierarchicalTest1VO>();
			HierarchicalTest1VO hierarchicalTest1VO = (HierarchicalTest1VO) vo;
			result.setVO(hierarchicalTest1VO);
			hierarchicalTest1VOs.add((HierarchicalTest1VO) vo);
			hierarchicalTest1VO.setId(hierarchicalTest1VOs.size());

			callback.onSuccess((Result<ValidateAndCreateVOType>) result);
		} else if (vo instanceof HierarchicalTest2VO) {
			Result<HierarchicalTest2VO> result = new Result<HierarchicalTest2VO>();
			HierarchicalTest2VO hierarchicalTest2VO = (HierarchicalTest2VO) vo;
			result.setVO(hierarchicalTest2VO);
			hierarchicalTest2VOs.add((HierarchicalTest2VO) vo);
			hierarchicalTest2VO.setId(hierarchicalTest2VOs.size());
			callback.onSuccess((Result<ValidateAndCreateVOType>) result);
		} else if (vo instanceof HierarchicalTest3VO) {
			Result<HierarchicalTest3VO> result = new Result<HierarchicalTest3VO>();
			HierarchicalTest3VO hierarchicalTest3VO = (HierarchicalTest3VO) vo;
			result.setVO(hierarchicalTest3VO);
			hierarchicalTest3VOs.add((HierarchicalTest3VO) vo);
			hierarchicalTest3VO.setId(hierarchicalTest3VOs.size());
			callback.onSuccess((Result<ValidateAndCreateVOType>) result);
		} else {
			callback.onFailure(new RuntimeException("unsupported vo type '" + vo.getClass() + "'"));
		}
	}

	/** {@inheritDoc} */
	@Override
	public <ValidateAndSaveVOType extends IBaseVO> void validateAndSave(ValidateAndSaveVOType vo, AsyncCallback<Result<ValidateAndSaveVOType>> callback) {
		if (vo instanceof Test1VO) {

			Result<Test1VO> result = new Result<Test1VO>();

			Test1VO test1vo = (Test1VO) vo;
			result.setVO(test1vo);

			if (test1vo.getString1().contains("error")) {
				ValidationMessage validationMessage = new ValidationMessage(IMessage.SEVERITY.ERROR, "error", "error", "error", CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY, "string1"));
				result.getValidationMessages().add(validationMessage);
			}
			this.test1vos.set((int) test1vo.getId(), test1vo);

			callback.onSuccess((Result<ValidateAndSaveVOType>) result);
		} else {
			callback.onFailure(new RuntimeException("unsupported vo type '" + vo.getClass() + "'"));
		}
	}

}
