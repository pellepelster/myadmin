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
package de.pellepelster.myadmin.demo.server.test.dictionary;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.db.index.ISearchIndexService;
import de.pellepelster.myadmin.demo.DemoDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;
import de.pellepelster.myadmin.demo.client.web.test1.Test2VO;
import de.pellepelster.myadmin.server.services.search.DictionaryLabelIndexElementFactory;

public final class DemoSearchIndexTest extends BaseDemoDictionaryTest
{

	@Autowired
	protected ISearchIndexService searchIndexService;

	@Autowired
	protected IBaseVODAO baseVODAO;

	@Test
	public void testDeleteAll()
	{
		this.baseVODAO.deleteAll(Test1VO.class);

		Assert.assertEquals(0, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));
		Assert.assertEquals(0, this.baseVODAO.getCount(Test1VO.class));

		Test1VO test1VO1 = new Test1VO();
		test1VO1.setTextDatatype1("aaa");
		this.baseVODAO.create(test1VO1);

		Assert.assertEquals(1, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));
		Assert.assertEquals(1, this.baseVODAO.getCount(Test1VO.class));

		this.baseVODAO.deleteAll(Test1VO.class);

		Assert.assertEquals(0, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));
		Assert.assertEquals(0, this.baseVODAO.getCount(Test1VO.class));
	}

	@Test
	public void testDelete()
	{
		this.baseVODAO.deleteAll(Test1VO.class);

		Assert.assertEquals(0, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));
		Assert.assertEquals(0, this.baseVODAO.getCount(Test1VO.class));

		Test1VO test1VO1 = new Test1VO();
		test1VO1.setTextDatatype1("aaa");
		test1VO1 = this.baseVODAO.create(test1VO1);

		Test1VO test1VO2 = new Test1VO();
		test1VO2.setTextDatatype1("bbb");
		test1VO2 = this.baseVODAO.create(test1VO2);

		Assert.assertEquals(2, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));
		Assert.assertEquals(2, this.baseVODAO.getCount(Test1VO.class));

		this.baseVODAO.delete(test1VO1);

		Assert.assertEquals(1, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));
		Assert.assertEquals(1, this.baseVODAO.getCount(Test1VO.class));
	}

	@Test
	public void testGetCount()
	{
		this.searchIndexService.deleteAll(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1));

		Assert.assertEquals(0, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));

		Test1VO test1VO1 = new Test1VO();
		test1VO1.setTextDatatype1("aaa");
		this.baseVODAO.create(test1VO1);

		Assert.assertEquals(1, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));

		Test2VO test2VO1 = new Test2VO();
		test2VO1.setTextDatatype2("aaa");
		this.baseVODAO.create(test2VO1);

		Assert.assertEquals(1, this.searchIndexService.getCount(DictionaryLabelIndexElementFactory.createElementQuery(DemoDictionaryIDs.DICTIONARY1)));
	}

	public void baseJacksonTest()
	{
		SolrServer server = new HttpSolrServer("http://localhost:8380/solr/");

		try
		{
			// for (int o = 0; o < 100; o++)
			// {
			// Collection<SolrInputDocument> docs = new
			// ArrayList<SolrInputDocument>();
			//
			// for (int i = 0; i < 100; i++)
			// {
			// SolrInputDocument doc1 = new SolrInputDocument();
			// doc1.addField("id", UUID.randomUUID().toString());
			// docs.add(doc1);
			//
			// }
			//
			// server.add(docs);
			// server.commit();
			// }

			SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			QueryResponse rsp = server.query(query);

			SolrDocumentList docs = rsp.getResults();

			for (SolrDocument solrDocument : docs)
			{
				System.out.println(solrDocument.getFieldValue("id"));
			}

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	@Override
	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

	public void setSearchIndexService(ISearchIndexService searchIndexService)
	{
		this.searchIndexService = searchIndexService;
	}

}
