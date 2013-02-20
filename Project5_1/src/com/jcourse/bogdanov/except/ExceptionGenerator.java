package com.jcourse.bogdanov.except;

public interface ExceptionGenerator {
    void generateNullPointerException();
    void generateClassCastException();
    void generateNumberFormatException();
    void generateStackOverflowError();
    void generateOutOfMemoryError();
    void generateMyExceptionException(String message) throws MyException;
}
