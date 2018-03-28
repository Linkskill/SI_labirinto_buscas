package labirinto;

import java.util.Comparator;
import java.util.HashMap;

public class EstadoComHeuristica implements Comparator<Estado>{
    private HashMap<Estado, Double> valorHeuristica = new HashMap<>();
    private Labirinto labirinto;
    
    public EstadoComHeuristica(HashMap valorHeuristica, Labirinto labirinto){
        this.valorHeuristica = valorHeuristica;
        this.labirinto = labirinto;
    }

    @Override
    public int compare(Estado o1, Estado o2) {
        if((valorHeuristica.get(o1) + heuristica(o1)) < (valorHeuristica.get(o2) + heuristica(o2))){
            return -1;
        }
        if((valorHeuristica.get(o1) + heuristica(o1)) > (valorHeuristica.get(o2) + heuristica(o2))){
            return 1;
        }
        
        return 0;
    }
    
    private double heuristica (Estado e){
        int x1 = e.getX();
        int y1 = e.getY();
        int x2 = labirinto.getExit().getX();
        int y2 = labirinto.getExit().getY();
        return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }
}
