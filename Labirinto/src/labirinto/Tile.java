/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author Saphira
 */
public class Tile {
    private char type;
    /*  'X' é parede
        ' ' é caminho
        '-' é por onde o agente já andou
        'S' é o início
        'E' é a saída
        'A' é o agente
    */
    private Posicao posicao;
    private List<Tile> neighboors; /* Lista de vizinhos válidos para
    evitar ter que testar se está saindo dos limites do labirinto. */
    
    public Tile (char type, int x, int y){
        this.type = type;
        posicao = new Posicao(x, y);
        neighboors = new LinkedList<>();
    }
    public boolean isWall(){
        return (type == 'X');
    }
    public boolean isPath(){
        return (type == ' ');
    }
    public void addNeighboor(Tile t){
        neighboors.add(t);
    }

    public char getType(){ return type; }
    public Posicao getPosicao(){ return posicao; }
    public List<Tile> getNeighboors(){ return neighboors; }    
    public void setType(char type){ this.type = type; }
}
