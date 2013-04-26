package de.pellepelster.myadmin.dsl.graphiti.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;

public class DiagramUtil
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

	public static Anchor getOrCreateChopboxAnchor(AnchorContainer anchorContainer)
	{
		if (anchorContainer instanceof Anchor)
		{
			return (Anchor) anchorContainer;
		}
		else if (Graphiti.getPeService().getChopboxAnchor(anchorContainer) != null)
		{
			return Graphiti.getPeService().getChopboxAnchor(anchorContainer);
		}
		else
		{
			return Graphiti.getPeService().createChopboxAnchor(anchorContainer);

		}
	}

}
