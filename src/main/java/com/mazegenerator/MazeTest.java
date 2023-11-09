package com.mazegenerator;

public class MazeTest {
    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(20);
        System.out.println("Result: ");
        maze.printTable();
    }
}
