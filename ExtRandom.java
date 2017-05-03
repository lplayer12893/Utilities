package edu.labelgeneration;

import java.util.Random;

public class ExtRandom extends Random{

	private static final long serialVersionUID = 1L;
	
	public ExtRandom(){
		super();
	}
	
	public boolean nextBoolean(double prob_true){
		int guess = nextInt(100);
		
		if(guess >= 0 && guess < (int)(100*prob_true)){
			return true;
		}
		return false;
	}
}
