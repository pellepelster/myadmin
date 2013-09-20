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
package de.pellepelster.myadmin.server.test;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.web.services.IUserService;

public class SearchIndexTest // extends BaseMyAdminJndiContextTest
{

	@Autowired
	protected IUserService userService;

	public void setUserService(IUserService userService)
	{
		this.userService = userService;
	}

	@Test
	public void baseJacksonTest()
	{
		SolrServer server = new HttpSolrServer("http://localhost:8380/solr/");

		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id1", 1.0f);
		doc1.addField("name", "doc1", 1.0f);
		doc1.addField("price", 10);

		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);

		try
		{
			server.add(docs);
			server.commit();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}
}
