package com.mazegenerator;

import java.util.*;

public class MazeGenerator {
    int mazeSize;

    /**
     * fieldTable
     * 0 - initialized on start
     * 1 - path
     * 2 - wall
     * 3 - start/end
     */
    int[][] fieldTable;

    HashSet<List<Integer>> visitedFields = new HashSet<>();

    public MazeGenerator(int mazeSize) {
        this.mazeSize = mazeSize;
        this.fieldTable = new int[mazeSize][mazeSize];

        for (int i = 0; i < this.fieldTable.length; i++) {
            for (int j = 0; j < fieldTable.length; j++) {
                this.fieldTable[i][j] = 0;
            }
        }
    }

    private int drawStartYField(){
        Random random = new Random();
        return random.nextInt(this.mazeSize);
    }
    private int chooseEndField() {
        List<Integer> endFields = new ArrayList<>();

        int i = this.fieldTable.length -1;
        for (int j = 0; j < fieldTable.length; j++) {
            if (this.fieldTable[j][i] == 1) {
                endFields.add(j);
            }
        }

        System.out.println("----------");

        Random random = new Random();
        int randomIndex = random.nextInt(endFields.size());

        System.out.println();
        System.out.println("Random value: " + endFields.get(randomIndex));

        return endFields.get(randomIndex);
    }

    public int[][] dfsMazeCreator() {
        int startY = this.drawStartYField();
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

        // Marking fields as start/end
        this.fieldTable[startY][0] = 3;
        this.fieldTable[chooseEndField()][this.fieldTable.length-1] = 3;

        return this.fieldTable;
    }

    private void dfs_recur(List<Integer> startField, List<List<Integer>> result) {
        this.fieldTable[startField.get(0)][startField.get(1)] = 1;
        visitedFields.add(startField);
        result.add(startField);

        printTable();

        List<List<Integer>> neighbours = getUnvisitedNeighbours(startField.get(0), startField.get(1));

        Iterator<List<Integer>> iterator = neighbours.iterator();
        //Removing non-enterable fields
        while (iterator.hasNext()) {
            List<Integer> element = iterator.next();
            int x = element.get(0);
            int y = element.get(1);
            int numberOfNeighbours = getUnvisitedNeighbours(x, y).size();

            if (numberOfNeighbours > 2 && !(isFieldBrink(x, y))) {
                // Field has more than 2 neighbors and is not a brink field
                //OK
            }
            else if (numberOfNeighbours > 1 && isFieldBrink(x, y)) {
                // Field has more than 1 neighbor and is a brink field
                // OK
            }
            else if (numberOfNeighbours > 0 && isCornerField(x, y)) {
                // Field has at least 1 neighbor and is a corner field
                // OK
            }
            else {
                // If the field is considered non-enterable, mark it as a wall and delete from this DFS Algorithm
                this.fieldTable[element.get(0)][element.get(1)] = 2;
                iterator.remove();
            }
        }

        if (!neighbours.isEmpty()) {
            Collections.shuffle(neighbours);
        }
        for (List<Integer> neighbour : neighbours) {
            if(!(visitedFields.contains(neighbour)) && this.fieldTable[neighbour.get(0)][neighbour.get(1)] != 2) {
                dfs_recur(neighbour, result);
            }
        }
    }

    /**
     * If tableOfField[x][y] == 0, it means that field has not been visited yey
     * @param x - 'x' coordinate of starting field
     * @param y - 'y' coordinate of starting field
     * @return - List of unvisited neighbors
     */
    public List<List<Integer>> getUnvisitedNeighbours(int x, int y){

        List<List<Integer>> neighbours = new ArrayList<>();

        if (x - 1 >= 0 && this.fieldTable[x-1][y] != 1) {
            neighbours.add(List.of(x -1, y));
        }
        if (y -1 >= 0 && this.fieldTable[x][y-1] != 1)  {
            neighbours.add(List.of(x, y-1));
        }
        if (x + 1 <= this.mazeSize - 1 && this.fieldTable[x+1][y] != 1) {
            neighbours.add(List.of(x + 1, y));
        }
        if (y + 1 <= this.mazeSize -1 && this.fieldTable[x][y+1] != 1) {
            neighbours.add(List.of(x, y+1));
        }
        return neighbours;

    }

    /**
     * Check whether this field located on the brink of the maze
     * @param x - 'x' coordinate of field
     * @param y - 'y' coordinate of field
     */
    public boolean isFieldBrink (int x, int y) {
        return x == 0 || y == 0 || x == this.mazeSize - 1 || y == this.mazeSize - 1;
    }

    private boolean isCornerField(int x, int y) {
        return x == 0 && y == 0 ||
                x == 0 && y == (mazeSize - 1) ||
                x == (mazeSize - 1) && y == 0 ||
                x == (mazeSize - 1) && y == (mazeSize - 1);
    }

    public void printTable() {
        for (int i = 0; i < this.fieldTable.length; i++) {
            System.out.print("{");
            for (int[] ints : fieldTable) {
                System.out.print(ints[i] + ", ");
            }
            System.out.print("}");
            System.out.println();
        }
        System.out.println("----------");
    }

}
