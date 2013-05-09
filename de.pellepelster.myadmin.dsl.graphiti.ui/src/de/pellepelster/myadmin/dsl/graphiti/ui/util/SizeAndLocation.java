package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.ui.services.GraphitiUi;

public class SizeAndLocation
{

	private IGaService gaService = Graphiti.getGaService();

	private int x;

	private int y;

	private int width;

	private int height;

	private GraphicsAlgorithm parentGraphicsAlgorithm;

	private boolean changed = false;

	public static SizeAndLocation create(IAddContext context, int width, int height)
	{
		return new SizeAndLocation(context.getX(), context.getY(), width, height, null);
	}

	public static SizeAndLocation create(int x, int y, int width, int height)
	{
		return new SizeAndLocation(x, y, width, height, null);
	}

	public static SizeAndLocation create(GraphicsAlgorithm graphicsAlgorithm)
	{
		return new SizeAndLocation(0, 0, graphicsAlgorithm.getWidth(), graphicsAlgorithm.getHeight(), graphicsAlgorithm);
	}

	public static SizeAndLocation create(ContainerShape containerShape)
	{
		return create(containerShape.getGraphicsAlgorithm());
	}

	public static SizeAndLocation create1(GraphicsAlgorithm graphicsAlgorithm)
	{
		return create(graphicsAlgorithm.getX(), graphicsAlgorithm.getY(), graphicsAlgorithm.getWidth(), graphicsAlgorithm.getHeight());
	}

	private SizeAndLocation(int x, int y, int width, int height, GraphicsAlgorithm parentGraphicsAlgorithm)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parentGraphicsAlgorithm = parentGraphicsAlgorithm;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public SizeAndLocation setLocationAndSize(GraphicsAlgorithm graphicsAlgorithm)
	{
		if (graphicsAlgorithm.getX() != getX() || graphicsAlgorithm.getY() != getY() || graphicsAlgorithm.getWidth() != getWidth()
				|| graphicsAlgorithm.getHeight() != getHeight())
		{
			this.gaService.setLocationAndSize(graphicsAlgorithm, getX(), getY(), getWidth(), getHeight());
			this.changed = true;
		}

		return this;
	}

	public int[] getPoints()
	{
		return new int[] { getX(), getY(), getWidth(), getHeight() };
	}

	public SizeAndLocation setYAndHeight(int yAndheight)
	{
		this.y = yAndheight;
		this.height = yAndheight;

		return this;
	}

	public SizeAndLocation setHeight(int height)
	{
		this.height = height;
		return this;
	}

	public SizeAndLocation setHeight(Text text)
	{
		this.height = GraphitiUi.getUiLayoutService().calculateTextSize("x", text.getFont()).getHeight();
		;
		return this;
	}

	public SizeAndLocation setY(int y)
	{
		this.y = y;
		return this;
	}

	public SizeAndLocation setYAndShrinkHeight(int y)
	{
		this.y = y;
		this.height -= y;
		return this;
	}

	public SizeAndLocation updatePoints(Polyline headerLine)
	{
		Point firstPoint = headerLine.getPoints().get(0);

		if (firstPoint.getX() != getX() || firstPoint.getY() != getY())
		{
			firstPoint = this.gaService.createPoint(getX(), getY());
			headerLine.getPoints().set(0, firstPoint);
			this.changed = true;
		}

		Point secondPoint = headerLine.getPoints().get(0);

		if (firstPoint.getX() != getWidth() || firstPoint.getY() != getHeight())
		{
			secondPoint = this.gaService.createPoint(getWidth(), getHeight());
			headerLine.getPoints().set(1, secondPoint);
			this.changed = true;
		}

		return this;
	}

	public boolean hasChanged(boolean previousState)
	{
		return this.changed || previousState;
	}

	public SizeAndLocation shrinkWidth(int widthOffset)
	{
		this.width -= widthOffset;
		return this;
	}

	public SizeAndLocation setWidth(int width)
	{
		this.width = width;
		return this;
	}

	public SizeAndLocation setWidthFactor(double widthFactor)
	{
		this.width *= widthFactor;
		return this;
	}

	public SizeAndLocation center()
	{
		if (this.parentGraphicsAlgorithm != null)
		{
			this.x = (this.parentGraphicsAlgorithm.getWidth() - getWidth()) / 2;
		}
		else
		{
			throw new RuntimeException(String.format("no parent GraphicsAlgorithm found"));
		}

		return this;
	}

}
