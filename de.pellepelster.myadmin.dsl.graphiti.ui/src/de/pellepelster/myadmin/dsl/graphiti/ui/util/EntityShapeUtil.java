package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class EntityShapeUtil
{
	public static List<Shape> getEntityShape(Diagram diagram, Entity entity)
	{
		List<Shape> shapes = new ArrayList<Shape>();

		for (Shape shape : diagram.getChildren())
		{
			for (EObject eObject : shape.getLink().getBusinessObjects())
			{
				if (eObject instanceof Entity)
				{
					Entity otherEntity = (Entity) eObject;

					if (otherEntity.getName().equals(entity.getName()))
					{
						shapes.add(shape);
					}
				}
			}
		}

		return shapes;
	}

}
