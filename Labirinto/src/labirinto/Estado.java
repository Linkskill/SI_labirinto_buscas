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
    private List<Aresta> adjascencias;
    private boolean visited;
    private double f;
    private int g;
    private double h;
    private Estado pai;
    
    public Estado (int y, int x){
        coordenada = new Coordenada(y, x);
        adjascencias = new LinkedList<>();
        visited = false;
    }
    public void adicionarAdjascencia(Estado e, int custo){
        adjascencias.add(new Aresta(e, custo));
    }
    public void setVisited(boolean b){
        visited = b;
    }
    public boolean isVisited(){
        return visited;
    }
    public int getY() { return coordenada.getY(); }
    public int getX() { return coordenada.getX(); }
    public Coordenada getCoordenada() { return coordenada; }
    public List<Aresta> getAdjascencias(){ return adjascencias; }
    public double getF() { return f; }
    public int getG() { return g; }
    public double getH() { return h; }
    public Estado getPai() { return pai; }
    public void setF(double f) {
        this.f = f;
    }
    public void setG(int g) {
        this.g = g;
    }
    public void setH(double h) {
        this.h = h;
    }
    public void setPai(Estado e) {
        pai = e;
    }

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
