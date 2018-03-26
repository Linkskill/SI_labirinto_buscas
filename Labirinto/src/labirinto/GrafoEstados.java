/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Saphira
 */
public class GrafoEstados {
    private List<Estado> listaVertices;
    private final Labirinto labirinto;

    public GrafoEstados(Labirinto lab){
        listaVertices = new ArrayList<>();
        labirinto = lab;
    }
    /** 
     * Constrói o grafo de estados a partir do estado inicial.
     * @param start Estado inicial do ambiente.
     */
    public void construir(Estado start){
        List<Estado> fila = new LinkedList<>(); // Estados que não estão no grafo
        Estado estadoAtual;

        fila.add(start);
        while(!fila.isEmpty())
        {
            estadoAtual = fila.remove(0);
            calculaAdjascencias(estadoAtual, fila);
            listaVertices.add(estadoAtual);
        }
    }
    /** 
     * Calcula as adjascências de um estado no grafo.
     * @param estado Estado a ter as adjascências calculadas.
     * @param fila Fila para colocar os adjascentes que não 
     *        estão no grafo.
     */
    private void calculaAdjascencias(Estado estado, List<Estado> fila){
        int x = estado.getX();
        int y = estado.getY();
        List<Estado> vizinhos = labirinto.calculaEstadosPossiveis(y, x);
        Estado vertice;

        for (Estado vizinho : vizinhos)
        {
            vertice = getVertice(vizinho);
            if (vertice == null) {
                estado.getAdjascentes().add(vizinho);
                fila.add(vizinho);
            } else
                estado.getAdjascentes().add(vertice);
        }
    }
    /** 
     * Retorna o vértice passado por parâmetro.
     * @param estado Vértice a ser procurado.
     * @return Vértice v se encontrou no grafo,
     *         null caso contrário.
     */
    public Estado getVertice(Estado estado){
        for (Estado v : listaVertices)
            if(estado.equals(v))
                return v;
        return null;
    }
    public List<Estado> getListaVertices() { return listaVertices; }
    public int getSize(){ return listaVertices.size(); }
    /** 
     * Verifica se o labirinto tem solução.
     * @param estadoFinal Estado final do ambiente.
     * @return true se o estado final está no grafo,
     *         falso caso contrário.
     */
    public boolean temSolucao(Estado estadoFinal) {
        /* Podemos usar o contains() pois ele chama o equals(),
        que nós demos Override para dizer que um estado é
        igual a outro se o atributo posição for igual. */
        if(listaVertices.contains(estadoFinal))
            return true;
        return false;
    }
}
