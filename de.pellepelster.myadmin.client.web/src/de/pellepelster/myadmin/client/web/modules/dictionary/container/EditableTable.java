package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IEditableTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IEditableTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class EditableTable<VOType extends IBaseVO> extends BaseTable<VOType, IEditableTableModel> implements IEditableTable<VOType>
{
	public final static String CONTROL_FIRST_EDIT_DATA_KEY = "CONTROL_FIRST_EDIT_DATA_KEY";

	public EditableTable(IEditableTableModel editableTableModel, BaseModelElement<IBaseModel> parent)
	{
		super(editableTableModel, parent);
	}

	@Override
	public void add(final AsyncCallback<List<ITableRow<VOType>>> asyncCallback)
	{
		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService()
				.getNewVO(getModel().getVOName(), new HashMap<String, String>(), new BaseErrorAsyncCallback<IBaseVO>()
				{

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(IBaseVO newVO)
					{
						for (IBaseControlModel baseControlModel : getModel().getControls())
						{
							newVO.getData().put(baseControlModel.getName(), CONTROL_FIRST_EDIT_DATA_KEY);
						}
						addRow((VOType) newVO);

						asyncCallback.onSuccess(EditableTable.this.getRows());
						// getSelectionModel().setSelected(newVO, true);
					}
				});
	}
}
