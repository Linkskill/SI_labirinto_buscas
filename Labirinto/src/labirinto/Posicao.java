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
public class Posicao {
    private final int x;
    private final int y;
    
    public Posicao(int y, int x){
        this.y = y;
        this.x = x;
    }
    @Override
    public String toString(){
        return "(" + y + ", " + x + ")";
    }
    @Override
    public boolean equals(Object other){
        Posicao p = (Posicao)other;
        if (x == p.getX() && y == p.getY())
            return true;
        return false;
    }
    @Override
    public int hashCode() {
        //Sempre que der Override no equals() precisa dar no hashCode() também
        int hash = 7;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        return hash;
    }
    public int getX(){ return x; }
    public int getY(){ return y; }
}
