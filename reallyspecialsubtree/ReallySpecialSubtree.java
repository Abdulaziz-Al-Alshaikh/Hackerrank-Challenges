/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reallyspecialsubtree;
import java.util.*;
/**
 *
 * @author Abdulaziz Al-Alshaikh
 */
public class ReallySpecialSubtree {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), e = in.nextInt();
        List<Integer> from = new ArrayList<>(), to = new ArrayList<>(), weight = new ArrayList<>();
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < e; i++) {
            from.add(in.nextInt());
            to.add(in.nextInt());
            weight.add(in.nextInt());
        }
        
        int result = kruskals(n, from, to, weight);
        System.out.println(result);
    }
    
    public static int kruskals(int n, List<Integer> from, List<Integer> to, List<Integer> weight) {
        
        List<Node> nodeList = new ArrayList<>();
        for(int i  = 0; i < n; i++){
            nodeList.add(new Node());
        }
        
        Graph g = new Graph(nodeList);
        for(int i = 0; i < from.size(); i++) {
            g.addWeightedEdge(from.get(i)-1, to.get(i)-1, weight.get(i));
        }
        int res = g.getSpecialSubtreeWeight();
        
       return res;
    }
    static class Node{
        List<Node> neighbors;
        Map<Node, Integer> weightedEdges;
        int dist;
        boolean isVis;
        
        public Node() {
            this.neighbors = new ArrayList<>();
            this.weightedEdges = new HashMap<>();
            this.dist = Integer.MAX_VALUE;
        }
        

    }
    static class Graph{
        List<Node> nodeList;
        public Graph(List<Node> nodeList){this.nodeList = nodeList;}
        
        public void addWeightedEdge(int u, int v, int weight) {
            this.nodeList.get(u).neighbors.add(this.nodeList.get(v));
            this.nodeList.get(u).weightedEdges.put(this.nodeList.get(v), weight);
            
            this.nodeList.get(v).neighbors.add(this.nodeList.get(u));
            this.nodeList.get(v).weightedEdges.put(this.nodeList.get(u), weight);
        }
        
        
        public int getSpecialSubtreeWeight() {
            Set<Node> visited = new HashSet<>();
            if(nodeList.isEmpty()) return 0;
            visited.add(nodeList.get(0));
            nodeList.get(0).isVis = true;
            int sum = 0, selectedEdges = 0;
            //I need to select the min edge (u, v) such that u belongs to the set 'visited' and v doesn't (to avoid a cycle).
            int size = nodeList.size();
            while(selectedEdges < size - 1) {
                int minEdge = Integer.MAX_VALUE;
                for(Node n : visited) {
                    for(Node neighbor : n.neighbors) {
                        if(!neighbor.isVis) {
                            minEdge = Math.min(minEdge, n.weightedEdges.get(neighbor));
                        }
                    }
                }
                sum += minEdge;
                addMinEdge(visited, minEdge);
                selectedEdges++;

            }
            
            return sum;
        }

        private void addMinEdge(Set<Node> visited, int minEdge) {
            for(Node node : visited){
                for(Node neighbor : node.neighbors){
                    if(!neighbor.isVis && node.weightedEdges.get(neighbor) == minEdge){      
                        neighbor.isVis = true;
                        visited.add(neighbor);
                        return;
                    }
                }
            }
        }
    }
}
