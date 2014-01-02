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
package de.pellepelster.myadmin.demo.server.test.dictionary;

import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.db.daos.BaseVODAO;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;
import de.pellepelster.myadmin.demo.server.test.BaseDemoTest;

public abstract class BaseDemoDictionaryTest extends BaseDemoTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	protected BaseVODAO baseVODAO;

	public IBaseEntityService getBaseEntityService()
	{
		return this.baseEntityService;
	}

	public void createTest1VO(String text)
	{
		Test1VO test1VO1 = new Test1VO();
		test1VO1.setTextDatatype1(text);
		this.baseVODAO.create(test1VO1);

	}

	public void createTest1VOs()
	{
		String[] texts = new String[] { "achromatize", "achromatized", "achromatizes", "achromatizing", "adonizes", "aerography", "aerologies", "aerology",
				"aerometer", "aggrandizer", "albumenize", "alchemize", "alchemized", "alchemizes", "alchemizing", "alcoholize", "alcoholized", "alcoholizes",
				"alcoholizing", "alkalinize", "alkalinized", "alkalinizes", "alkalinizing", "alkalization", "allegorize", "allegorized", "allegorizes",
				"allegorizing", "aluminize", "aluminized", "aluminizes", "aluminizing", "amebocyte", "amenorrhea", "amenorrheas", "amenorrheic", "amorist",
				"amorists" };

		for (int i = 0; i < texts.length; i++)
		{
			createTest1VO(texts[i]);
		}
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

}
