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
package de.pellepelster.myadmin.server.services;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.db.vos.IValidationMessage;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.core.utils.HierarchicalUtils;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.client.web.services.IBaseEntityServiceGWT;
import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.db.util.BeanUtil;
import de.pellepelster.myadmin.server.validators.Validator;

/**
 * Implementation for {@link IBaseEntityService}
 * 
 * @author pelle
 * 
 */
@Transactional
public class BaseEntityServiceImpl implements IBaseEntityServiceGWT
{
	private final static Logger LOG = Logger.getLogger(BaseEntityServiceImpl.class);

	@Resource
	private IBaseVODAO baseVODAO;

	/*
	 * @Override public LimitFilterResult filter(GenericFilterVO
	 * genericFilterVO, int start, int limit) { try { List<? extends IBaseVO>
	 * result = baseVODAO.filter(genericFilterVO, start, limit); long count =
	 * baseVODAO.getCount(genericFilterVO);
	 * 
	 * return new LimitFilterResult(result, (int) count);
	 * 
	 * } catch (Exception e) { logger.error(e); throw new RuntimeException(e); }
	 * }
	 */

	@Autowired
	private Validator validator;

	/** {@inheritDoc} */
	@Override
	public <VOType extends IBaseVO> VOType create(VOType vo)
	{
		LOG.debug(String.format("creating '%s'", vo.getClass().getName()));
		return baseVODAO.create(vo);
	}

	/*
	 * public String filterExportCSV(GenericFilterVO genericFilterVO, String
	 * dictionaryName) {
	 * 
	 * DictionaryVO dictionaryVO =
	 * dictionaryService.getDictionary(dictionaryName);
	 * 
	 * try { ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	 * 
	 * WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
	 * WritableSheet sheet = workbook.createSheet("First Sheet", 0);
	 * 
	 * List<? extends IBaseVO> result = baseVODAO.filter(genericFilterVO);
	 * 
	 * int column = 0; Map<String, DictionaryVO> dictionaryMap = new
	 * HashMap<String, DictionaryVO>(); for (DictionaryFieldVO dictionaryFieldVO
	 * : dictionaryVO.getSearch().getResult().getFields()) {
	 * 
	 * sheet.addCell(new Label(column, 0, dictionaryFieldVO.getColumnLabel()));
	 * 
	 * if (dictionaryFieldVO.getDatatype().getBaseType() ==
	 * DICTIONARY_BASETYPEVO.REFERENCE) {
	 * 
	 * String refDictionaryName =
	 * dictionaryFieldVO.getDatatype().getReference(); DictionaryVO
	 * refDictionaryVO = dictionaryService.getDictionary(refDictionaryName);
	 * 
	 * dictionaryMap.put(refDictionaryName, refDictionaryVO); }
	 * 
	 * }
	 * 
	 * column = 0; int row = 1; for (IBaseVO vo : result) { column = 0;
	 * 
	 * for (DictionaryFieldVO dictionaryFieldVO :
	 * dictionaryVO.getSearch().getResult().getFields()) {
	 * 
	 * Object cellObjectContent = vo.get(dictionaryFieldVO.getAttributeName());
	 * String cellContent = "";
	 * 
	 * if (cellObjectContent != null) {
	 * 
	 * if (dictionaryFieldVO.getDatatype().getBaseType() ==
	 * DICTIONARY_BASETYPEVO.REFERENCE) {
	 * 
	 * String refDictionaryName =
	 * dictionaryFieldVO.getDatatype().getReference();
	 * 
	 * for (DictionaryFieldVO refFieldVO :
	 * dictionaryMap.get(refDictionaryName).getFieldLabels()) {
	 * 
	 * Object refob = ((IBaseVO)
	 * vo.get(dictionaryFieldVO.getAttributeName())).get(refFieldVO
	 * .getAttributeName());
	 * 
	 * if (refob != null) { cellContent = refob.toString(); } } } else {
	 * cellContent = cellObjectContent.toString(); }
	 * 
	 * sheet.addCell(new Label(column, row, cellContent)); }
	 * 
	 * column++; } row++; }
	 * 
	 * workbook.write(); workbook.close();
	 * 
	 * return fileStorage.storeFile(outputStream.toByteArray(),
	 * String.format("%s.%s", dictionaryVO.getName(), "xls"));
	 * 
	 * } catch (Exception e) { logger.error(e); throw new RuntimeException(e); }
	 * 
	 * }
	 */

	@Override
	public <DeleteVOType extends IBaseVO> void delete(DeleteVOType vo)
	{
		baseVODAO.delete(vo);
	}

	@Override
	public void deleteAll(String voClassName)
	{
		baseVODAO.deleteAll(BeanUtil.getVOClass(voClassName));

	}

	/** {@inheritDoc} */
	@Override
	public <VOType extends IBaseVO> List<VOType> filter(GenericFilterVO<VOType> genericFilterVO)
	{
		return baseVODAO.filter(genericFilterVO);
	}

	/** {@inheritDoc} */
	@Override
	public IBaseVO getNewVO(String voClassName, HashMap<String, String> parameters)
	{

		IHierarchicalVO parent = HierarchicalUtils.getParentVO(parameters, this);

		try
		{
			LOG.error(String.format("getting new instance for class '%s'", voClassName));

			Class<?> voClass = Class.forName(voClassName);

			IBaseVO vo = (IBaseVO) voClass.getConstructor().newInstance();

			if (parent != null && vo instanceof IHierarchicalVO)
			{
				IHierarchicalVO hierarchicalVO = (IHierarchicalVO) vo;
				hierarchicalVO.setParent(parent);
			}

			return vo;

		}
		catch (Exception e)
		{
			String message = String.format("error instantiating class '%s'", voClassName);
			LOG.error(message, e);

			throw new RuntimeException(message, e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public IBaseVO read(Long id, String voClassName)
	{
		return baseVODAO.read(id, BeanUtil.getVOClass(voClassName));
	}

	/** {@inheritDoc} */
	@Override
	public <VOType extends IBaseVO> VOType save(VOType vo)
	{
		return baseVODAO.save(vo);
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

	public void setValidator(Validator validator)
	{
		this.validator = validator;
	}

	/** {@inheritDoc} */
	@Override
	public <ValidateVOType extends IBaseVO> List<IValidationMessage> validate(ValidateVOType vo)
	{
		return validator.validate(vo);
	}

	/** {@inheritDoc} */
	@Override
	public <ValidateAndCreateVOType extends IBaseVO> Result<ValidateAndCreateVOType> validateAndCreate(ValidateAndCreateVOType vo)
	{

		ValidateAndCreateVOType newVO = null;

		Result<ValidateAndCreateVOType> result = new Result<ValidateAndCreateVOType>();

		List<IValidationMessage> validationMessages = validate(vo);
		result.getValidationMessages().addAll(validationMessages);

		if (validationMessages.isEmpty())
		{
			newVO = create(vo);
		}
		result.setVo(newVO);

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public <ValidateAndSaveVOType extends IBaseVO> Result<ValidateAndSaveVOType> validateAndSave(ValidateAndSaveVOType vo)
	{

		Result<ValidateAndSaveVOType> result = new Result<ValidateAndSaveVOType>();

		List<IValidationMessage> validationMessages = validate(vo);
		result.getValidationMessages().addAll(validationMessages);

		if (validationMessages.isEmpty())
		{
			vo = save(vo);
		}
		result.setVo(vo);

		return result;
	}

}
