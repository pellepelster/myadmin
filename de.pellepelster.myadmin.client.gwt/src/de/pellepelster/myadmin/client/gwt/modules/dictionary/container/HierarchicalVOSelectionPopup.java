package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.IVOSelectHandler;

public class HierarchicalVOSelectionPopup<VOType extends IBaseVO> extends BaseVOSelectionPopup<VOType>
{
	private final String voClassName;

	private VOTable<VOType> voTable;

	private IHierarchicalControlModel hierarchicalControlModel;

	private HierarchicalVOSelectionPopup(String voClassName, String message, IHierarchicalControlModel hierarchicalControlModel,
			final IVOSelectHandler<VOType> voSelectHandler)
	{
		super(message, voSelectHandler);

		this.voClassName = voClassName;
		this.hierarchicalControlModel = hierarchicalControlModel;
	}

	public static <VOType extends IBaseVO> HierarchicalVOSelectionPopup<VOType> create(IHierarchicalControlModel hierarchicalControlModel,
			IVOSelectHandler<VOType> voSelectHandler)
	{
		return null; // new
						// HierarchicalVOSelectionPopup<VOType>(dictionaryModel.getTitle(),
						// hierarchicalControlModel, voSelectHandler);
	}

	@Override
	protected Widget createDialogBoxContent()
	{
		// vo table
		// voTable = new VOTable<VOType>(baseControlModels);

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

		return voTable;
	}

	public void show()
	{
		// if (dialogBox == null)
		// {
		// MyAdmin.getInstance().getRemoteServiceLocator().getHierachicalService()
		// .getConfigurationById(hierarchicalControlModel.getHierarchicalId(),
		// new AsyncCallback<HierarchicalConfiguration>()
		// {
		//
		// @Override
		// public void onFailure(Throwable caught)
		// {
		// throw new RuntimeException(caught);
		// }
		//
		// @Override
		// public void onSuccess(HierarchicalConfiguration result)
		// {
		// initDialogBox();
		// }
		// });
		// }
		//
		// dialogBox.show();
	}

	@Override
	protected VOType getCurrentSelection()
	{
		return voTable.getCurrentSelection();
	}

}
