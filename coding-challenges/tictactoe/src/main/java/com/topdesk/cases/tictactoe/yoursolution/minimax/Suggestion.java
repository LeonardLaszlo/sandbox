package com.topdesk.cases.tictactoe.yoursolution.minimax;

public class Suggestion {
    private final int x;
    private final int y;
    private final BoardCell player;

    public Suggestion(int x, int y, BoardCell player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BoardCell getPlayer() {
        return player;
    }
}
