package de.pellepelster.myadmin.dsl.graphiti.ui.datatype;

import org.eclipse.graphiti.features.IFeatureProvider;

import de.pellepelster.myadmin.dsl.graphiti.ui.base.BaseNameDirectEditFeature;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public abstract class BaseDatatypeDirectEditFeature<T extends Datatype> extends BaseNameDirectEditFeature<T>
{
	public BaseDatatypeDirectEditFeature(IFeatureProvider fp, Class<T> datatypeClass)
	{
		super(fp, datatypeClass, MyAdminDslPackage.Literals.DATATYPE__NAME);
	}
}