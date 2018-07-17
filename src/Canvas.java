import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example.
 *
 * @author: Bruce Quig
 * @author: Michael Kölling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas
{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas()
    {
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 1000, 1000,
                    new Color(23, 74, 99));
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List objects;
    private HashMap shapes;

    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColour)
    {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList();
        shapes = new HashMap();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false)
     */
    public void setVisible(boolean visible)
    {
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
    // Note: this is a slightly backwards way of maintaining the shape
    // objects. It is carefully designed to keep the visible shape interfaces
    // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape)
    {
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(referenceObject, shape, color));
        redraw();
    }

    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased
     */
    public void erase(Object referenceObject)
    {
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param  newColour   the new colour for the foreground of the Canvas
     */
    public void setForegroundColor(int alpha, String colorString)
    {
        if(colorString.equals("red"))
            graphic.setColor(new Color(255, 0, 0, alpha));
        else if(colorString.equals("black"))
            graphic.setColor(new Color(0, 0, 0, alpha));
        else if(colorString.equals("blue"))
            graphic.setColor(new Color(0, 0, 255, alpha));
        else if(colorString.equals("yellow"))
            graphic.setColor(new Color(255, 255, 0, alpha));
        else if(colorString.equals("green"))
            graphic.setColor(new Color(0, 255, 0, alpha));
        else if(colorString.equals("magenta"))
            graphic.setColor(new Color(255, 0, 255, alpha));
        else if(colorString.equals("white"))
            graphic.setColor(new Color(255, 255, 255, alpha));
        else if(colorString.equals("gray"))
            graphic.setColor(new Color(128, 128, 128, alpha));
        else if(colorString.equals("orange"))
            graphic.setColor(new Color(255, 165, 0, alpha));
        else if(colorString.equals("pink"))
            graphic.setColor(new Color(255, 192, 203, alpha));
        else if(colorString.equals("dark gray"))
            graphic.setColor(new Color(105, 105, 105, alpha));
        else if(colorString.equals("light gray"))
            graphic.setColor(new Color(211, 211, 211, alpha));
        else if(colorString.equals("cyan"))
            graphic.setColor(new Color(0, 255, 255, alpha));
        else if(colorString.equals("darkBlue"))
            graphic.setColor(new Color(23, 74, 99, alpha));
        else if(colorString.equals("brown"))
            graphic.setColor(new Color(70, 42, 18, alpha));
        else
            graphic.setColor(Color.black);
    }


    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number
     */
    public void wait(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (Exception e)
        {
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw()
    {
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
            ((ShapeDescription)shapes.get(i.next())).draw(graphic);
        }
        canvas.repaint();
    }

    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase()
    {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel
    {
        public void paint(Graphics g)
        {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription
    {
        private Object obj;
        private Shape shape;
        private String colorString;

        public ShapeDescription(Object obj, Shape shape, String color)
        {
            this.obj = obj;
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic)
        {


            double rotation = 0;
            int alpha = 0;
            if (obj instanceof Square)
            {
                rotation = Math.toRadians(((Square)obj).getRotation());
                alpha = ((Square)obj).getTransparency();
            }
            else if (obj instanceof Triangle)
            {
                rotation = Math.toRadians(((Triangle)obj).getRotation());
                alpha = ((Triangle)obj).getTransparency();
            }
            else if (obj instanceof Rect)
            {
                rotation = Math.toRadians(((Rect)obj).getRotation());
                alpha = ((Rect)obj).getTransparency();
            }
            else if (obj instanceof Circle)
            {
                alpha = ((Circle)obj).getTransparency();
            }

            setForegroundColor((100 - alpha) * 255 / 100, colorString);

            Rectangle bounds = shape.getBounds();
            graphic.rotate(rotation, bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);
            graphic.fill(shape);
            graphic.rotate(-rotation, bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);
        }
    }

}
