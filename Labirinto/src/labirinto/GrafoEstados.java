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
 * @author Gabriel Eugenio, Lincoln Batista e Jorge Straub
 */
public class GrafoEstados {
    private List<Estado> estados;
    private final Labirinto labirinto;

    public GrafoEstados(Labirinto lab){
        estados = new ArrayList<>();
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
            estados.add(estadoAtual);
        }
    }
    /** 
     * Calcula as adjascências de um estado no grafo.
     * @param estado Estado a ter as adjascências calculadas.
     * @param fila Fila para colocar os estados adjascentes
     *        que ainda não estão no grafo.
     */
    private void calculaAdjascencias(Estado estado, List<Estado> fila){
        int x = estado.getX();
        int y = estado.getY();
        List<Estado> vizinhos = labirinto.retornaEstadosPossiveis(estado);
        Estado vertice;

        boolean estaNoGrafo, estaNaFila;
        for (Estado vizinho : vizinhos)
        {
            estaNoGrafo = estados.indexOf(vizinho) != -1;
            estaNaFila = fila.indexOf(vizinho) != -1;
            // Se vizinho já está no grafo ou na fila,
            // referencia ele ao invés de criar um novo
            if (estaNoGrafo) {
                vertice = estados.get(estados.indexOf(vizinho));
                estado.adicionarAdjascencia(vertice, 1);
            } else if (estaNaFila) {
                vertice = fila.get(fila.indexOf(vizinho));
                estado.adicionarAdjascencia(vertice, 1);
            } else {
                estado.adicionarAdjascencia(vizinho, 1);
                fila.add(vizinho);
            }
        }
    }
    /** 
     * Procura por um vértice igual ao passado por
     * parâmetro no grafo.
     * @param estado Vértice a ser procurado.
     * @return Vértice v se encontrou no grafo,
     *         null caso contrário.
     */
    public Estado getEstado(Estado estado){
        for (Estado e : estados)
            if(estado.equals(e))
                return e;
        return null;
    }
    public List<Estado> getEstados() { return estados; }
    public int getSize(){ return estados.size(); }
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
        if(estados.contains(estadoFinal))
            return true;
        return false;
    }
}
