package de.pellepelster.myadmin.dsl.validation;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.validation.NamesAreUniqueValidationHelper;

import com.google.common.collect.ImmutableSet;

import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class MyAdminNamesAreUniqueValidationHelper extends NamesAreUniqueValidationHelper {

	@Override
	protected ImmutableSet<EClass> getClusterTypes() {
		return ImmutableSet.of(MyAdminDslPackage.Literals.ENTITY, MyAdminDslPackage.Literals.NAVIGATION_NODE, MyAdminDslPackage.Literals.DICTIONARY, MyAdminDslPackage.Literals.DICTIONARY_SEARCH,
				MyAdminDslPackage.Literals.DICTIONARY_EDITOR, MyAdminDslPackage.Literals.DICTIONARY_RESULT, MyAdminDslPackage.Literals.DATATYPE);
	}
}
