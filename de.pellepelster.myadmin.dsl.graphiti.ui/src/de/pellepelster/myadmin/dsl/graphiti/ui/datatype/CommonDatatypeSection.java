package de.pellepelster.myadmin.dsl.graphiti.ui.datatype;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.BasePropertySection;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class CommonDatatypeSection extends BasePropertySection<Datatype>
{

	public CommonDatatypeSection()
	{
		super(Datatype.class);
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage)
	{
		super.createControls(parent, tabbedPropertySheetPage);

		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		Composite composite = factory.createFlatFormComposite(parent);

		addTextProperty(composite, MyAdminDslPackage.Literals.DATATYPE__NAME, Messages.Name);

	}

}