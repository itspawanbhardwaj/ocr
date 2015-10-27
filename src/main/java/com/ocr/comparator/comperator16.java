package com.ocr.comparator;

import com.ocr.ocr.showOutput;

public class comperator16 {

    static char alphabet = 'A';
    static int rw = 0;
    static int a[][] = new int[16][16];
    static int count = 0;
    int max = 0;
    int i = 0;
    int count1 = 0;
    int count001=0;
    int more[] = new int[52];
    static char m1;

    public void mymethod16(int arry[][], int arry2[][], char ch) {

        System.out.println("In comperator 16*16 class for character "+ch);




        count = 0;
        count001=0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                
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


System.out.println("matched  with " + ch +  "  one : " + count + "  times and zero :  "+count001+" times");


    }

    public void maxmatch16() {
                    System.out.println("matched with : " + m1 + " " );
                    String st=Character.toString(m1);
           showOutput so=new showOutput();
           so.displayOutput(st);
                    


//        for (i = 0; i < 52; i++) {
////        System.out.println("printing count value: "+i+" : "+more[i]);
//            if (max == more[i]) {
//                count1++;
//
//            }
//        }
//        if (count1 > 1) {
//            System.out.println("matched with more then one in 8*8  ");
//
//
//        } else {
//            System.out.println("matched with : " + m1 + " count 1 :" + count1);
//        }
    }
}
