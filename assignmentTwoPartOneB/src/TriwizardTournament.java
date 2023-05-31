import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Structure to represent a position in the labyrinth
class Position {
    int row;
    int col;

    Position(int r, int c) {
        row = r;
        col = c;
    }
}

// Structure to represent a wizard
class Wizard {
    Position position;
    int speed;

    Wizard(Position pos, int spd) {
        position = pos;
        speed = spd;
    }
}

public class TriwizardTournament {
    // Function to check if a position is within the labyrinth bounds
    static boolean isValidPosition(Position pos, int rows, int cols) {
        return pos.row >= 0 && pos.row < rows && pos.col >= 0 && pos.col < cols;
    }

    // Function to perform breadth-first search (BFS) to find the shortest path to the exit
    static int findShortestPath(List<List<Character>> labyrinth, Position start) {
        int rows = labyrinth.size();
        int cols = labyrinth.get(0).size();
        int[][] visited = new int[rows][cols];
        Queue<Position> queue = new LinkedList<>();

        queue.offer(start);
        visited[start.row][start.col] = 1;

        int[] dr = {-1, 1, 0, 0};  // Row offsets for neighbors (up, down, left, right)
        int[] dc = {0, 0, -1, 1};  // Column offsets for neighbors (up, down, left, right)

        while (!queue.isEmpty()) {
            Position currPos = queue.poll();

            // Check if the current position is the exit
            if (labyrinth.get(currPos.row).get(currPos.col) == 'E') {
                return visited[currPos.row][currPos.col] - 1;
            }

            // Explore the neighboring positions
            for (int i = 0; i < 4; i++) {
                int newRow = currPos.row + dr[i];
                int newCol = currPos.col + dc[i];
                Position neighbor = new Position(newRow, newCol);

                if (isValidPosition(neighbor, rows, cols) && labyrinth.get(neighbor.row).get(neighbor.col) != '#' && visited[neighbor.row][neighbor.col] == 0) {
                    queue.offer(neighbor);
                    visited[neighbor.row][neighbor.col] = visited[currPos.row][currPos.col] + 1;
                }
            }
        }

        // If the exit is not reachable, return a large value
        return rows * cols;
    }

    // Function to predict the winner of the Triwizard Tournament
    static int predictWinner(List<List<Character>> labyrinth, List<Wizard> wizards) {
        int winner = -1;
        int shortestTime = Integer.MAX_VALUE;

        for (int i = 0; i < wizards.size(); i++) {
            Wizard wizard = wizards.get(i);
            int time = findShortestPath(labyrinth, wizard.position);
            int totalTime = time / wizard.speed;  // Calculate total time based on wizard's speed

            if (totalTime < shortestTime) {
                shortestTime = totalTime;
                winner = i;
            }
        }

        return winner;
    }

    public static void main(String[] args) {
        // Example labyrinth
        List<List<Character>> labyrinth = new ArrayList<>();
        labyrinth.add(List.of('S', '.', '.', '.', '#', '.', '.'));
        labyrinth.add(List.of('.', '#', '#', '.', '#', '#', '.'));
        labyrinth.add(List.of('.', '.', '#', '.', '.', '.', '.'));
        labyrinth.add(List.of('.', '#', '#', '#', '#', '#', '.'));
        labyrinth.add(List.of('.', '.', '.', '.', '.', '#', 'E'));

        // Example wizards and their speeds
        List<Wizard> wizards = new ArrayList<>();
        wizards.add(new Wizard(new Position(0, 0), 1));  // Wizard 1: Starting position (0, 0) with speed 2
        wizards.add(new Wizard(new Position(1, 1), 1));  // Wizard 2: Starting position (2, 2) with speed 3
        wizards.add(new Wizard(new Position(1, 6), 1));  // Wizard 3: Starting position (3, 1) with speed 1

        int winner = predictWinner(labyrinth, wizards);

        if (winner != -1) {
            System.out.println("Wizard " + (winner + 1) + " wins the Triwizard Tournament!");
        } else {
            System.out.println("No winner. The exit is not reachable for any wizard.");
        }
    }
}
