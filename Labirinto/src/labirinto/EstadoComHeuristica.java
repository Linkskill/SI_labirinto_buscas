package labirinto;

public class EstadoComHeuristica {
    private Estado estado;
    private double prioridade;
    
    public EstadoComHeuristica(Estado e, double p){
        estado = e;
        prioridade = p;
    }
    public Estado getEstado() { return estado; }
    public double getPrioridade() { return prioridade; }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public void setPrioridade(double prioridade) {
        this.prioridade = prioridade;
    }
}
