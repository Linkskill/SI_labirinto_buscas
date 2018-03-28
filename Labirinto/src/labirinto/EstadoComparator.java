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
public class EstadoComparator implements Comparator<EstadoComHeuristica> {
    @Override
    public int compare(EstadoComHeuristica e1, EstadoComHeuristica e2) {
        // 0 se for igual, positivo se prioridade do e1 é menor
        // do que e2, negativo se prioridade do e2 é menor do que e1
        return (int) (e2.getPrioridade() - e1.getPrioridade());
    }
}
