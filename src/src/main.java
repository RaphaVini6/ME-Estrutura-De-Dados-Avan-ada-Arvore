import javax.swing.*;

public class main {

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        SwingUtilities.invokeLater(() -> {
            MenuFrame menuFrame = new MenuFrame(binaryTree);
            menuFrame.setVisible(true);
        });
    }
}

class MenuFrame extends JFrame {
    private final BinaryTree binaryTree;

    public MenuFrame(BinaryTree binaryTree) {
        this.binaryTree = binaryTree;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ÁRVORE BINÁRIA");
        setSize(200, 100);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] options = {
                "Adicionar Elemento",
                "Ver Nível da Árvore",
                "Ver Nível de um Nó Específico",
                "Ver Profundidade de Cada Nó",
                "Ver Altura de Cada Nó",
                "Ver Altura da Árvore",
                "Imprimir Árvore com Identação",
                "Ver Elementos no Percurso LRN",
                "Ver Elementos no Percurso NLR",
                "Ver Elementos no Percurso LNR",
                "Ver Profundidade da Árvore",
                "Sair"
        };

        JComboBox<String> comboBox = new JComboBox<>(options);
        panel.add(comboBox);

        JButton button = new JButton("Executar");
        button.addActionListener(e -> {
            String selectedOption = (String) comboBox.getSelectedItem();
            executeOption(selectedOption);
        });
        panel.add(button);

        add(panel);
    }

    private void executeOption(String option) {
        switch (option) {
            case "Adicionar Elemento":
                int number = Integer.parseInt(JOptionPane.showInputDialog("Digite um número: "));
                binaryTree.addElement(number);
                JOptionPane.showMessageDialog(this, "Elemento adicionado com sucesso.");
                break;
            case "Ver Nível da Árvore":
                int treeLevel = binaryTree.treeLevel();
                JOptionPane.showMessageDialog(this, "Nível da Árvore: " + treeLevel);
                break;
            case "Ver Nível de um Nó Específico":
                int nodeValue = Integer.parseInt(JOptionPane.showInputDialog("Digite o valor do nó: "));
                int nodeLevel = binaryTree.getNodeLevel(nodeValue);
                if (nodeLevel != 0) {
                    JOptionPane.showMessageDialog(this, "Nível do Nó " + nodeValue + ": " + nodeLevel);
                } else {
                    JOptionPane.showMessageDialog(this, "Nó não encontrado na árvore.");
                }
                break;
            case "Ver Profundidade de Cada Nó":
                JOptionPane.showMessageDialog(this, "Profundidade de cada nó:\n" + binaryTree.nodeDepths());
                break;
            case "Ver Altura de Cada Nó":
                JOptionPane.showMessageDialog(this, "Altura de cada nó:\n" + binaryTree.nodeHeights());
                break;
            case "Ver Altura da Árvore":
                int treeHeight = binaryTree.treeHeight();
                JOptionPane.showMessageDialog(this, "Altura da Árvore: " + treeHeight);
                break;
            case "Imprimir Árvore com Identação":
                binaryTree.printWithIndentation();
                break;
            case "Ver Elementos no Percurso LRN":
                JOptionPane.showMessageDialog(this, "Elementos no Percurso LRN:\n" + binaryTree.lrnTraversalForDialog());
                break;
            case "Ver Elementos no Percurso NLR":
                JOptionPane.showMessageDialog(this, "Elementos no Percurso NLR:\n" + binaryTree.nlrTraversalForDialog());
                break;
            case "Ver Elementos no Percurso LNR":
                JOptionPane.showMessageDialog(this, "Elementos no Percurso LNR:\n" + binaryTree.lnrTraversalForDialog());
                break;
            case "Ver Profundidade da Árvore":
                int treeDepth = binaryTree.treeDepth();
                JOptionPane.showMessageDialog(this, "Profundidade da Árvore: " + treeDepth);
                break;
            case "Sair":
                System.exit(0);
                break;
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

    public int treeLevel() {
        return calculateTreeLevel(root);
    }

    private int calculateTreeLevel(Node node) {
        if (node == null)
            return 0;

        int leftLevel = calculateTreeLevel(node.left);
        int rightLevel = calculateTreeLevel(node.right);

        return Math.max(leftLevel, rightLevel) + 1;
    }

    public String nodeDepths() {
        return calculateNodeDepths(root, 0);
    }

    private String calculateNodeDepths(Node node, int depth) {
        StringBuilder sb = new StringBuilder();
        if (node != null) {
            sb.append(node.value).append(": ").append(depth).append("\n");
            sb.append(calculateNodeDepths(node.left, depth + 1));
            sb.append(calculateNodeDepths(node.right, depth + 1));
        }
        return sb.toString();
    }

    public String nodeHeights() {
        return calculateNodeHeights(root);
    }

    private String calculateNodeHeights(Node node) {
        StringBuilder sb = new StringBuilder();
        if (node != null) {
            sb.append(node.value).append(": ").append(calculateHeight(node)).append("\n");
            sb.append(calculateNodeHeights(node.left));
            sb.append(calculateNodeHeights(node.right));
        }
        return sb.toString();
    }

    private int calculateHeight(Node node) {
        if (node == null)
            return -1;

        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int treeHeight() {
        return calculateHeight(root);
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

    public String lrnTraversalForDialog() {
        return lrnRecursiveForDialog(root);
    }

    private String lrnRecursiveForDialog(Node node) {
        StringBuilder sb = new StringBuilder();
        if (node != null) {
            sb.append(lrnRecursiveForDialog(node.left));
            sb.append(lrnRecursiveForDialog(node.right));
            sb.append(node.value).append(" ");
        }
        return sb.toString();
    }

    public String nlrTraversalForDialog() {
        return nlrRecursiveForDialog(root);
    }

    private String nlrRecursiveForDialog(Node node) {
        StringBuilder sb = new StringBuilder();
        if (node != null) {
            sb.append(node.value).append(" ");
            sb.append(nlrRecursiveForDialog(node.left));
            sb.append(nlrRecursiveForDialog(node.right));
        }
        return sb.toString();
    }

    public String lnrTraversalForDialog() {
        return lnrRecursiveForDialog(root);
    }

    private String lnrRecursiveForDialog(Node node) {
        StringBuilder sb = new StringBuilder();
        if (node != null) {
            sb.append(lnrRecursiveForDialog(node.left));
            sb.append(node.value).append(" ");
            sb.append(lnrRecursiveForDialog(node.right));
        }
        return sb.toString();
    }

    public int getNodeLevel(int value) {
        return getNodeLevelRecursive(root, value, 1);
    }

    private int getNodeLevelRecursive(Node node, int value, int level) {
        if (node == null)
            return 0;
        if (node.value == value)
            return level;

        int downLevel = getNodeLevelRecursive(node.left, value, level + 1);
        if (downLevel != 0)
            return downLevel;

        downLevel = getNodeLevelRecursive(node.right, value, level + 1);
        return downLevel;
    }
    public int treeDepth() {
        return calculateTreeDepth(root);
    }

    private int calculateTreeDepth(Node node) {
        if (node == null)
            return 0;

        int leftDepth = calculateTreeDepth(node.left);
        int rightDepth = calculateTreeDepth(node.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    class Node {
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