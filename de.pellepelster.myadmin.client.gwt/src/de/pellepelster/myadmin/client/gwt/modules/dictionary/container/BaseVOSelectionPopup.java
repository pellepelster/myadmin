package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.IVOSelectHandler;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;

public abstract class BaseVOSelectionPopup<VOType extends IBaseVO>
{
	private final String message;

	private DialogBox dialogBox;

	private IVOSelectHandler<VOType> voSelectHandler;

	protected BaseVOSelectionPopup(String message, final IVOSelectHandler<VOType> voSelectHandler)
	{
		this.message = message;
		this.voSelectHandler = voSelectHandler;
	}

	protected abstract Widget createDialogBoxContent();

	private void createOkButton(HorizontalPanel buttonPanel)
	{
		ImageButton okButton = new ImageButton(MyAdmin.RESOURCES.ok());
		buttonPanel.add(okButton);
		okButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				closeDialogWithSelection(getCurrentSelection());
			}
		});
	}

	protected void closeDialogWithSelection(VOType selection)
	{
		voSelectHandler.onSingleSelect(selection);
		dialogBox.hide();
	}

	private void createCancelButton(HorizontalPanel buttonPanel)
	{
		ImageButton cancelButton = new ImageButton(MyAdmin.RESOURCES.cancel());
		cancelButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				dialogBox.hide();
			}
		});
		buttonPanel.add(cancelButton);
	}

	public void show()
	{
		if (dialogBox == null)
		{
			initDialogBox();
		}

		dialogBox.show();
	}

	protected void initDialogBox()
	{
		dialogBox = new DialogBox(false, true);
		dialogBox.setGlassEnabled(true);
		dialogBox.setWidth(BaseCellTable.DEFAULT_TABLE_WIDTH);
		dialogBox.center();
		dialogBox.setTitle(MyAdmin.MESSAGES.voSelectionHeader(message));
		dialogBox.setText(MyAdmin.MESSAGES.voSelectionHeader(message));

		VerticalPanel verticalPanel = new VerticalPanel();
		dialogBox.add(verticalPanel);
		verticalPanel.setSpacing(GwtStyles.SPACING);

		Widget dialogBoxContent = createDialogBoxContent();
		verticalPanel.add(dialogBoxContent);

		// buttons
		HorizontalPanel buttonPanel = new HorizontalPanel();
		verticalPanel.add(buttonPanel);
		buttonPanel.setSpacing(GwtStyles.SPACING);

		createOkButton(buttonPanel);
		createCancelButton(buttonPanel);
	}

	public void setVoSelectHandler(IVOSelectHandler<VOType> voSelectHandler)
	{
		this.voSelectHandler = voSelectHandler;
	}

	protected abstract VOType getCurrentSelection();
}
