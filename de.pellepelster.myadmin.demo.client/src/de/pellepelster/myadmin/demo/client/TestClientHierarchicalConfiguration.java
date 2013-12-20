package de.pellepelster.myadmin.demo.client;

import de.pellepelster.myadmin.client.base.modules.hierarchical.BaseHierarchicalConfiguration;
import de.pellepelster.myadmin.demo.client.web.dictionaries.DemoDictionaryModel;

public class TestClientHierarchicalConfiguration extends BaseHierarchicalConfiguration
{
	public static final String ID = "test";

	public TestClientHierarchicalConfiguration()
	{
		super(ID, "Test Hierarchy");

		addHierarchy(DemoDictionaryModel.COMPANY);
		addHierarchy(DemoDictionaryModel.MANAGER, DemoDictionaryModel.COMPANY);
		addHierarchy(DemoDictionaryModel.EMPLOYEE, DemoDictionaryModel.COMPANY, DemoDictionaryModel.MANAGER);
	}
}
