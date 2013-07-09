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
package de.pellepelster.myadmin.server.base.xml;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

@Target({ TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlVOMapping
{
	boolean isListWrapper() default false;

	boolean isReference() default false;

	boolean isReferenceListWrapper() default false;

	Class<? extends IBaseVO> voClass();
}