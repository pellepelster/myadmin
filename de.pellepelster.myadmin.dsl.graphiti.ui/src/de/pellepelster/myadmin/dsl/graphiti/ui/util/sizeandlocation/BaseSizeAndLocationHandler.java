package de.pellepelster.myadmin.dsl.graphiti.ui.util.sizeandlocation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.ui.services.GraphitiUi;

public abstract class BaseSizeAndLocationHandler<Q>
{

	private IGaService gaService = Graphiti.getGaService();

	private boolean changed = false;

	private boolean absolute = false;

	public static class SizeAndLocation
	{

		private int x;

		private int y;

		private int width;

		private int height;

		public SizeAndLocation()
		{
			this(0, 0, 0, 0);
		}

		public SizeAndLocation(int width, int height)
		{
			this(0, 0, width, height);
		}

		public SizeAndLocation(int x, int y, int width, int height)
		{
			super();
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		@Override
		public SizeAndLocation clone()
		{
			return new SizeAndLocation(this.x, this.y, this.width, this.height);
		}

		public int getX()
		{
			return this.x;
		}

		public void setX(int x)
		{
			this.x = x;
		}

		public int getY()
		{
			return this.y;
		}

		public void setY(int y)
		{
			this.y = y;
		}

		public int getWidth()
		{
			return this.width;
		}

		public void setWidth(int width)
		{
			this.width = width;
		}

		public int getHeight()
		{
			return this.height;
		}

		public void setHeight(int height)
		{
			this.height = height;
		}

	}

	private interface ISizeAndLocationModifier
	{
		void modifySizeAndLocation(SizeAndLocation getParentSizeAndLocation, SizeAndLocation sizeAndLocation);
	}

	private List<ISizeAndLocationModifier> modificationSteps = new ArrayList<ISizeAndLocationModifier>();

	protected abstract Q getQueryInstance();

	protected SizeAndLocation computeSizeAndLocationInternal(SizeAndLocation parentSizeAndLocation)
	{
		SizeAndLocation sizeAndLocation = parentSizeAndLocation.clone();

		if (!isAbsolute())
		{
			sizeAndLocation.setX(0);
			sizeAndLocation.setY(0);
		}

		for (ISizeAndLocationModifier sizeAndLocationModifier : this.modificationSteps)
		{
			sizeAndLocationModifier.modifySizeAndLocation(parentSizeAndLocation, sizeAndLocation);
		}

		return sizeAndLocation;
	}

	protected SizeAndLocation getSizeAndLocation(GraphicsAlgorithm graphicsAlgorithm)
	{
		return new SizeAndLocation(graphicsAlgorithm.getX(), graphicsAlgorithm.getY(), graphicsAlgorithm.getWidth(), graphicsAlgorithm.getHeight());
	}

	protected Q setSizeAndLocation(SizeAndLocation parentSizeAndLocation, GraphicsAlgorithm graphicsAlgorithm)
	{

		this.changed = false;

		SizeAndLocation sizeAndLocation = computeSizeAndLocationInternal(parentSizeAndLocation);

		if (graphicsAlgorithm.getX() != sizeAndLocation.getX() || graphicsAlgorithm.getY() != sizeAndLocation.getY()
				|| graphicsAlgorithm.getWidth() != sizeAndLocation.getWidth() || graphicsAlgorithm.getHeight() != sizeAndLocation.getHeight())
		{
			this.gaService.setLocationAndSize(graphicsAlgorithm, sizeAndLocation.getX(), sizeAndLocation.getY(), sizeAndLocation.getWidth(),
					sizeAndLocation.getHeight());
			this.changed = true;
		}

		return getQueryInstance();
	}

	protected int[] getPoints(SizeAndLocation parentSizeAndLocation)
	{
		SizeAndLocation sizeAndLocation = computeSizeAndLocationInternal(parentSizeAndLocation);

		return new int[] { sizeAndLocation.getX(), sizeAndLocation.getY(), sizeAndLocation.getWidth(), sizeAndLocation.getHeight() };
	}

