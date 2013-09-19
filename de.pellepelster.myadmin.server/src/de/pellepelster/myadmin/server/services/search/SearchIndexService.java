package de.pellepelster.myadmin.server.services.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryLabelIndexVO;
import de.pellepelster.myadmin.client.web.services.IDictionaryService;
import de.pellepelster.myadmin.db.ISearchIndexService;

public class SearchIndexService implements InitializingBean, ISearchIndexService
{
	private IDictionaryService dictionaryService;

	private Map<String, List<String>> dictionaryLabelIndexAttributes = new HashMap<String, List<String>>();

	private final static Logger LOG = Logger.getLogger(SearchIndexService.class);

	@Override
	public void afterPropertiesSet() throws Exception
	{
		List<String> indexAttributes = new ArrayList<String>();

		for (IDictionaryModel dictionaryModel : this.dictionaryService.getAllDictionaries())
		{
			for (IBaseControlModel baseControlModel : dictionaryModel.getLabelControls())
			{
				indexAttributes.add(baseControlModel.getAttributePath());
			}

			if (!indexAttributes.isEmpty())
			{
				LOG.info(String.format("the following attributes for vo '%s' will be indexed: %s", dictionaryModel.getVOName(), indexAttributes.toString()));
				this.dictionaryLabelIndexAttributes.put(dictionaryModel.getVOName(), indexAttributes);
			}
		}

	}

	public void setDictionaryService(IDictionaryService dictionaryService)
	{
		this.dictionaryService = dictionaryService;
	}

	private String getLabelText(IBaseVO baseVO)
	{
		if (hasIndexAttributes(baseVO))
		{
			// String labelText
		}

		return null;
	}

	private boolean hasIndexAttributes(IBaseVO baseVO)
	{
		return this.dictionaryLabelIndexAttributes.containsKey(baseVO.getClass().getName());
	}

	@Override
	public void createVO(IBaseVO baseVO)
	{
		DictionaryLabelIndexVO l = new DictionaryLabelIndexVO();

	}

	@Override
	public void deleteVO(IBaseVO baseVO)
	{
	}

	@Override
	public void update(IBaseVO baseVO)
	{
	}

	@Override
	public void deleteAllVO(Class voClass)
	{
	}

}
