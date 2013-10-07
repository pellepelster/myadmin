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
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class VOSelectionPopup<VOType extends IBaseVO> extends BaseVOSelectionPopup<VOType> {
	private final String voClassName;

	private VOTable<VOType> voTable;

	private List<IBaseControlModel> baseControlModels;

	private VOSelectionPopup(String voClassName, String message, List<IBaseControlModel> baseControlModels, final SimpleCallback<VOType> voSelectHandler) {
		super(message, voSelectHandler);

		this.voClassName = voClassName;
		this.baseControlModels = baseControlModels;
	}

	@SuppressWarnings("unchecked")
	private void refreshTable() {
		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService()
				.filter((GenericFilterVO<VOType>) ClientGenericFilterBuilder.createGenericFilter(voClassName).setMaxResults(BaseCellTable.DEFAULT_MAX_RESULTS).getGenericFilter(), new AsyncCallback<List<VOType>>() {

					@Override
					public void onFailure(Throwable caught) {
						throw new RuntimeException(caught);
					}

					@Override
					public void onSuccess(List<VOType> result) {
						voTable.setContent(result);
					}
				});
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(String voClassName, String message, List<IBaseControlModel> baseControlModels, SimpleCallback<VOType> voSelectHandler) {
		return new VOSelectionPopup<VOType>(voClassName, message, baseControlModels, voSelectHandler);
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(IAssignmentTableModel assignmentTableModel, SimpleCallback<VOType> voSelectHandler) {
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getCachedDictionaryModel(assignmentTableModel.getDictionaryName());

		return new VOSelectionPopup<VOType>(dictionaryModel.getVOName(), dictionaryModel.getTitle(), assignmentTableModel.getControls(), voSelectHandler);
	}

	@Override
	protected Widget createDialogBoxContent() {
		// vo table
		voTable = new VOTable<VOType>(baseControlModels);

		voTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		voTable.setWidth("100%");
		voTable.addVOSelectHandler(new SimpleCallback<VOType>() {

			@Override
			public void onCallback(VOType t) {
				closeDialogWithSelection(vo);
			}
		});
		refreshTable();

		return voTable;
	}

	@Override
	protected VOType getCurrentSelection() {
		return voTable.getCurrentSelection();
	}

}
