package kg.apc.jmeter.charting;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author apc
 */
public abstract class AbstractGraphRow
{
   /**
    *
    */
   public static final int MARKER_SIZE_NONE = 0;
   /**
    *
    */
   public static final int MARKER_SIZE_SMALL = 2;
   /**
    *
    */
   public static final int MARKER_SIZE_BIG = 4;
   /**
    *
    */
   public static final float LINE_THICKNESS_BIG = 3.0f;
   /**
    *
    */


   /*
    * rows types
    */
   public static final int ROW_AVERAGES = 0;
   public static final int ROW_EXACT_VALUES = 1;
   public static final int ROW_OVERALL_AVERAGES = 2;
   public static final int ROW_PERCENTILES = 3;
   public static final int ROW_SUM_VALUES = 4;
   public static final int ROW_ROLLING_SUM_VALUES = 5;

   protected boolean drawLine = false;
   private boolean drawValueLabel = false;
   private boolean showInLegend = true;
   private boolean drawThickLines = false;
   /**
    *
    */
   protected int markerSize = MARKER_SIZE_NONE;
   /**
    *
    */
   protected Color color = Color.BLACK;
   /**
    *
    */
   protected String label = "";
   /**
    *
    */
   protected long maxX = Long.MIN_VALUE;
   /**
    *
    */
   protected long minX = Long.MAX_VALUE;
   /**
    *
    */
   private boolean drawOnChart = true;
   private boolean drawBar = false;
   private int granulation = 0;

   public void setDrawThickLines(boolean isThickLine)
   {
      drawThickLines = isThickLine;
   }

   public boolean isDrawThickLines()
   {
      return drawThickLines;
   }

   /**
    *
    * @param b
    */
   public void setDrawLine(boolean b)
   {
      drawLine = b;
   }

   /**
    *
    * @param aMarkerSize
    */
   public void setMarkerSize(int aMarkerSize)
   {
      markerSize = aMarkerSize;
   }

   /**
    * @return the drawLine
    */
   public boolean isDrawLine()
   {
      return drawLine;
   }

   /**
    * @return the markerSize
    */
   public int getMarkerSize()
   {
      return markerSize;
   }

   /**
    *
    * @return
    */
   public Color getColor()
   {
      return color;
   }

   /**
    *
    * @param nextColor
    */
   public void setColor(Color nextColor)
   {
      color = nextColor;
   }

   /**
    * @return the label
    */
   public String getLabel()
   {
      return label;
   }

   /**
    * @param label the label to set
    */
   public void setLabel(String label)
   {
      this.label = label;
   }

   /**
    * @return the maxX
    */
   public long getMaxX()
   {
      return maxX;
   }

   /**
    * @return the exact maxY, taking in account maxPoint limit
    */
   public double[] getMinMaxY(int maxPoints)
   {
      int factor;
      double[] minMax = new double[2];
      minMax[0] = Double.MAX_VALUE;
      minMax[1] = 0;
      Entry<Long, AbstractGraphPanelChartElement> element;

      if (maxPoints > 0)
      {
          factor = this.size() / maxPoints;
          if (factor < 1) factor = 1;
      } else
      {
          factor = 1;
      }

      Iterator<Entry<Long, AbstractGraphPanelChartElement>> it = this.iterator();

      double calcY;
      boolean valid;

      while (it.hasNext())
      {
          calcY = 0;
          valid = true;

          if (factor == 1)
          {
              element = it.next();
              AbstractGraphPanelChartElement elt = (AbstractGraphPanelChartElement) element.getValue();
              calcY = elt.getValue();
          } else {
              for (int i = 0; i < factor; i++)
              {
                  if (it.hasNext())
                  {
                      element = it.next();
                      calcY = calcY + ((AbstractGraphPanelChartElement) element.getValue()).getValue();
                  } else {
                      valid = false;
                  }
              }
              calcY = calcY / factor;
          }

          if(valid)
          {
              if(minMax[0] > calcY) minMax[0] = calcY;
              if(minMax[1] < calcY) minMax[1] = calcY;
          }
       }
      return minMax;
   }

   /**
    * @return the minX
    */
   public long getMinX()
   {
      return minX;
   }

   /**
    *
    * @param xVal
    * @param yVal
    */
   public void add(long xVal, double yVal)
   {
      if (xVal > maxX)
      {
         maxX = xVal;
      }
      if (xVal < minX)
      {
         minX = xVal;
      }
   }

   /**
    *
    * @return
    */
   public abstract Iterator<Entry<Long, AbstractGraphPanelChartElement>> iterator();

   /**
    * @return the drawValueLabel
    */
   public boolean isDrawValueLabel()
   {
      return drawValueLabel;
   }

   /**
    * @param drawValueLabel the drawValueLabel to set
    */
   public void setDrawValueLabel(boolean drawValueLabel)
   {
      this.drawValueLabel = drawValueLabel;
   }

   /**
    * @return the showInLegend
    */
   public boolean isShowInLegend()
   {
      return showInLegend;
   }

   /**
    * @param showInLegend the showInLegend to set
    */
   public void setShowInLegend(boolean showInLegend)
   {
      this.showInLegend = showInLegend;
   }

   /**
    * @return the drawOnChart
    */
   public boolean isDrawOnChart()
   {
      return drawOnChart;
   }

   /**
    * @param drawOnChart the drawOnChart to set
    */
   public void setDrawOnChart(boolean drawOnChart)
   {
      this.drawOnChart = drawOnChart;
   }

   public void setDrawBar(boolean b)
   {
      this.drawBar = b;
   }

   /**
    * @return the drawBar
    */
   public boolean isDrawBar()
   {
      return drawBar;
   }

   /**
    * @return the granulation value for drawbar width
    */
   public int getGranulationValue()
   {
      return granulation;
   }

   /**
    * set the granulation value for drawbar width
    */
   public void setGranulationValue(int value)
   {
      this.granulation = value;
   }

   /**
     * For bar chart x axis too big prevention. Must be overridden if necessary.
     * @param excludeOutOfRangeValues
     */
    public void setExcludeOutOfRangeValues(boolean excludeOutOfRangeValues)
    {
    }

    /**
     * Needed for csv export.
     * @param value the key to get the element
     * @return the corresponding element
     */
    public abstract AbstractGraphPanelChartElement getElement(long value);

    public abstract int size();
}
