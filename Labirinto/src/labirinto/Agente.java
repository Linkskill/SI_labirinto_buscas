/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Saphira
 */
public class Agente {
    private Posicao posicaoAtual;
    private List<Posicao> caminhoPercorrido;
    private Labirinto labirinto;
    
    public Agente(Labirinto labirinto){
        posicaoAtual = null;
        caminhoPercorrido = new ArrayList<>();
    }
    public void run(){
        posicaoAtual = labirinto.getStart();
        while(posicaoAtual != labirinto.getExit()){
            deliberar();
        }
    }
    public void deliberar() {
        //Decide para onde ir
        
        //caminhoPercorrido.add(decisao);
        //posicaoAtual = decisao;
    }
    public Posicao getPosicaoAtual(){
        return posicaoAtual;
    }
}
