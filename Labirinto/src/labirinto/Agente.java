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
    private Labirinto labirinto; //Associação com um labirinto
    private Posicao posicaoAtual;
    private List<Posicao> caminhoPercorrido;
    /* Um estado é representado por posição atual e matriz com
    os obstáculos e objetivos.
        Não é necessário alocar uma grid ou labirinto novo para
    cada estado, basta salvar a posição do agente. */
    
    public Agente(Labirinto lab){
        //associação bidirecional entre labirinto e agente
        labirinto = lab; 
        labirinto.setAgente(this);
        
        posicaoAtual = labirinto.getStart();
        caminhoPercorrido = new ArrayList<>();
    }
    public void run(){
        posicaoAtual = labirinto.getStart();
        /* Enquanto o nosso agente é burro, faz apenas alguns
        movimentos (se ele não resolveu nesse tempo, é provável
        que nem consiga resolver). 
            Depois, quando nós implementarmos as buscas, vai ser
        algo do tipo:
        while(posicaoAtual != labirinto.getExit()){
            deliberar();
            labirinto.show();
        }*/
        
        for(int i=0; i < 15; i++){
            deliberar();
            labirinto.show();
            if (posicaoAtual.equals(labirinto.getExit())){
                System.out.println("Resolvido!");
                //System.out.println("Solucao (y,x):");
                //for (Posicao p : caminhoPercorrido){
                //    System.out.print(p + " ");
                break;
            }
        }
        
    }
    public void deliberar() {
        //Decide o próximo lugar para onde ir

        /* Por enquanto o agente é bem "burro", ele só vê se pode
        ir para cima, se não der tenta ir pra cima-direita, senão
        direita... todas as 8 direções possíveis */
        int x = posicaoAtual.getX();
        int y = posicaoAtual.getY();
        Posicao decisao=null;
        if(labirinto.isAccessible(y-1, x)) //up
            decisao = new Posicao(y-1, x);
        else if (labirinto.isAccessible(y-1, x+1)) //up-right
            decisao = new Posicao(y-1, x+1);
        else if (labirinto.isAccessible(y, x+1)) //right
            decisao = new Posicao(y, x+1);
        else if (labirinto.isAccessible(y+1, x+1)) //down-right
            decisao = new Posicao(y+1, x+1);
        else if (labirinto.isAccessible(y+1, x)) //down
            decisao = new Posicao(y+1, x);
        else if (labirinto.isAccessible(y+1, x-1)) //down-left
            decisao = new Posicao(y+1, x-1);
        else if (labirinto.isAccessible(y, x-1)) //left
            decisao = new Posicao(y, x-1);
        else if (labirinto.isAccessible(y-1, x-1)) //up-left
            decisao = new Posicao(y-1, x-1);
        
        //Quanto tiver as buscas vai ser algo como
        //Posicao decisao = busca();
        
        caminhoPercorrido.add(decisao);
        posicaoAtual = decisao;
    }
    public Posicao getPosicaoAtual(){
        return posicaoAtual;
    }
}
