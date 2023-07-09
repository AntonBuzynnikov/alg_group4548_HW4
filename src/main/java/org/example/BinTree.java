package org.example;

import javax.print.DocFlavor;

public class BinTree {
    Node root;

    public boolean add(int value){
        if(root != null){
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.black;
            return result;
        } else{
            root = new Node();
            root.color = Color.black;
            root.value = value;
            return true;
        }
    }
    private boolean addNode(Node node, int value){
        if (node.value == value) {
            return false;
        }
        if (node.value > value) {
            if(node.leftChild != null){
                boolean result = addNode(node.leftChild, value);
                node.leftChild = rebalance(node.leftChild);
                return result;
            } else{
                node.leftChild = new Node();
                node.leftChild.color = Color.red;
                node.leftChild.value = value;
                return true;
            }
        } else {
            if(node.rightChild != null){
                boolean result = addNode(node.rightChild, value);
                node.rightChild = rebalance(node.rightChild);
                return result;
            } else {
                node.rightChild = new Node();
                node.rightChild.color = Color.red;
                node.rightChild.value = value;
                return true;
            }
        }
    }

    private Node rebalance(Node node){
        Node result = node;
        boolean needRebalance;
        do{
            needRebalance = false;
            if(result.rightChild != null && result.rightChild.color ==
                    Color.red && (result.leftChild == null || result.leftChild.color == Color.black)){
                needRebalance = true;
                result = rightSwap(result);
            }
            if(result.leftChild != null && result.leftChild.color == Color.red && result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.red){
                needRebalance = true;
                result = leftSwap(result);
            }
            if(result.leftChild != null && result.leftChild.color == Color.red && result.rightChild != null && result.rightChild.color == Color.red){
                needRebalance = true;
                colorSwap(result);
            }
        } while (needRebalance);
        return result;
    }
    private Node rightSwap(Node node){
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.red;
        return rightChild;
    }
    private Node leftSwap(Node node){
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.red;
        return leftChild;
    }
    private void colorSwap(Node node){
        node.rightChild.color = Color.black;
        node.leftChild.color = Color.black;
        node.color = Color.red;
    }

    public boolean contain(int value){
        Node currentNode = root;
        while (currentNode != null){
            if(currentNode.value == value){
                return true;
            }
            if(currentNode.value > value){
                currentNode = currentNode.leftChild;
            } else {
                currentNode = currentNode.rightChild;
            }
        }
        return false;
    }

    private class Node{
        private int value;
        private Node leftChild;
        private Node rightChild;
        private Color color;

        @Override
        public String toString(){
            return "Node{" + "value+" + value + ", color=" + color+'}';
        }
        Node(){
            this.color = Color.red;
        }
        Node(int value){
            this.value = value;
            this.color = Color.red;
        }
    }
    enum Color {red,black}
}
