package jpeg;
import jcp.*;
import java.awt.*;
import java.awt.image.*;
/**
 *  ImageCombiner.java
 *  A component that reconstructs an image from its red, green, and blue
 *  color components.
 *
 *  Input(s):
 *      red - The red component of the image.
 *      green - The green component of the image.
 *      blue - The blue component of the image.
 *  Ouput(s):
 *      in - The reconstructed image.
 *
 *  @see ImageSplitter
 *  @author James S. Young, based on original source by Florian Raemy &
 *  LCAV, Swiss Federal Institute of Technology, Lausanne, Switzerland
 */

public class ImageCombiner extends SynchComponent
{
    private Port rp,gp,bp,out;

    public ImageCombiner() {
        setName("Image Combiner");
        IntMatrix temp = new IntMatrix();
        rp = addPort(true,"red");
        gp = addPort(true,"green");
        bp = addPort(true,"blue");

        rp.setType(temp.getClass());
        gp.setType(temp.getClass());
        bp.setType(temp.getClass());

        out = addPort(false,"out");
    }

     int count = 0;

     public void go(Port port) {

    count++;
        if ((count >= 3) && (rp.defined()) && (gp.defined()) && (bp.defined())) {
            Object s0 = rp.signal();
            Object s1 = gp.signal();
            Object s2 = bp.signal();

            int[][] r = ((IntMatrix)s0).get_matrix();
            int[][] g = ((IntMatrix)s1).get_matrix();
            int[][] b = ((IntMatrix)s2).get_matrix();

            emit(new IntMatrix(convertRGB(r,g,b)),out);
        count = 0;
        }
    }

    private int[][] convertRGB(int[][] R, int[][] G, int[][] B)
    {
        int imageWidth = R.length;
        int imageHeight = R[0].length;

        int array[][] = new int[imageWidth][imageHeight];

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                array[x][y]= ((0xff << 24) |
                              ((R[x][y] << 16) & 0x00ff0000) |
                              ((G[x][y] << 8) & 0x0000ff00) |
                              (B[x][y] & 0x000000ff));
            }
        }
        return array;
    }
}
