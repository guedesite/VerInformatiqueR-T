package fr.guedesite.vinfo.utils;

import java.util.Arrays;

public class bruteUtils {

	
	public static void reset() {
		alphabet = new char[0];
		alphabetLength = 0;
		indices = new int[0];
		combination = new char[0];
		
		String alphabet2 = LOWER_CASE_ALPHABET;
		
		alphabet = alphabet2.toCharArray();
	    alphabetLength = alphabet2.length();

	    indices = new int[1];
	    combination = new char[1];
	}
	
	  private static char[] alphabet;
	  private static int alphabetLength;
	  private static int[] indices;
	  private static char[] combination;
	  
	  private static final String NUMERIC_ALPHABET = "0123456789";
	  private static final String LOWER_CASE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	  private static final String UPPER_CASE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String computeNextCombination() {
	    combination[0] = alphabet[indices[0]];
	    String nextCombination = String.valueOf(combination);

	    if (indices[0] < alphabetLength - 1) {
	      indices[0]++;
	    } else {
	      for (int i = 0; i < indices.length; i++) {
	        if (indices[i] < alphabetLength - 1) {
	          indices[i]++;
	          combination[i] = alphabet[indices[i]];
	          break;
	        } else {
	          indices[i] = 0;
	          combination[i] = alphabet[indices[i]];

	          if (i == indices.length - 1) {
	            indices = Arrays.copyOf(indices, indices.length + 1);
	            combination = Arrays.copyOf(combination, combination.length + 1);
	            combination[combination.length - 1] = alphabet[indices[indices.length - 1]];
	            break;
	          }
	        }
	      }
	    }

	    return nextCombination;
	  }
}
