package br.com.joaopedro.objective.model;


import jakarta.persistence.Table;

@Table(name = "pagamento_tipos")
public enum PagamentoTipo {
    P(0.00), C(0.05), D(0.03);

    private final double taxa;

    PagamentoTipo(double taxa) {
        this.taxa = taxa;
    }

    public double getTaxa() {
        return taxa;
    }
}
