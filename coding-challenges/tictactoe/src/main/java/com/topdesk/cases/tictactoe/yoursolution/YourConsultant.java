package com.topdesk.cases.tictactoe.yoursolution;

import com.topdesk.cases.tictactoe.CellLocation;
import com.topdesk.cases.tictactoe.CellState;
import com.topdesk.cases.tictactoe.Consultant;
import com.topdesk.cases.tictactoe.GameBoard;
import com.topdesk.cases.tictactoe.yoursolution.minimax.Board;
import com.topdesk.cases.tictactoe.yoursolution.minimax.BoardCell;
import com.topdesk.cases.tictactoe.yoursolution.minimax.Suggestion;

public class YourConsultant implements Consultant {

    @Override
    public CellLocation suggest(GameBoard gameBoard) {
        Board board = new Board(adaptBoard(gameBoard));
        Suggestion suggestion = board.getSuggestion();
        System.out.println("Player: " + suggestion.getPlayer());
        return getCellLocationFromCoordinate(suggestion.getX(), suggestion.getY());
    }

    private BoardCell[][] adaptBoard(GameBoard gameBoard) {
        BoardCell[][] board = new BoardCell[3][3];
        board[0][0] = adaptBoardCell(gameBoard.getCellState(CellLocation.TOP_LEFT));
        board[0][1] = adaptBoardCell(gameBoard.getCellState(CellLocation.TOP_CENTRE));
        board[0][2] = adaptBoardCell(gameBoard.getCellState(CellLocation.TOP_RIGHT));
        board[1][0] = adaptBoardCell(gameBoard.getCellState(CellLocation.CENTRE_LEFT));
        board[1][1] = adaptBoardCell(gameBoard.getCellState(CellLocation.CENTRE_CENTRE));
        board[1][2] = adaptBoardCell(gameBoard.getCellState(CellLocation.CENTRE_RIGHT));
        board[2][0] = adaptBoardCell(gameBoard.getCellState(CellLocation.BOTTOM_LEFT));
        board[2][1] = adaptBoardCell(gameBoard.getCellState(CellLocation.BOTTOM_CENTRE));
        board[2][2] = adaptBoardCell(gameBoard.getCellState(CellLocation.BOTTOM_RIGHT));

        return board;
    }

    private CellLocation getCellLocationFromCoordinate(int x, int y) {
        if (x == 0 && y == 0) {
            return CellLocation.TOP_LEFT;
        } else if (x == 0 && y == 1) {
            return CellLocation.TOP_CENTRE;
        } else if (x == 0 && y == 2) {
            return CellLocation.TOP_RIGHT;
        } else if (x == 1 && y == 0) {
            return CellLocation.CENTRE_LEFT;
        } else if (x == 1 && y == 1) {
            return CellLocation.CENTRE_CENTRE;
        } else if (x == 1 && y == 2) {
            return CellLocation.CENTRE_RIGHT;
        } else if (x == 2 && y == 0) {
            return CellLocation.BOTTOM_LEFT;
        } else if (x == 2 && y == 1) {
            return CellLocation.BOTTOM_CENTRE;
        } else if (x == 2 && y == 2) {
            return CellLocation.BOTTOM_RIGHT;
        } else {
            throw new IllegalStateException();
        }
    }

    private BoardCell adaptBoardCell(CellState cellState) {
        BoardCell result;
        switch (cellState) {
            case OCCUPIED_BY_X:
                result = BoardCell.X;
                break;
            case OCCUPIED_BY_O:
                result = BoardCell.O;
                break;
            default:
                result = BoardCell.EMPTY;
                break;
        }
        return result;
    }
}
