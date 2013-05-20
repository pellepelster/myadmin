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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.ui.ModelUiUtil;

public abstract class BasePropertySection<BO extends EObject> extends GFPropertySection implements ITabbedPropertyConstants
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

	private void setValueToBusinessObject(final EStructuralFeature eStructuralFeature, final Object value)
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
					businessObject.eSet(eStructuralFeature, value);
				}
			});
		}
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
				setValueToBusinessObject(eStructuralFeature, text.getText());
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

	public void addTypeSelectorProperty(Composite parent, final EStructuralFeature eStructuralFeature, String name)
	{
		FormData data;

		final Hyperlink link = getWidgetFactory().createHyperlink(parent, Messages.None, SWT.NONE);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, VSPACE);
		link.setLayoutData(data);

		link.addHyperlinkListener(new HyperlinkAdapter()
		{
			@Override
			public void linkActivated(HyperlinkEvent e)
			{
				EntitySelectionDialog entitySelectionDialog = new EntitySelectionDialog(PlatformUI.createDisplay().getActiveShell(), getDiagramTypeProvider()
						.getFeatureProvider());

				entitySelectionDialog.setTitle(Messages.DatatypeSelection);
				entitySelectionDialog.setMessage(Messages.DatatypeSelectFromList);
				entitySelectionDialog.setInitialPattern("?");
				entitySelectionDialog.setBlockOnOpen(true);
				entitySelectionDialog.open();

				setValueToBusinessObject(eStructuralFeature, entitySelectionDialog.getFirstResult());
				link.setText(ModelUiUtil.getLabelProvider().getText(entitySelectionDialog.getFirstResult()));
			}
		});

		CLabel label = getWidgetFactory().createCLabel(parent, name);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(link, -HSPACE);
		data.top = new FormAttachment(link, 0, SWT.CENTER);
		label.setLayoutData(data);

		this.propertyRefreshHandlers.add(new IPropertyRefreshHandler<BO>()
		{
			@Override
			public void onRefresh(BO businessObject)
			{
				Object value = businessObject.eGet(eStructuralFeature);

				if (value != null)
				{
					link.setText(ModelUiUtil.getLabelProvider().getText(value));
				}
				else
				{
					link.setText(Messages.None);
				}
			}

		});
	}

	public boolean hasBusinessObject()
	{
		return getBusinessObject() != null;
	}

	@SuppressWarnings("unchecked")
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