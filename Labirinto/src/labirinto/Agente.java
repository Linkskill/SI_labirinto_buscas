/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Saphira
 */
public class Agente {
    private Labirinto labirinto; //Associação com um labirinto
    private Estado estadoAtual;
    private List<Estado> grafoEstados;
    private List<Posicao> solucao;
    /* OU lista de estados */
    
    /* Um estado é representado por posição atual e matriz com
    os obstáculos e objetivos.
        Não é necessário alocar uma grid ou labirinto novo para
    cada estado, basta salvar a posição do agente. */
    
    public Agente(Labirinto lab){
        //associação bidirecional entre labirinto e agente
        labirinto = lab; 
        labirinto.setAgente(this);
        
        estadoAtual = labirinto.getStart();
        solucao = new ArrayList<>();
    }
    public void run(){
        grafoEstados = montaGrafo(estadoAtual);
    }
        
    private List<Estado> montaGrafo(Estado start){
        List<Estado> fila = new LinkedList<>(); //Fila de nós que ainda falta
        //colocar no grafo
        
        /* Coloca os vizinhos do estado inicial na lista */
        List<Posicao> posicoes = start.calculaPosicoesPossiveis();
        for (Posicao p : posicoes)
            start.getEstadosPossiveis().add(new Estado(p, labirinto));
        grafoEstados.add(start);

        for (Posicao p : posicoes)
            fila.add(new Estado(p,labirinto));
        
        Estado atual;
        while(!fila.isEmpty())
        {
            /* Tira um estado da fila (ie. um estado que não está no grafo)
            cria a lista de adjascências desse estado e coloca ele no grafo.*/
            atual = fila.remove(0);
            posicoes = atual.calculaPosicoesPossiveis();

            boolean isAlreadyOnGraph;
            for (Posicao p : posicoes)
            {
                /* Se já existe um estado com essa mesma posição, referencia à
                este objeto ao invés de criar um novo. */
                isAlreadyOnGraph = false;
                for (Estado e : grafoEstados)
                {
                    if(p.equals(e.getPosicao()))
                    {
                        isAlreadyOnGraph = true;
                        atual.getEstadosPossiveis().add(e);
                        break;
                    }
                }
                /* Se não está no grafo,
                if(!isAlreadyOnGraph)
                    atual.getEstadosPossiveis().add(new Estado(p,labirinto));
            }
            grafoEstados.add(atual);
        }
    }
    
    
    public void move(){
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

    /* */
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
        
        solucao.add(decisao);
        posicaoAtual = decisao;
    }
    public int getXAtual(){
        return xAtual;
    }
    public int getYAtual(){
        return yAtual;
    }
}
