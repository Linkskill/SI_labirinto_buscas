/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Saphira
 */
public class Labirinto {
    private final int altura;
    private final int largura;
    private char[][] cells;
    private Agente agente;
    private Estado start;
    private Estado exit;
    
    public Labirinto (int alt, int larg, char[][] matrix){
        altura = alt;
        largura = larg;
        cells = matrix;
        /* Salva o ponto de start e exit para fácil acesso */
        for(int i=0; i < altura; i++)
            for(int j=0; j < largura; j++)
                if(cells[i][j] == 'S') 
                    start = new Estado(i, j);
                else if(cells[i][j] == 'E')
                    exit = new Estado(i, j);
        agente = null;
    }
    public Labirinto (int alt, int larg){
        altura = alt;
        largura = larg;
        cells = geraMatrizPrim(alt, larg);
        /* Salva o ponto de start e exit para fácil acesso */
        for(int i=0; i < altura; i++)
            for(int j=0; j < largura; j++)
                if(cells[i][j] == 'S') 
                    start = new Estado(i, j);
                else if(cells[i][j] == 'E')
                    exit = new Estado(i, j);
        agente = null;
    }
    /**
     * Posiciona o agente na matriz (se existir
     * um agente) e chama o método print().
     */
    public void show(){
        if(agente != null) {
            /* Antes de imprimir coloca o agente na matriz. Depois
            retorna ela à representação original. */
            int xAtual = agente.getEstadoAtual().getX();
            int yAtual = agente.getEstadoAtual().getY();

            char original = cells[yAtual][xAtual];
            cells[yAtual][xAtual] = 'A';
            print();
            cells[yAtual][xAtual] = original;
        } else {
            /* Se o agente ainda não está funcionando, somente
            imprime o labirinto */
            print();
        }
    }
    /**
     * Imprime a grid do labirinto.
     * Legenda:
     *   "   " = posição acessível
     *   "XXX" = parede
     *   " S " = start
     *   " E " = exist
     *   " A " = agente
     */
    private void print(){
        String linhaSeparadora = "+";
        for(int j=0; j < largura; j++)
            linhaSeparadora = linhaSeparadora.concat("---+");
        
        /* Número das colunas */
        System.out.print("\n ");
        for(int j=0; j < largura; j++)
            System.out.print(" " + j + "  ");
        System.out.println("\n" + linhaSeparadora);
        
        /* Número das linhas e o labirinto em si */
        for(int i=0; i < altura; i++){
            System.out.print("|");
            for(int j=0; j < largura; j++)
                switch (cells[i][j]) {
                    case '0':
                        System.out.print("   |");
                        break;
                    case '1':
                        System.out.print("XXX|");
                        break;
                    default:
                        System.out.print(" " + cells[i][j] + " |");
                        break;
                }
            System.out.println(" " + i);
            System.out.println(linhaSeparadora);
        }
        System.out.println();
    }
    /**
     * Para cada vizinho acessível, salva o estado
     * correspondente em uma lista, caso contrário
     * ignora.
     * @param row Linha/coordenada y a ser avaliada
     * @param col Coluna/coordenada x a ser avaliada
     * @return Lista de estados representando os destinos possíveis.
     */
    public List<Estado> calculaEstadosPossiveis(int row, int col) {
        List<Estado> estados = new ArrayList<>();
        boolean isCoordinate;
        
        for(int i=row-1; i <= row+1; i++)
            for(int j=col-1; j <= col+1; j++)
            {
                isCoordinate = (i == row && j == col);
                if (!isCoordinate && isAccessible(i, j))
                    estados.add(new Estado(i, j));
            }
        return estados;
    }
    /** 
     * Indica se a posição (row,col) na grid é acessível.
     * @param row Linha a ser analisada.
     * @param col Coluna a ser analisada.
     * @return True se cells[row][col] não for uma parede
     *         nem estiver fora dos limites, false caso
     *         contrário.
     */
    public boolean isAccessible(int row, int col) {
        if(row < 0 || col < 0 || row >= altura || col >= largura
            || cells[row][col] == '1')
            return false;
        return true;
    }
    public void setAgente(Agente a){
        agente = a;
    }
    public Estado getStart(){ return start; }
    public Estado getExit() { return exit; }
    public char[][] getCells(){ return cells; }

