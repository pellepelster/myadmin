package de.pellepelster.myadmin.demo.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DemoBaseEntityServiceTest.class, DemoDictionaryTest.class, DemoHierarchicalTest.class })
public class DemoTestsuite
{

}
