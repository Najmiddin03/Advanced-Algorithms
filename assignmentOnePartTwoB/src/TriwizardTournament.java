import java.util.*;
// TODO: fix code
public class TriwizardTournament {
    private static final int[] dx = {-1, 1, 0, 0}; // Possible movements: up, down, left, right
    private static final int[] dy = {0, 0, -1, 1};

    public static int predictWinner(char[][] labyrinth, int[] initialPositions, int[] speeds) {
        int n = labyrinth.length; // Number of rows
        int m = labyrinth[0].length; // Number of columns

        Queue<Integer> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][m];

        // Enqueue initial positions of the wizards
        for (int i = 0; i < initialPositions.length; i++) {
            int position = initialPositions[i];
            queue.offer(position);
            visited[position / m][position % m] = true;
        }

        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int currentPosition = queue.poll();
                int x = currentPosition / m;
                int y = currentPosition % m;

                // Check if the current position is the exit
                if (labyrinth[x][y] == 'E') {
                    return currentPosition; // Return the index of the winning wizard
                }

                // Explore adjacent rooms or corridors
                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    // Check if the next position is valid and not visited
                    if (isValid(nx, ny, n, m) && !visited[nx][ny] && labyrinth[nx][ny] != '#') {
                        visited[nx][ny] = true;
                        queue.offer(nx * m + ny);
                    }
                }
            }
            steps++;
        }

        return -1; // No winner found
    }

    private static boolean isValid(int x, int y, int n, int m) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }

    public static void main(String[] args) {
        char[][] labyrinth = {
                {'E', '.', '#', '#', '.', '#', '.'},
                {'.', '.', '.', '.', '.', '#', '.'},
                {'.', '#', '#', '.', '#', '#', '.'},
                {'.', '#', '.', '.', '.', '.', '.'}
        };

        int[] initialPositions = {2, 1, 0}; // Starting positions of the wizards
        int[] speeds = {1, 1, 1}; // Speeds of the wizards (corridors per minute)

        int winnerIndex = predictWinner(labyrinth, initialPositions, speeds);

        if (winnerIndex != -1) {
            System.out.println("Wizard " + winnerIndex + " wins!");
        } else {
            System.out.println("No winner found.");
        }
    }
}
