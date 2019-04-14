package jogoDos8;

import java.util.*;

public class Main {

    public static Map<Integer, Integer> MAP_OBJETIVO_POSICAO = new HashMap<>();
    public static Nodo OBJETIVO;

    public static void main(String[] args) {
        System.out.println("Bem vindo ao jogo dos 8!");
        Algoritmo algoritmo = defineAlgoritmo();
        Nodo estadoFinal = defineEstadoFinal();
        Nodo estadoInicial = geraEstadoInicial();

        Tabuleiro tabuleiro = new Tabuleiro(estadoInicial, estadoFinal, algoritmo);

        try {
            List<Nodo> caminho = tabuleiro.acharCaminho();
            imprimeCaminho(caminho);
        } catch (Exception erro) {
            System.out.println("Algum erro aconteceu!" + erro);
        }
    }

    // Retorna o Nodo final (objetivo) do jogo. O nodo objetivo foi definido de acordo com os criterios do trabalho,
    // onde a sequencia eh crescente, da esquerda para a direita, de cima para baixo, e o nodo vazio (zero) eh o ultimo.
    private static Nodo defineEstadoFinal() {
        // Definimos a ordem dos quadrados do estado final.
        ArrayList<Integer> quadrados = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0));

        // O estado final tem custo zero por padrao.
        int custo = 0;

        // Criamos o nodo de estado final.
        Nodo estadoFinal = new Nodo(quadrados, custo);

        OBJETIVO = estadoFinal;

        for (int i = 0; i < quadrados.size(); i++) {
            MAP_OBJETIVO_POSICAO.put(quadrados.get(i), i);
        }

        // Retornamos o estado final.
        return estadoFinal;
    }

    private static Algoritmo defineAlgoritmo() {
        // TODO utilizar esse metodo para ler uma das 3 opcaoes possiveis atravez de input do usuario.
        //Scanner scaner = new Scanner(System.in);

        //scaner.next();

        return Algoritmo.CUSTO_UNIFORME;
    }

    private static Nodo geraEstadoInicial() {
        // TODO utilizar esse metodo para gerar um estado inicial inicial aleatorio.
        // Definimos a ordem dos quadrados do estado inicial aleatoriamente.
        ArrayList<Integer> quadrados = new ArrayList<>(Arrays.asList(1, 0, 3, 4, 2, 6, 7, 5, 8));

        // O estado inicial tem custo zero.
        int custo = 0;

        // Criamos o nodo de estado inicial.
        Nodo estadoInicial = new Nodo(quadrados, custo);

        // Retornamos o estado inicial.
        return estadoInicial;
    }

    private static void imprimeCaminho(List<Nodo> caminho) {
        System.out.println("Tamanho do caminho total percorrido: " +  caminho.size());
        System.out.println("Caminho percorrido:");

        for(Nodo nodo : caminho){
            imprimeNodo(nodo);
        }
    }

    public static void imprimeNodo(Nodo nodo) {
        int posicaoLinha = 1;

        for(int posicao : nodo.getQuadrados()) {
            // Printa a parte superior do quadrado.
            if (posicaoLinha == 1) System.out.println("+---+---+---+");

            // Printa o numero da posicao, ou um espaco em branco, se for '0'.
            System.out.print("| " + (posicao != 0 ? posicao : " ") + " ");

            // Verifica se devemos passar para a proxima linha e incrementamos a posicao atual em que estamos na
            // linha atual (incrementamos a coluna em que estamos, no caso, que eh a posicao).
            if (posicaoLinha++ == 3) {
                // Reseta o contador de linhas.
                posicaoLinha = 1;
                // Printa a ultima barra da linha do quadrado.
                System.out.println("|");
            }
        }
        // Printa a parte inferior do quadrado e quebra uma linha para separar do proximo caminho.
        System.out.println("+---+---+---+\n");
    }
}
