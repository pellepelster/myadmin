package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;

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

	public static void initializeDiagram(Diagram diagram)
	{
		diagram.setGridUnit(10);
		diagram.setSnapToGrid(true);
		diagram.setVisible(true);

		Color c1 = Graphiti.getGaService().manageColor(diagram, 249, 238, 227);
		Color c2 = Graphiti.getGaService().manageColor(diagram, IColorConstant.WHITE);

		Rectangle rectangle = Graphiti.getGaService().createRectangle(diagram);
		rectangle.setBackground(c2);
		rectangle.setForeground(c1);
		rectangle.setFilled(true);
		rectangle.setHeight(1000);
		rectangle.setWidth(1000);
		rectangle.setLineStyle(LineStyle.SOLID);
		rectangle.setLineVisible(true);
		rectangle.setLineWidth(1);
		rectangle.setX(0);
		rectangle.setY(0);
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
