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
    private final Posicao posicao;
    private final Labirinto lab; // Todos os estados tem uma referência ao mesmo labirinto
    private List<Estado> adjascentes;
    private boolean visited;
    
    public Estado (Posicao p, Labirinto lab){
        posicao = p;
        this.lab = lab;
        adjascentes = new LinkedList<>();
        visited = false;
    }
    /**
     * Verifica a vizinhança-8 no grid.
     * Para cada posição, se for acessível salva em uma lista,
     * caso contrário ignora.
     * @return Lista de posições representando os destinos possíveis.
     */
    public List<Posicao> calculaPosicoesPossiveis(){
        List<Posicao> posicoes = new LinkedList<>();
        int x = posicao.getX();
        int y = posicao.getY();

        if(lab.isAccessible(y-1, x)) //up
            posicoes.add(new Posicao (y-1, x));
        if (lab.isAccessible(y-1, x+1)) //up-right
            posicoes.add(new Posicao (y-1, x+1));
        if (lab.isAccessible(y, x+1)) //right
            posicoes.add(new Posicao (y, x+1));
        if (lab.isAccessible(y+1, x+1)) //down-right
            posicoes.add(new Posicao (y+1, x+1));
        if (lab.isAccessible(y+1, x)) //down
            posicoes.add(new Posicao (y+1, x));
        if (lab.isAccessible(y+1, x-1)) //down-left
            posicoes.add(new Posicao (y+1, x-1));
        if (lab.isAccessible(y, x-1)) //left
            posicoes.add(new Posicao (y, x-1));
        if (lab.isAccessible(y-1, x-1)) //up-left
            posicoes.add(new Posicao (y-1, x-1));
        
        return posicoes;
    }
    @Override
    public String toString(){
        return posicao.toString();
    }
    @Override
    public boolean equals(Object other){
        //Como só trabalhamos com um labirinto por vez, a única coisa que
        //precisamos para comparar se dois estados são iguais é a posição.
        Estado e = (Estado)other;
        if (posicao.equals(e.getPosicao()))
            return true;
        return false;
    }
    @Override
    public int hashCode() {
        //Sempre que der Override no equals() precisa dar no hashCode() também
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.posicao);
        hash = 47 * hash + Objects.hashCode(this.lab);
        return hash;
    }

    public Posicao getPosicao(){
        return posicao;
    }
    public List<Estado> getAdjascentes(){
        return adjascentes;
    }
    public boolean isVisited(){
        return visited;
    }
    public void setVisited(boolean b){
        visited = b;
    }
}
