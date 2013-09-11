package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDataGrid;
import de.pellepelster.myadmin.client.web.MyAdmin;

public class VOTable<VOType extends IBaseVO> extends BaseDataGrid<VOType>
{
	private final ListDataProvider<VOType> dataProvider = new ListDataProvider<VOType>();

	public VOTable(List<IBaseControlModel> baseControlModels)
	{
		super(baseControlModels);

		createModelColumns();
		dataProvider.addDataDisplay(this);
	}

	@Override
	protected Column<VOType, ?> getColumn(IBaseControlModel baseControlModel)
	{
		return (Column<VOType, ?>) MyAdmin.getInstance().getControlHandler().createColumn(baseControlModel, false, dataProvider, this);
	}

	public void setContent(List<VOType> content)
	{
		dataProvider.setList(content);
	}

}
