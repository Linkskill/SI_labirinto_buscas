/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

/**
 *
 * @author Gabriel Eugenio, Lincoln Batista e Jorge Straub
 */
public class Coordenada {
    private final int x;
    private final int y;
    
    public Coordenada(int y, int x){
        this.y = y;
        this.x = x;
    }
    public int getX(){ return x; }
    public int getY(){ return y; }

    @Override
    public String toString() {
        return "(" + y + ", " + x + ")";
    }
    @Override
    public boolean equals(Object other){
        Coordenada coord = (Coordenada)other;
        if (x == coord.getX() && y == coord.getY())
            return true;
        return false;
    }
    @Override
    //Sempre que der Override no equals() precisa dar no hashCode() também
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.x;
        hash = 13 * hash + this.y;
        return hash;
    }
}
