package com.topdesk.cases.tictactoe.yoursolution.minimax;

import java.util.SortedSet;
import java.util.TreeSet;

public class Board {
    private final BoardCell[][] gameBoard;
    private BoardCell player;

    public Board(BoardCell[][] board) {
        this.gameBoard = boardDeepCopy(board);
        validateGameBoard();
        determinePlayer();
    }

    public Suggestion getSuggestion() {
        SortedSet<Score> scores = new TreeSet<>();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoard[i][j].equals(BoardCell.EMPTY)) {
                    BoardCell[][] playerBoard = boardDeepCopy(gameBoard);
                    playerBoard[i][j] = getPlayer();
                    Score score = new Score(i, j, miniMax(playerBoard, 1));
                    scores.add(score);
                }
            }
        }
        Score score = scores.last();
        return new Suggestion(score.getX(), score.getY(), getPlayer());
    }

    private boolean didUserWin(BoardCell[][] board, BoardCell user) {

        // horizontal lines
        if (board[0][0].equals(user) && board[0][1].equals(user) && board[0][2].equals(user)) return true;
        if (board[1][0].equals(user) && board[1][1].equals(user) && board[1][2].equals(user)) return true;
        if (board[2][0].equals(user) && board[2][1].equals(user) && board[2][2].equals(user)) return true;
        // vertical lines
        if (board[0][0].equals(user) && board[1][0].equals(user) && board[2][0].equals(user)) return true;
        if (board[0][1].equals(user) && board[1][1].equals(user) && board[2][1].equals(user)) return true;
        if (board[0][2].equals(user) && board[1][2].equals(user) && board[2][2].equals(user)) return true;
        // diagonal lines
        if (board[0][0].equals(user) && board[1][1].equals(user) && board[2][2].equals(user)) return true;
        if (board[0][2].equals(user) && board[1][1].equals(user) && board[2][0].equals(user)) return true;

        return false;
    }

    private boolean didPlayerWin(BoardCell[][] board) {
        return this.didUserWin(board, getPlayer());
    }

    private boolean didOpponentWin(BoardCell[][] board) {
        return this.didUserWin(board, getOpponent());
    }

    private boolean isBoardFull(BoardCell[][] board) {
        for (BoardCell[] row : board) {
            for (BoardCell cell : row) {
                if (cell.equals(BoardCell.EMPTY)) return false;
            }
        }
        return true;
    }

    private int score(BoardCell[][] board, int depth) {
        if (didPlayerWin(board))
            return 10 - depth;
        else if (didOpponentWin(board))
            return depth - 10;
        else
            return 0;
    }

    private BoardCell[][] boardDeepCopy(BoardCell[][] board) {
        if (board.length != 3) throw new RuntimeException("The board size is wrong!");
        BoardCell[][] newBoard = new BoardCell[3][3];

        for (int i = 0; i < board.length; i++) {
            if (board[i].length != 3) throw new RuntimeException("The board size is wrong!");
            System.arraycopy(board[i], 0, newBoard[i], 0, newBoard.length);
        }

        return newBoard;
    }

    private BoardCell changeTurn(BoardCell user) {
        if (user.equals(BoardCell.X))
            return BoardCell.O;
        else
            return BoardCell.X;
    }

    private Integer miniMax(BoardCell[][] board, int depth) {
        if (isBoardFull(board)) return score(board, depth);
        if (didPlayerWin(board)) return score(board, depth);
        if (didOpponentWin(board)) return score(board, depth);
        depth++;

        BoardCell turn;
        SortedSet<Integer> scores = new TreeSet<>();

        if (depth % 2 == 0)
            turn = getOpponent();
        else
            turn = getPlayer();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(BoardCell.EMPTY)) {
                    BoardCell[][] playerBoard = boardDeepCopy(board);
                    playerBoard[i][j] = turn;
                    Integer score = miniMax(playerBoard, depth);
                    scores.add(score);
                }
            }
        }

        if (turn.equals(player))
            return scores.last();
        else
            return scores.first();
    }

    private void validateGameBoard() {
        int xCounter = 0;
        int oCounter = 0;

        for (BoardCell[] row : gameBoard) {
            for (BoardCell cell : row) {
                switch (cell) {
                    case X:
                        xCounter++;
                        break;
                    case O:
                        oCounter++;
                        break;
                    case EMPTY:
                        break;
                    default:
                        throw new IllegalStateException("Invalid cell state!");
                }
            }
        }

        if (xCounter > 4) throw new IllegalStateException("Too many X's!");
        if (oCounter > 4) throw new IllegalStateException("Too many O's!");
        int difference = xCounter - oCounter;
        if (difference < 0 || difference > 1)
            throw new IllegalStateException("The difference of X's and O's is not right!");
        if (didUserWin(gameBoard, BoardCell.X)) throw new IllegalStateException("Game is over!");
        if (didUserWin(gameBoard, BoardCell.O)) throw new IllegalStateException("Game is over!");
    }

    private void determinePlayer() {
        int xCounter = 0;
        int oCounter = 0;

        for (BoardCell[] row : gameBoard) {
            for (BoardCell cell : row) {
                switch (cell) {
                    case X:
                        xCounter++;
                        break;
                    case O:
                        oCounter++;
                        break;
                    default:
                        break;
                }
            }
        }

        int difference = xCounter - oCounter;

        if (difference == 0)
            player = BoardCell.X;
        else
            player = BoardCell.O;
    }

    private BoardCell getPlayer() {
        if (player != null)
            return player;
        else
            throw new IllegalStateException("Player was not set!");
    }

    private BoardCell getOpponent() {
        return changeTurn(player);
    }
}
