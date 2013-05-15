package de.pellepelster.myadmin.dsl.graphiti.ui.base;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class BasePropertySection<BO extends EObject> extends GFPropertySection implements ITabbedPropertyConstants
{
	private final Class<BO> businessObjectClass;

	private List<IPropertyRefreshHandler<BO>> propertyRefreshHandlers = new ArrayList<IPropertyRefreshHandler<BO>>();

	private interface IPropertyRefreshHandler<BO extends EObject>
	{
		void onRefresh(BO businessObject);

	};

	public BasePropertySection(Class<BO> businessObjectClass)
	{
		super();
		this.businessObjectClass = businessObjectClass;
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage)
	{
		super.createControls(parent, tabbedPropertySheetPage);

		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		Composite composite = factory.createFlatFormComposite(parent);

		addTextProperty(composite, MyAdminDslPackage.Literals.DATATYPE__NAME, Messages.Name);

	}

	public void addTextProperty(Composite parent, final EStructuralFeature eStructuralFeature, String name)
	{
		FormData data;

		final Text text = getWidgetFactory().createText(parent, "");
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, VSPACE);
		text.setLayoutData(data);
		text.addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent e)
			{
				if (hasBusinessObject())
				{
					final BO businessObject = getBusinessObject();

					TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(businessObject);
					domain.getCommandStack().execute(new RecordingCommand(domain)
					{
						@Override
						public void doExecute()
						{
							businessObject.eSet(eStructuralFeature, text.getText());
						}
					});
				}
			}

			@Override
			public void focusGained(FocusEvent e)
			{
			}
		});

		CLabel label = getWidgetFactory().createCLabel(parent, name);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(text, -HSPACE);
		data.top = new FormAttachment(text, 0, SWT.CENTER);
		label.setLayoutData(data);

		this.propertyRefreshHandlers.add(new IPropertyRefreshHandler<BO>()
		{
			@Override
			public void onRefresh(BO businessObject)
			{
				Object value = businessObject.eGet(eStructuralFeature);

				if (value != null)
				{
					text.setText(value.toString());
				}
			}

		});
	}

	public boolean hasBusinessObject()
	{
		return getBusinessObject() != null;
	}

	public BO getBusinessObject()
	{
		PictogramElement pe = getSelectedPictogramElement();

		if (pe == null)
		{
			return null;
		}
		else
		{
			Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);

			if (bo != null && this.businessObjectClass.isAssignableFrom(bo.getClass()))
			{
				return (BO) bo;
			}
			else
			{
				return null;
			}
		}
	}

	@Override
	public void refresh()
	{
		if (hasBusinessObject())
		{
			BO businessObject = getBusinessObject();

			for (IPropertyRefreshHandler<BO> propertyRefreshHandler : this.propertyRefreshHandlers)
			{
				propertyRefreshHandler.onRefresh(businessObject);
			}
		}
	}
}