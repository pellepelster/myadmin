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
package de.pellepelster.myadmin.db.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import de.pellepelster.myadmin.db.test.mockup.entities.MapTest;

@Transactional
public class HibernateTest  extends BaseDBTest
{
	
	@PersistenceContext
	private EntityManager entityManager;
	
//	@Test
//	public void testTree()
//	{
//		Person person1 = new Person("Helmut", "Schmidt");
//		entityManager.persist(person1);
//		
//		Employee employee1 = new Employee("Hubert", "Stadlhuber", "Marketing");
//		entityManager.persist(employee1);
//		
//		PeopleTree peopleTree1 = new PeopleTree();
//		
//		PeopleTree peopleTree2 = new PeopleTree();
//		peopleTree2.setPerson(person1);
//		peopleTree1.getChildren().add(peopleTree2);
//		
//		PeopleTree peopleTree3 = new PeopleTree();
//		peopleTree3.setPerson(employee1);
//		peopleTree1.getChildren().add(peopleTree3);
//
//		entityManager.persist(peopleTree1);
//		entityManager.persist(peopleTree2);
//		entityManager.persist(peopleTree3);
//
//		
//		entityManager.flush();
//
//		Query query = entityManager.createQuery("SELECT p FROM PeopleTree p");
//
//		List<PeopleTree> result = query.getResultList();
//		
//		PeopleTree resultPeopleTree1 = result.get(0);
//
//		PeopleTree resultPeopleTree2 = resultPeopleTree1.getChildren().get(0);
//		PeopleTree resultPeopleTree3 = resultPeopleTree1.getChildren().get(1);
//
//	}

	@Test
	public void testMap()
	{
		MapTest mapTest = new MapTest();
		mapTest.getMap().put("aaa", "bbb");
		mapTest.getMap().put("ccc", "ddd");
		entityManager.persist(mapTest);
		
		entityManager.flush();

		Query query = entityManager.createQuery("SELECT m FROM MapTest m");

		List<MapTest> result = query.getResultList();
		
		MapTest resultMapTest = result.get(0);

		Assert.assertEquals(2, resultMapTest.getMap().size());
		
	}

	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
}
