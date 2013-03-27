/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.dsl.test;

import org.eclipse.xtext.junit.util.ParseHelper;
import org.eclipse.xtext.junit.validation.ValidationTestHelper;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.xbase.compiler.OnTheFlyJavaCompiler.EclipseRuntimeDependentJavaCompiler;
import org.eclipse.xtext.xbase.junit.evaluation.AbstractXbaseEvaluationTest;
import org.eclipse.xtext.xbase.lib.Functions;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.google.inject.Provider;

import de.pellepelster.myadmin.dsl.generator.MyAdminDslGenerator;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;

/**
 * Xbase integration test.
 * 
 * runs all Xbase tests from {@link AbstractXbaseEvaluationTest} in the context
 * of an entity operation.
 * 
 * Unsupported features can be disabled by overriding the respective test
 * method.
 * 
 * @author Sven Efftinge
 */
@RunWith(XtextRunner.class)
@InjectWith(InjectorProviderCustom.class)
public class XbaseIntegrationTest extends AbstractXbaseEvaluationTest
{

	@Inject
	private EclipseRuntimeDependentJavaCompiler javaCompiler;

	@Inject
	private ParseHelper<Model> parseHelper;

	@Inject
	private ValidationTestHelper validationHelper;

	@Inject
	private MyAdminDslGenerator generator;

	@Before
	public void initializeClassPath() throws Exception
	{
		javaCompiler.addClassPathOfClass(getClass()); // this bundle
		javaCompiler.addClassPathOfClass(AbstractXbaseEvaluationTest.class); // xbase.junit
		javaCompiler.addClassPathOfClass(Functions.class); // xbase.lib
		javaCompiler.addClassPathOfClass(Provider.class); // google guice
		javaCompiler.addClassPathOfClass(Supplier.class); // google collect
	}

	// @Override
	// protected Object invokeXbaseExpression(String expression) throws
	// Exception {
	// Model parse = parseHelper.parse("entity Foo { op doStuff() : Object { " +
	// expression + " } } ");
	// validationHelper.assertNoErrors(parse);
	// // StringConcatenation concatenation = generator.compile((Entity)
	// // parse.getElements().get(0));
	// Class<?> class1 = javaCompiler.compileToClass("Foo",
	// concatenation.toString());
	// Object foo = class1.newInstance();
	// Method method = class1.getDeclaredMethod("doStuff");
	// return method.invoke(foo);
	// }

	/**
	 * disabled, because checked exceptions are not supported by the domain
	 * model language
	 */
	@Override
	public void testThrowExpression_01() throws Exception
	{
	}

}
