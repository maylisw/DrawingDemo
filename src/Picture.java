
/**
 * This class represents a simple picture. You can draw the picture using
 * the draw method.
 *
 * This class was written as an early example for teaching Java with BlueJ.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 1.1  (24 May 2001)  (modified 2015-08 by Garrett Shorr)
 */
public class Picture
{
    public static void main(String[] args)
    {
        Picture p = new Picture();
        p.draw();
    }

    /**
     * Draw this picture.  The colors available are: red, black, blue, yellow, green,
     * magenta, white, gray, orange, pink, dark gray, light gray, and cyan.
     * In the changeColor method, supply the color as a String in all lowercase as
     * written above.
     */
    public void draw()
    {
        Rect cake = new Rect();
        cake.setY(400);
        cake.setX(200);
        cake.changeSize(300,550);
        cake.changeColor("white");
        cake.makeVisible();

        Rect table =new Rect();
        table.setY(700);
        table.setX(0);
        table.changeSize(10000,10000);
        table.changeColor("brown");
        table.makeVisible();

        Circle candle1 = new Circle();
        candle1.setY(275);
        candle1.setX(250);
        candle1.changeColor("orange");
        candle1.makeVisible();


    }
}
