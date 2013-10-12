package de.pellepelster.myadmin.demo.client.test;

import de.pellepelster.myadmin.client.base.modules.hierarchical.BaseHierarchicalConfiguration;
import de.pellepelster.myadmin.demo.client.web.dictionaries.CompanyDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.dictionaries.EmployeeDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.dictionaries.ManagerDictionaryIDs;

public class TestClientHierarchicalConfiguration extends BaseHierarchicalConfiguration
{
	public static final String ID = "test";

	public TestClientHierarchicalConfiguration()
	{
		super(ID);

		addHierarchy(CompanyDictionaryIDs.COMPANY);
		addHierarchy(ManagerDictionaryIDs.MANAGER, CompanyDictionaryIDs.COMPANY);
		addHierarchy(EmployeeDictionaryIDs.EMPLOYEE, CompanyDictionaryIDs.COMPANY, ManagerDictionaryIDs.MANAGER);
	}
}
