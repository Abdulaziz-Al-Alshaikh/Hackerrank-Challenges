/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redKnightShortestPath;

/**
 *
 * @author Abdulaziz Al-Alshaikh
 */
import java.util.*;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(": ");
        int n = in.nextInt(), i_start = in.nextInt(), j_start = in.nextInt(), i_end = in.nextInt(), j_end = in.nextInt();
        printShortestPath(n, i_start, j_start, i_end, j_end);
    }
    

    public static void printShortestPath(int n, int i_start, int j_start, int i_end, int j_end) {
        int [][] dist = { {0,2}, {0,-2}, {2,1}, {2,-1}, {-2,-1}, {-2,1}};
        List<Node> nodeList = new ArrayList<>();
        
        for(int i = 0; i < n; i++) 
            for(int j = 0; j < n; j++) 
                nodeList.add(new Node(i, j));
        
        Graph g = new Graph(nodeList);
        for(Node node : nodeList) {
            for(int [] cords : dist) {
                int i = node.i + cords[0], j = node.j + cords[1];
                if(isValid(i,j,n)){
                    g.addWeightedEdge(nodeList.get( (n * i) + j), node, 6);
                }
            }
        }

        
        g.dijkstra(nodeList.get((n * i_start) + j_start), nodeList.get((n * i_end) + j_end));
        

    }
    static boolean isValid(int i, int j, int size) {return (i > -1 && i < size && j > -1 && j < size);}
    
    static class Node implements Comparable<Node> {
    
        Node parent;
        int distance,i,j;
        boolean isVisited;
        HashMap<Node, Integer> weightMap;
        List<Node> neighbors;


        public Node(int i, int j) {
            this.distance = Integer.MAX_VALUE;
            this.weightMap = new HashMap<>();
            this.isVisited = false;
            this.neighbors = new ArrayList<>();
            this.i = i;
            this.j = j;
        }

      

        @Override
        public int compareTo(Node node) {
            if(this.distance != node.distance)
                return this.distance - node.distance;
            return node.i == this.i ? this.j - node.j : node.i - this.i;
        }
    }
    static class Graph {
        List<Node> nodeList;
        public Graph(List<Node> nodeList) {
            this.nodeList = nodeList;
        }


        public void dijkstra(Node source, Node destination) {
            PriorityQueue <Node> priorityQueue = new PriorityQueue<>();
            source.distance = 0;
            priorityQueue.add(source);
            
            while(!priorityQueue.isEmpty()) {
                Node currentNode = priorityQueue.poll();
                currentNode.isVisited = true;
                for(Node neighbor : currentNode.neighbors) {
                    if(!neighbor.isVisited){
                        if(neighbor.distance >= currentNode.distance + currentNode.weightMap.get(neighbor)) {
                            neighbor.distance = currentNode.distance + currentNode.weightMap.get(neighbor);
                            neighbor.parent = currentNode;
                            priorityQueue.remove(neighbor);
                            priorityQueue.add(neighbor);
                        }
                       
                    }
                }

            }
            if(destination.parent == null){
                System.out.println("Impossible");
            }
            else{
                System.out.println(getMoves(destination));
                pathPrint(destination);
            }
        }

        public void pathPrint(Node node) {
            if(node.parent != null) {
                pathPrint(node.parent);
            }
            
            if(node.parent != null) {
                if(node.i == node.parent.i) {
                    System.out.print(node.parent.j < node.j? "R " : "L ");
                }
                else{
                    if(node.parent.i < node.i) {
                        System.out.print(node.parent.j < node.j? "LR ": "LL ");
                    }
                    
                    else{
                        System.out.print(node.parent.j > node.j? "UL " : "UR ");
                    }
                }
            }
        }

        public void addWeightedEdge(Node one, Node two, int dist) {
              one.weightMap.put(two, dist);
              one.neighbors.add(two);
              two.weightMap.put(one, dist);
              two.neighbors.add(one);
        }

        private int getMoves(Node destination) {
            if(destination.parent == null)
                return 0;
            return 1 + getMoves(destination.parent);
        }

    }
}
