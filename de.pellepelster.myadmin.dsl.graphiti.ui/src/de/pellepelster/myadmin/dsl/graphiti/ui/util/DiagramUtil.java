package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;

public class DiagramUtil
{

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

	public static void createOrUpdatePackageText(Diagram diagram)
	{
		// String[] projectNameSegments =
		// MyAdminProjectUtil.splitFQDNProjectName(diagram.getName());
		//
		// IPeCreateService peCreateService = Graphiti.getPeCreateService();
		// IGaService gaService = Graphiti.getGaService();
		//
		// Shape packageTextShapeShape = peCreateService.createShape(diagram,
		// false);
		//
		// // create and set text graphics algorithm
		// Text text = gaService.createText(packageTextShapeShape,
		// addedEntity.getName());
		// text.setForeground(DiagramUtil.manageColor(diagram,
		// MyAdminGraphitiConstants.PACKAGE_TEXT_FOREGROUND));
		// text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		//
		// // vertical alignment has as default value "center"
		// text.setFont(gaService.manageDefaultFont(diagram, false, true));
		// gaService.setLocationAndSize(text, 0, 0,
		// diagram.getGraphicsAlgorithm().getWidth(), 20);
		//
		// // create link and wire it
		// link(packageTextShapeShape, addedEntity);

	}

	public static Color manageColor(Diagram diagram, IColorConstant colorConstant)
	{
		return Graphiti.getGaService().manageColor(diagram, colorConstant);
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

	public static void updateDiagram(Diagram diagram)
	{
		createOrUpdatePackageText(diagram);
	}

}
