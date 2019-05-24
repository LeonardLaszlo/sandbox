package com.topdesk.cases.tictactoe.yoursolution.minimax;

class Score implements Comparable<Score> {
    private final int x;
    private final int y;
    private final int score;

    Score(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public int compareTo(Score o) {
        return this.score - o.score;
    }
}
