/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Saphira
 */
public class Estado {
    Posicao pos;
    Labirinto lab; // Todos os estados tem uma referência ao mesmo labirinto
    List<Estado> estadosPossiveis; //estados para o qual é possível ir a partir do atual
    
    public Estado (Posicao p, Labirinto lab){
        pos = p;
        this.lab = lab;
        estadosPossiveis = new LinkedList<>();
    }
    public List<Posicao> calculaPosicoesPossiveis(){
        List<Posicao> posicoes = new LinkedList<>();
        int x = pos.getX();
        int y = pos.getY();

        /* Verifica a vizinhança 8. Se for um lugar acessível, salva a
        posição para colocar no grafo de estados mais tarde. */
        if(lab.isAccessible(y-1, x)) //up
            posicoes.add(new Posicao (y-1, x));
        else if (lab.isAccessible(y-1, x+1)) //up-right
            posicoes.add(new Posicao (y-1, x+1));
        else if (lab.isAccessible(y, x+1)) //right
            posicoes.add(new Posicao (y, x+1));
        else if (lab.isAccessible(y+1, x+1)) //down-right
            posicoes.add(new Posicao (y+1, x+1));
        else if (lab.isAccessible(y+1, x)) //down
            posicoes.add(new Posicao (y+1, x));
        else if (lab.isAccessible(y+1, x-1)) //down-left
            posicoes.add(new Posicao (y+1, x-1));
        else if (lab.isAccessible(y, x-1)) //left
            posicoes.add(new Posicao (y, x-1));
        else if (lab.isAccessible(y-1, x-1)) //up-left
            posicoes.add(new Posicao (y-1, x-1));
        
        return posicoes;
    }
    public List<Estado> getEstadosPossiveis(){
        return estadosPossiveis;
    }
    public Posicao getPosicao(){
        return pos;
    }
}
