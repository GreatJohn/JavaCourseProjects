package com.jcourse.bogdanov.testtasks;


import com.jcourse.bogdanov.except.ExceptionGenerator;
import com.jcourse.bogdanov.except.MyException;

public class Demo implements ExceptionGenerator {

    public static void main(String[] args) {
        Demo d = new Demo();
        //d.generateNullPointerException();
        //d.generateClassCastException();
        //d.generateNumberFormatException();
        //d.generateStackOverflowError();
        //d.generateOutOfMemoryError();
        try {
            d.generateMyExceptionException("message text");
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateNullPointerException() {
        int i = 0;
        Integer iI = null;
        i = iI;
    }

    @Override
    public void generateClassCastException() {
        Object x = new Integer(0);
        System.out.println((String)x);
    }

    @Override
    public void generateNumberFormatException() {
        Double d = Double.parseDouble("string");
    }

    @Override
    public void generateStackOverflowError(){
        Demo d = new Demo();
        d.generateStackOverflowError();
    }

    @Override
    public void generateOutOfMemoryError() {
        while (true) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    double[] arr = new double[10000000];
                }
            });
            t.start();
        }
    }

    @Override
    public void generateMyExceptionException(String message) throws MyException {
        throw new MyException(message);
    }
}
