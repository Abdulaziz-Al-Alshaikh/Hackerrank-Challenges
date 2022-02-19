/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventree;
import java.util.*;
/**
 *
 * @author Abdulaziz Al-Alshaikh
 */
public class Solution {
        // Complete the evenForest function below.
    static int evenForest(int t_nodes, int t_edges, List<Integer> t_from, List<Integer> t_to) {

        List<Node> nodeList = new ArrayList<>();
        for(int i = 0; i < t_nodes; i++){
            nodeList.add(new Node());
        }
        
        Graph g = new Graph(nodeList);
        for(int i = 0; i < t_from.size(); i++)
            g.addDirectedEdge(t_from.get(i) - 1, t_to.get(i) - 1);
        int result = g.getResult();
        return result;
        
    }
    
    static class Graph{
        List<Node> nodeList;
        
        public Graph(List<Node> nodeList){this.nodeList = nodeList;}
        
        public void addDirectedEdge(int i, int j) {
            this.nodeList.get(j).neighbors.add(this.nodeList.get(i));
        }
        

        private int getResult() {
            //first of all, i need to iterate over all nodes in the list.
            //second of all, for each node of this list, i need to perform on it a BFS. 
            //If the BFS resulted on a number of nodes that is even, then we can remove that edge otherwise not.
            int result = 0;
            for(Node node : this.nodeList) {
                for(Node neighbor : node.neighbors) {
                    if(canRemoveEdge(neighbor)){
                        result++;
                    }
                }
            }
            return result;
        }
        private boolean canRemoveEdge(Node node) {
            Queue<Node> q = new LinkedList<>();
            q.add(node);
            int count = 0;
            while(!q.isEmpty()) {
                count++;
                Node curNode = q.poll();
                for(Node neighbor : curNode.neighbors){
                    q.add(neighbor);
                }
            }
            
            return count % 2 == 0;
        }
    }
    
    static class Node{
        List<Node> neighbors;
        public Node() {this.neighbors = new ArrayList<>();}
    }

}
