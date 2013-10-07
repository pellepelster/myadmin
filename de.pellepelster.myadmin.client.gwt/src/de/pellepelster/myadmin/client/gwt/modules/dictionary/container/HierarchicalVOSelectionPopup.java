package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfiguration;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.hierarchical.HierarchicalTree;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class HierarchicalVOSelectionPopup extends BaseVOSelectionPopup<IHierarchicalVO>
{
	private HierarchicalTree hierarchicalTree;

	private HierarchicalConfiguration hierarchicalConfiguration;

	private DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO;

	private HierarchicalVOSelectionPopup(HierarchicalConfiguration hierarchicalConfiguration, IHierarchicalControlModel hierarchicalControlModel)
	{
		super("<none>", null);

		this.hierarchicalConfiguration = hierarchicalConfiguration;
	}

	public static void create(final IHierarchicalControlModel hierarchicalControlModel, final AsyncCallback<HierarchicalVOSelectionPopup> asyncCallback)
	{
		MyAdmin.getInstance().getRemoteServiceLocator().getHierachicalService()
				.getConfigurationById(hierarchicalControlModel.getHierarchicalId(), new AsyncCallback<HierarchicalConfiguration>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						asyncCallback.onFailure(caught);
					}

					@Override
					public void onSuccess(HierarchicalConfiguration result)
					{
						asyncCallback.onSuccess(new HierarchicalVOSelectionPopup(result, hierarchicalControlModel));
					}
				});

	}

	@Override
	protected Widget createDialogBoxContent()
	{
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		scrollPanel.setWidth("100%");

		hierarchicalTree = new HierarchicalTree(hierarchicalConfiguration, false, new SimpleCallback<DictionaryHierarchicalNodeVO>()
		{

			@Override
			public void onCallback(DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO)
			{
				HierarchicalVOSelectionPopup.this.dictionaryHierarchicalNodeVO = dictionaryHierarchicalNodeVO;

			}
		});

		hierarchicalTree.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		hierarchicalTree.setWidth("100%");
		scrollPanel.add(hierarchicalTree);

		return scrollPanel;
	}

	// public void show()
	// {
	// // if (dialogBox == null)
	// // {
	// //
	// MyAdmin.getInstance().getRemoteServiceLocator().getHierachicalService()
	// // .getConfigurationById(hierarchicalControlModel.getHierarchicalId(),
	// // new AsyncCallback<HierarchicalConfiguration>()
	// // {
	// //
	// // @Override
	// // public void onFailure(Throwable caught)
	// // {
	// // throw new RuntimeException(caught);
	// // }
	// //
	// // @Override
	// // public void onSuccess(HierarchicalConfiguration result)
	// // {
	// // initDialogBox();
	// // }
	// // });
	// // }
	// //
	//
	// }

	@Override
	protected void getCurrentSelection(final AsyncCallback<IHierarchicalVO> asyncCallback)
	{
		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService()
				.read(dictionaryHierarchicalNodeVO.getVoId(), dictionaryHierarchicalNodeVO.getVoClassName(), new AsyncCallback<IBaseVO>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						asyncCallback.onFailure(caught);
					}

					@Override
					public void onSuccess(IBaseVO result)
					{
						if (result instanceof IHierarchicalVO)
						{
							asyncCallback.onSuccess((IHierarchicalVO) result);
						}
						else
						{
							throw new RuntimeException("unsupported vo type '" + result.getClass().getName() + "'");
						}
					}
				});
	}

}
