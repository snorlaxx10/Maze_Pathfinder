import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {

    public static boolean hasPath(ArrayList<mazeCell> totalMaze, int index) {
        mazeCell cell = totalMaze.get(index);

        if (cell.canGo == 1 && index != 0) {

            if (cell.string.charAt(0) == '0' && !cell.visited.contains('N')) {
                return true;
            }

            else if (cell.string.charAt(1) == '0' && !cell.visited.contains('S')) {

                return true;
            }

            else if (cell.string.charAt(2) == '0' && !cell.visited.contains('W')) {

                return true;
            }

            else if (cell.string.charAt(3) == '0' && !cell.visited.contains('E')) {

                return true;
            }


        }

        if ( !cell.visited.contains('N') && cell.north == '0' && cell.cameFrom != 'N') {

            return true;
        }

        else if ( !cell.visited.contains('S') && cell.south == '0' && cell.cameFrom != 'S') {

            return true;
        }

        else if ( !cell.visited.contains('W') && cell.west == '0' && cell.cameFrom != 'W') {

            return true;
        }

        else if ( !cell.visited.contains('E') && cell.east == '0' && cell.cameFrom != 'E') {

            return true;
        }

        return false;
    }

    public static int sequence(ArrayList<Character> firstArray, ArrayList<Character> secondArray, int lengthfirst, int lengthsecond) {

        int[][] answer = new int[lengthfirst + 1][lengthsecond + 1];

        for (int i = 0; i <= lengthfirst; i++) {

            for (int j = 0; j <= lengthsecond; j++) {

                if (i == 0 || j == 0) {
                    answer[i][j] = 0;
                }

                // if match, look at diagonal
                else if (firstArray.get(i - 1) == secondArray.get(j - 1)) {
                    answer[i][j] = answer[i - 1][j - 1] + 1;
                }

                // get the max between left and above
                else {
                    answer[i][j] = Math.max(answer[i - 1][j], answer[i][j - 1]);
                }
            }
        }

        return answer[lengthfirst][lengthsecond];
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\User\\Desktop\\Codes\\CECS 328 Java\\src\\mazeSamples\\input2.txt");

        Scanner scanner = new Scanner(file);

        ArrayList< ArrayList<Character> > allMaze = new ArrayList<>();

        int numberofMaze = scanner.nextInt();
        scanner.nextLine();
        int dimension = scanner.nextInt();
        scanner.nextLine();
        String line = "";

        System.out.println(numberofMaze);
        System.out.println(dimension);

        for (int i = 0; i < numberofMaze; i++) {

            ArrayList<mazeCell> totalMaze = new ArrayList<>();
            ArrayList<Character> finalArray = new ArrayList<>();

            int j = 0;

            while (j != dimension) {
                line = scanner.nextLine();
                // System.out.println(line);

                if ( !line.isEmpty() ) {

                    for (int k = 0; k < dimension * 4; k += 4) {
                        mazeCell cell = new mazeCell(line.substring(k, k + 4));
                        totalMaze.add(cell);
                    }
                    j++;
                }
            }

            int index = 0;

            do {
                mazeCell cell = totalMaze.get(index);

                if (cell.canGo == 1 && index != 0) {

                    if (cell.string.charAt(0) == '0' && !cell.visited.contains('N')) {

                        finalArray.add('N');

                        cell.visited.add('N');
                        cell.canGo--;

                        index = index - dimension;
                    }

                    else if (cell.string.charAt(1) == '0' && !cell.visited.contains('S')) {

                        finalArray.add('S');

                        cell.visited.add('S');
                        cell.canGo--;

                        index = index + dimension;
                    }

                    else if (cell.string.charAt(2) == '0' && !cell.visited.contains('W')) {

                        finalArray.add('W');

                        cell.visited.add('W');
                        cell.canGo--;

                        index = index - 1;
                    }

                    else if (cell.string.charAt(3) == '0' && !cell.visited.contains('E')) {

                        finalArray.add('E');

                        cell.visited.add('E');
                        cell.canGo--;

                        index = index + 1;
                    }


                }

                if ( !cell.visited.contains('N') && cell.north == '0' && cell.cameFrom != 'N') {

                    finalArray.add('N');

                    cell.visited.add('N');
                    cell.canGo--;

                    mazeCell nextCell = totalMaze.get(index - dimension);
                    nextCell.cameFrom = 'S';

                    index = index - dimension;
                }

                else if ( !cell.visited.contains('S') && cell.south == '0' && cell.cameFrom != 'S') {


                    finalArray.add('S');

                    cell.visited.add('S');
                    cell.canGo--;

                    mazeCell nextCell = totalMaze.get(index + dimension);
                    nextCell.cameFrom = 'N';

                    index = index + dimension;
                }

                else if ( !cell.visited.contains('W') && cell.west == '0' && cell.cameFrom != 'W') {


                    finalArray.add('W');

                    cell.visited.add('W');
                    cell.canGo--;

                    mazeCell nextCell = totalMaze.get(index - 1);
                    nextCell.cameFrom = 'E';

                    index = index - 1;
                }

                else if ( !cell.visited.contains('E') && cell.east == '0' && cell.cameFrom != 'E') {


                    finalArray.add('E');

                    cell.visited.add('E');
                    cell.canGo--;

                    mazeCell nextCell = totalMaze.get(index + 1);
                    nextCell.cameFrom = 'W';

                    index = index + 1;
                }

            } while ( hasPath(totalMaze, index) );

            System.out.println(finalArray);
            allMaze.add(finalArray);

        }
        System.out.println();

        int biggestDifference = 100000;
        int diffpairx = 0;
        int diffpairy = 0;

        for (int i = 0; i < allMaze.size() - 1; i++) {
            int same = 0;

            for (int j = i + 1; j < allMaze.size(); j++) {
                same = sequence(allMaze.get(i), allMaze.get(j), allMaze.get(i).size(), allMaze.get(j).size());

                // System.out.println(same);
                System.out.println("Common sequence for " + i + " and " + j + ": " + same);

                if (biggestDifference > same) {
                    biggestDifference = same;
                    diffpairx = i;
                    diffpairy = j;
                }

            }

        }

        System.out.println();
        System.out.println("Least alike at: " + diffpairx + " and " + diffpairy + " with " + biggestDifference);
        System.out.println(diffpairx + " " + diffpairy);
    }
}

class mazeCell {
    public String string;
    public ArrayList<Character> visited;
    int canGo;
    public char north;
    public char south;
    public char west;
    public char east;
    public char cameFrom;

    public mazeCell() {

    }

    public mazeCell(String string) {
        this.string = string;
        north = string.charAt(0);
        south = string.charAt(1);
        west = string.charAt(2);
        east = string.charAt(3);
        cameFrom = '0';
        visited = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (string.charAt(i) == '0') {
                canGo++;
            }
        }

    }
}