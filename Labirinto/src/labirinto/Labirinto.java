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
    private Posicao start;
    private Posicao exit;
    private Agente agent;
    
    public Labirinto (int larg, int alt, char[][] matrix){
        largura = larg;
        altura = alt;
        cells = matrix;
        /* Salva a posição de spawn e final para acesso fácil posteriormente */
        for(int i=0; i < altura; i++)
            for(int j=0; j < largura; j++)
            {
                if(cells[i][j] == 'S')
                    start = new Posicao(j, i);
                else if(cells[i][j] == 'E')
                    exit = new Posicao(j, i);
            }
        agent = null;
    }
    /** Adaptação do Algoritmo de Prim Randomizado
     * https://en.wikipedia.org/wiki/Maze_generation_algorithm
     */
    public static char[][] genMatrix(int larg, int alt){
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
                walls.add(new Posicao(j, i));

        /* Pega uma célula qualquer do lado inferior direito e marca como saída. */
        int xExit = rand.nextInt(larg/2) + larg/2;
        int yExit = rand.nextInt(alt/2) + alt/2;
        auxGrid[yExit][xExit]= 'E';
        /* Coloca as paredes vizinhas dessa célula na lista. */
        for(int i=max(yExit-1, 0); i <= min(yExit+1, alt-1); i++)
            for(int j=max(xExit-1, 0); j <= min(xExit+1, larg-1); j++)
                walls.add(new Posicao(j, i));
            
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
                        caminhosVizinhos.add(new Posicao(j, i));
                    else
                        paredesVizinhas.add(new Posicao(j, i));
            
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

    public void print(Posicao agente){
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
            for(int j=0; j < largura; j++){
                if(agente.getY() == i && agente.getX() == j){
                    System.out.print(" A |");
                } else {
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
                }
            }
            System.out.println(" " + i);
            System.out.println(linhaSeparadora);
        }
    }
    public Posicao getStart(){
        return start;
    }
    public Posicao getExit(){
        return exit;
    }
    public char[][] getCells(){
        return cells;
    }
    public char getPos(Posicao pos){
        if(pos.getY() < 0 || pos.getX() < 0){
            return 'x';
        } else if(pos.getY() >= altura || pos.getX() >= largura){
            return 'x';
        } else return cells[pos.getY()][pos.getX()];
    }
}
