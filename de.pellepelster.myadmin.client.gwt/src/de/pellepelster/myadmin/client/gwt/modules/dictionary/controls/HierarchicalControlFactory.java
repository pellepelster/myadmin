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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControl;

/**
 * control factory for text controls
 * 
 * @author pelle
 * 
 */
public class HierarchicalControlFactory extends BaseControlFactory<IHierarchicalControlModel>
{

	/** {@inheritDoc} */
	@Override
	public IUIControl<Widget> createControl(IHierarchicalControlModel controlModel, LAYOUT_TYPE layoutType)
	{
		return new HierarchicalControl(controlModel);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(IBaseControlModel baseControlModel)
	{
		return baseControlModel instanceof IHierarchicalControlModel;
	}

}
