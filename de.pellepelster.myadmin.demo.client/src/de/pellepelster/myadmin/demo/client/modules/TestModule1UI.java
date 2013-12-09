package de.pellepelster.myadmin.demo.client.modules;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseModuleUI;

public class TestModule1UI extends BaseModuleUI<TestModule1>
{
	private VerticalPanel panel = new VerticalPanel();

	public TestModule1UI(TestModule1 module)
	{
		super(module);

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
		return getModule().getModuleName();
	}

}
