import javax.swing.JOptionPane;

public class main {

    public static void main(String[] args) {
        ArvoreBinaria arvoreBinaria = new ArvoreBinaria();
        Menu menu = new Menu(arvoreBinaria);
        menu.exibirMenu();
    }
}

class Menu {
    private ArvoreBinaria arvoreBinaria;
    private String menuText = "ARVURE BINARIA\n" +
            "1 - Adicionar Elemento\n" +
            "2 - Ver elementos na pré-ordem\n" +
            "3 - Ver elementos na pós-ordem\n" +
            "4 - Ver elementos em ordem\n" +
            "5 - Ver nível de um nó\n" +
            "6 - Ver profundidade de um nó\n" +
            "7 - Ver profundidade da árvore\n" +
            "8 - Ver altura de um nó\n" +
            "9 - Impressão da árvore com identação\n" +
            "99 - Sair";

    public Menu(ArvoreBinaria arvoreBinaria) {
        this.arvoreBinaria = arvoreBinaria;
    }

    public void exibirMenu() {
        int opcao = 0;
        while (opcao != 99) {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(menuText));
            switch (opcao) {
                case 1:
                    int num = Integer.parseInt(JOptionPane.showInputDialog("Digite um Número: "));
                    arvoreBinaria.adicionarElemento(num);
                    break;
                case 2:
                    arvoreBinaria.preOrdem();
                    break;
                case 3:
                    arvoreBinaria.posOrdem();
                    break;
                case 4:
                    arvoreBinaria.emOrdem();
                    break;
                case 5:
                    int valor = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor do Nó: "));
                    int nivel = arvoreBinaria.nivelNo(valor);
                    if (nivel == -1)
                        System.out.println("Nó não encontrado na árvore.");
                    else
                        System.out.println("Nível do Nó: " + nivel);
                    break;
                case 6:
                    valor = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor do Nó: "));
                    int profundidadeNo = arvoreBinaria.profundidadeNo(valor);
                    if (profundidadeNo == -1)
                        System.out.println("Nó não encontrado na árvore.");
                    else
                        System.out.println("Profundidade do Nó: " + profundidadeNo);
                    break;
                case 7:
                    int profundidadeArvore = arvoreBinaria.profundidadeArvore();
                    System.out.println("Profundidade da Árvore: " + profundidadeArvore);
                    break;
                case 8:
                    valor = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor do Nó: "));
                    int alturaNo = arvoreBinaria.alturaNo(valor);
                    if (alturaNo == -1)
                        System.out.println("Nó não encontrado na árvore.");
                    else
                        System.out.println("Altura do Nó: " + alturaNo);
                    break;
                case 9:
                    arvoreBinaria.imprimirComIdentacao();
                    break;
            }
        }
    }
}

class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    public void adicionarElemento(int valor) {
        raiz = adicionarRecursivo(raiz, valor);
    }

    private No adicionarRecursivo(No no, int valor) {
        if (no == null) {
            return new No(valor);
        }

        if (valor < no.valor) {
            no.esquerda = adicionarRecursivo(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = adicionarRecursivo(no.direita, valor);
        }

        return no;
    }

    public void preOrdem() {
        preOrdemRecursivo(raiz);
    }

    private void preOrdemRecursivo(No no) {
        if (no != null) {
            System.out.print(no.valor + " ");
            preOrdemRecursivo(no.esquerda);
            preOrdemRecursivo(no.direita);
        }
    }

    public void posOrdem() {
        posOrdemRecursivo(raiz);
    }

    private void posOrdemRecursivo(No no) {
        if (no != null) {
            posOrdemRecursivo(no.esquerda);
            posOrdemRecursivo(no.direita);
            System.out.print(no.valor + " ");
        }
    }

    public void emOrdem() {
        emOrdemRecursivo(raiz);
    }

    private void emOrdemRecursivo(No no) {
        if (no != null) {
            emOrdemRecursivo(no.esquerda);
            System.out.print(no.valor + " ");
            emOrdemRecursivo(no.direita);
        }
    }

    public int nivelNo(int valor) {
        return nivelNoRecursivo(raiz, valor, 1);
    }

    private int nivelNoRecursivo(No no, int valor, int nivelAtual) {
        if (no == null)
            return -1;

        if (no.valor == valor)
            return nivelAtual;

        int nivelEsquerda = nivelNoRecursivo(no.esquerda, valor, nivelAtual + 1);
        if (nivelEsquerda != -1)
            return nivelEsquerda;

        int nivelDireita = nivelNoRecursivo(no.direita, valor, nivelAtual + 1);
        return nivelDireita;
    }

    public int profundidadeNo(int valor) {
        return profundidadeNoRecursivo(raiz, valor);
    }

    private int profundidadeNoRecursivo(No no, int valor) {
        if (no == null)
            return -1;

        if (no.valor == valor)
            return 0;

        int profundidadeEsquerda = profundidadeNoRecursivo(no.esquerda, valor);
        if (profundidadeEsquerda != -1)
            return profundidadeEsquerda + 1;

        int profundidadeDireita = profundidadeNoRecursivo(no.direita, valor);
        if (profundidadeDireita != -1)
            return profundidadeDireita + 1;

        return -1; // Não encontrado
    }

    public int profundidadeArvore() {
        return calcularProfundidade(raiz);
    }

    private int calcularProfundidade(No no) {
        if (no == null)
            return -1;

        int profundidadeEsquerda = calcularProfundidade(no.esquerda);
        int profundidadeDireita = calcularProfundidade(no.direita);

        return Math.max(profundidadeEsquerda, profundidadeDireita) + 1;
    }

    public int alturaNo(int valor) {
        return calcularAltura(raiz, valor);
    }

    private int calcularAltura(No no, int valor) {
        if (no == null)
            return -1;

        if (no.valor == valor)
            return alturaNoRecursivo(no);

        int alturaEsquerda = calcularAltura(no.esquerda, valor);
        if (alturaEsquerda != -1)
            return alturaEsquerda;

        int alturaDireita = calcularAltura(no.direita, valor);
        return alturaDireita;
    }

    private int alturaNoRecursivo(No no) {
        if (no == null)
            return -1;

        int alturaEsquerda = alturaNoRecursivo(no.esquerda);
        int alturaDireita = alturaNoRecursivo(no.direita);

        return Math.max(alturaEsquerda, alturaDireita) + 1;
    }

    public void imprimirComIdentacao() {
        imprimirComIdentacaoRecursivo(raiz, 0);
    }

    private void imprimirComIdentacaoRecursivo(No no, int nivel) {
        if (no != null) {
            imprimirComIdentacaoRecursivo(no.direita, nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("\t");
            }
            System.out.println(no.valor);
            imprimirComIdentacaoRecursivo(no.esquerda, nivel + 1);
        }
    }

    private static class No {
        int valor;
        No esquerda;
        No direita;

        public No(int valor) {
            this.valor = valor;
            this.esquerda = null;
            this.direita = null;
        }
    }
}