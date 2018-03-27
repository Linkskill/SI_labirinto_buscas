/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Saphira
 */
public class Agente {
    private Labirinto labirinto;
    private Estado estadoAtual;
    private GrafoEstados grafoEstados;
    private List<Estado> solucao;
    private int numEstadosVisitadosNaBusca;

    
    public Agente(Labirinto lab){
        labirinto = lab; 
        labirinto.setAgente(this);
        
        estadoAtual = labirinto.getStart();
        grafoEstados = new GrafoEstados(labirinto);
        solucao = new ArrayList<>();
        numEstadosVisitadosNaBusca = 0;
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
        System.out.println("Número de estados no grafo de estados: "
                            + grafoEstados.getSize());
        System.out.println("Número de estados visitados na busca: "
                            + numEstadosVisitadosNaBusca);
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
        numEstadosVisitadosNaBusca++;

        if(profMax >= 1) {
            for(Edge edge : estado.getAdjascencias())
                if(!edge.getVizinho().isVisited()) {
                    achouSaida = LimitedDFS(edge.getVizinho(), profMax-1);
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
        i=2: Visita A chama recursivo para B, visita B, chama recursivo
             para C, visita C, volta pra B, volta pra A --> C já foi
             visitado, A não considera o caminho A--C--D
        */
        estado.setVisited(false);
        return false;
    }
    /**
     * Soluciona o labirinto levando em conta uma função
     * de custo e uma função heurística.
     * @param inicial Estado inicial
     */
    public void AEstrela(Estado inicial)
    {
        // Estrutura para guardar as distancias até o estado final.
        // Até pensei em colocar como atributo de Estado, mas não
        // faz sentido já que só usa essa ideia de heurística aqui
        HashMap<Estado, Double> valorHeuristica = new HashMap<>();

        // Pré-computa as distâncias (computar elas enquanto procura
        // pela saída pode fazer com que chame a função várias vezes
        // para um mesmo estado (processamento desnecessário))
        for (Estado e : grafoEstados.getEstados())
            valorHeuristica.put(e, heuristica(e));
        
        // Cria um heap mínimo para guardar os estados abertos -->
        // os que ainda vamos tentar explorar
        // Dar uma procurada em PriorityQueue do Java

        // Cria uma lista para guardar os estados fechados --> os
        //quais já verificamos todos os vizinhos
        
        Estado estado=null, anterior;

        //Coloca o estado inicial no heap, com f=0
        //Enquanto tem elementos no heap
            // Extrai estado com menor f do heap
            numEstadosVisitadosNaBusca++;

            if (estado.equals(labirinto.getExit())) {
                // Se achou a saida, salva esse caminho como a solução
                solucao.add(estado);
                anterior = estado.getPaiMenorCusto();
                while(anterior != null)
                {
                    solucao.add(anterior);
                    anterior = anterior.getPaiMenorCusto();
                }
                /* Como a lista foi preenchida da exit para o spawn,
                é necessário inverter a ordem dos passos para que o
                agente ande do spawn até a exit. */
                Collections.reverse(solucao);
                return;
            }
            for (Edge edge : estado.getAdjascencias())
            {
                // custo(vizinho) = custo(estado) + peso da aresta(estado,vizinho)
                // f(vizinho) = custo(vizinho) + valorHeuristica(vizinho)

                // if (  ???  )
                //    atualiza menorCusto e pai do vizinho
                //    coloca vizinho no heap
            }
            // Coloca E no conjunto de estados fechados
    }
    /**
     * Calcula a distância euclidiana entre o estado
     * passado por parâmetro e o final.
     * @param e Estado que terá a distância calculada.
     * @return Distância euclidiana entre o estado
     *         e o estado final.
     */
    private double heuristica (Estado e){
        int x1 = e.getX();
        int y1 = e.getY();
        int x2 = labirinto.getExit().getX();
        int y2 = labirinto.getExit().getY();
        return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
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
