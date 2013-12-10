package de.pellepelster.myadmin.demo.server.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.pellepelster.myadmin.demo.server.test.dictionary.DemoDictionaryTest;
import de.pellepelster.myadmin.demo.server.test.dictionary.DemoHierarchicalTest;

@RunWith(Suite.class)
@SuiteClasses({ DemoBaseEntityServiceTest.class, DemoDictionaryTest.class, DemoHierarchicalTest.class })
public class DemoTestsuite
{
}