	public Q setYAndHeight(final int yAndheight)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setHeight(yAndheight);
				sizeAndLocation.setY(yAndheight);

			}
		});

		return getQueryInstance();
	}

	protected Q updatePoints(SizeAndLocation parentSizeAndLocation, Polyline headerLine)
	{

		this.changed = false;

		SizeAndLocation sizeAndLocation = computeSizeAndLocationInternal(parentSizeAndLocation);

		Point firstPoint = headerLine.getPoints().get(0);

		if (firstPoint.getX() != sizeAndLocation.getX() || firstPoint.getY() != sizeAndLocation.getY())
		{
			firstPoint = this.gaService.createPoint(sizeAndLocation.getX(), sizeAndLocation.getY());
			headerLine.getPoints().set(0, firstPoint);
			this.changed = true;
		}

		Point secondPoint = headerLine.getPoints().get(0);

		if (firstPoint.getX() != sizeAndLocation.getWidth() || firstPoint.getY() != sizeAndLocation.getHeight())
		{
			secondPoint = this.gaService.createPoint(sizeAndLocation.getWidth(), sizeAndLocation.getHeight());
			headerLine.getPoints().set(1, secondPoint);
			this.changed = true;
		}

		return getQueryInstance();
	}

	public boolean hasChanged(boolean previousState)
	{
		return this.changed || previousState;
	}

	/**
	 * Add a width offset to the GA
	 * 
	 * @param widthOffset
	 * @return
	 */
	public Q addWidthOffset(final int widthOffset)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setWidth(sizeAndLocation.getWidth() + widthOffset);
			}
		});

		return getQueryInstance();
	}

	/**
	 * Sets the GAs width to n
	 * 
	 * @param width
	 * @return
	 */
	public Q setWidth(final int width)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setWidth(width);
			}
		});

		return getQueryInstance();
	}

	/**
	 * Sets the GAs height to n
	 * 
	 * @param height
	 * @return
	 */
	public Q setHeight(final int height)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setHeight(height);
			}
		});

		return getQueryInstance();
	}

	/**
	 * Set the GA height to the character height of the font used by
	 * <code>text</code>
	 * 
	 * @param text
	 * @return
	 */
	public Q setHeight(Text text)
	{
		setHeight(GraphitiUi.getUiLayoutService().calculateTextSize("X", text.getFont()).getHeight());

		return getQueryInstance();
	}

	/**
	 * Add a width factor to the GAs width
	 * 
	 * @param widthFactor
	 * @return
	 */
	public Q addWidthFactor(final double widthFactor)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setWidth((int) (sizeAndLocation.getWidth() * widthFactor));
			}
		});

		return getQueryInstance();
	}

	/**
	 * Add a height factor to the GAs height
	 * 
	 * @param widthFactor
	 * @return
	 */
	public Q addHeightFactor(final double heightFactor)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setHeight((int) (sizeAndLocation.getHeight() * heightFactor));
			}
		});

		return getQueryInstance();
	}

	/**
	 * Adjusts the GA size an location assuming the parent GA is divided in
	 * <code>columnCount</code> columns and the GA is placed in column number
	 * <code>column</code>
	 * 
	 * @param columnCount
	 * @param column
	 * @return
	 */
	public Q setColumn(final int columnCount, final int column)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{

				sizeAndLocation.setWidth(parentSizeAndLocation.getWidth() / columnCount);
				sizeAndLocation.setX(sizeAndLocation.getWidth() * column);

			}
		});

		return getQueryInstance();
	}

	/**
	 * Centers the GA horizontal relative to the parent GA
	 * 
	 * @return
	 */
	public Q centerHorizontal()
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setX((parentSizeAndLocation.getWidth() - sizeAndLocation.getWidth()) / 2);
			}
		});

		return getQueryInstance();
	}

	/**
	 * Centers the GA vertical relative to the parent GA
	 * 
	 * @return
	 */
	public Q centerVertical()
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setY((parentSizeAndLocation.getHeight() - sizeAndLocation.getHeight()) / 2);
			}
		});

		return getQueryInstance();
	}

	/**
	 * Sets the x coordinate
	 * 
	 * @param x
	 * @return
	 */
	public Q setX(final int x)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setX(x);
			}
		});

		return getQueryInstance();
	}

	/**
	 * Sets the y coordinate
	 * 
	 * @param y
	 * @return
	 */
	public Q setY(final int y)
	{
		this.modificationSteps.add(new ISizeAndLocationModifier()
		{
			@Override
			public void modifySizeAndLocation(SizeAndLocation parentSizeAndLocation, SizeAndLocation sizeAndLocation)
			{
				sizeAndLocation.setY(y);
			}
		});

		return getQueryInstance();
	}

	protected boolean isAbsolute()
	{
		return this.absolute;
	}

	protected void setAbsolute(boolean absolute)
	{
		this.absolute = absolute;
	}

}
