package jogoDos8;

import java.util.ArrayList;

public class Caminho implements Comparable<Caminho> {
    private final ArrayList<Nodo> nodos;

    public Caminho(ArrayList<Nodo> nodos) {
        this.nodos = nodos;
    }

    public Caminho(Nodo nodo) {
        this.nodos = new ArrayList<>();
        this.nodos.add(nodo);
    }

    public int getCustoTotal() {
        if (nodos == null){
            return 0;
        }

        int custoTotal = 0;

        for (Nodo nodo: this.nodos) {
            custoTotal += nodo.getCusto();
        }

        return custoTotal;
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    @Override
    public int compareTo(Caminho caminho) {
        Integer custo = this.getCustoTotal();
        Integer custoOutroCaminho = caminho.getCustoTotal();

        return custo.compareTo(custoOutroCaminho);
    }

    @Override
    public boolean equals(Object obj) {
        // Se o objeto for `null` retorna `falso`.
        if (obj == null) {
            return false;
        }
        // Se o objeto nao for do tipo `Caminho` retorna `falso`.
        if (!Caminho.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        // Cria um `Caminho` com o objeto passado.
        final Caminho caminhoComparado = (Caminho) obj;

        // Pega os Nodos do Caminho criado.
        final ArrayList<Nodo> nodosComparados = caminhoComparado.getNodos();

        // Se os nodos a serem comparados nao tiverem o mesmo tamanho retorna `falso`.
        if (this.nodos.size() != nodosComparados.size()) {
            return false;
        }

        // Itera por todos os nodos e se achar um que seja diferente entre, retorna `falso`.
        for (int i = 0; i < this.nodos.size(); i++) {
            if (!nodos.get(i).equals(nodosComparados.get(i))) return false;
        }

        // Se chegou ate aqui, entao os dois nodos comparados sao iguais, retorna `true`.
        return true;
    }

    @Override
    public String toString() {
        return this.nodos.toString();
    }
}
