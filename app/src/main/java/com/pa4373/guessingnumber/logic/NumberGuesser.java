package com.pa4373.guessingnumber.logic;

import java.util.Random;

public class NumberGuesser {

    public enum Outcome {
        LARGER, SMALLER, MATCH
    }

    private Random randomNumGenerator;
    private Integer target;
    private Integer maxBound;

    public NumberGuesser(Integer maxBound) {
        this.maxBound = maxBound;
        this.reset();
    }

    public Outcome guess(Integer guessNumber) {
        if(guessNumber > this.target) {
            return Outcome.SMALLER;
        } else if (guessNumber < this.target) {
            return Outcome.LARGER;
        } else {
            return Outcome.MATCH;
        }
    }

    public void reset() {
        this.randomNumGenerator = new Random();
        this.target = this.randomNumGenerator.nextInt(this.maxBound);
    }
}
