package jogoDos8;

import java.util.*;

public class Tabuleiro {
    private final Nodo estadoInicial;
    private final Nodo estadoFinal;
    private final ArrayList<Nodo> estadosVizitados;
    private final ArrayList<Caminho> fronteira;
    private  Nodo estadoAtual;
    private int maiorTamanhoDaFronteira;

    public Tabuleiro(Nodo estadoInicial, Nodo estadoFinal) {
        this.estadoInicial = estadoInicial;
        this.estadoAtual = estadoInicial;
        this.estadoFinal = estadoFinal;
        this.estadosVizitados = new ArrayList<>();
        this.fronteira = new ArrayList<>();
        this.maiorTamanhoDaFronteira = 0;
    }

    public ResultadoBusca acharCaminho() {
        // Adicionamos o estado atual na fronteira (caminho percorrido ate entao).
        Caminho caminhoAtual = new Caminho(this.estadoAtual);

        this.fronteira.add(caminhoAtual);

        // Chamamos `acharCaminho` passando o estado atual.
        return buscaObjetivo();
    }

    private ResultadoBusca buscaObjetivo() {
        while (!this.estadoAtual.equals(this.estadoFinal) && (this.fronteira.size() > 0)) {

            //System.out.println("Tamanho da fronteira: " + this.fronteira.size());

            // Atualiza o maior tamanho da fronteira caso a fronteira atual seja maior que o maior ja estabelecido.
            if (this.fronteira.size() > this.maiorTamanhoDaFronteira) {
                this.maiorTamanhoDaFronteira = this.fronteira.size();
            }

            // Adicionamos o estado atual passado na lista de estados já vizitados.
            adicionaNosEstadosVisitados(this.estadoAtual);

            // pega o menor caminho (primeira posicao na fronteira, pois a mesma eh ordenada)
            Caminho menorCaminho = this.fronteira.get(0);

            // Expande o estado atual e pega os seus filhos filhos (ja ordenados) criados.
            ArrayList<Nodo> filhos = this.estadoAtual.expandeNodo(menorCaminho.getNodos());

            if (filhos.size() > 0) {
                ArrayList<Caminho> fronteirasExpandidas = new ArrayList<>();

                for (Nodo filho: filhos) {
                    ArrayList<Nodo> nodosEspandidos = new ArrayList<>();
                    nodosEspandidos.addAll(menorCaminho.getNodos());
                    nodosEspandidos.add(filho);

                    Caminho caminhoExpandido = new Caminho(nodosEspandidos);

                    fronteirasExpandidas.add(caminhoExpandido);
                }

                this.fronteira.remove(0);
                this.fronteira.addAll(fronteirasExpandidas);
                Collections.sort(this.fronteira);

                Caminho novoMenorCaminho = this.fronteira.get(0);
                ArrayList<Nodo> nodosNovoMenorCaminho = novoMenorCaminho.getNodos();

                this.estadoAtual = nodosNovoMenorCaminho.get(nodosNovoMenorCaminho.size() - 1);

                //System.out.println("Nodos do novo melhor caminho: " + nodosNovoMenorCaminho.toString());
                //System.out.println("Estado atual atualizado! estado atual: " + this.estadoAtual.toString());
            } else {
                // Se chegamos aqui, eh porque nao estamos no estado final, e o nodo atual nao tem mais filhos
                // para serem espandidos, chegamos num beco sem saida. Esse estado nao deveria acontecer, e seria um
                // erro. Caso acontece, simplesmente printamos uma mensagem na tela e retornamos o menor caminho
                // gerado ate entao so para retornar algo.
                this.fronteira.remove(0);
                Caminho novoMenorCaminho = this.fronteira.get(0);
                ArrayList<Nodo> nodosNovoMenorCaminho = novoMenorCaminho.getNodos();

                this.estadoAtual = nodosNovoMenorCaminho.get(nodosNovoMenorCaminho.size() - 1);
//                System.out.println("Nao foi possivel encontrar o nodo objetivo. Nosso algoritmo tem algo de errado!");
//                System.out.println("Numero de nodos ja vizitados: " + this.estadosVizitados.size());
//                return this.fronteira.get(0).getNodos();
            }
        }

        // Cria um resultado de busca.
        ResultadoBusca resultado = new ResultadoBusca(fronteira.get(0).getNodos(), this.estadosVizitados, this.maiorTamanhoDaFronteira);

        // Retorna o resultado de busca criado.
        return resultado;
    }

    private void adicionaNosEstadosVisitados(Nodo estado) {
        // Verifica se o estado ja esta inserido na lista de estados vizitados antes de adicionar,
        // se ja esta contido nela, nao faz nada.
        if (!this.estadosVizitados.contains(estado)) {
            this.estadosVizitados.add(estado);
        }
    }
}
