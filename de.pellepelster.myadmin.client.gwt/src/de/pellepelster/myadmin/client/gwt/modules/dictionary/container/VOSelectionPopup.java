package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.util.SimpleCallback;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.AssignmentTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;

public class VOSelectionPopup<VOType extends IBaseVO> extends BaseVOSelectionPopup<VOType>
{
	private final String voClassName;

	private VOTable<VOType> voTable;

	private static List<BaseDictionaryControl<?, ?>> baseControls;

	private VOSelectionPopup(String voClassName, String message, List<BaseDictionaryControl<?, ?>> baseControls, final SimpleCallback<VOType> voSelectHandler)
	{
		super(message, voSelectHandler);

		this.voClassName = voClassName;
		this.baseControls = baseControls;
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
						throw new RuntimeException("TODO");
						// voTable.setContent(DictionaryElementUtil.vos2TableRows(result));
					}
				});
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(String voClassName, String message, List<IBaseControlModel> baseControlModels,
			SimpleCallback<VOType> voSelectHandler)
	{
		return new VOSelectionPopup<VOType>(voClassName, message, baseControls, voSelectHandler);
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(AssignmentTable<?> assignmentTable, SimpleCallback<VOType> voSelectHandler)
	{
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(assignmentTable.getModel().getDictionaryName());

		return new VOSelectionPopup<VOType>(dictionaryModel.getVoName(), dictionaryModel.getLabel(), assignmentTable.getControls(), voSelectHandler);
	}

	@Override
	protected Widget createDialogBoxContent()
	{
		// vo table
		voTable = new VOTable<VOType>(baseControls);

		voTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		voTable.setWidth("100%");
		voTable.addVOSelectHandler(new SimpleCallback<IBaseTable.ITableRow<VOType>>()
		{
			@Override
			public void onCallback(IBaseTable.ITableRow<VOType> tableRow)
			{
				closeDialogWithSelection(tableRow.getVO());
			}
		});
		refreshTable();

		return voTable;
	}

	@Override
	protected void getCurrentSelection(AsyncCallback<VOType> asyncCallback)
	{
		asyncCallback.onSuccess(voTable.getCurrentSelection().getVO());
	}

}
