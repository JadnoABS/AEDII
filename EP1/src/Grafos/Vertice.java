package Grafos;

import java.util.Objects;

public class Vertice {
    private String label;
    private Character color;

    private int in;
    private int out;
    private Vertice PI;

    public Vertice(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public int compareTo(Vertice v) {
        if(this.label.equals(v.getLabel())) return 0;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Objects.equals(label, vertice.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    public Character getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public Vertice getPI() {
        return PI;
    }

    public void setPI(Vertice PI) {
        this.PI = PI;
    }
}
