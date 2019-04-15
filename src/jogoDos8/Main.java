package jogoDos8;

import java.util.*;

public class Main {

    public static Map<Integer, Integer> MAP_OBJETIVO_POSICAO = new HashMap<>();
    public static Nodo OBJETIVO;
    public static Algoritmo AlGORITMO;

    public static void main(String[] args) {
        System.out.println("Bem vindo ao jogo dos 8!");

        defineAlgoritmo();
        Nodo estadoFinal = defineEstadoFinal();
        Nodo estadoInicial = geraEstadoInicial();

        System.out.println("Algoritmo definido: " + AlGORITMO.getDescricao());
        System.out.println("Estado inicial aleatorio gerado:");
        imprimeNodo(estadoInicial);

        Tabuleiro tabuleiro = new Tabuleiro(estadoInicial, estadoFinal);

        try {
            ResultadoBusca resultadoBusca = tabuleiro.acharCaminho();
            resultadoBusca.imprimeTela();
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

    private static void defineAlgoritmo() {
        System.out.println("Defina qual sera o algoritmo utilizado. (Digite o numero correspondente)");

        // Pegamos todos os algoritmos disponiveis definidos.
        Algoritmo[] algoritmos = Algoritmo.values();
        // Inicializamos o contador de algoritmo utilizado para imprimir na tela todos os algoritmos com `1`;
        int contador = 1;

        // para cada algoritmo disponivel, imprimimos a descricao dele na tela junto com seu numero correspondente
        for (Algoritmo algoritmo : algoritmos) {
            System.out.println((contador++) + " - " + algoritmo.getDescricao());
        }

        // Criamos um `Scanner` para ler a opcao digitada pelo usuario.
        Scanner scaner = new Scanner(System.in);

        // Lemos a opcao digitada
        int escolha = scaner.nextInt();

        // Se o valor digitado eh menor que `1` ou maior que o comprimento da lista de algoritmos menos um
        // (subtraimos `1` porque array comeca em `0`, e nao em `1`), entao definimos por padrao a escolha como
        // `0`, para pegar o primeiro algoritmo da lista, que no caso eh `CUSTO_UNIFORME`.
        if ( (escolha < 1) || (escolha > (algoritmos.length)) ) {
            escolha = 0;
        } else {
            // Se a escolha foi valida, subtraimos em `1` para pegarmos a posivao correta no array, ja que ele
            // comeca em `0` e nao em `1`.
            escolha = escolha - 1;
        }

        // Definimos qual o algoritmo escolhido.
        Algoritmo algoritmoEscolhido = algoritmos[escolha];

        // Seta a variavel statica ALGORITMO com o algoritmo escolhido.
        AlGORITMO = algoritmoEscolhido;
    }

    private static Nodo geraEstadoInicial() {
        // Definimos a ordem dos quadrados do estado inicial aleatoriamente.
        ArrayList<Integer> quadrados = new ArrayList<>(Arrays.asList(1, 3, 6, 4, 0, 2, 7, 5, 8));
//        ArrayList<Integer> quadrados = new ArrayList<>(Arrays.asList(
//                1, 3, 0,
//                7, 4, 5,
//                5, 8, 2)
//        );
        //ArrayList<Integer> quadrados = new ArrayList<>(Arrays.asList(5, 0, 3, 1, 8, 6, 7, 4, 2));
        // O estado inicial tem custo zero.
        int custo = 0;

        // Criamos o nodo de estado inicial.
        Nodo estadoInicial = new Nodo(quadrados, custo);

        // Retornamos o estado inicial.
        return estadoInicial;
    }

    public static void imprimeCaminho(List<Nodo> caminho) {
        for(Nodo nodo : caminho){
            imprimeNodo(nodo);
        }
    }

    private static void imprimeNodo(Nodo nodo) {
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
