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
package de.pellepelster.myadmin.client.gwt.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import de.pellepelster.myadmin.client.gwt.test.SuggestCellControl.IValueFormatter;
import de.pellepelster.myadmin.client.gwt.test.SuggestCellControl.SuggestCellSuggestion;

public class CellTableTest implements EntryPoint
{

	private class ExampleSuggestOracle extends SuggestOracle
	{
		@Override
		public void requestSuggestions(final Request request, Callback callback)
		{
			List<SuggestCellSuggestion<String>> suggestions = new ArrayList<SuggestCellSuggestion<String>>();

			for (int i = 0; i < 10; i++)
			{
				final int o = i;

				suggestions.add(new SuggestCellSuggestion<String>()
				{

					private final ExampleTableItem exampleTableItem = new ExampleTableItem(request.getQuery() + Integer.toString(o), request.getQuery()
							+ Integer.toString(o), 1, false, new Date());

					@Override
					public String getDisplayString()
					{
						return exampleTableItem.getString();
					}

					@Override
					public String getReplacementString()
					{
						return exampleTableItem.getString();
					}

					@Override
					public String getValue()
					{
						return exampleTableItem.getString();
					}
				});
			}

			Response response = new Response();
			response.setSuggestions(suggestions);

			callback.onSuggestionsReady(request, response);
		}
	}

	public class ExampleTableItem
	{
		private Boolean bool;
		private Date date;
		private Integer integer;
		private String string;
		private String string1;

		public ExampleTableItem(String string, String string1, Integer integer, Boolean bool, Date date)
		{
			super();
			this.string = string;
			this.string1 = string1;
			this.integer = integer;
			this.bool = bool;
			this.date = date;
		}

		public Boolean getBool()
		{
			return bool;
		}

		public Date getDate()
		{
			return date;
		}

		public Integer getInteger()
		{
			return integer;
		}

		public String getString()
		{
			return string;
		}

		public String getString1()
		{
			return string1;
		}

		public void setBool(Boolean bool)
		{
			this.bool = bool;
		}

		public void setDate(Date date)
		{
			this.date = date;
		}

		public void setInteger(Integer integer)
		{
			this.integer = integer;
		}

		public void setString(String string)
		{
			this.string = string;
		}

		public void setString1(String string1)
		{
			this.string1 = string1;
		}

	}

	private void addColumns(CellTable<ExampleTableItem> cellTable)
	{
		IValueFormatter<String> valueFormatter = new IValueFormatter<String>()
		{

			@Override
			public String format(String value)
			{
				return value;
			}
		};

		Column<ExampleTableItem, String> stringColumn = new Column<ExampleTableItem, String>(
				new SuggestCellControl<String>(new ExampleSuggestOracle(), valueFormatter))
		{

			@Override
			public String getValue(ExampleTableItem exampleTableItem)
			{
				return exampleTableItem.getString();
			}

			@Override
			public void render(Context context, ExampleTableItem object, SafeHtmlBuilder sb)
			{
				super.render(context, object, sb);
			}
		};
		stringColumn.setFieldUpdater(new FieldUpdater<ExampleTableItem, String>()
		{
			@Override
			public void update(int index, ExampleTableItem object, String value)
			{
				object.setString(value);
			}
		});
		cellTable.addColumn(stringColumn, "String Column");

		Column<ExampleTableItem, String> string1Column = new Column<ExampleTableItem, String>(new EditTextCell())
		{
			@Override
			public String getValue(ExampleTableItem exampleTableItem)
			{
				return exampleTableItem.getString1();
			}
		};
		string1Column.setFieldUpdater(new FieldUpdater<ExampleTableItem, String>()
		{
			@Override
			public void update(int index, ExampleTableItem object, String value)
			{
				object.setString1(value);
			}
		});

		cellTable.addColumn(string1Column, "String Column");

	}

	private CellTable<ExampleTableItem> createTable()
	{
		CellTable<ExampleTableItem> cellTable = new CellTable<ExampleTableItem>();
		// cellTable.setSelectionEnabled( true );
		cellTable.setSelectionModel(new SingleSelectionModel<ExampleTableItem>());
		cellTable.setPageSize(5);
		cellTable.setPageStart(0);

		return cellTable;
	}

	private ArrayList<ExampleTableItem> getData()
	{
		ArrayList<ExampleTableItem> tableData = new ArrayList<ExampleTableItem>();
		tableData.add(new ExampleTableItem("AAA", "aaa", 10, true, new Date()));
		tableData.add(new ExampleTableItem("BBB", "aaa", 200, false, new Date()));
		tableData.add(new ExampleTableItem("CCC", "aaa", 3000, true, new Date()));
		tableData.add(new ExampleTableItem("DDD", "aaa", 40, false, new Date()));
		tableData.add(new ExampleTableItem("EEE", "aaa", 500, true, new Date()));
		tableData.add(new ExampleTableItem("FFF", "aaa", 6000, false, new Date()));
		tableData.add(new ExampleTableItem("GGG", "aaa", 70, true, new Date()));
		tableData.add(new ExampleTableItem("HHH", "aaa", 800, false, new Date()));
		tableData.add(new ExampleTableItem("III", "aaa", 9000, true, new Date()));
		tableData.add(new ExampleTableItem("JJJ", "aaa", 10, false, new Date()));
		tableData.add(new ExampleTableItem("KKK", "aaa", 11, true, new Date()));
		return tableData;
	}

	@Override
	public void onModuleLoad()
	{
		CellTable<ExampleTableItem> cellTable = createTable();

		addColumns(cellTable);

		ListDataProvider<ExampleTableItem> listDataProvider = new ListDataProvider<ExampleTableItem>();
		listDataProvider.setList(getData());
		listDataProvider.addDataDisplay(cellTable);

		RootPanel.get().add(cellTable);
	}

}