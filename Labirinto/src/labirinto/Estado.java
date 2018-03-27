/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Saphira
 */
public class Estado {
    private final Coordenada coordenada;
    private List<Edge> adjascencias;
    private boolean visited;
    private int menorCusto;
    private Estado paiMenorCusto;
    
    public Estado (int y, int x){
        coordenada = new Coordenada(y, x);
        adjascencias = new LinkedList<>();
        visited = false;
        menorCusto = Integer.MAX_VALUE;
        paiMenorCusto = null;
    }
    public void adicionarAdjascencia(Estado e, int custo){
        adjascencias.add(new Edge(e, custo));
    }
    public void setVisited(boolean b){
        visited = b;
    }
    public boolean isVisited(){
        return visited;
    }
    public void setMenorCusto(int menorCusto) {
        this.menorCusto = menorCusto;
    }
    public void setPaiMenorCusto(Estado paiMenorCusto) {
        this.paiMenorCusto = paiMenorCusto;
    }
    public int getY() { return coordenada.getY(); }
    public int getX() { return coordenada.getX(); }
    public Coordenada getCoordenada() { return coordenada; }
    public List<Edge> getAdjascencias(){ return adjascencias; }
    public int getMenorCusto() { return menorCusto; }
    public Estado getPaiMenorCusto() { return paiMenorCusto; }

    @Override
    public String toString(){
        return coordenada.toString();
    }
    @Override
    public boolean equals(Object other){
        //Como só trabalhamos com um labirinto por vez, a única coisa que
        //precisamos para comparar se dois estados são iguais é a posição.
        Estado e = (Estado)other;
        if (coordenada.equals(e.getCoordenada()))
            return true;
        return false;
    }
    @Override
    //Sempre que der Override no equals() precisa dar no hashCode() também
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.coordenada);
        hash = 79 * hash + Objects.hashCode(this.adjascencias);
        hash = 79 * hash + (this.visited ? 1 : 0);
        return hash;
    }
}
