package gps.utils;


public class Board {

	private static int[][] levelOne = { 
			{ 1, 2, 2, 0, 2, 0, 2, 1 },
			{ 0, 2, 2, 1, 1, 2, 2, 1 }, 
			{ 0, 1, 0, 2, 2, 1, 1, 2 },
			{ 0, 2, 1, 0, 1, 2, 1, 1 }, 
			{ 1, 0, 2, 1, 2, 1, 2, 2 },
			{ 2, 2, 1, 0, 0, 2, 1, 0 }, 
			{ 2, 0, 1, 2, 1, 1, 2, 2 },
			{ 1, 1, 2, 2, 0, 0, 1, 2 } };
	
	private static int[][] levelTwo = { 
			{ 0, 1, 0, 0, 2, 1, 2, 1 },
			{ 0, 2, 0, 1, 2, 2, 1, 0 }, 
			{ 0, 1, 0, 2, 0, 1, 0, 0 },
			{ 0, 0, 2, 1, 2, 0, 1, 2 }, 
			{ 2, 2, 1, 2, 0, 1, 2, 1 },
			{ 2, 1, 0, 2, 1, 0, 2, 1 }, 
			{ 1, 2, 1, 1, 0, 2, 1, 2 },
			{ 1, 0, 1, 2, 1, 0, 1, 2 } };

	private static int[][] levelThree = { 
			{ 0, 2, 0, 1, 2, 0, 1, 0 },
			{ 2, 1, 0, 0, 2, 1, 2, 1 }, 
			{ 1, 2, 1, 0, 1, 2, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 2, 0, 1, 1, 2, 1, 0, 1 },
			{ 0, 1, 0, 2, 1, 2, 1, 2 }, 
			{ 1, 2, 2, 1, 0, 1, 0, 1 },
			{ 0, 1, 0, 2, 1, 1, 0, 2 } };

	private static int[][] levelFour = { 
			{ 0, 0, 0, 0, 2, 0, 2, 1 },
			{ 0, 2, 0, 0, 2, 0, 0, 0 }, 
			{ 0, 0, 0, 2, 1, 1, 2, 0 },
			{ 1, 0, 0, 1, 0, 0, 1, 0 }, 
			{ 2, 0, 1, 2, 1, 1, 2, 1 },
			{ 2, 1, 2, 2, 1, 0, 2, 0 }, 
			{ 1, 2, 1, 0, 2, 0, 1, 2 },
			{ 1, 0, 1, 0, 0, 0, 1, 0 } };
	
	private static int[][] levelFive = { 
			{ 0, 0, 1, 1, 2, 0, 0, 0 },
			{ 0, 0, 1, 0, 2, 0, 0, 0 }, 
			{ 0, 0, 2, 0, 1, 0, 0, 0 },
			{ 0, 0, 2, 1, 1, 2, 1, 0 }, 
			{ 0, 0, 1, 0, 2, 0, 2, 0 },
			{ 1, 0, 1, 0, 0, 0, 2, 0 }, 
			{ 0, 0, 2, 0, 0, 0, 1, 0 },
			{ 0, 1, 2, 1, 2, 2, 1, 2 } };
	
	public static int[][] getInitialBard(Integer boardLevel) {
		switch(boardLevel) {
		case 1:
			return levelOne;
		case 2:
			return levelTwo;
		case 3:
			return levelThree;
		case 4:
			return levelFour;
		case 5:
			return levelFive;
		default:
			throw new IllegalArgumentException();
		}
	}

}
