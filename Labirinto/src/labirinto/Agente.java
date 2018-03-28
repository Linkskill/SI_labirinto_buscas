/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 *
 * @author Saphira
 */
public class Agente {
    private Labirinto labirinto;
    private Estado estadoAtual;
    private GrafoEstados grafoEstados;
    private List<Estado> solucao;
    private int numEstadosPercorridosNaBusca;

    
    public Agente(Labirinto lab){
        labirinto = lab; 
        labirinto.setAgente(this);
        
        estadoAtual = labirinto.getStart();
        grafoEstados = new GrafoEstados(labirinto);
        solucao = new ArrayList<>();
        numEstadosPercorridosNaBusca = 0;
    }
    
    /**
     * "Liga" o agente.
     * @param alg Algoritmo de busca a ser usado.
     * '1' - Aprof Iterativo
     * '2' - A*
     */
    public void run(char alg){
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
        if (alg == '1')
            aprofundamentoIterativo(estadoAtual);
        else //alg == '2'
            AEstrela(estadoAtual);
        
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
                            + numEstadosPercorridosNaBusca);
        
        AEstrela(estadoAtual);
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
        numEstadosPercorridosNaBusca++;

        if(profMax >= 1) {
            for(Aresta aresta : estado.getAdjascencias())
                if(!aresta.getVizinho().isVisited()) {
                    achouSaida = LimitedDFS(aresta.getVizinho(), profMax-1);
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
     * de custo e uma função heurística. Escolhe sempre
     * escolher o estado E com menor f tal que f=g(E)+h(E)
     * g = função de custo
     * h = função heurística
     * 
     * @param inicial Estado inicial
     */
    public void AEstrela(Estado inicial)
    {
        // Pré-computa as distâncias
        for (Estado e : grafoEstados.getEstados())
        {
            e.setF(Double.MAX_VALUE);
            e.setG(Integer.MAX_VALUE);
            e.setH(heuristica(e));
            e.setPai(null);
        }
       
        // Cria um heap mínimo para guardar os estados abertos -->
        // os que ainda vamos tentar explorar
        PriorityQueue<Estado> heapMin = new PriorityQueue<>(10, new EstadoComparator());

        // Cria uma lista para guardar os estados fechados --> os
        //quais já verificamos todos os vizinhos
        List<Estado> fechados = new ArrayList<>();
        
        Estado estado, vizinho;
        double novoF;
        int novoG;

        inicial.setG(0);
        inicial.setF(inicial.getH());
        heapMin.add(inicial);
        while(!heapMin.isEmpty())
        {
            estado = heapMin.remove();
            numEstadosPercorridosNaBusca++;

            if (estado.equals(labirinto.getExit())) {
                // Se achou a saida, salva esse caminho como a solução
                reconstroiCaminho(estado);
                return;
            }
            for (Aresta aresta : estado.getAdjascencias())
            {
                vizinho = aresta.getVizinho();
                novoG = custo(estado, aresta);
                novoF = novoG + vizinho.getH();

                //Se já foi fechado, ignora
                if (fechados.contains(vizinho))
                    continue;
                //Se já foi descoberto
                if (heapMin.contains(vizinho)) {
                    //E sabemos um caminho menor, ignora
                    if (novoF >= vizinho.getF())
                        continue;
                    else { //Se esse caminho é melhor, atualiza
                        heapMin.remove(vizinho);
                        vizinho.setG(novoG);
                        vizinho.setF(novoF);
                        vizinho.setPai(estado);
                        heapMin.add(vizinho);
                    }
                } else { //Se ainda não foi descoberto
                    vizinho.setG(novoG);
                    vizinho.setF(novoF);
                    vizinho.setPai(estado);
                    heapMin.add(vizinho);
                }
            }
            fechados.add(estado);
        }
    }
    /**
     * Função G do A*.
     * G(vizinho) = G(estado) + peso da aresta (estado,vizinho)
     */
    private int custo (Estado e, Aresta a){
        return e.getG() + a.getPeso();
    }
    /**
     * Função H do A*.
     * Calcula a distância euclidiana entre o estado
     * passado por parâmetro e o final. 
     */
    private double heuristica (Estado e){    
        int x1 = e.getX();
        int y1 = e.getY();
        int x2 = labirinto.getExit().getX();
        int y2 = labirinto.getExit().getY();
        return Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
    }
    public void reconstroiCaminho(Estado estado){
        Estado anterior;
        solucao.add(estado);
        anterior = estado.getPai();
        while(anterior != null)
        {
            solucao.add(anterior);
            anterior = anterior.getPai();
        }
        /* Como a lista foi preenchida da exit para o spawn,
        é necessário inverter a ordem dos passos para que o
        agente ande do spawn até a exit. */
        Collections.reverse(solucao);
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
