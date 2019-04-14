package jogoDos8;

public enum Algoritmo {
    CUSTO_UNIFORME("Custo uniforme"),
    A_ESTRELA_SIMPLES("A* simples"),
    A_ESTRELA_MELHORADO("A* melhorado");

    private final String algoritmo;

    Algoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public String getDescricao() {
        return this.algoritmo;
    }
}
