/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Saphira
 */
public class T1_Labirinto {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Labirinto lab = null;
        char[][] matrix = null;
        
        System.out.println("(1) Matriz personalizada");
        System.out.println("(2) Randomizado");
        char option = input.next().charAt(0);
        
        System.out.println();
        if (option == '1') {
            System.out.println("A primeira linha do arquivo deve conter 2 "
                    + "números no formato (N M), representando o número de "
                    + "linhas e colunas do labirinto.");
            System.out.println("As próximas N linhas devem conter a matriz "
                    + "propriamente dita, obedecendo a seguinte legenda:");
            System.out.println(" 0 - caminho");
            System.out.println(" 1 - parede");
            System.out.println(" S - posição de spawn/início");
            System.out.println(" E - final");
            System.out.println();
            System.out.println("Digite o nome do arquivo contendo a matriz: ");
            String filename = input.next();
            
            matrix = getMatrixFromFile(filename);
            lab = new Labirinto(matrix.length, matrix[0].length, matrix);
        } else if (option == '2') {
            System.out.println("N = número de linhas");
            System.out.println("M = número de colunas");
            System.out.println("Tamanho (N M):");
            int y = input.nextInt();
            int x = input.nextInt();
            System.out.println();
            System.out.println("Gerando matriz de " +y+ "x" +x+ "...");
            lab = new Labirinto(y, x);
        } else {
            System.out.println("Opcao invalida. Saindo...");
            System.exit(3);
        }

        lab.show();
        System.out.println("Inserindo agente...");
        Agente agente = new Agente(lab);
        agente.run();
    }

    public static char[][] getMatrixFromFile(String filename){
        char[][] matrix = null;
        boolean inicio=false, fim=false;
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));
            
            /* Lê as dimensões da matriz */
            String[] dimensions = file.readLine().split(" ");
            int altura = Integer.parseInt(dimensions[0]);
            int largura = Integer.parseInt(dimensions[1]);

            matrix = new char[altura][largura];
            
            /* Lê os caracteres (ignorando espaços) e coloca na matriz */
            String line;
            for(int i=0; i < altura; i++)
            {
                line = file.readLine().replace(" ", "");
                for(int j=0; j < largura; j++)
                {
                    matrix[i][j] = line.charAt(j);
                    if(matrix[i][j] == 'S')
                        inicio = true;
                    else if (matrix[i][j] == 'E')
                        fim = true;
                }
            }
            if(!inicio || !fim) {
                System.out.println("Ponto de início ou fim não reconhecidos.");
                System.out.println("Verifique se a matriz do seu arquivo possui "
                        + "exatamente um 'S' e um 'E' e tente novamente.");
                System.exit(3);
            }
                
                
        } catch (FileNotFoundException ex) {
            System.err.println("Arquivo não encontrado!");
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Problema no readLine.");
            System.err.println("Arquivo vazio ou número de linhas na matriz "
                    + "não condiz com o especificado na primeira linha do "
                    + "arquivo.");
            System.out.println("Saindo...");
            System.exit(2);
        }
        return matrix;
    }
}
