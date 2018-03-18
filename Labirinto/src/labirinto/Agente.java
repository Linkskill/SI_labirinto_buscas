/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Saphira
 */
public class Agente {
    private Labirinto labirinto; //Associação com um labirinto
    private Posicao posicaoAtual;
    private List<Posicao> caminhoPercorrido;
    
    public Agente(Labirinto lab){
        labirinto = lab;
        posicaoAtual = lab.getStart();
        caminhoPercorrido = new ArrayList<>();
    }
    public void run(){
        posicaoAtual = labirinto.getStart();
        while(posicaoAtual != labirinto.getExit()){
            deliberar();
            labirinto.print(posicaoAtual);
            deliberar();
            labirinto.print(posicaoAtual);
            deliberar();
            labirinto.print(posicaoAtual);
            deliberar();
            labirinto.print(posicaoAtual);
            deliberar();
            labirinto.print(posicaoAtual);
            System.exit(0);
        }
    }
    public void deliberar() {
        //Decide o próximo lugar para onde ir
        Posicao decisao = new Posicao(this.posicaoAtual.getX(), this.posicaoAtual.getY() + 1);
        
        if(labirinto.getPos(decisao) == 'x'){
            System.out.println("Violação de mapa");
        } else if(labirinto.getPos(decisao) == '1'){
            System.out.println("Parede no caminho!");
        } else {
            caminhoPercorrido.add(decisao);
            posicaoAtual = decisao;
        }
    }
    public Posicao getPosicaoAtual(){
        return posicaoAtual;
    }
}
