package de.pellepelster.myadmin.client.web.test.modules.navigation;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import junit.framework.Assert;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.web.modules.navigation.NavigationTreeElements;

public class NavigationTreeTestElements implements Iterable<NavigationTreeTestElements>
{
	private NavigationTreeElements navigationTreeElements;

	private final List<NavigationTreeTestElements> children;

	public NavigationTreeTestElements(NavigationTreeElements navigationTreeElements)
	{
		super();
		this.navigationTreeElements = navigationTreeElements;

		this.children = Lists.transform(navigationTreeElements.getChildren(), new Function<NavigationTreeElements, NavigationTreeTestElements>()
		{
			@Override
			@Nullable
			public NavigationTreeTestElements apply(NavigationTreeElements navigationTreeElements)
			{
				return new NavigationTreeTestElements(navigationTreeElements);
			}
		});
	}

	@Override
	public Iterator<NavigationTreeTestElements> iterator()
	{
		return this.children.iterator();
	}

	public void assertNavigationText(String expected)
	{
		Assert.assertEquals(expected, this.navigationTreeElements.getLabel());
	}

	public void assertChildrenCount(int count)
	{
		Assert.assertEquals(count, this.children.size());
	}

	public void assertChildNavigationText(int index, String expected)
	{
		this.children.get(index).assertNavigationText(expected);

	}
}
