package de.pellepelster.myadmin.client.base.modules.navigation;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

public class NavigationTreeElement
{
	private static final Ordering<NavigationTreeElement> ORDERING = Ordering.natural().nullsFirst().onResultOf(new Function<NavigationTreeElement, String>()
	{
		@Override
		public String apply(NavigationTreeElement navigationTreeElements)
		{
			return navigationTreeElements.getLabel();
		}
	});

	private List<NavigationTreeElement> children = new ArrayList<NavigationTreeElement>();

	private String label;

	private String moduleLocator;

	public List<NavigationTreeElement> getChildren()
	{
		// this.children =
		// ORDERING.immutableSortedCopy(createChildren(navigationTreeElement.getChildren()));
		return this.children;
	}

	public String getLabel()
	{
		return this.label;
	}

	public boolean hasModuleLocator()
	{
		return getModuleLocator() != null;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getModuleLocator()
	{
		return this.moduleLocator;
	}

	public void setModuleLocator(String moduleLocator)
	{
		this.moduleLocator = moduleLocator;
	}

}
