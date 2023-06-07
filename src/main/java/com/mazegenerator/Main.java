package com.mazegenerator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        MazeTest mazeTest = new MazeTest(7);

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> test2 = new ArrayList<>();


    //    mazeTest.printUnvisitedNeighbours(0, 4);

        mazeTest.dfsMazeCreator();
        mazeTest.printTable();
    }
}
