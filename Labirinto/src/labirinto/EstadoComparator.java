/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.Comparator;

/**
 *
 * @author Saphira
 */
public class EstadoComparator implements Comparator<Estado> {
    @Override
    public int compare(Estado e1, Estado e2) {
        //https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
        //O primeiro elemento de PriorityQueue é o menor elemento
        // em relação à ordem especificada. Se a ordem é natural,
        // (1<2<3<4...), funciona como uma lista de prioridades
        // mínima (maior prioridade = valor mais baixo).
        
        // Para definir um ordem natural, precisamos retornar:
        //  inteiro negativo se e1 vem antes de e2
        //  0 se e1 e e2 forem iguais
        //  inteiro positivo se e2 vem antes de e1
        return (int) (e1.getF() - e2.getF());
    }
}
