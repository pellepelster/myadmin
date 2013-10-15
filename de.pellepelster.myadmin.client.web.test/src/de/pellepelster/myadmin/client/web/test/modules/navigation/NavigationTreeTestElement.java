package de.pellepelster.myadmin.client.web.test.modules.navigation;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import junit.framework.Assert;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;

public class NavigationTreeTestElement
{
	private final ModuleNavigationVO moduleNavigationVO;

	private final Collection<NavigationTreeTestElement> children;

	public NavigationTreeTestElement(ModuleNavigationVO moduleNavigationVO)
	{
		super();
		this.moduleNavigationVO = moduleNavigationVO;
		this.children = createChildren(moduleNavigationVO.getChildren());
	}

	public void assertNaivationText(String expected)
	{
		Assert.assertEquals(expected, this.moduleNavigationVO.getTitle());
	}

	public static Collection<NavigationTreeTestElement> createChildren(List<ModuleNavigationVO> children)
	{
		return Collections2.transform(children, new Function<ModuleNavigationVO, NavigationTreeTestElement>()
		{
			@Override
			@Nullable
			public NavigationTreeTestElement apply(ModuleNavigationVO moduleNavigationVO)
			{
				return new NavigationTreeTestElement(moduleNavigationVO);
			}
		});
	}
}
