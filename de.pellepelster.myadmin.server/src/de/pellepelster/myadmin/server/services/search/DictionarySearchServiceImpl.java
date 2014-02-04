package de.pellepelster.myadmin.server.services.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.SearchResultItemVO;
import de.pellepelster.myadmin.client.web.services.search.IDictionarySearchServiceGWT;
import de.pellepelster.myadmin.db.index.IDictionarySearchElement;
import de.pellepelster.myadmin.db.index.ISearchService;
import de.pellepelster.myadmin.server.services.dictionary.DictionaryMetaDataService;

public class DictionarySearchServiceImpl implements IDictionarySearchServiceGWT
{
	private static final String HIGHLIGHT_START = "<span class=\"dictionarySearchTextHighlight\">";

	private static final String HIGHLIGHT_END = "</span>";

	@Autowired
	private DictionaryMetaDataService dictionaryMetaDataService;

	private Optional<ISearchService> searchService = Optional.absent();

	public String highlightQueryText(String text, String highlightText)
	{
		return text.replaceAll("(?i)(" + highlightText + ")", HIGHLIGHT_START + "$1" + HIGHLIGHT_END);
	}

	@Override
	public List<SearchResultItemVO> search(final String query)
	{
		if (this.searchService.isPresent())
		{
			List<SearchResultItemVO> result = new ArrayList<SearchResultItemVO>();

			for (IDictionarySearchElement searchElement : this.searchService.get().search(String.format("%s*", query)))
			{
				for (IDictionaryModel dictionaryModel : DictionarySearchServiceImpl.this.dictionaryMetaDataService.getModelsForVO(searchElement.getType()))
				{
					String title = String.format("%s %s", DictionaryModelUtil.getLabel(dictionaryModel), highlightQueryText(searchElement.getLabel(), query));

					SearchResultItemVO resultItem = new SearchResultItemVO();

					resultItem.setTitle(title);

					StringBuilder sb = new StringBuilder();

					resultItem.setText(sb.toString());

					result.add(resultItem);

				}
			}

			return result;
		}
		else
		{
			return Collections.emptyList();
		}

	}

	@Autowired(required = false)
	public void setSearchService(ISearchService searchService)
	{
		this.searchService = Optional.of(searchService);
	}

	public void setDictionaryMetaDataService(DictionaryMetaDataService dictionaryMetaDataService)
	{
		this.dictionaryMetaDataService = dictionaryMetaDataService;
	}

}
