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
        
        if (option == '1'){
            System.out.println("Para isso é necessário um arquivo de texto contendo a matriz.");
            System.out.println("- A primeira linha do arquivo deve conter 2 números, "
                    + "representando a largura e a altura do labirinto.");
            System.out.println("- As próximas linhas devem conter a matriz "
                    + "propriamente dita, obedecendo a seguinte legenda:");
            System.out.println(" 0 - caminho");
            System.out.println(" 1 - parede");
            System.out.println(" S - posição de spawn/início");
            System.out.println(" E - final");
            System.out.println();
            System.out.println("Digite o nome do arquivo contendo a matriz: ");
            String filename = input.next();
            
            matrix = getMatrixFromFile(filename);
            
            lab = new Labirinto(matrix[0].length, matrix.length, matrix);
        }
        else if (option == '2'){
            System.out.println("Tamanho (X Y): ");
            int x = input.nextInt();
            int y = input.nextInt();
            
            matrix = Labirinto.genMatrix(x, y);
            
            lab = new Labirinto(matrix[0].length, matrix.length, matrix);
        }
        else{
            System.out.println("Opcao invalida. Saindo...");
            System.exit(3);
        }
        lab.print(new Posicao(-1, -1));
        
        Agente agente = new Agente(lab);
        agente.run();
    }

    public static char[][] getMatrixFromFile(String filename){
        char[][] matrix = null;
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));
            
            /* Lê as dimensões da matriz */
            String[] dimensions = file.readLine().split(" ");
            int largura = Integer.parseInt(dimensions[0]);
            int altura = Integer.parseInt(dimensions[1]);

            matrix = new char[altura][largura];
            
            /* Lê os caracteres (ignorando espaços) e coloca na matriz */
            String line;
            for(int i=0; i < altura; i++){
                line = file.readLine().replace(" ", "");
                for(int j=0; j < largura; j++)
                    matrix[i][j] = line.charAt(j);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado!");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Problema no readLine.");
            System.out.println("Arquivo vazio ou número de linhas na matriz "
                    + "não condiz com o especificado na primeira linha do "
                    + "arquivo.");
            System.out.println("Saindo...");
            System.exit(2);
        }
        return matrix;
    }
}
