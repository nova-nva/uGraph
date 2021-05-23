package Tree;

import Main.Link;
import Main.Nodo;

public class Tree {
    private class Node{
        public Node parent, right, left;
        public int key;
        public Object contenido;

        // Graphic control
        int x, y;
        int id;

        public Node(int index){
            key = index;
            right = null;
            left = null;
            parent = null;
            contenido = null;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    Node root;
    int counter = 0;
    String inOrderCad = "", preOrderCad = "", postOrderCad = "";

    public Tree(){
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void insert(int index, Object content){
        Node n = new Node(index);
        n.contenido = content;
        n.setId(counter);

        if(root == null){
            root = n;
            n.setX(303);
            n.setY(Nodo.diametro);
            TreePanel.nodos.add(new Nodo(303, Nodo.diametro, String.valueOf(index), this.counter));
            counter++;
        }
        else{
            Node aux = root;
            int currentX = aux.getX();
            int currentY = aux.getY();
            int scale = -20;

            while(aux != null){
                scale += 40;
                currentX = aux.getX();
                currentY = aux.getY();

                n.parent = aux;
                if(n.key >= aux.key){
                    aux = aux.right;
                }
                else{
                    aux = aux.left;
                }
            }

            if(n.key < n.parent.key){
                n.parent.left = n;
                n.setX(currentX - 150 + scale);
            }
            else{
                n.parent.right = n;
                n.setX(currentX + 150 - scale);
            }
            n.setY(currentY + Nodo.diametro + 20);
            TreePanel.nodos.add(new Nodo(n.getX(), n.getY(), String.valueOf(index), counter));

            int previousX = n.parent.getX();
            int previousY = n.parent.getY();

            TreePanel.links.add(new Link(previousX, previousY, n.getX(), n.getY(), n.parent.getId(), n.getId(), "", "", "", 0));
            counter++;
        }
    }

    public String inOrder(Node n){
        if(n != null){
            inOrder(n.left);
            System.out.println("Index: " + n.key);
            inOrderCad += n.key + ", ";
            inOrder(n.right);
        }
        return inOrderCad;
    }

    public String preOrder(Node n){
        if(n != null){
            System.out.println("Index: " + n.key);
            preOrderCad += n.key + ", ";
            preOrder(n.left);
            preOrder(n.right);
        }
        return preOrderCad;
    }

    public String postOrder(Node n){
        if(n != null){
            postOrder(n.left);
            postOrder(n.right);
            System.out.println("Index: " + n.key);
            postOrderCad += n.key + ", ";
        }
        return postOrderCad;
    }

    public void resetStrings(){
        inOrderCad = "";
        preOrderCad = "";
        postOrderCad = "";
    }
}
