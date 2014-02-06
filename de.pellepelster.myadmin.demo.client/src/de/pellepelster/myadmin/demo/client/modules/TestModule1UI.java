package de.pellepelster.myadmin.demo.client.modules;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseGwtModuleUI;

public class TestModule1UI extends BaseGwtModuleUI<TestModule1>
{

	public static final String MODULE_ID = TestModule1UI.class.getName();

	private VerticalPanel panel = new VerticalPanel();

	public TestModule1UI(TestModule1 module)
	{
		super(module, MODULE_ID);

		HTML html = new HTML("<h1>" + getModule().getVo().getId() + "</h1>");
		this.panel.add(html);

		// Image img = new
		// Image(GwtFileControl.getFileUrl(getModule().getVo().g));
		// this.panel.add(img);

	}

	@Override
	public Panel getContainer()
	{
		return this.panel;
	}

	@Override
	public String getTitle()
	{
		return getModule().getTitle();
	}

}
