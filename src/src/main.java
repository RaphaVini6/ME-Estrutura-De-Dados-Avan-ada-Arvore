import javax.swing.JOptionPane;

public class main {

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        Menu menu = new Menu(binaryTree);
        menu.displayMenu();
    }
}

class Menu {
    private BinaryTree binaryTree;
    private String menuText = "ÁRVORE BINÁRIA\n" +
            "1 - Adicionar Elemento\n" +
            "2 - Ver Elementos na Pré-Ordem\n" +
            "3 - Ver Elementos na Pós-Ordem\n" +
            "4 - Ver Elementos em Ordem\n" +
            "5 - Ver Nível de um Nó\n" +
            "6 - Ver Profundidade de um Nó\n" +
            "7 - Ver Profundidade da Árvore\n" +
            "8 - Ver Altura de um Nó\n" +
            "9 - Imprimir Árvore com Identação\n" +
            "10 - Ver Elementos no Percurso LRN\n" +
            "11 - Ver Elementos no Percurso NLR\n" +
            "12 - Ver Elementos no Percurso LNR\n" +
            "99 - Sair";

    public Menu(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
    }

    public void displayMenu() {
        int option = 0;
        while (option != 99) {
            option = Integer.parseInt(JOptionPane.showInputDialog(menuText));
            switch (option) {
                case 1:
                    int number = Integer.parseInt(JOptionPane.showInputDialog("Digite um número: "));
                    binaryTree.addElement(number);
                    System.out.println("Elemento adicionado com sucesso.");
                    break;
                case 2:
                    System.out.println("Elementos na Pré-Ordem: ");
                    binaryTree.preOrderTraversal();
                    break;
                case 3:
                    System.out.println("Elementos na Pós-Ordem: ");
                    binaryTree.postOrderTraversal();
                    break;
                case 4:
                    System.out.println("Elementos em Ordem: ");
                    binaryTree.inOrderTraversal();
                    break;
                case 5:
                    int value = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor do Nó: "));
                    int level = binaryTree.getNodeLevel(value);
                    if (level == -1)
                        System.out.println("Nó não encontrado na árvore.");
                    else
                        System.out.println("Nível do Nó: " + level);
                    break;
                case 6:
                    value = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor do Nó: "));
                    int nodeDepth = binaryTree.getNodeDepth(value);
                    if (nodeDepth == -1)
                        System.out.println("Nó não encontrado na árvore.");
                    else
                        System.out.println("Profundidade do Nó: " + nodeDepth);
                    break;
                case 7:
                    int treeDepth = binaryTree.treeDepth();
                    System.out.println("Profundidade da Árvore: " + treeDepth);
                    break;
                case 8:
                    value = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor do Nó: "));
                    int nodeHeight = binaryTree.getNodeHeight(value);
                    if (nodeHeight == -1)
                        System.out.println("Nó não encontrado na árvore.");
                    else
                        System.out.println("Altura do Nó: " + nodeHeight);
                    break;
                case 9:
                    System.out.println("Árvore com Identação: ");
                    binaryTree.printWithIndentation();
                    break;
                case 10:
                    System.out.println("Elementos no Percurso LRN: ");
                    binaryTree.lrnTraversal();
                    break;
                case 11:
                    System.out.println("Elementos no Percurso NLR: ");
                    binaryTree.nlrTraversal();
                    break;
                case 12:
                    System.out.println("Elementos no Percurso LNR: ");
                    binaryTree.lnrTraversal();
                    break;
            }
            // Após executar uma operação, limpa o buffer do console
            System.out.println();
        }
    }
}

class BinaryTree {
    private Node root;

    public BinaryTree() {
        this.root = null;
    }

    public void addElement(int value) {
        root = addRecursive(root, value);
    }

    private Node addRecursive(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = addRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = addRecursive(node.right, value);
        }

        return node;
    }

    public void preOrderTraversal() {
        preOrderRecursive(root);
    }

    private void preOrderRecursive(Node node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preOrderRecursive(node.left);
            preOrderRecursive(node.right);
        }
    }

    public void postOrderTraversal() {
        postOrderRecursive(root);
    }

    private void postOrderRecursive(Node node) {
        if (node != null) {
            postOrderRecursive(node.left);
            postOrderRecursive(node.right);
            System.out.print(node.value + " ");
        }
    }

    public void inOrderTraversal() {
        inOrderRecursive(root);
    }

    private void inOrderRecursive(Node node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.print(node.value + " ");
            inOrderRecursive(node.right);
        }
    }

    public int getNodeLevel(int value) {
        return getNodeLevelRecursive(root, value, 1);
    }

    private int getNodeLevelRecursive(Node node, int value, int currentLevel) {
        if (node == null)
            return -1;

        if (node.value == value)
            return currentLevel;

        int leftLevel = getNodeLevelRecursive(node.left, value, currentLevel + 1);
        if (leftLevel != -1)
            return leftLevel;

        int rightLevel = getNodeLevelRecursive(node.right, value, currentLevel + 1);
        return rightLevel;
    }

    public int getNodeDepth(int value) {
        return getNodeDepthRecursive(root, value);
    }

    private int getNodeDepthRecursive(Node node, int value) {
        if (node == null)
            return -1;

        if (node.value == value)
            return 0;

        int leftDepth = getNodeDepthRecursive(node.left, value);
        if (leftDepth != -1)
            return leftDepth + 1;

        int rightDepth = getNodeDepthRecursive(node.right, value);
        if (rightDepth != -1)
            return rightDepth + 1;

        return -1; // Não encontrado
    }

    public int treeDepth() {
        return calculateTreeDepth(root);
    }

    private int calculateTreeDepth(Node node) {
        if (node == null)
            return -1;

        int leftDepth = calculateTreeDepth(node.left);
        int rightDepth = calculateTreeDepth(node.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    public int getNodeHeight(int value) {
        return calculateHeight(root, value);
    }

    private int calculateHeight(Node node, int value) {
        if (node == null)
            return -1;

        if (node.value == value)
            return heightRecursive(node);

        int leftHeight = calculateHeight(node.left, value);
        if (leftHeight != -1)
            return leftHeight;

        int rightHeight = calculateHeight(node.right, value);
        return rightHeight;
    }

    private int heightRecursive(Node node) {
        if (node == null)
            return -1;

        int leftHeight = heightRecursive(node.left);
        int rightHeight = heightRecursive(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public void printWithIndentation() {
        printWithIndentationRecursive(root, 0);
    }

    private void printWithIndentationRecursive(Node node, int level) {
        if (node != null) {
            printWithIndentationRecursive(node.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            System.out.println(node.value);
            printWithIndentationRecursive(node.left, level + 1);
        }
    }
    public void lrnTraversal() {
        lrnRecursive(root);
    }

    private void lrnRecursive(Node node) {
        if (node != null) {
            lrnRecursive(node.left);
            lrnRecursive(node.right);
            System.out.print(node.value + " ");
        }
    }

    public void nlrTraversal() {
        nlrRecursive(root);
    }

    private void nlrRecursive(Node node) {
        if (node != null) {
            System.out.print(node.value + " ");
            nlrRecursive(node.left);
            nlrRecursive(node.right);
        }
    }

    public void lnrTraversal() {
        lnrRecursive(root);
    }

    private void lnrRecursive(Node node) {
        if (node != null) {
            lnrRecursive(node.left);
            System.out.print(node.value + " ");
            lnrRecursive(node.right);
        }
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
}

