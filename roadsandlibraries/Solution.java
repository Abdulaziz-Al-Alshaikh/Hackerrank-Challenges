/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadsandlibraries;

/**
 *
 * @author Abdulaziz Al-Alshaikh
 */
import java.util.*;
public class Solution {
    public static void main(String[] args) {
       
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int i = 0; i < q; i++) {
            List<List<Integer>> cities = new ArrayList<>();
            int n = in.nextInt(), m = in.nextInt(), cLb = in.nextInt(), cRd = in.nextInt();
            for(int j = 0; j < m; j++) {
                cities.add(Arrays.asList(in.nextInt(), in.nextInt()));
            }
            System.out.println(roadsAndLibraries(n, cLb, cRd, cities));
        }
        
    }
    
    
    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
        if(c_lib < c_road) {
            return  c_lib * (long) n;
        }
        
        long rd = c_road, lib = c_lib;
        
        List<Node> nodeList = new ArrayList<>();
        for(int i = 0; i < n; i++) nodeList.add(new Node());
        
        Graph g = new Graph(nodeList);
        for(List<Integer> c : cities)
            g.addEdge(c.get(0) - 1, c.get(1) - 1);
            
        int connectedComponents = 0;
        for(int i = 0; i < nodeList.size(); i++) {
            if(nodeList.get(i).isVis) continue;
            connectedComponents++;
            DFS(nodeList.get(i));
        }
        long result = (n - connectedComponents) * (long)c_road + (connectedComponents * (long)c_lib);
        return result;
    
    }

    private static void DFS(Node n) {
        if(n.isVis) return;
        n.isVis = true;
        for(Node neighbor : n.neighbors)
            if(!neighbor.isVis) DFS(neighbor);
    }
    
    static class Graph{
        List<Node> nodeList;
        public Graph(List<Node> nodeList){this.nodeList = nodeList;}
        
        public void addEdge(int i, int j) {
            this.nodeList.get(i).neighbors.add(this.nodeList.get(j));
            this.nodeList.get(j).neighbors.add(this.nodeList.get(i));
        }
    }
    
    
    static class Node{
        List<Node> neighbors;
        boolean isVis;
        public Node() {this.neighbors = new ArrayList<>();}
    }
    
    

}
