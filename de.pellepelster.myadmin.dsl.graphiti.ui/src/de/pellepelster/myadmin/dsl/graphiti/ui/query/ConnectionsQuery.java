package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.services.Graphiti;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;

public class ConnectionsQuery extends BaseListQuery<Connection>
{

	private ConnectionsQuery(Collection<Connection> connections)
	{
		super(connections);
	}

	public static ConnectionsQuery create(Collection<Connection> connections)
	{
		return new ConnectionsQuery(connections);
	}

	public ConnectionsQuery filterConnectionsByTargetId(final String elementId)
	{
		setList(Collections2.filter(getList(), new Predicate<Connection>()
		{
			@Override
			public boolean apply(Connection connection)
			{
				return elementId.equals(GraphitiProperties.get(connection.getEnd().getParent(), MyAdminGraphitiConstants.ELEMENT_ID_KEY));
			}
		}));

		return this;
	}

	@SuppressWarnings("unchecked")
	public <BO> Map<Connection, List<BO>> getLinkedBusinessObjectType(final Class<BO> businessObjectClass)
	{
		Map<Connection, List<BO>> businessObjects = new HashMap<Connection, List<BO>>();

		for (Connection connection : getList())
		{
			businessObjects.put(connection, new ArrayList<BO>());

			for (EObject businessObject : Graphiti.getLinkService().getAllBusinessObjectsForLinkedPictogramElement(connection.getEnd().getParent()))
			{
				if (businessObjectClass.isAssignableFrom(businessObject.getClass()))
				{
					businessObjects.get(connection).add((BO) businessObject);
				}
			}
		}

		return businessObjects;
	}

	public ConnectionsQuery filterConnectionsByLinkedBusinessObjectType(final Class<? extends EObject> businessObjectClass)
	{
		setList(Collections2.filter(getList(), new Predicate<Connection>()
		{
			@Override
			public boolean apply(Connection connection)
			{
				for (EObject businessObject : Graphiti.getLinkService().getAllBusinessObjectsForLinkedPictogramElement(connection.getEnd().getParent()))
				{
					if (!businessObjectClass.isAssignableFrom(businessObject.getClass()))
					{
						return false;
					}
				}

				return true;
			}
		}));

		return this;
	}

	public ConnectionsQuery filterConnectionsById(final String elementId)
	{
		setList(Collections2.filter(getList(), new Predicate<Connection>()
		{
			@Override
			public boolean apply(Connection connection)
			{
				return elementId.equals(GraphitiProperties.get(connection, MyAdminGraphitiConstants.ELEMENT_ID_KEY));
			}
		}));

		return this;
	}

}
