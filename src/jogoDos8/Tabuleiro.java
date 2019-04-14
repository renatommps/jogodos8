package jogoDos8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tabuleiro {
    private final Nodo estadoInicial;
    private final Nodo estadoFinal;
    private final Algoritmo algoritmo;
    private final ArrayList<Nodo> estadosVizitados;
    private final ArrayList<Nodo> fronteira;

    public Tabuleiro(Nodo estadoInicial, Nodo estadoFinal, Algoritmo algoritmo) {
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
        this.algoritmo = algoritmo;
        this.estadosVizitados = new ArrayList<>();
        this.fronteira = new ArrayList<>();
    }

    public List<Nodo> acharCaminho() {
        // Definimos o estado atual com o estado inicial.
        Nodo estadoAtual = this.estadoInicial;

        // Adicionamos o estado atual na fronteira (caminho percorrido ate entao).
        this.fronteira.add(estadoAtual);

        // Chamamos `acharCaminho` passando o estado atual.
        return acharCaminho(estadoAtual);
    }

    private void adicionaNosEstadosVisitados(Nodo estado) {
        // Verifica se o estado ja esta inserido na lista de estados vizitados antes de adicionar,
        // se ja esta contido nela, nao faz nada.
        if (!this.estadosVizitados.contains(estado)) {
            this.estadosVizitados.add(estado);
        }
    }

    private List<Nodo> acharCaminho(Nodo estadoAtual) {
        // Adicionamos o estado atual passado na lista de estados já vizitados.
        adicionaNosEstadosVisitados(estadoAtual);

        if (!estadoAtual.equals(this.estadoFinal)) {
            // Pega os filhos (ja ordenados) do nodo atual.
            ArrayList<Nodo> filhos = estadoAtual.getFilhos(this.estadosVizitados);

            while (filhos.size() > 0) {
                // se o estado atual tem pelo menos um filho, pegamos o primeiro (que eh o com o menor custo).
                Nodo menorFilho = filhos.remove(0);

                // Adicionamos o menor filho na fronteira (caminho percorrido ate entao).
                fronteira.add(menorFilho);

                // Se o nodo com o menor caminho nao eh o nodo objetivo, chamanos recursivamente o `acharCaminho`
                // passando ele como parametro.
                if (!menorFilho.equals(this.estadoFinal)) {
                    List<Nodo> caminho = acharCaminho(menorFilho);

                    // pegamos o ultimo filho gerado a partir desse caminho.
                    Nodo ultimoFilhoDoCaminho = caminho.get(caminho.size() - 1);

                    // Se o ultimo filho nao for o nodo objetivo, retornamos a fronteira gerada recursivamente,
                    // caso contrario, nao fazemos nada, pois o caminho gerado a partir desse filho nao levou
                    // ate o objetivo, e devemos novamente pegar o primeiro filho da lista de filhos, ate que a mesma
                    // esteja vazia.
                    if (ultimoFilhoDoCaminho.equals(this.estadoFinal)) {
                        return this.fronteira;
                    }
                } else {
                    return this.fronteira;
                }
            }
            System.out.println("nao achamos o objetivo :(");
        }

        System.out.println("nao achamos o objetivo :(");

        return this.fronteira;
    }
}
