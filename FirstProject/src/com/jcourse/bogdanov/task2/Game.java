package com.jcourse.bogdanov.task2;

import java.io.*;
import java.util.*;

public class Game {
    int start = 1;
    int end = 100;
    int attempts = 8;

    public boolean setRange(int rangeLast){// 1..rangeLast, default 1..100
        if (rangeLast>start) {
            end = rangeLast;
            return true;
        }
        return false;
    }

    public boolean setRange(int rangeFirst,int rangeLast){ // rangeFirst..rangeLast, default 1..100
        if (rangeFirst<rangeLast){
            start = rangeFirst;
            end = rangeLast;
        }
        return false;
    }

    public boolean setAttempts(int inAttempts){ //default 8
        if (inAttempts>0){
            attempts = inAttempts;
        }
        return false;
    }

    public void startGame() throws IOException {getImplementation().startImp();} //starts implementation

    private Implementation getImplementation(){return new Implementation();}

    private class Implementation {
        private Random rd = new Random();
        private int secretNum = rd.nextInt(end - start) + 1;

        private void startImp() throws IOException {
            Random rd = new Random();
            int secretNum = rd.nextInt(end - start) + 1;
            System.out.println("Choose number from " + start + " to " + end + "!!!");
            for (int i = 0; i < 8; i++) {
                Integer userNum = null;
                while (userNum == null){//if input not a number
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        String s;
                        if ((s = br.readLine()) != null && s.length() != 0) userNum = Integer.parseInt(s);
                        if (userNum == secretNum) {
                            System.out.println("Correct!!!");
                            return;
                        }
                        if (userNum < secretNum) System.out.println("Bigger!!!");
                        else System.out.println("Lesser!!!");
                    }catch (NumberFormatException e){
                        System.out.println("This isn't a number, try again!!!");
                    }
                }
            }

            System.out.println("You fail, correct number is " + secretNum);
            return;
        }

    }

}


