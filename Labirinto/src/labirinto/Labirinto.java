/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Saphira
 */
public class Labirinto {
    private int largura;
    private int altura;
    private Tile[][] cells;
    
    private Posicao start;
    private Posicao exit;
    private Agente agent;
    
    public Labirinto (int x, int y){
        largura = x;
        altura = y;
        cells = randomizedPrim();
        start = null;
        exit = null;
        agent = null;
    }
    /** 
     * Algoritmo de Prim Randomizado para gerar um labirinto
     * https://en.wikipedia.org/wiki/Maze_generation_algorithm
     */
    private Tile[][] randomizedPrim(){
        Random rand = new Random();
        Tile[][] auxGrid = new Tile[altura][largura];
        
        /* Inicializa todo tile como sendo parede */
        for(int i=0; i < altura; i++)
            for(int j=0; j < largura; j++)
                auxGrid[i][j] = new Tile('X', i, j);

        /* Salva quem são os vizinhos válidos de cada Tile
        (para não precisar ficar checando se está saindo do
        labirinto depois) */
        for(int i=0; i < altura; i++)
            for(int j=0; j < largura; j++){
                if(i-1 >= 0)
                    auxGrid[i][j].addNeighboor(auxGrid[i-1][j]);
                if(j-1 >= 0)
                    auxGrid[i][j].addNeighboor(auxGrid[i][j-1]);
                if(i+1 < altura)
                    auxGrid[i][j].addNeighboor(auxGrid[i+1][j]);
                if(j+1 < largura)
                    auxGrid[i][j].addNeighboor(auxGrid[i][j+1]);
            }
        
        /* Pega uma célula qualquer, marca como parte do labirinto. */
        int i = rand.nextInt(altura);
        int j = rand.nextInt(largura);
        auxGrid[i][j].setType(' ');
    
        /* Coloca os vizinhos dessa célula numa lista. */
        List<Tile> walls = new LinkedList<>();
        for (Tile neighboor: auxGrid[i][j].getNeighboors())
            walls.add(neighboor);
        
        /* Enquanto existem paredes na lista */
        while(!walls.isEmpty()){
            /* Escolhe uma parede aleatória*/
            Tile wall = walls.get(rand.nextInt(walls.size()));
            i = wall.getPosicao().getX();
            j = wall.getPosicao().getY();

            int caminhosPossiveis = 0;
            for (Tile neighboor: auxGrid[i][j].getNeighboors())
                if (neighboor.isPath())
                    caminhosPossiveis++;
            
            if (caminhosPossiveis == 1){
                /* Se só tem um caminho vizinho transforma a
                parede em um caminho e coloca as paredes
                vizinhas na lista */
                auxGrid[i][j].setType(' ');
                
                for (Tile neighboor: auxGrid[i][j].getNeighboors())
                    if (neighboor.isWall())
                        walls.add(neighboor);
            }
            /* Remove a parede da lista */
            walls.remove(wall);
        }
        return auxGrid;
    }
    public void print(){
        String linhaSeparadora = "+";
        for(int j=0; j < largura; j++)
            linhaSeparadora = linhaSeparadora.concat("---+");
        
        for(int i=0; i < altura; i++){
            System.out.println(linhaSeparadora);
            System.out.print("|");
            for(int j=0; j < largura; j++){
                if (cells[i][j].isWall())
                    System.out.print("XXX|");
                else
                    System.out.print(" " + cells[i][j].getType() + " |");
            }
            System.out.println();
        }
        System.out.println(linhaSeparadora);
    }
    public void setStart(int x, int y){
        cells[x][y].setType('S');
        start = new Posicao(x,y);
    }
    public void setExit(int x, int y){
        cells[x][y].setType('E');
        exit = new Posicao(x,y);
    }
    public Posicao getStart(){
        return start;
    }
    public Posicao getExit(){
        return exit;
    }
}
