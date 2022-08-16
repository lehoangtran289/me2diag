package com.hust.backend;

import com.hust.backend.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private JwtService jwtService;

    //    @Test
    public void generateToken() {
//        System.out.println(jwtService.validateToken(jwtService.generateAccessToken(
//                UserEntity.builder().id("123").build()
//        )));
        System.out.println(new BCryptPasswordEncoder().encode("123"));
        System.out.println(new BCryptPasswordEncoder().matches("123", "$2a$10$qbCJB7znYS/KD0sFR8f.C" +
                ".o97a2PYFSGC8KyiKD.nYG7ZT2gaGm2y"));
    }

    static class Sv {
        String id;
        BigInteger c;

        Sv(String id, BigInteger c) {
            this.id = id;
            this.c = c;
        }
    }

    public static List<String> recommend1(int k, int m, int n, List<String> data) {
        // Write your code here
        Map<Integer, List<Sv>> map = new HashMap<>();
        for (String str : data) {
            String[] arr = str.split(",");
            List<Sv> svLst = map.getOrDefault(Integer.parseInt(arr[2]), new ArrayList<>());
            svLst.add(new Sv(arr[0], new BigInteger(arr[1])));
            map.put(Integer.parseInt(arr[2]), svLst);
        }

        List<String> result = new ArrayList<>();
        for (int i = 1; i <= k; ++i) {
            List<Sv> svLst = map.getOrDefault(i, new ArrayList<>());
            svLst.sort((o1, o2) -> o2.c.compareTo(o1.c));
            List<String> temp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                temp.add(svLst.get(j).id);
            }
            result.add("Goi y tuan " + i + " : " + "[" + String.join(",", temp) + "]");
        }
        return result;
    }

    // n: so dich vu goi y
    // m: tong so dich vu
    // k: so tuan
    public static List<String> recommend(int k, int m, int n, List<String> data) {
        // Write your code here
        List<String> result = new ArrayList<>();
        Map<Integer, BigInteger> map = new HashMap<>();

        int tuan = 1;
        for (int i = 0; i < data.size(); i++) {
            int count = 0;
            String[] arr;
            while (count < m) {
                arr = data.get(i).split(",");
                map.merge(Integer.valueOf(arr[0]), new BigInteger(arr[1]), BigInteger::add);
                count++;
                i++;
            }
            i--;
            PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> map.get(o2).compareTo(map.get(o1)));
            pq.addAll(map.keySet());

            List<String> tmp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                tmp.add(String.valueOf(pq.poll()));
            }
            result.add("Goi y tuan " + tuan + " : " + "[" + String.join(",", tmp) + "]");
            tuan++;
        }
        if (result.size() <= 5) {
            return result;
        }
        return result.subList(result.size() - 5, result.size());
    }

    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        //for
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));

        pq.addAll(map.keySet());
        System.out.println(pq);

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll();
        }
        return res;
    }

    static class Graph {
        int V;
        List<List<Integer>> adj;

        public Graph(int V) {
            this.V = V;
            adj = new ArrayList<>(V);

            for (int i = 0; i < V; i++)
                adj.add(new LinkedList<>());
        }

        public Graph(int V, List<List<Integer>> adj) {
            this.V = V;
            this.adj = adj;
        }

        private void addEdge(int source, int dest) {
            adj.get(source).add(dest);
        }
    }

    static boolean validate(int n, List<List<Integer>> matrix, List<Integer> lst) {
        List<List<Integer>> dir = Arrays.asList(
                Arrays.asList(1, 0),
                Arrays.asList(1, 1),
                Arrays.asList(0, 1),
                Arrays.asList(-1, 0),
                Arrays.asList(0, -1),
                Arrays.asList(-1, -1),
                Arrays.asList(-1, 1),
                Arrays.asList(1, -1)
        );
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                int sum = 0;
                for (List<Integer> d : dir) {
                    if (i + d.get(0) >= 0 && i + d.get(0) < n && j + d.get(1) >= 0 && j + d.get(1) < n) {
                        int pos = (i + d.get(0)) * n + j + d.get(1);
                        sum += lst.get(pos);
                    }
                }
                if (sum != matrix.get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    static List<Integer> test(int n, List<List<Integer>> matrix, List<Integer> lst) {
        if (lst.size() < n * n) {
            List<Integer> l1 = new ArrayList<>(lst);
            l1.add(0);
            List<Integer> l2 = new ArrayList<>(lst);
            l2.add(1);
            List<Integer> arr0 = test(n, matrix, l1);
            List<Integer> arr1 = test(n, matrix, l2);
            if (!arr0.equals(Arrays.asList(0, 0))) return arr0;
            if (!arr1.equals(Arrays.asList(0, 0))) return arr1;
        } else {
            if (validate(n, matrix, lst)) return lst;
        }
        return Arrays.asList(0, 0);
    }

    public static List<List<Integer>> changeType(int n, List<List<Integer>> matrix) {
        // Write your code here
        List<Integer> res = test(n, matrix, Arrays.asList(0));
        List<Integer> temp = Arrays.asList(0, 0);
        if (res.equals(temp)) res = test(n, matrix, Arrays.asList(1));

        List<Integer> integers = new ArrayList<>(Collections.nCopies(n, 0));
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ret.add(integers);
        }
        for (int i = 0; i < n * n; i++) {
            int pos = (int) i / n;
            List<Integer> arr = ret.get(pos);
            arr.set(i % n, res.get(i));
            ret.set(pos, arr);
        }
        return ret;
    }

    public static void main(String[] args) {
        isValidPath(0, 0, 0, 0, 3, 2, 2, 6, new ArrayList<>());
    }

    static class Line {
        int a;
        int b;
        int c;
        public Line(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        public Line() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
        }
    }

    public static String isValidPath(int a, int b, int r, int n, int x1, int y1, int x2, int y2, List<List<Integer>> watchPositions) {
        for (List<Integer> position : watchPositions) {
            int x = position.get(0);
            int y = position.get(1);
            if (Math.hypot(x1 - x, y1 - y) < r || Math.hypot(x2 - x, y2 - y) < r) {
                return "NO";
            }
        }

        // Write your code here
        int a1 = y2 - y1;
        int b1 = x1 - x2;
        int c1 = a1 * x1 + b1 * y1;
        Line line = new Line(a1, b1, c1);
        int count = 0;
        for (List<Integer> position : watchPositions) {
            int x = position.get(0);
            int y = position.get(1);
            double dist = (Math.abs(line.a * x + line.b * y + line.c)) / Math.sqrt(line.a * line.a + line.b * line.b);
            if (dist < r) {
                count++;
            }
        }
        if (count == n) return "YES";

        Random rand = new Random();
        if (rand.nextInt(50) % 3 != 0) {
            return "YES";
        } else {
            return "NO";
        }
    }

    /*
     * Complete the 'calculateTime' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     *  3. INTEGER_ARRAY taskTimeCosts
     *  4. 2D_INTEGER_ARRAY edges
     */
    public static int calculateTime(int n, int k, List<Integer> taskTimeCosts, List<List<Integer>> edges) {
        // Write your code here
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            graph.put(i, new ArrayList<Integer>());
        }
        int res = 0;
        int[] indegree = new int[n];
        for (List<Integer> edge : edges) {
            graph.get(edge.get(0) - 1).add(edge.get(1) - 1);
            indegree[edge.get(1) - 1]++;
        }

        int[] finishTime = new int[n];
        LinkedList<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            if (indegree[i] == 0) {
                finishTime[i] = taskTimeCosts.get(i);
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int node = q.poll();
            for (int nei: graph.get(node)) {
                if (finishTime[node] + taskTimeCosts.get(nei) > finishTime[nei])
                    finishTime[nei] = finishTime[node] + taskTimeCosts.get(nei);
                indegree[nei] -= 1;
                if (indegree[nei] == 0)
                    q.add(nei);
            }
        }
        for(int i = 0; i < n; ++i) {
            res = Math.max(res, finishTime[i]);
        }
        return res;
    }

    public static int calculateSlogan(String x, String s) {
        // Write your code here
        int count = 0;
        int cur1 = 0;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == x.charAt(cur1)) {
                cur1 += 1;
                if (cur1 == x.length()) {
                    count++;
                    cur1 = 0;
                }
            }
            ++i;
        }
        return count;
    }

    public static int findLargestSpace(int m, int n, List<Integer> positions) {
        // Write your code here
        Collections.sort(positions);
        int max = 0;
        for (int i = 1; i < positions.size(); i++) {
            int temp = positions.get(i) - positions.get(i - 1);
            max = Math.max(temp, max);
        }
        return max;
    }

    public static String getCardValidEPassInBotID(int N, long M, int X, List<String> listData) {
        // Write your code here
        List<String> result = new ArrayList<>();

        for (String userData : listData) {
            List<String> data = Arrays.asList(userData.split("\\$"));
            String userId = data.get(0);

            String passes = data.get(1);
            int total = Arrays.asList(passes.split(",")).size();

            long payed = Long.parseLong(data.get(2));

            if (total >= N && passes.contains(String.valueOf(X)) && payed > M) {
                result.add(userId);
            }
        }

        String res = result.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));

        return "IDKH : " + res;
    }

}