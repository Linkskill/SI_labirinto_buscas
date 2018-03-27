/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

/**
 *
 * @author Saphira
 */
public class Aresta {
    private Estado vizinho;
    private int peso; 

    public Aresta(Estado e, int peso){
        vizinho = e;
        this.peso = peso;
    }
    public Estado getVizinho() { return vizinho; }
    public int getPeso() { return peso; }
}
