package jogoDos8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static jogoDos8.Main.*;

public class Nodo implements Comparable<Nodo> {
    private final ArrayList<Integer> quadrados;
    private final int custo;
    private Nodo nodoPai;
    private  ArrayList<Nodo> filhos;

    public Nodo(ArrayList<Integer> quadrados, int custo, Nodo nodoPai) {
        this.quadrados = quadrados;
        this.custo = custo;
        this.nodoPai = nodoPai;
    }

    public Nodo(ArrayList<Integer> quadrados, int custo) {
        this.quadrados = quadrados;
        this.custo = custo;
    }

    public Nodo(Nodo nodo) {
        this.quadrados = nodo.quadrados;
        this.custo = nodo.getCusto();
        this.filhos = nodo.filhos;
        this.nodoPai = nodo.nodoPai;
    }

    public ArrayList<Integer> getQuadrados() {
        return this.quadrados;
    }

    // Retorna em que posicao do tabuleiro esta o quadrado vazio.
    public Integer getPosicaoVazia() {
        for (int i = 0; i < quadrados.size(); i++) {
            if (quadrados.get(i) == 0) return i;
        }
        // Se nao achamas a posicao vazia returnamos "-1" (esse caso nunca deve ocorrer).
        return -1;
    }

    // Returna o custo do nodo atual em relacao ao nodo objetivo, segundo a nossa heuristica definida,
    // a qual simplesmente conta o numero de casas diferentes do estado atual para o estado objetivo.
    public int getCusto() {
        int custoTotal = this.custo;

        if (AlGORITMO.equals(Algoritmo.A_ESTRELA_SIMPLES)) {
            ArrayList<Integer> quadradosObjetivo = OBJETIVO.getQuadrados();
            for(int i = 0; i < this.quadrados.size(); i++){
                Integer quadrado = this.quadrados.get(i);
                Integer quadradoObjetivo = quadradosObjetivo.get(i);

                // Se o quadrado da posicao atual eh diferente do quadrado desta mesma posicao no nodo objetivo,
                // entao incrementamos o custo em uma unidade.
                if (quadrado != quadradoObjetivo){
                    custoTotal += 1;
                }
            }
        }

        if (AlGORITMO.equals(Algoritmo.A_ESTRELA_MELHORADO)) {
            ArrayList<Integer> quadradosObjetivo = OBJETIVO.getQuadrados();
            for(int i = 0; i < this.quadrados.size(); i++){
                Integer quadrado = this.quadrados.get(i);
                Integer quadradoObjetivo = quadradosObjetivo.get(i);

                // Se o quadrado da posicao atual eh diferente do quadrado desta mesma posicao no nodo objetivo,
                // entao incrementamos o custo de acordo com a distancia do quadrado em relacao ao seu destino.
                if (quadrado != quadradoObjetivo){
                    Integer posicaoAlvo = MAP_OBJETIVO_POSICAO.get(quadrado);

                    int distanciaHorizontal = Math.abs( (posicaoAlvo % 3) - (i % 3) );
                    int distanciaVertical =  Math.abs( (int)Math.floor(posicaoAlvo / 3) - (int)Math.floor(i / 3) );

                    custoTotal += (distanciaHorizontal + distanciaVertical);
                }
            }
        }

        return custoTotal;
    }

    // Returna o custo do nodo atual em relacao ao nodo objetivo, segundo a nossa heuristica definida,
    // a qual simplesmente conta o numero de casas diferentes do estado atual para o estado objetivo.
    public int getCustoTotal() {
        int custototal = this.custo;

//        if (this.filhos != null) {
//            int custoFilhos = this.filhos.get(0).getCustoTotal();
//            custototal += custoFilhos;
//        }
//
//
//        ArrayList<Integer> quadradosObjetivo = OBJETIVO.getQuadrados();
//        int custoEstado = 0;
//
//        for(int i = 0; i < this.quadrados.size(); i++){
//            Integer quadrado = this.quadrados.get(i);
//            Integer quadradoObjetivo = quadradosObjetivo.get(i);
//
//            // Se o quadrado da posicao atual eh diferente do quadrado desta mesma posicao no nodo objetivo,
//            // entao incrementamos o custo em uma unidade.
//            if (quadrado != quadradoObjetivo){
//                Integer posicaoAlvo = MAP_OBJETIVO_POSICAO.get(quadrado);
//
//                int distanciaHorizontal = Math.abs( (posicaoAlvo % 3) - (i % 3) );
//                int distanciaVertical =  Math.abs( (int)Math.floor(posicaoAlvo / 3) - (int)Math.floor(i / 3) );
//
//                custoEstado += (distanciaHorizontal + distanciaVertical);
//            }
//        }

        // Retorna o custo total.
        //return custoEstado;
        return custototal;
    }

