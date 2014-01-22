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
package de.pellepelster.myadmin.server.services.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.core.utils.HierarchicalUtils;
import de.pellepelster.myadmin.client.web.entities.dictionary.FileVO;
import de.pellepelster.myadmin.client.web.services.vo.IBaseEntityService;
import de.pellepelster.myadmin.client.web.services.vo.IBaseEntityServiceGWT;
import de.pellepelster.myadmin.db.daos.BaseVODAO;
import de.pellepelster.myadmin.db.util.AttributeDescriptorIterator;
import de.pellepelster.myadmin.db.util.BeanUtils;
import de.pellepelster.myadmin.server.util.TempFileStore;
import de.pellepelster.myadmin.server.validators.IValidator;

@Transactional(propagation = Propagation.REQUIRED)
public class BaseEntityServiceImpl implements IBaseEntityServiceGWT
{
	private final static Logger LOG = Logger.getLogger(IBaseEntityService.class);

	@Autowired
	private TempFileStore tempFileStore;

	@Autowired
	private BaseVODAO baseVODAO;

	@Autowired(required = false)
	private List<IValidator> validators = new ArrayList<IValidator>();

	private void resolveAttributesFromData(IBaseVO baseVO)
	{
		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtils.getAttributeDescriptors(baseVO.getClass()))
		{
			if (baseVO.getData() != null && baseVO.getData().containsKey(attributeDescriptor.getAttributeName()))
			{
				Object attributeData = baseVO.getData().get(attributeDescriptor.getAttributeName());

				if (FileVO.class.equals(attributeDescriptor.getAttributeType()))
				{
					if (attributeData instanceof String)
					{
						String tempFileUUID = (String) attributeData;
						FileVO tempFile = this.tempFileStore.getTempFile(tempFileUUID);
						baseVO.set(attributeDescriptor.getAttributeName(), tempFile);
					}
				}
			}
		}
	}

	public <VOType extends IBaseVO> void cleanVO(List<VOType> vos)
	{
		for (VOType vo : vos)
		{
			cleanVO(vo);
		}
	}

	public <VOType extends IBaseVO> VOType cleanVO(VOType vo)
	{
		// TODO get/set for attributes descriptors
		for (IAttributeDescriptor<FileVO> fileVOAttributeDescriptor : new AttributeDescriptorIterator<FileVO>(vo, FileVO.class))
		{
			FileVO fileVO = (FileVO) vo.get(fileVOAttributeDescriptor.getAttributeName());

			if (fileVO != null)
			{
				fileVO.setFileContent(null);
				fileVO.getChangeTracker().clearChanges();
			}
		}

		return vo;
	}

	/** {@inheritDoc} */
	@Override
	public <VOType extends IBaseVO> VOType create(VOType vo)
	{
		LOG.debug(String.format("creating '%s'", vo.getClass().getName()));

		resolveAttributesFromData(vo);

		return cleanVO(this.baseVODAO.create(vo));
	}

	@Override
	public <DeleteVOType extends IBaseVO> void delete(DeleteVOType vo)
	{
		this.baseVODAO.delete(vo);
	}

	@Override
	public void deleteAll(String voClassName)
	{
		this.baseVODAO.deleteAll(BeanUtils.getVOClass(voClassName));

	}

	/** {@inheritDoc} */
	@Override
	public <VOType extends IBaseVO> List<VOType> filter(GenericFilterVO<VOType> genericFilterVO)
	{
		List<VOType> result = this.baseVODAO.filter(genericFilterVO);
		cleanVO(result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public IBaseVO getNewVO(String voClassName, HashMap<String, String> parameters)
	{

		IHierarchicalVO parent = HierarchicalUtils.getParentVO(parameters, this);

		try
		{
			LOG.info(String.format("creating new instance for vo class '%s'", voClassName));

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
		return cleanVO(this.baseVODAO.read(id, BeanUtils.getVOClass(voClassName)));
	}

	/** {@inheritDoc} */
	@Override
	public <VOType extends IBaseVO> VOType save(VOType vo)
	{
		LOG.debug(String.format("saving '%s'", vo.getClass().getName()));

		resolveAttributesFromData(vo);

		return cleanVO(this.baseVODAO.save(vo));
	}

	public void setBaseVODAO(BaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

	/** {@inheritDoc} */
	@Override
	public <ValidateVOType extends IBaseVO> List<IValidationMessage> validate(ValidateVOType vo)
	{
		List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

		for (IValidator validator : this.validators)
		{
			if (validator.canValidate(vo))
			{
				validationMessages.addAll(validator.validate(vo));
			}
		}

		return validationMessages;
	}

	public void setValidators(List<IValidator> validators)
	{
		this.validators = validators;
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
		result.setVO(newVO);

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
		result.setVO(vo);

		return result;
	}

	public void setTempFileStore(TempFileStore tempFileStore)
	{
		this.tempFileStore = tempFileStore;
	}
}
