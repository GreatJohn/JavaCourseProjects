package com.jcourse.bogdanov.sort;

public class WordCounter {
    private final String word;
    private final int counter;

    public WordCounter(String word, int counter) {
        this.word = word;
        this.counter = counter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() == this.getClass()) {
            WordCounter dt = (WordCounter) obj;
            if ((this.word.compareTo(dt.word) == 0) && (this.counter == dt.counter)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.word.hashCode() + this.counter * 133;
    }

    public String getWord() {
        return word;
    }

    public int getCounter() {
        return counter;
    }
}
