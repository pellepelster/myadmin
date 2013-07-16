package de.pellepelster.myadmin.dsl.query.controls;

import java.util.Objects;

import org.eclipse.emf.ecore.EStructuralFeature;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBigDecimalControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBooleanControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryDateControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEnumerationControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryIntegerControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryReferenceControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryTextControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class DictionaryControlQuery<T extends DictionaryControl>
{
	private T dictionaryControl;

	private EStructuralFeature referenceStructuralFeature;

	protected DictionaryControlQuery(T dictionaryControl, EStructuralFeature referenceStructuralFeature)
	{
		this.dictionaryControl = dictionaryControl;
		this.referenceStructuralFeature = referenceStructuralFeature;
	}

	public static DictionaryControlQuery<?> create(DictionaryControl dictionaryControl)
	{
		if (dictionaryControl instanceof DictionaryTextControl)
		{
			return new DictionaryTextControlQuery((DictionaryTextControl) dictionaryControl);
		}

		if (dictionaryControl instanceof DictionaryDateControl)
		{
			return new DictionaryDateControlQuery((DictionaryDateControl) dictionaryControl);
		}

		if (dictionaryControl instanceof DictionaryIntegerControl)
		{
			return new DictionaryIntegerControlQuery((DictionaryIntegerControl) dictionaryControl);
		}

		if (dictionaryControl instanceof DictionaryBigDecimalControl)
		{
			return new DictionaryBigDecimalControlQuery((DictionaryBigDecimalControl) dictionaryControl);
		}

		if (dictionaryControl instanceof DictionaryBooleanControl)
		{
			return new DictionaryBooleanControlQuery((DictionaryBooleanControl) dictionaryControl);
		}

		if (dictionaryControl instanceof DictionaryEnumerationControl)
		{
			return new DictionaryEnumerationControlQuery((DictionaryEnumerationControl) dictionaryControl);
		}

		if (dictionaryControl instanceof DictionaryReferenceControl)
		{
			return new DictionaryReferenceControlQuery((DictionaryReferenceControl) dictionaryControl);
		}

		throw new RuntimeException(String.format("unsupported dictionary control type '%s'", dictionaryControl.getClass().getName()));
	}

	public String getName(boolean resolve)
	{
		T nextDictionaryControl = this.dictionaryControl;

		while (resolve && nextDictionaryControl.eGet(this.referenceStructuralFeature) != null
				&& nextDictionaryControl.eGet(MyAdminDslPackage.Literals.DICTIONARY_CONTAINER_CONTENT__NAME) == null)
		{
			nextDictionaryControl = (T) nextDictionaryControl.eGet(this.referenceStructuralFeature);
		}

		return Objects.toString(nextDictionaryControl.eGet(MyAdminDslPackage.Literals.DICTIONARY_CONTAINER_CONTENT__NAME), null);
	}
}
