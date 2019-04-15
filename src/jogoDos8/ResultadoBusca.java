package jogoDos8;

import java.util.ArrayList;

import static jogoDos8.Main.imprimeCaminho;

public class ResultadoBusca {
    private final ArrayList<Nodo> nodos;
    private final ArrayList<Nodo> estadosVisitados;
    private final int maiorTamanhoDaFronteira;

    public ResultadoBusca(ArrayList<Nodo> nodos, ArrayList<Nodo> estadosVisitados, int maiorTamanhoDaFronteira) {
        this.nodos = nodos;
        this.estadosVisitados = estadosVisitados;
        this.maiorTamanhoDaFronteira = maiorTamanhoDaFronteira;
    }

    public void imprimeTela() {
        System.out.println("Resultado da busca: ");
        System.out.println("Numero de nodos visitados: " + this.estadosVisitados.size());
        System.out.println("Maior tamanho da fronteira: " + this.maiorTamanhoDaFronteira);
        System.out.println("Nodos visitados:");
        imprimeCaminho(this.nodos);
    }
}
