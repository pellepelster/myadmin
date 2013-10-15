package de.pellepelster.myadmin.client.web.modules.navigation;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;

public class NavigationTreeElements implements Iterable<NavigationTreeElements>
{
	private static final Ordering<NavigationTreeElements> ORDERING = Ordering.natural().nullsFirst().onResultOf(new Function<NavigationTreeElements, String>()
	{
		@Override
		public String apply(NavigationTreeElements navigationTreeElements)
		{
			return navigationTreeElements.getTitle();
		}
	});

	private ModuleNavigationVO moduleNavigationVO;

	private final ImmutableList<NavigationTreeElements> children;

	public List<NavigationTreeElements> getChildren()
	{
		return this.children;
	}

	public NavigationTreeElements(List<ModuleNavigationVO> moduleNavigationVOs)
	{
		super();
		this.children = ORDERING.immutableSortedCopy(createChildren(moduleNavigationVOs));
	}

	public NavigationTreeElements(ModuleNavigationVO moduleNavigationVO)
	{
		super();
		this.moduleNavigationVO = moduleNavigationVO;
		this.children = ORDERING.immutableSortedCopy(createChildren(moduleNavigationVO.getChildren()));
	}

	public static List<NavigationTreeElements> createChildren(List<ModuleNavigationVO> children)
	{
		return Lists.transform(children, new Function<ModuleNavigationVO, NavigationTreeElements>()
		{
			@Override
			@Nullable
			public NavigationTreeElements apply(ModuleNavigationVO moduleNavigationVO)
			{
				return new NavigationTreeElements(moduleNavigationVO);
			}
		});
	}

	@Override
	public Iterator<NavigationTreeElements> iterator()
	{
		return this.children.iterator();
	}

	public String getTitle()
	{
		return this.moduleNavigationVO.getTitle();
	}

	public boolean hasModule()
	{
		return this.moduleNavigationVO.getModule() != null;
	}

	public String getModuleName()
	{
		return (hasModule()) ? this.moduleNavigationVO.getModule().getName() : null;
	}

}
