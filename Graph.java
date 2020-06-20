package com.codebin;

import javafx.util.Pair;
import java.util.LinkedList;

public class Graph {
    int vertex;
    boolean directed;
    LinkedList<Pair<Integer, Integer>> list[];

    public Graph(int vertex, boolean directed) {
        this.vertex = vertex;
        this.directed = directed;
        list = new LinkedList[vertex];
        for (int i = 0; i < vertex; i++) {
            list[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) {

        //add edge
        list[source].addFirst(new Pair<>(destination, weight));
        if(!directed){
            list[destination].addFirst(new Pair<>(source, weight));
        }

    }

    public void printGraph() {
        for (int i = 0; i < vertex; i++) {
            if (list[i].size() > 0) {
                System.out.print("Vertex " + i + " is connected to: ");
                for (int j = 0; j < list[i].size(); j++) {
                    System.out.print(list[i].get(j) + " ");
                }
                System.out.println();
            }
        }
    }

    public int getSize(){
        return vertex;
    }

    public boolean isDirected(){
        return this.directed;
    }

    public LinkedList<Pair<Integer, Integer>> [] getAdjList(){
        return list;
    }
}