    /** Adaptação do Algoritmo de Prim Randomizado
     * https://en.wikipedia.org/wiki/Maze_generation_algorithm.
     * http://weblog.jamisbuck.org/2011/1/10/maze-generation-prim-s-algorithm
     *     A implementação é um pouco diferente por estarmos lidando
     * com paredes que são células, e não paredes entre-células.
     * A principal diferença é o fato do termo "célula-vizinha"
     * significar com distância 2. Ou seja, dado uma célula
     * cells[y][x], suas células vizinhas são: cells[y-2][x],
     * cells[y+2][x], cells[y][x-2] e cells[y][x+2].
     * @param larg Largura da matriz a ser gerada.
     * @param alt Altura da matriz a ser gerada.
     * @return Matriz de chars onde '1' significa parede,
     *         '0' significa caminho, 'S' significa início
     *         e 'E' significa saída.
     */
    public static char[][] geraMatrizPrim(int alt, int larg){
        Random rand = new Random();
        char[][] auxGrid = new char[alt][larg];
        
        /* Inicializa toda célula como sendo parede */
        for(int i=0; i < alt; i++)
            for(int j=0; j < larg; j++)
                auxGrid[i][j] = '1';

        /* Escolhe uma aleatória para ser o início. Coloca
        as paredes "vizinhas" dessa célula numa lista
        de células que ainda não fazem parte do labirinto,
        mas que são "vizinhas" de alguma que faz parte. */
        int xStart, yStart;
        yStart = rand.nextInt(alt);
        xStart = rand.nextInt(larg);
        auxGrid[yStart][xStart]= 'S';

        List<Coordenada> fronteiras = new LinkedList<>();
        if (yStart-2 >= 0 && auxGrid[yStart-2][xStart] == '1')
            fronteiras.add(new Coordenada(yStart-2, xStart));
        if (yStart+2 < alt && auxGrid[yStart+2][xStart] == '1')
            fronteiras.add(new Coordenada(yStart+2, xStart));
        if (xStart-2 >= 0 && auxGrid[yStart][xStart-2] == '1')
            fronteiras.add(new Coordenada(yStart, xStart-2));
        if (xStart+2 < larg && auxGrid[yStart][xStart+2] == '1')
            fronteiras.add(new Coordenada(yStart, xStart+2));
        
        int x, y, xVizinho, yVizinho;
        Coordenada atual, vizinho;
        List<Coordenada> vizinhosAcessiveis = new LinkedList<>();
        List<Coordenada> paredesVizinhas = new LinkedList<>();
        while(!fronteiras.isEmpty())
        {
            /* Escolhe e remove uma parede aleatória da lista. */
            atual = fronteiras.get(rand.nextInt(fronteiras.size()));
            x = atual.getX();
            y = atual.getY();

            /* Verifica as células "vizinhas", separando quais são
            são acessíveis e quais são paredes. */
            vizinhosAcessiveis.clear();
            paredesVizinhas.clear();
            if (y-2 >= 0) {
                if (auxGrid[y-2][x] != '1')
                    vizinhosAcessiveis.add(new Coordenada(y-2, x));
                else
                    paredesVizinhas.add(new Coordenada(y-2, x));
            }
            if (y+2 < alt) {
                if (auxGrid[y+2][x] != '1')
                    vizinhosAcessiveis.add(new Coordenada(y+2, x));
                else
                    paredesVizinhas.add(new Coordenada(y+2, x));
            }
            if (x-2 >= 0) {
                if (auxGrid[y][x-2] != '1')
                    vizinhosAcessiveis.add(new Coordenada(y, x-2));
                else
                    paredesVizinhas.add(new Coordenada(y, x-2));
            }
            if (x+2 < larg) {
                if (auxGrid[y][x+2] != '1')
                    vizinhosAcessiveis.add(new Coordenada(y, x+2));
                else
                    paredesVizinhas.add(new Coordenada(y, x+2));
            }

            /* Escolhe aleatoriamente um dos "vizinhos" acessíveis. */
            vizinho = vizinhosAcessiveis.get(rand.nextInt(vizinhosAcessiveis.size()));
            
            /* Conecta o "vizinho" à célula atual, transformando a
            célula entre eles em um caminho, assim como a própria
            célula atual. */
            xVizinho = vizinho.getX();
            yVizinho = vizinho.getY();
            auxGrid[(yVizinho+y)/2][(xVizinho+x)/2] = '0';
            auxGrid[y][x] = '0';
            
            /* Coloca as paredes-"vizinhas" da célula atual
            na lista de paredes, se elas já não estiverem la. */
            for(Coordenada p : paredesVizinhas)
                if (!fronteiras.contains(p)) //funciona por causa do override no equals
                    fronteiras.add(p);

            /* Quando for remover o último elemento da fila,
            marca como final do labirinto. */
            if(fronteiras.size() == 1)
                auxGrid[y][x] = 'E';
            
            fronteiras.remove(atual);
        }
        return auxGrid;
    }
}