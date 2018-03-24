/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Saphira
 */
public class Agente {
    private Labirinto labirinto; //Associação com um labirinto
    private Estado estadoAtual;
    private List<Estado> grafoEstados;
    private List<Estado> solucao;
    
    public Agente(Labirinto lab){
        //associação bidirecional entre labirinto e agente
        labirinto = lab; 
        labirinto.setAgente(this);
        
        estadoAtual = labirinto.getStart();
        grafoEstados = new ArrayList<>();
        solucao = new ArrayList<>();
    }
    public void run(){
        boolean temSolucao = montaGrafoEstados(estadoAtual);
        if(!temSolucao) {
            System.out.println("Não é possível chegar do início até a saída.");
            System.exit(-1);
        }
        aprofundamentoIterativo(grafoEstados.get(0));
        walkSolution();
    }
    /**
     * Monta o grafo de estados do ambiente, partindo do estado inicial.
     * @param start Estado inicial
     * @return true se é o labirinto tem solução (existe ao menos
     *         um caminho entre o inicio e a saída), false caso
     *         contrário.
     */
    private boolean montaGrafoEstados(Estado start){
        List<Estado> fila = new LinkedList<>(); /* Fila de nós que ainda falta
                                                colocar no grafo */
        fila.add(start);

        List<Posicao> posicoes;
        Estado atual;
        boolean isAlreadyOnGraph;
        while(!fila.isEmpty())
        {
            /* Tira um estado da fila, cria a lista de
            adjascências dele e o coloca no grafo.*/
            atual = fila.remove(0);
            posicoes = atual.calculaPosicoesPossiveis();
            for (Posicao p : posicoes)
            {
                /* Se já existe um estado com essa mesma posição
                no grafo, referencia à este mesmo objeto ao invés
                de criar um novo. */
                isAlreadyOnGraph = false;
                for (Estado e : grafoEstados)
                    if(e.getPosicao().equals(p)) {
                        isAlreadyOnGraph = true;
                        atual.getAdjascentes().add(e);
                        break;
                    }
                
                /* Se ainda não há nenhum estado no grafo com essa
                posição, cria um novo estado. */
                if(!isAlreadyOnGraph) {
                    Estado novo = new Estado(p, labirinto);
                    atual.getAdjascentes().add(novo);
                    fila.add(novo);
                }
            }
            grafoEstados.add(atual);
        }

        /* Confere se o estado final foi colocado no grafo */
        for (Estado e : grafoEstados)
            if(e.equals(labirinto.getExit()))
                return true;
        return false;
    }

    /**
     * Faz DFS com diferentes profundidades máximas.
     * Ao terminar, o atributo solucao contem a lista de estados
     * pelos quais é necessário passar para chegar a saída.
     * @param inicial Estado inicial
     */
    public void aprofundamentoIterativo(Estado inicial)
    {
        /* Solução não é ótima como deveria ser. Tem alguma coisa
        errada na lógica. Com certeza está marcando os vértices como
        visitados quando não deveria.
        No 1.txt tá achando slução com 9 passos, quando a melhor é 7 */
        boolean achouSaida;
        for(int i=1; i < grafoEstados.size(); i++)
        {
            System.out.println("i=" + i);
            /* Em cada iteração, marca todo vértice como não visitado */
            for(Estado e : grafoEstados)
                e.setVisited(false);
            
            achouSaida = LimitedDFS(inicial, i);
            if (achouSaida)
                break;
            System.out.println("\n");
        }
        /* Como a lista foi preenchida da exit para o spawn,
        é necessário inverter a ordem dos passos para que o
        agente ande do spawn até a exit. */
        Collections.reverse(solucao);
    }
    /**
     * Depth First Search (busca em profundidade).
     * @param estado Estado que está sendo visitado no momento
     * @param profMax Profundidade máxima para realizar a busca, contando
     *                a partir do estado passado por parâmetro
     * @return true se achou a saída naquele ramo, false caso contrário
     */
    private boolean LimitedDFS(Estado estado, int profMax)
    {
        System.out.print(estado + " " + profMax + " ");
        boolean achouSaida = false;
        if(profMax >= 1) {
            for(Estado e : estado.getAdjascentes())
                if(!e.isVisited()) {
                    achouSaida = LimitedDFS(e, profMax-1);
                    if (achouSaida) {
                        solucao.add(estado);
                        return true;
                    }
                }
            estado.setVisited(true);
        } else { //profMax == 0
            /* Se achou a saída, vai saindo na recursão e adicionando os
            estados daquele ramo (ie. o caminho até a saída) na solução. */
            if(estado.equals(labirinto.getExit()))
            {
                solucao.add(estado);
                return true;
            }
        }
        return false;
    }
    public void AEstrela()
    {
        
    }
    
    public void walkSolution(){
        for(Estado e : solucao)
        {
            move(e);
            labirinto.show();
        }
        System.out.println("Resolvido!");
        System.out.println(solucao.size() + " passos");
        //System.out.println("Solucao (y,x):");
        //for (Posicao p : solucao){
        //    System.out.print(p + " ");
    }
    private void move(Estado e){
        estadoAtual = e;
    }
    public Estado getEstadoAtual(){
        return estadoAtual;
    }
}
