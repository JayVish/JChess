package model;

import java.util.Objects;

public class Square {
    private int r;
    private int c;

    public Square(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public Square(Square s) {
        this.r = s.getR();
        this.c = s.getC();
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return r == square.r &&
                c == square.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }

    public String toString() {
        return "R: " + r + " C: " + c;
    }
}