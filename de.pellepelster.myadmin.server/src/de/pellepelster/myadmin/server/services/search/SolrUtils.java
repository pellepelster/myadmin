package de.pellepelster.myadmin.server.services.search;

import java.util.Objects;

import org.apache.solr.common.SolrDocument;

import de.pellepelster.myadmin.db.index.ISearchIndexElement;

public class SolrUtils
{
	public static String getDynamicStringFieldName(String fieldName)
	{
		return fieldName + ISearchIndexElementFactory.DYNAMIC_STRING_FIELD_POSTFIX;
	}

	public static void addDynamicStringField(StringBuilder sb, String fieldName, String fieldValue)
	{
		sb.append(String.format("%s:%s", getDynamicStringFieldName(fieldName), fieldValue));
	}

	public static String getDynamicStringField(SolrDocument solrDocument, String fieldName)
	{
		return Objects.toString(solrDocument.getFieldValue(SolrUtils.getDynamicStringFieldName(fieldName)));
	}

	public static void setDynamicStringField(ISearchIndexElement indexElement, SolrDocument solrDocument, String fieldName)
	{
		indexElement.getFields().put(fieldName, SolrUtils.getDynamicStringField(solrDocument, fieldName));
	}

}
