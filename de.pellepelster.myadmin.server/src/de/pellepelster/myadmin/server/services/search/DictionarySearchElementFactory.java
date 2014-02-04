package de.pellepelster.myadmin.server.services.search;

import org.elasticsearch.search.SearchHit;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.query.DictionaryModelQuery;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.db.index.IDictionarySearchElement;
import de.pellepelster.myadmin.db.index.DictionarySearchElement;

public class DictionarySearchElementFactory implements ISearchElementFactory
{

	public static IDictionarySearchElement createElement(IDictionaryModel dictionaryModel, IBaseVO vo)
	{
		DictionarySearchElement indexElement = new DictionarySearchElement(vo, DictionaryUtil.getLabel(dictionaryModel.getLabelControls(), vo));

		for (IBaseControlModel baseControlModel : DictionaryModelQuery.create(dictionaryModel).getControls().getList())
		{
			Object value = vo.get(baseControlModel.getAttributePath());

			if (value != null)
			{
				indexElement.getFields().put(baseControlModel.getAttributePath(), value);
			}
		}

		return indexElement;
	}

	public static IDictionarySearchElement createElement(SearchHit searchHit)
	{
		DictionarySearchElement indexElement = new DictionarySearchElement(searchHit.getType(), searchHit.getId());
		indexElement.getFields().putAll(searchHit.getSource());
		return indexElement;
	}
}
