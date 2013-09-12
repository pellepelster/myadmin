package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.IVOSelectHandler;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;

public class VOSelectionPopup<VOType extends IBaseVO>
{

	private final PopupPanel popupPanel;

	private final String voClassName;

	private final VOTable<VOType> voTable;

	private VOSelectionPopup(String voClassName, String message, List<IBaseControlModel> baseControlModels, final IVOSelectHandler<VOType> voSelectHandler)
	{
		this.voClassName = voClassName;

		popupPanel = new PopupPanel();
		popupPanel.setWidth(BaseCellTable.DEFAULT_TABLE_WIDTH);
		popupPanel.center();
		popupPanel.setGlassEnabled(true);
		popupPanel.setTitle("Title");
		popupPanel.setModal(true);

		VerticalPanel verticalPanel = new VerticalPanel();
		popupPanel.add(verticalPanel);
		verticalPanel.setSpacing(GwtStyles.SPACING);

		HTMLPanel htmlPanel = new HTMLPanel(MyAdmin.MESSAGES.voSelectionHeader(message));
		htmlPanel.setWidth("100%");
		verticalPanel.add(htmlPanel);

		// vo table
		voTable = new VOTable<VOType>(baseControlModels);
		verticalPanel.add(voTable);

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
		refreshTable();

		// buttons
		HorizontalPanel buttonPanel = new HorizontalPanel();
		verticalPanel.add(buttonPanel);

		createOkButton(buttonPanel);
		createCancelButton(buttonPanel);
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

	private void createOkButton(HorizontalPanel buttonPanel)
	{
		ImageButton okButton = new ImageButton(MyAdmin.RESOURCES.ok());
		buttonPanel.add(okButton);
	}

	private void createCancelButton(HorizontalPanel buttonPanel)
	{
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

	public void show()
	{
		popupPanel.show();
	}

}
