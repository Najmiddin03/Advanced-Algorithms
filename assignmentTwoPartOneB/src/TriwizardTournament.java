import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Position {
    int row;
    int col;

    Position(int r, int c) {
        row = r;
        col = c;
    }
}

class Wizard {
    Position position;
    int speed;

    Wizard(Position pos, int spd) {
        position = pos;
        speed = spd;
    }
}

public class TriwizardTournament {
    static boolean isValidPosition(Position pos, int rows, int cols) {
        return pos.row >= 0 && pos.row < rows && pos.col >= 0 && pos.col < cols;
    }

    static int findShortestPath(List<List<Character>> labyrinth, Position start) {
        int rows = labyrinth.size();
        int cols = labyrinth.get(0).size();
        int[][] visited = new int[rows][cols];
        Queue<Position> queue = new LinkedList<>();

        queue.offer(start);
        visited[start.row][start.col] = 1;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Position currPos = queue.poll();

            if (labyrinth.get(currPos.row).get(currPos.col) == 'E') {
                return visited[currPos.row][currPos.col] - 1;
            }

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

        return rows * cols;
    }

    static int predictWinner(List<List<Character>> labyrinth, List<Wizard> wizards) {
        int winner = -1;
        int shortestTime = Integer.MAX_VALUE;

        for (int i = 0; i < wizards.size(); i++) {
            Wizard wizard = wizards.get(i);
            int time = findShortestPath(labyrinth, wizard.position);
            int totalTime = time / wizard.speed;

            if (totalTime < shortestTime) {
                shortestTime = totalTime;
                winner = i;
            }
        }

        return winner;
    }

    public static void main(String[] args) {
        List<List<Character>> labyrinth = new ArrayList<>();
        labyrinth.add(List.of('.', '.', '.', '.', '#', '.', '.'));
        labyrinth.add(List.of('.', '#', '#', '.', '#', '#', '.'));
        labyrinth.add(List.of('.', '.', '#', '.', '.', '.', '.'));
        labyrinth.add(List.of('.', '#', '#', '#', '#', '#', '.'));
        labyrinth.add(List.of('.', '.', '.', '.', '.', '#', 'E'));

        List<Wizard> wizards = new ArrayList<>();
        wizards.add(new Wizard(new Position(0, 1), 1));
        wizards.add(new Wizard(new Position(1, 1), 1));
        wizards.add(new Wizard(new Position(4, 4), 3));

        int winner = predictWinner(labyrinth, wizards);

        if (winner != -1) {
            System.out.println("Wizard " + (winner + 1) + " wins!");
        } else {
            System.out.println("No winner.");
        }
    }
}