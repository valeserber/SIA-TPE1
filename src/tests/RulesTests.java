package tests;

import gps.api.GPSState;
import gps.exception.NotAppliableException;
import gps.model.GameRule;
import gps.model.GameState;

public class RulesTests {
	// @org.junit.Test(expected = NotAppliableException.class)
	// public void testEvalRuleOccupied() throws NotAppliableException {
	// GameRule rule = new GameRule(1, 0, 2);
	// int[][] board = { { 1, 0, 2, 1, 1, 2, 1, 0 },
	// { 2, 1, 0, 1, 2, 1, 0, 1 }, { 1, 0, 2, 2, 1, 2, 1, 2 },
	// { 2, 1, 2, 1, 0, 0, 2, 2 }, { 0, 2, 1, 2, 2, 1, 2, 1 },
	// { 1, 2, 1, 2, 0, 2, 1, 1 }, { 1, 0, 2, 1, 2, 1, 2, 2 },
	// { 2, 1, 1, 2, 1, 1, 2, 2 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }
	//
	// @org.junit.Test(expected = NotAppliableException.class)
	// public void testEvalRuleIsRowOrColFull() throws NotAppliableException {
	// GameRule rule = new GameRule(1, 1, 1);
	// int[][] board = {
	// { 1, 1, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 2, 2, 0, 2, 0, 2 },
	// { 0, 0, 0, 0, 0, 0, 0, 1 },
	// { 0, 1, 2, 0, 0, 1, 0, 0 },
	// { 0, 0, 0, 0, 1, 0, 0, 2 },
	// { 1, 1, 0, 0, 1, 0, 2, 2 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 1, 0, 0, 0, 2, 2, 0 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }
	//
	// @org.junit.Test(expected = NotAppliableException.class)
	// public void testEvalRuleHasThreeAdjacentTilesInRow() throws
	// NotAppliableException {
	// GameRule rule = new GameRule(2, 1, 1);
	// int[][] board = {
	// { 1, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 2, 2, 0, 0, 0, 2 },
	// { 0, 0, 0, 0, 0, 0, 0, 1 },
	// { 0, 1, 2, 0, 0, 1, 0, 0 },
	// { 0, 0, 0, 0, 1, 0, 0, 2 },
	// { 1, 1, 0, 0, 1, 0, 2, 2 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 1, 0, 0, 0, 2, 2, 0 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }
	//
	// @org.junit.Test(expected = NotAppliableException.class)
	// public void testEvalRuleHasThreeAdjacentTilesInCol() throws
	// NotAppliableException {
	// GameRule rule = new GameRule(2, 6, 7);
	// int[][] board = {
	// { 1, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 2, 2, 0, 0, 0, 2 },
	// { 0, 0, 0, 0, 0, 0, 0, 1 },
	// { 0, 1, 2, 0, 0, 1, 0, 0 },
	// { 0, 0, 0, 0, 1, 0, 0, 2 },
	// { 1, 1, 0, 0, 1, 0, 2, 2 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 1, 0, 0, 0, 2, 2, 0 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }
	//
	// @org.junit.Test(expected = NotAppliableException.class)
	// public void testEvalRuleHasThreeAdjacentTilesInbetween() throws
	// NotAppliableException {
	// GameRule rule = new GameRule(2, 1, 6);
	// int[][] board = {
	// { 1, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 2, 0, 0, 2, 0, 2 },
	// { 0, 0, 0, 0, 0, 0, 0, 1 },
	// { 0, 1, 2, 0, 0, 1, 0, 0 },
	// { 0, 0, 0, 0, 1, 0, 0, 2 },
	// { 1, 1, 0, 0, 1, 0, 2, 2 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 1, 0, 0, 0, 2, 2, 0 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }

//	@org.junit.Test(expected = NotAppliableException.class)
//	public void testEvalRuleBlockTailInRow() throws NotAppliableException {
//		GameRule rule = new GameRule(2, 4, 0);
//		int[][] board = { { 0, 0, 0, 0, 0, 0, 0, 0 },
//				{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
//				{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 2, 1, 2, 1, 0, 1, 2 },
//				{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
//				{ 0, 0, 0, 0, 0, 0, 0, 0 } };
//		GPSState state = new GameState(board);
//		rule.evalRule(state);
//	}

	// @org.junit.Test(expected = NotAppliableException.class)
	// public void testEvalRuleBlockTailInRowTwo() throws NotAppliableException
	// {
	// GameRule rule = new GameRule(2, 6, 3);
	// int[][] board = {
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 2, 2, 1, 0, 2, 0, 0, 1 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }

	// @org.junit.Test
	// public void testEvalRuleBlockTailInRowOkay() throws NotAppliableException
	// {
	// GameRule rule = new GameRule(2, 0, 1);
	// int[][] board = {
	// { 1, 0, 0, 2, 2, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }
	//
	// @org.junit.Test
	// public void testEvalRuleBlockTailInColOkay() throws NotAppliableException
	// {
	// GameRule rule = new GameRule(2, 1, 0);
	// int[][] board = {
	// { 1, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 2, 0, 0, 0, 0, 0, 0, 0 },
	// { 2, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }
	//
	// @org.junit.Test(expected = NotAppliableException.class)
	// public void testEvalRuleBlockTailInCol() throws NotAppliableException {
	// GameRule rule = new GameRule(2, 1, 1);
	// int[][] board = {
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 1, 0, 1, 0, 0, 0, 0, 0 },
	// { 0, 2, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0, 0 } };
	// GPSState state = new GameState(board);
	// rule.evalRule(state);
	// }

//	@org.junit.Test
//	public void testEvalRuleOkay() throws NotAppliableException {
//		GameRule rule = new GameRule(2, 1, 1);
//		int[][] board = { { 1, 2, 1, 2, 1, 1, 2, 2 },
//				{ 2, 0, 1, 1, 2, 2, 1, 1 }, { 1, 1, 2, 1, 2, 1, 2, 2 },
//				{ 2, 0, 2, 2, 1, 0, 0, 1 }, { 1, 2, 0, 2, 1, 2, 1, 2 },
//				{ 1, 2, 0, 1, 2, 0, 0, 2 }, { 2, 1, 2, 1, 2, 1, 2, 1 },
//				{ 2, 1, 2, 2, 1, 2, 1, 1 } };
//		GPSState state = new GameState(board);
//		rule.evalRule(state);
//	}
	
	@org.junit.Test
	public void testEvalRuleOkay() throws NotAppliableException {
		GameRule rule = new GameRule(1, 3, 6);
		int[][] board = 
			{{1,2,1,2,1,1,2,2},
			{2,0,0,1,0,2,1,1},
			{0,0,0,1,2,0,2,2},
			{2,0,2,2,1,0,0,1},
			{1,2,0,2,0,2,1,2},
			{1,2,0,1,2,0,0,2},
			{2,1,2,1,2,1,2,1},
			{2,1,2,2,1,2,1,1}};
		GPSState state = new GameState(board);
		rule.evalRule(state);
	}

}
