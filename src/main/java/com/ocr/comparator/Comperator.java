package com.ocr.comparator;

import com.ocr.ocr.showOutput;

public class Comperator {

    static char alphabet = 'A';
    static int rw = 0;
    static int a[][] = new int[8][8];
    static int count = 0;
    int max = 0;
    int i = 0;
    int count1 = 0;
    int more[] = new int[52];
    static char m1;
    int count0=0;

    public void mymethod(int arry[][], int arry2[][], char ch) {

        System.out.println("In comperator 8*8 class for character " + ch);




        count = 0;
        count0=0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              
                    if (arry[i][j] == arry2[i][j]) {
                        count++;

                    }
             

            }
        }
        more[i] = count;
        i++;

        if (max < count) {
            max = count;
            m1 = ch;

        }


        System.out.println("matched  with " + ch + "  one : " + count + "  times and zero : "+count0+" times");
       

    }

    public void maxmatch(int nwidth, int nheight,int xpos,int ypos) {

        for (i = 0; i < 52; i++) {
//        System.out.println("printing count value: "+i+" : "+more[i]);
            if (max == more[i]) {
                count1++;
            }
        }
        if (count1 > 1) {
            System.out.println("matched with more then one in 8*8  ");
            String st="matched with more then one in 8x8.Please check in 16x16 ";
            showOutput so=new showOutput();
           so.displayOutput(st);
            //makearray16(h, nwidth, nheight,0,0);

        } else {
            System.out.println("matched with : " + m1 + " count 1 :" + count1);
            String st=Character.toString(m1);
           showOutput so=new showOutput();
           so.displayOutput(st);
        }
         
            
        
    }
}
