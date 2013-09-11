package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.IVOSelectHandler;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;

public class VOSelectionPopup<VOType extends IBaseVO>
{

	final PopupPanel popupPanel;

	@SuppressWarnings("unchecked")
	private VOSelectionPopup(String voClassName, List<IBaseControlModel> baseControlModels, final IVOSelectHandler<VOType> voSelectHandler)
	{
		popupPanel = new PopupPanel();

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(GwtStyles.SPACING);

		final VOTable<VOType> voTable = new VOTable<VOType>(baseControlModels);

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

		voTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		voTable.setWidth("100%");
		voTable.addVOSelectHandler(new IVOSelectHandler<VOType>()
		{

			@Override
			public void onSingleSelect(VOType vo)
			{
				popupPanel.hide();
				voSelectHandler.onSingleSelect(vo);
			}
		});

		popupPanel.setWidth(BaseCellTable.DEFAULT_TABLE_WIDTH);

		vPanel.add(voTable);

		HorizontalPanel buttonPanel = new HorizontalPanel();

		ImageButton okButton = new ImageButton(MyAdmin.RESOURCES.ok());
		buttonPanel.add(okButton);

		ImageButton cancelButton = new ImageButton(MyAdmin.RESOURCES.cancel());
		cancelButton.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				popupPanel.hide();
			}
		});
		buttonPanel.add(cancelButton);

		vPanel.add(buttonPanel);

		popupPanel.add(vPanel);

		popupPanel.center();
		popupPanel.setGlassEnabled(true);
		popupPanel.setTitle("Title");
		popupPanel.setModal(true);
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(String voClassName, List<IBaseControlModel> baseControlModels,
			IVOSelectHandler<VOType> voSelectHandler)
	{
		return new VOSelectionPopup<VOType>(voClassName, baseControlModels, voSelectHandler);
	}

	public void show()
	{
		popupPanel.show();
	}

}
