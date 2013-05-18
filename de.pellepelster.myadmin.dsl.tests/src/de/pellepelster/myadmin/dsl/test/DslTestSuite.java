package de.pellepelster.myadmin.dsl.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EntityQueryTest.class, ModelQueryTest.class, EntityAttributeQueryTest.class })
public class DslTestSuite
{

}
