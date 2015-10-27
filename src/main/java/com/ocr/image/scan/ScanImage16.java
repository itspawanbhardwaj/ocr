package com.ocr.image.scan;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;

import com.ocr.database.reader.FileReadAll16;

/**
 *
 * @author Windows 4
 */
public class ScanImage16 extends FileReadAll16 {
      int nwidth ;
  int nheight;

    static int myimage[][] = new int[16][16];

    public Image makearray16(Image im,int nwidth,int nheight,int xpos,int ypos) {
int aw=nwidth+20;
int ah=nheight+20;
        try {
            int[] pgPixels = new int[aw * ah];

            PixelGrabber pg = new PixelGrabber(im, 0, 0, nwidth, nheight, pgPixels, 0, nwidth);
            int newwidth=(aw/16);
            int newheight=(ah/16);
      System.out.println("The width of image: " + aw);
  System.out.println("The height of image: " + ah);
            if (pg.grabPixels() && ((pg.status() & ImageObserver.ALLBITS) != 0)) {
                for (int y = ypos; y < nheight; y++) {
                    for (int x = xpos; x < nwidth; x++) {
                        int i = y * nwidth + x;
                        int a = (pgPixels[i] & 0xff000000) >> 24;
                        int r = (pgPixels[i] & 0x00ff0000) >> 16;
                        int g = (pgPixels[i] & 0x0000ff00) >> 8;
                        int b = (pgPixels[i] & 0x000000ff);
                        if (r <=50 && g <=50 && b <=50) {
                            int a1 = x /newwidth;
                            int a2 = y /newheight;
                           // System.out.println(" "+x+" "+y);
                            myimage[a2][a1] = 1;

                        }
                    }
                }//mymthd(myimage);
              //  im = createImage(new MemoryImageSource(width, height, pgPixels, 0, width));
                System.out.println("16*16 array representation of image");
                for (int j = 0; j <16; j++) {
                    for (int k = 0; k< 16; k++) {
                        System.out.print(" " + myimage[j][k]);
                    }
                    System.out.println();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
       readfile16(myimage);
        return im;
    }
}
