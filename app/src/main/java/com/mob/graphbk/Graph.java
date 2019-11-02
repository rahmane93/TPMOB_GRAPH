package com.mob.graphbk;

import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Graph {

    public int MAX_X = 800;
    public int MAX_Y = 600;
    /*Les noeuds du graph
    * Les arcs qui relient les nodes
    * Les couleurs possibles des noeuds
    */
    private List<Node> nodes = new ArrayList<>();
    private List<Arc> arcs = new ArrayList<Arc>();
    public static List<Integer> nodeColors = new ArrayList<>();

    /**
     * Constructeur avec le nombre de nodes de depart
     *
     * @param nbNodes
     */
    public Graph(int nbNodes) {
        initNodeColors();
        for (int i = 0; i < nbNodes; i++) {
            int x = Node.getRandomCoord(MAX_X);
            int y = Node.getRandomCoord(MAX_Y);
            Node node = new Node(x, y);
            node.setColor(getRandomColor());
            int num = getNodes().size() + 1;
            node.setEtiquette("" + num);
            boolean add = addNode(node);
            while (!add) {
                x = Node.getRandomCoord(MAX_X);
                y = Node.getRandomCoord(MAX_Y);
                node = new Node(x, y);
                node.setColor(getRandomColor());
                num = getNodes().size() + 1;
                node.setEtiquette("" + num);
                add = addNode(node);
            }
        }
    }
    /*
    * retourne une couleur  parmi celles qui exitent de facon aleatoire
     */
    public static int getRandomColor(){
        Random random=new Random();
        int numColor=random.nextInt(nodeColors.size());
        return nodeColors.get(numColor);
    }

    /**
     * Initialise les couleurs disponibles pour les noeuds
     */
    public void initNodeColors() {
        nodeColors.add(Color.BLUE);
        nodeColors.add(Color.CYAN);
        nodeColors.add(Color.DKGRAY);
        nodeColors.add(Color.RED);
        nodeColors.add(Color.GRAY);
        nodeColors.add(Color.GREEN);
        nodeColors.add(Color.MAGENTA);
        nodeColors.add(Color.LTGRAY);
        nodeColors.add(Color.YELLOW);
    }

    /**
     * Ajoute un nouveau Node au graphe quand c'est possible
     *
     * @param node
     * @return true quand le Node est ajouté
     */
    public boolean addNode(Node node) {
        boolean chevauchement = false;
        Iterator<Node> it = nodes.iterator();
        baka:
        while (it.hasNext()) {
            Node n = it.next();
            if (Node.overlap(n, node)) {
                chevauchement = true;
                break baka;
            }
        }
        if (!chevauchement) {
            this.nodes.add(node);
        }
        return !chevauchement; // prouve que le node à bien de l'espace
    }


    /**
     * @return ArrayList de tous les noeuds
     */
    public List<Node> getNodes() {
        return this.nodes;
    }

    /**
     * @return ArrayList de tous les arcs
     */
    public List<Arc> getArcs() {
        return this.arcs;
    }

    /**
     * @return La liste de toutes les couleurs disponibles
     */
    public List<Integer> getNodeColors() {
        return this.nodeColors;
    }

    /**
     * Ajoute un Arc
     *
     * @param arc
     */
    public void addArc(Arc arc) {
        if (!Node.overlap(arc.getDebut(), arc.getFin()))
            this.arcs.add(arc);
    }
    /**
     * Ajoute n arcs de manière aléatoire (n<=nombre de nodes)
     */
    public void addRandomArcs() {
        for (int i = 0; i < nodes.size(); i++) {
            addArc(getRandomNodeIndex(), getRandomNodeIndex());
        }
    }
    /**
     * Retoune l'index d'un Node de manière aléatoire de la liste de nodes
     *
     * @return un index
     */
    public int getRandomNodeIndex() {
        Random rand = new Random();
        int x = rand.nextInt(getNodes().size());
        return x;
    }
    /**
     * Ajoute un arc entre les nodes index1 et index2 de la liste de nodes
     *
     * @param index1
     * @param index2
     */
    public void addArc(int index1, int index2) {
        if (index1 != index2) {
            Node n1 = getNodes().get(index1);
            Node n2 = getNodes().get(index2);
            if (!Node.overlap(n1, n2)) {
                Log.d("XXXX", "add arc ");
                this.arcs.add(new Arc(n1, n2));
            }
        }

    }
    /**
     * Retourne le node selectionné ou null
     *
     * @param x
     * @param y
     * @return
     */
    public Node selectedNode(int x, int y) {
        Node node = new Node(x, y);
        boolean overlap = false;

        Iterator<Node> it = nodes.iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            if (Node.overlap(n, node)) {
                return n;
            }
        }
        return null;
    }

}
