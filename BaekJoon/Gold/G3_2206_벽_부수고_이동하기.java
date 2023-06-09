package BaekJoon.Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G3_2206_벽_부수고_이동하기 {
    static int N;
    static int M;
    static int min = Integer.MAX_VALUE;

    static final int[] dr = {0, 0, 1, -1};
    static final int[] dc = {1, -1, 0, 0};

    static int[][] maze;

    static boolean[][][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        maze = new int[N][M];

        for(int i=0; i<N; i++) {
            String input = br.readLine();

            for(int j=0; j<input.length(); j++)
                maze[i][j] = input.charAt(j) - '0';
        }

        BFS();

        if(min == Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(min);
    }

    static void BFS() {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(0, 0, 1, 1));

        visited = new boolean[N][M][2];
        visited[0][0][1] = true;

        while(!queue.isEmpty()) {
            Point now = queue.poll();

            if(now.r == N-1 && now.c == M-1)
                min = Math.min(min, now.distance);

            for(int d=0; d<4; d++) {
                int nextR = now.r + dr[d];
                int nextC = now.c + dc[d];

                if(outofmapCheck(nextR, nextC))
                    continue;

                if(visited[nextR][nextC][now.wallCnt])
                    continue;

                if(wallCheck(nextR, nextC)) {
                    if(now.wallCnt == 1) {
                        queue.offer(new Point(nextR, nextC, now.wallCnt-1, now.distance+1));
                        visited[nextR][nextC][now.wallCnt-1] = true;
                    }

                    continue;
                }

                queue.offer(new Point(nextR, nextC, now.wallCnt, now.distance+1));
                visited[nextR][nextC][now.wallCnt] = true;
            }
        }
    }

    static boolean outofmapCheck(int r, int c) {
        return r<0 || c<0 || r>=N || c>=M;
    }

    static boolean wallCheck(int r, int c) {
        return maze[r][c] == 1;
    }

    static class Point {
        int r;
        int c;
        int wallCnt;
        int distance;

        public Point(int r, int c, int wallCnt, int distance) {
            this.r = r;
            this.c = c;
            this.wallCnt = wallCnt;
            this.distance = distance;
        }
    }
}
