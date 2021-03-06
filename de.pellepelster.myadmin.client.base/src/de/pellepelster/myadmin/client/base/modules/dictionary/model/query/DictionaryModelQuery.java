package de.pellepelster.myadmin.client.base.modules.dictionary.model.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.search.IFilterModel;

public class DictionaryModelQuery
{

	private IDictionaryModel dictionaryModel;

	private DictionaryModelQuery(IDictionaryModel dictionaryModel)
	{
		this.dictionaryModel = dictionaryModel;
	}

	public static DictionaryModelQuery create(IDictionaryModel dictionaryModel)
	{
		return new DictionaryModelQuery(dictionaryModel);
	}

	private void recurseContainerModels(List<IBaseControlModel> controlModels, IBaseContainerModel baseContainerModel)
	{
		controlModels.addAll(baseContainerModel.getControls());

		if (!baseContainerModel.getChildren().isEmpty())
		{
			recurseContainerModels(controlModels, baseContainerModel.getChildren());
		}
	}

	private void recurseContainerModels(List<IBaseControlModel> controlModels, List<IBaseContainerModel> baseContainerModels)
	{
		for (IBaseContainerModel baseContainerModel : baseContainerModels)
		{
			recurseContainerModels(controlModels, baseContainerModel);
		}
	}

	private Collection<IBaseControlModel> getAllControlModels()
	{
		List<IBaseControlModel> allControlModels = new ArrayList<IBaseControlModel>();

		allControlModels.addAll(this.dictionaryModel.getLabelControls());

		for (IFilterModel filterModel : this.dictionaryModel.getSearchModel().getFilterModels())
		{
			recurseContainerModels(allControlModels, filterModel.getCompositeModel());
		}

		allControlModels.addAll(this.dictionaryModel.getSearchModel().getResultModel().getControls());

		recurseContainerModels(allControlModels, this.dictionaryModel.getEditorModel().getCompositeModel());

		return allControlModels;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ControlModelQuery<IBaseControlModel> getControls()
	{
		return new ControlModelQuery(getAllControlModels());
	}

}
