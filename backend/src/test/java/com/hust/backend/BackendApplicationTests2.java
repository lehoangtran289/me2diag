package com.hust.backend;

import com.hust.backend.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class BackendApplicationTests2 {

}
// relations = edges;
class Solution {
    public int minimumTime(int n, int[][] relations, int[] time) {
        int[] finishTime = new int[n];
        int[] indegree = new int[n];
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < n; i++) {
            graph.putIfAbsent(i, new ArrayList<>());
        }
        for(int[] relation: relations) {
            // a -> b;
            int a = relation[0] - 1;
            int b = relation[1] - 1;
            graph.get(a).add(b);
            indegree[b]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < n; i++) {
            if(indegree[i] == 0) {
                finishTime[i] = time[i];
                queue.offer(i);
            }
        }

        while(!queue.isEmpty()) {
            Integer node = queue.poll();
            for(Integer neighbor: graph.get(node)) {
                finishTime[neighbor] = Math.max(finishTime[neighbor], finishTime[node] + time[neighbor]);
                indegree[neighbor]--;
                if(indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        int res = 0;
        for(int i = 0; i < n; i++) {
            res = Math.max(res, finishTime[i]);
        }
        return res;
    }
}

class Dfs1 {

    // Returns adjacency list representation of graph from
    // given set of pairs.
    static ArrayList<HashSet<Integer> >
    make_graph(int numTasks, int[][] prerequisites)
    {
        ArrayList<HashSet<Integer> > graph
                = new ArrayList(numTasks);
        for (int i = 0; i < numTasks; i++)
            graph.add(new HashSet<Integer>());
        for (int[] pre : prerequisites)
            graph.get(pre[1]).add(pre[0]);
        return graph;
    }

    // Does DFS and adds nodes to Topological Sort
    static boolean dfs(ArrayList<HashSet<Integer> > graph,
                       int node, boolean[] onpath,
                       boolean[] visited,
                       ArrayList<Integer> toposort)
    {
        if (visited[node])
            return false;
        onpath[node] = visited[node] = true;
        for (int neigh : graph.get(node))
            if (onpath[neigh]
                    || dfs(graph, neigh, onpath, visited,
                    toposort))
                return true;
        toposort.add(node);
        return onpath[node] = false;
    }

    // Returns an order of tasks so that all tasks can be
    // finished.
    static ArrayList<Integer>
    findOrder(int numTasks, int[][] prerequisites)
    {
        ArrayList<HashSet<Integer> > graph
                = make_graph(numTasks, prerequisites);
        ArrayList<Integer> toposort
                = new ArrayList<Integer>();
        boolean[] onpath = new boolean[numTasks];
        boolean[] visited = new boolean[numTasks];
        for (int i = 0; i < numTasks; i++)
            if (!visited[i]
                    && dfs(graph, i, onpath, visited, toposort))
                return new ArrayList<Integer>();
        Collections.reverse(toposort);
        return toposort;
    }

    // Driver code
    public static void main(String[] args)
    {
        int numTasks = 4;
        int[][] prerequisites
                = { { 1, 0 }, { 2, 1 }, { 3, 0 } };

        ArrayList<Integer> v
                = findOrder(numTasks, prerequisites);

        for (int i = 0; i < v.size(); i++) {
            System.out.print(v.get(i) + " ");
        }
    }
}
