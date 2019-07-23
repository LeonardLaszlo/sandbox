package sandbox.interview.hospital;

import java.util.Arrays;

class Solution {
    public int solution(int[] A, int Y) {
        // write your code in Java
        if (A == null || A.length == 1) return 0;
        int N = A.length;
        int[] bestSequences = new int[N];
        int howManyNailHaveCurrentHeightSoFar = 1;
        for (int i = 0; i < N; i++) {
            if (i > 0 && A[i - 1] == A[i]) {
                howManyNailHaveCurrentHeightSoFar++;
            } else {
                howManyNailHaveCurrentHeightSoFar = 1;
            }
            if (i + Y < N) {
                bestSequences[i] = Y + howManyNailHaveCurrentHeightSoFar;
            } else {
                bestSequences[i] = (N - i - 1) + howManyNailHaveCurrentHeightSoFar;
            }
        }
        return Arrays.stream(bestSequences).max().orElse(0);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution(new int[]{1, 1, 3, 3, 3, 4, 5, 5, 5, 5}, 2));
        System.out.println(sol.solution(new int[]{}, 2));
        System.out.println(sol.solution(new int[]{1}, 3));
        System.out.println(sol.solution(new int[]{1}, 1));
        System.out.println(sol.solution(new int[]{1, 3}, 2));
        System.out.println(sol.solution(new int[]{1, 2, 3, 4}, 2));
    }
}