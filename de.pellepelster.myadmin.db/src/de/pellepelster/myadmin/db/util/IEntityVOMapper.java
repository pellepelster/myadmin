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
package de.pellepelster.myadmin.db.util;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.db.IBaseEntity;

public interface IEntityVOMapper
{
	List<Class<? extends IBaseEntity>> getEntityClasses();

	Class<?> getMappedClass(Class<?> clazz);

	List<Class<? extends IBaseVO>> getVOClasses();
}
