package de.pellepelster.myadmin.dsl.graphiti.ui.datatype.reference;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.base.BasePropertySection;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;

public class ReferenceDatatypeSection extends BasePropertySection<ReferenceDatatype>
{

	public ReferenceDatatypeSection()
	{
		super(ReferenceDatatype.class);
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage)
	{
		super.createControls(parent, tabbedPropertySheetPage);

		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		Composite composite = factory.createFlatFormComposite(parent);

		addTypeSelectorProperty(composite, MyAdminDslPackage.Literals.REFERENCE_DATATYPE__ENTITY, Messages.Entity);

	}

}