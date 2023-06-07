package com.mazegenerator;

import java.util.*;

public class MazeTest {

    //wstepnie
    int mazeSize = 7;
    int[][] tableOfFields;

//    HashSet<Field> visitedFields = new HashSet<>();
    HashSet<List<Integer>> visitedFields = new HashSet<>();


    public MazeTest(int mazeSize) {
        this.mazeSize = mazeSize;
        this.tableOfFields = new int[mazeSize][mazeSize];

        for (int i = 0; i < this.tableOfFields.length; i++) {
            for (int j = 0; j < tableOfFields.length; j++) {
                this.tableOfFields[i][j] = 0;
            }
        }
    }

 /*   public void drawMaze(List<Integer> startField, List<Integer> finishField) {
        int xStart = startField.get(0);
        int yStart = startField.get(1);

        int xFinish = finishField.get(0);
        int yFinish = finishField.get(1);
*/
    public void drawma() {
        int xStart = 0;
        int yStart = 5;
        int xFinish = 0;
        int yFinish = 14;


    }

    private int drawStartYField(){
        Random random = new Random();

        int startY = random.nextInt(7);

        return startY;
    }

    public void dfsMazeCreator() {
//        int startY = this.drawStartYField();
        int startY = 1;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> startField = new ArrayList<>();
        startField.add(0);
        startField.add(startY);

        dfs_recur(startField, result);

        System.out.println("DFS: ");

        for (List<Integer> integerList : result) {
            System.out.print(integerList + ", ");
        }
        System.out.println();
    }

    private void dfs_recur(List<Integer> startField, List<List<Integer>> result) {
        this.tableOfFields[startField.get(0)][startField.get(1)] = 1;
        visitedFields.add(startField);
        result.add(startField);

        List<List<Integer>> neigbours = printUnvisitedNeighbours(startField.get(0), startField.get(1));


        Iterator<List<Integer>> iterator = neigbours.iterator();
        //Sprawdzamy czy do tych pol bedziemy mogli w ogole wejsc
        while (iterator.hasNext()) {
            List<Integer> element = iterator.next();
            int x = element.get(0);
            int y = element.get(1);
            int numberOfNeighbours = printUnvisitedNeighbours(x, y).size();

            if (numberOfNeighbours > 2 && !(isFieldBrink(x, y))) {
                //OK
            }
            else if (numberOfNeighbours > 1 && isFieldBrink(x, y)) {
                // OK
            }
            else {
                //Jesli pole nie ma wystarczajacej liczby nieodwiedzonych sasiadow, to go usuwamy
                //Zaburzaloby to wyglad labiryntu
                iterator.remove();
            }
        }


        if (!neigbours.isEmpty()) {
            Collections.shuffle(neigbours);
        }

        for (List<Integer> neigbour : neigbours) {
            if(!(visitedFields.contains(neigbour))) {
                dfs_recur(neigbour, result);
            }
        }

    }

    /**
     * Jeesli tableOfField[x][y] == 0 --> to oznacza, ze
     * pole nie zostalo jeszcze odwiedzone
     * @param x - wspolrzedna x pola poczatkowego
     * @param y - wspolrzedna y pola poczatkowego
     * @return - Lista nieodwiedzonych sasiadow
     */
    public List<List<Integer>> printUnvisitedNeighbours(int x, int y){

        List<List<Integer>> neighbours = new ArrayList<>();

        if (x - 1 >= 0 && this.tableOfFields[x-1][y] == 0) {
            neighbours.add(List.of(x -1, y));
        }
        if (y -1 >= 0 && this.tableOfFields[x][y-1] == 0)  {
            neighbours.add(List.of(x, y-1));
        }
        if (x + 1 <= this.mazeSize - 1 && this.tableOfFields[x+1][y] == 0) {
            neighbours.add(List.of(x + 1, y));
        }
        if (y + 1 <= this.mazeSize -1 && this.tableOfFields[x][y+1] == 0) {
            neighbours.add(List.of(x, y+1));
        }
 //       for (List<Integer> neighbour : neighbours) {
   //         System.out.print(neighbour + ", ");
//        }
        return neighbours;

    }

    public boolean isFieldBrink (int x, int y) {
        if (x == 0 || y == 0 || x == this.mazeSize || y == this.mazeSize) {
            return true;
        }
        else { return false;}
    }



 /*   public boolean IsMovementPossible(int actualX, int actualY, int potentialX, int potentialY) {
        //Ruch w gore
        if (potentialX < actualX && potentialY == actualY) {
            int x = potentialX;
            int y = potentialY;
            if(     this.tableOfFields[x-1][y-1] == 0 &&
                    this.tableOfFields[x-1][y] == 0 &&
                    this.tableOfFields[x-1][y+1] == 0 &&
                    this.tableOfFields[x][y-1] == 0 &&
                    this.tableOfFields[x][y+1] == 0) {
                return true;
            }
        }
        if (potentialX > actualX && potentialY == actualY) {

        }
    }


  */
    public void printTable() {
        for (int[] field : tableOfFields) {
            for (int i : field) {
                System.out.println(field[i] +" ");
            }
            System.out.println();
        }
    }
}
