package com.ocr.image.scan;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;

import com.ocr.database.reader.FileReadAll;

/**
 *
 * @author Windows 4
 */
public class ScanImage extends FileReadAll {


    int myimage[][] = new int[8][8];


 
   
public  void mysize(Image imaa,int width1,int height1,int x1,int y1) throws InterruptedException, Exception{
    Toolkit tool = Toolkit.getDefaultToolkit();
  

  makearray(imaa,width1,height1,x1,y1);
}

    public Image makearray(Image im,int newwidth ,int newheight,int xpos  ,int ypos ) {
        System.out.println("into scan image");
int awh=(xpos-newwidth)+17;
int ahw=(ypos-newheight)+17;
int rt=ypos;
int st=xpos;

        try {
            int[] pgPixels = new int[ahw * awh];
System.out.println(pgPixels.length);

            PixelGrabber pg = new PixelGrabber(im, 0, 0, newwidth, newheight, pgPixels, 0, newwidth);
            
            if (pg.grabPixels() && ((pg.status() & ImageObserver.ALLBITS) != 0)) {
                
                for (int y =newwidth; y < rt; y++) {
                    System.out.println("xpos="+xpos);
                    for (int x =newheight; x <st; x++) {
                    System.out.println("ypos="+ypos);

                    int i = y * newwidth + x;
                    

                    int a = (pgPixels[i] & 0xff000000) >> 24;
                        int r = (pgPixels[i] & 0x00ff0000) >> 16;
                        int g = (pgPixels[i] & 0x0000ff00) >> 8;
                        int b = (pgPixels[i] & 0x000000ff);
                        if (r <=50 && g <=50 && b <=50) {
                            int a1 = x / (awh/8);
                            int a2 = y / (ahw/8);
                             System.out.println("i="+i);
                            // System.out.println(" "+x+" "+y);
                            myimage[a2][a1] = 1;

                        }
                    }
                }
                for(int xc=0;xc<8;xc++){
                    for(int yc=0;yc<8;yc++){
                        System.out.print(" "+myimage[xc][yc]);
                    }System.out.println("");
                }
             

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     // readfile(myimage,nwidth,nheight,xpos,ypos);
        return im;
    }
}
