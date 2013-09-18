package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.IVOSelectHandler;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;

public class VOSelectionPopup<VOType extends IBaseVO> extends BaseVOSelectionPopup<VOType>
{
	private final String voClassName;

	private VOTable<VOType> voTable;

	private List<IBaseControlModel> baseControlModels;

	private VOSelectionPopup(String voClassName, String message, List<IBaseControlModel> baseControlModels, final IVOSelectHandler<VOType> voSelectHandler)
	{
		super(message, voSelectHandler);

		this.voClassName = voClassName;
		this.baseControlModels = baseControlModels;
	}

	@SuppressWarnings("unchecked")
	private void refreshTable()
	{
		MyAdmin.getInstance()
				.getRemoteServiceLocator()
				.getBaseEntityService()
				.filter((GenericFilterVO<VOType>) ClientGenericFilterBuilder.createGenericFilter(voClassName).setMaxResults(BaseCellTable.DEFAULT_MAX_RESULTS)
						.getGenericFilter(), new AsyncCallback<List<VOType>>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						throw new RuntimeException(caught);
					}

					@Override
					public void onSuccess(List<VOType> result)
					{
						voTable.setContent(result);
					}
				});
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(String voClassName, String message, List<IBaseControlModel> baseControlModels,
			IVOSelectHandler<VOType> voSelectHandler)
	{
		return new VOSelectionPopup<VOType>(voClassName, message, baseControlModels, voSelectHandler);
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(IAssignmentTableModel assignmentTableModel, IVOSelectHandler<VOType> voSelectHandler)
	{
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getCachedDictionaryModel(assignmentTableModel.getDictionaryName());

		return new VOSelectionPopup<VOType>(dictionaryModel.getVOName(), dictionaryModel.getTitle(), assignmentTableModel.getControls(), voSelectHandler);
	}

	@Override
	protected Widget createDialogBoxContent()
	{
		// vo table
		voTable = new VOTable<VOType>(baseControlModels);

		voTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		voTable.setWidth("100%");
		voTable.addVOSelectHandler(new IVOSelectHandler<VOType>()
		{
			@Override
			public void onSingleSelect(VOType vo)
			{
				closeDialogWithSelection(vo);
			}
		});
		refreshTable();

		return voTable;
	}

	@Override
	protected VOType getCurrentSelection()
	{
		return voTable.getCurrentSelection();
	}

}
