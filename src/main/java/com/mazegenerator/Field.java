package com.mazegenerator;

import java.util.ArrayList;
import java.util.List;

public class Field {
    int x;
    int y;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Field> printNeighbours() {

        List<Field> neighboursList = new ArrayList<>();

        //neighboursList.add(Field(2,3));
        //Pola naroznikowe
        //TODO: 05.06.2023  - zamiast 15 - zmienny rozmiar labiryntu
        if (x == 0 && y == 0 ||
                x == 0 && y == 20 ||
                x == 20 && y == 0 ||
                x == 20 && y == 20) {


        }
        return neighboursList;
    }
}
