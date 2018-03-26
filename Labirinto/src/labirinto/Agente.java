/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Saphira
 */
public class Agente {
    private Labirinto labirinto; //Associação com um labirinto
    private Estado estadoAtual;
    private GrafoEstados grafoEstados;
    private List<Estado> solucao;
    
    public Agente(Labirinto lab){
        //associação bidirecional entre labirinto e agente
        labirinto = lab; 
        labirinto.setAgente(this);
        
        estadoAtual = labirinto.getStart();
        grafoEstados = new GrafoEstados(labirinto);
        solucao = new ArrayList<>();
    }
    
    /**
     * "Liga" o agente.
     */
    public void run(){
        System.out.println("Montando grafo de estados...");
        grafoEstados.construir(estadoAtual);

        boolean temSolucao = grafoEstados.temSolucao(labirinto.getExit());
        if(!temSolucao) {
            System.err.println("Não é possível chegar do início até a saída.");
            System.err.println("Certifique-se de que há um caminho de 0s ligando"
                    + "'S' e 'E' e tente novamente!");
            System.exit(-1);
        }

        System.out.println("Procurando solução...");
        aprofundamentoIterativo(estadoAtual);
        
        System.out.println("Percorrendo caminho...");
        walkThroughSolution();

        System.out.println("Número de passos dados: " + (solucao.size()-1));
        //-1 pois a solução inclui o estado inicial
        for (Estado e : solucao)
            System.out.print(e + " ");
        System.out.println();
    }

    /**
     * Soluciona o labirinto fazendo DFS com diferentes
     * profundidades máximas. Ao terminar, o atributo
     * solucao contem a lista de estados pelos quais é
     * necessário passar para chegar a saída.
     * @param inicial Estado inicial
     */
    public void aprofundamentoIterativo(Estado inicial)
    {
        boolean achouSaida;
        for(int i=1; i < grafoEstados.getSize(); i++)
        {
            achouSaida = LimitedDFS(inicial, i);
            if (achouSaida)
                break;
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
        boolean achouSaida;

        estado.setVisited(true);
        if(profMax >= 1) {
            for(Estado e : estado.getAdjascentes())
                if(!e.isVisited()) {
                    achouSaida = LimitedDFS(e, profMax-1);
                    if (achouSaida) {
                        solucao.add(estado);
                        return true;
                    }
                }
        } else { //profMax == 0
            /* Se achou a saída, vai saindo na recursão e adicionando os
            estados daquele ramo (ie. o caminho até a saída) na solução. */
            if(estado.equals(labirinto.getExit())) {
                solucao.add(estado);
                return true;
            }
        }
        /* Quando for voltar um nível da recursão, desvisita o nó.
        Se não fizer isso, o algoritmo pode não encontrar a solução
        ótima. Por exemplo:
        A -- B
          \  |
            C -- D
        i=2: Visita A chama recursivo para B, visita B, chama recursivo para
             C, visita C, volta pra B, volta pra A --> C já foi visitado, A não
             considera o caminho A--C--D
        */
        estado.setVisited(false);
        return false;
    }
    /**
     * 
     */
    public void AEstrela()
    {





        
    }
    /**
     * Faz o agente percorrer a solução encontrada.
     */
    public void walkThroughSolution(){
        for(Estado e : solucao)
        {
            move(e);
            labirinto.show();
        }
    }
    private void move(Estado e){
        estadoAtual = e;
    }
    public Estado getEstadoAtual(){ return estadoAtual; }
}
