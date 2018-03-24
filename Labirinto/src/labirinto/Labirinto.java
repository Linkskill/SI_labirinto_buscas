/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Saphira
 */
public class Labirinto {
    private final int largura;
    private final int altura;
    private char[][] cells;
    private Agente agente;

    private Estado start;
    private Estado exit;
    
    public Labirinto (int alt, int larg, char[][] matrix){
        largura = larg;
        altura = alt;
        cells = matrix;
        /* Salva a posição de spawn e final para acesso fácil posteriormente */
        for(int i=0; i < altura; i++)
            for(int j=0; j < largura; j++)
            {
                if(cells[i][j] == 'S') 
                    start = new Estado(new Posicao(i, j), this);
                else if(cells[i][j] == 'E')
                    exit = new Estado(new Posicao(i, j), this);
            }
        agente = null;
    }
    
    /** Adaptação do Algoritmo de Prim Randomizado
     * https://en.wikipedia.org/wiki/Maze_generation_algorithm
     * @param larg Largura da matriz a ser gerada.
     * @param alt Altura da matriz a ser gerada.
     * @return Matriz de chars onde '1' significa parede
     *         e '0' significa caminho.
     */
    public static char[][] genMatrix(int alt, int larg){
        Random rand = new Random();
        char[][] auxGrid = new char[alt][larg];
        
        /* Inicializa toda célula como sendo parede */
        for(int i=0; i < alt; i++)
            for(int j=0; j < larg; j++)
                auxGrid[i][j] = '1';

        /* Pega uma célula qualquer do lado superior esquerdo, e marca como spawn.*/
        int x = rand.nextInt(larg/2);
        int y = rand.nextInt(alt/2);
        auxGrid[y][x]= 'S';
        /* Coloca as paredes vizinhas dessa célula numa lista. */
        List<Posicao> walls = new LinkedList<>();
        for(int i=max(y-1, 0); i <= min(y+1, alt-1); i++)
            for(int j=max(x-1, 0); j <= min(x+1, larg-1); j++)
                walls.add(new Posicao(i, j));

        /* Pega uma célula qualquer do lado inferior direito e marca como saída. */
        int xExit = rand.nextInt(larg/2) + larg/2;
        int yExit = rand.nextInt(alt/2) + alt/2;
        auxGrid[yExit][xExit]= 'E';
        /* Coloca as paredes vizinhas dessa célula na lista. */
        for(int i=max(yExit-1, 0); i <= min(yExit+1, alt-1); i++)
            for(int j=max(xExit-1, 0); j <= min(xExit+1, larg-1); j++)
                walls.add(new Posicao(i, j));
            
        /* Enquanto existem paredes na lista */
        while(!walls.isEmpty()){
            /* Escolhe uma parede aleatória*/
            Posicao wall = walls.get(rand.nextInt(walls.size()));
            x = wall.getX();
            y = wall.getY();

            List<Posicao> caminhosVizinhos = new LinkedList<>();
            List<Posicao> paredesVizinhas = new LinkedList<>();
            for(int i=max(y-1, 0); i <= min(y+1, alt-1); i++)
                for(int j=max(x-1, 0); j <= min(x+1, larg-1); j++)
                    if (auxGrid[i][j] != '1')
                        caminhosVizinhos.add(new Posicao(i, j));
                    else
                        paredesVizinhas.add(new Posicao(i, j));
            
            if (caminhosVizinhos.size() <= 2){
                /* Se tem poucas maneiras de chegar até essa
                posição, transforma a parede em um caminho e
                coloca as paredes vizinhas na lista */
                auxGrid[y][x] = '0';
                
                for (Posicao p : paredesVizinhas)
                    walls.add(p);
            }
            /* Remove a parede da lista */
            walls.remove(wall);
        }
        return auxGrid;
    }
    
    /**
     * Posiciona o agente na matriz (se ele existir)
     * e chama o método print()..
     */
    public void show(){
        if(agente != null) {
            /* Antes de imprimir coloca o agente na matriz. Depois
            retorna ela à representação original. */
            int xAtual = agente.getEstadoAtual().getPosicao().getX();
            int yAtual = agente.getEstadoAtual().getPosicao().getY();
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
     * Imprime o labirinto.
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
        System.out.print(" ");
        for(int j=0; j < largura; j++)
            System.out.print(" " + j + "  ");
        System.out.println();
        System.out.println(linhaSeparadora);
        
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
    }
    /** 
     * @param row linha a ser verificada
     * @param col coluna a ser verificada
     * @return true se cells[row][col] é um caminho percorrível
     *         false caso sontrário
     */
    public boolean isAccessible(int row, int col) {
         /* Se estiver saindo dos limites do labirinto ou for
         uma parede, não pode ir para aquela posição. */
        if(row < 0 || col < 0 || row >= altura || col >= largura
            || cells[row][col] == '1')
            return false;
        else
            return true;
    }

    public void setAgente(Agente a){
        agente = a;
    }
    public Estado getStart(){
        return start;
    }
    public Estado getExit(){
        return exit;
    }
    public char[][] getCells(){
        return cells;
    }
}
