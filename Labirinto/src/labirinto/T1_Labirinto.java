/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

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
        Scanner reader = new Scanner(System.in);
        int x, y;

        System.out.println("Tamanho do labirinto (X Y): ");
        x = reader.nextInt();
        y = reader.nextInt();

        Labirinto lab = new Labirinto(x, y);
        lab.print();
        
        System.out.println("Inicio (X Y): ");
        x = reader.nextInt();
        y = reader.nextInt();
        lab.setStart(x, y);
        
        System.out.println("Fim (X Y): ");
        x = reader.nextInt();
        y = reader.nextInt();
        lab.setExit(x, y);

        lab.print();
        
        Agente agente = new Agente(lab);
        agente.run();
    }
    
}
