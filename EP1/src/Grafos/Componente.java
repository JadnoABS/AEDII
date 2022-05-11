package Grafos;

import java.util.ArrayList;
import java.util.List;

public class Componente {
    private List<Vertice> component;

    public Componente() {
        this.component = new ArrayList<>();
    }

    public List<Vertice> getComponentList() {
        return component;
    }

    public Vertice toVertice() {
        String label = "";
        for (Vertice vertice : this.component) {
            label += vertice.getLabel();
        }

        return new Vertice(label);
    }

    public void add(Vertice v) {
        if(!this.component.contains(v)){
           this.component.add(v);
        }
    }

    public boolean contains(Vertice v) {
        for (Vertice vertice : this.component) {
           if(vertice.equals(v)){
              return true;
           }
        }
        return false;
    }
}