    // gera os filhos que podem ser gerados a partir deste nodo. Os filhos sao todos os nodos
    // gerados trocando o zero (quadrado vazio) de lugar com as posicoes adjacentes a ele. O metodo recebe uma
    // lista de nodos vizitados, para cada filho gerado temos de excluir todos os que sejam iguais a algum dos
    // estados ja vizitados, pois nao faz sentido gerar um filho que seja igual a um estado ja vizitado.
    public ArrayList<Nodo> expandeNodo(ArrayList<Nodo> caminhoDeOrigem) {
        // Se os filhos já foram gerados anteriormente, simplesmente retornamos eles.
        if (this.filhos != null) {
            return this.filhos;
        }

        // Cria uma lista de filhos vazia.
        ArrayList<Nodo> filhos = new ArrayList<>();

        // Acha a posicao vazia.
        int posicaovazia = this.getPosicaoVazia();

        // Instancia um array lista para guardar as posicoes adjacentes a posivao onde o zero esta.
        ArrayList<Integer> posicoesAdjacentes = new ArrayList<>();

        // Dependendo de onde for a posicao vazia, retornamos um array de inteiros que representam, para aquela
        // posicao, qual posicoes sao as adjacentes, as posicoes retornadas sao de acordo com o quadrado 3x3 do jogo,
        // onde a posicao do array pode ser vista de acordo com esse esquema:
        // [0] [1] [2]
        // [3] [4] [5]
        // [6] [7] [8]
        switch(posicaovazia){
            case 0:
                posicoesAdjacentes.addAll(Arrays.asList(1, 3));
                break;
            case 1:
                posicoesAdjacentes.addAll(Arrays.asList(0, 2, 4));
                break;
            case 2:
                posicoesAdjacentes.addAll(Arrays.asList(1, 5));
                break;
            case 3:
                posicoesAdjacentes.addAll(Arrays.asList(0, 4, 6));
                break;
            case 4:
                posicoesAdjacentes.addAll(Arrays.asList(1, 3, 5, 7));
                break;
            case 5:
                posicoesAdjacentes.addAll(Arrays.asList(2, 4, 8));
                break;
            case 6:
                posicoesAdjacentes.addAll(Arrays.asList(3, 7));
                break;
            case 7:
                posicoesAdjacentes.addAll(Arrays.asList(4, 6, 8));
                break;
            case 8:
                posicoesAdjacentes.addAll(Arrays.asList(5, 7));
                break;
        }

        // Para cada posicao adjacente a posicao do zero do nodo, criamos um nodo novo e colocamos ele na lista
        // de filhos
        for (Integer posicao: posicoesAdjacentes) {
            // Primeiramente criamos ma copia das posicoes do nodo.
            ArrayList<Integer> quadradosCopiado = new ArrayList<>();
            quadradosCopiado.addAll(this.quadrados);

            // Salvamos o valor que estava armazenado na posicao que queremos trocar (colocar um zero)
            Integer valorSalvo = quadradosCopiado.get(posicao);

            // Colocamos zero na posicao que queremos trocar.
            quadradosCopiado.set(posicao, 0);

            // Colocamos o valor salvo na posicao original do zero
            quadradosCopiado.set(posicaovazia, valorSalvo);

            // Criamos um novo nodo com a posicao do zero ja trocada corretamente. O custo dos filhos eh iqual ao
            // custo do nodo mais um.
            Nodo filho = new Nodo(quadradosCopiado, (getCusto() + 1));

            // Adicionamos o filho criado na lista de filhos, primeiramente verificando se ele nao esta ja na
            // no caminho de onde esse nodo foi originado, evitando que ande em "circulos".
            if (!caminhoDeOrigem.contains(filho)) {
                filhos.add(filho);
            }
        }

        // Ordenamos a lista de filhos do menor para o maior.
        Collections.sort(filhos);

        // Adicionamos os filhos ordenados nos filhos do nodo, para simplesmente retornarmos eles se o metodo
        // for chamado de novo.
        this.filhos = filhos;

        // Retornamos a lista de filhos criados ordenados.
        return filhos;
    }

    @Override
    public int compareTo(Nodo nodo) {
        int custo = this.getCusto();
        int custoOutroNodo = nodo.getCusto();

        if (custo == custoOutroNodo) return 0;
        if (custo < custoOutroNodo) return -1;
        if (custo > custoOutroNodo) return 1;

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        // Se o objeto for `null` retorna `falso`.
        if (obj == null) {
            return false;
        }
        // Se o objeto nao for do tipo `Nodo` retorna `falso`.
        if (!Nodo.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        // Cria um nodo com o objeto passado.
        final Nodo nodoComparado = (Nodo) obj;

        // Pega os quadrados do nodo criado.
        final ArrayList<Integer> quadradosComparados = nodoComparado.getQuadrados();

        // Se os nodos a serem comparados nao tiverem o mesmo numero de quadrados retorna `falso`.
        if (this.quadrados.size() != quadradosComparados.size()) {
            return false;
        }

        // Itera por todos os quadrados e se achar um que seja diferente entre os nodos, retorna `falso`.
        for (int i = 0; i < this.quadrados.size(); i++) {
            if (quadrados.get(i) != quadradosComparados.get(i)) return false;
        }

        // Se chegou ate aqui, entao os dois nodos comparados sao iguais, retorna `true`.
        return true;
    }

    @Override
    public String toString() {
        return this.quadrados.toString();
    }
}
