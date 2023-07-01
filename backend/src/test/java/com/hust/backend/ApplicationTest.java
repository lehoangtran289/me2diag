package com.hust.backend;

import java.util.*;

public class ApplicationTest {

    public static void main1(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();

        List<List<Integer>> res = solution1(n);
        System.out.println(res.size());
        for (List<Integer> result : res) {
            System.out.println(result.get(0) + " " + result.get(1));
        }
    }

    public static List<List<Integer>> solution1(int n) {
        int n1 = n; // wolf
        int n2 = n; // cow
        int turn = 0;
        List<List<Integer>> res = new ArrayList<>();
        while (n1 > 0 && n2 > 0) {
            if (turn % 2 == 0) {
                if (n1 <= n2) {
                    n1--;
                    n2--;
                    res.add(Arrays.asList(1, 1));
                } else {
                    n2 -= 2;
                    res.add(Arrays.asList(2, 0));
                }
                turn++;
            } else {

                turn++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        long K = reader.nextInt();
        System.out.println(solution3(K));
    }

    public static Long solution3(long K) {
//        int c = 0;
//        for (int i = 2; i < 18; ++i) {
//            int[] num = new int[i];
//
//            long temp = 0;
//            if (checkSum(temp)) {
//                c++;
//                if (c == K) return temp;
//            }
//        }

        if (K < Math.pow(10, 6)) {
            long c = 0;
            for (long i = 0L; i < Long.MAX_VALUE; ++i) {
                if (checkSum(i)) {
                    c++;
                    if (c == K) return i;
                }
            }
        } else {
            long c = 0;
            for (long i = 7245946; i < Long.MAX_VALUE; ++i) {
                if (checkSum(i)) {
                    c++;
                    if (c == Math.pow(10, 6) + K) return i;
                }
            }
        }
        return 0L;
    }

    static boolean checkSum(long n) {
        long sum = 0;
        while (n != 0) {
            sum += Math.pow(n % 10, 3);
            n = n / 10;
        }
        return isPrime(sum);
    }

    static boolean isPrime(long n) {
        // Corner case
        if (n <= 1)
            return false;
        // Check from 2 to n-1
        for (long i = 2; i < n; i++)
            if (n % i == 0)
                return false;
        return true;
    }

    public static void main2(String args[]) throws Exception {
        Scanner reader = new Scanner(System.in);
        String[] arr = reader.nextLine().split(" ");
        int N = Integer.parseInt(arr[0]);
        int M = Integer.parseInt(arr[1]);

        int[][] relations = new int[M][2];
        for (int i = 0; i < M; ++i) {
            String[] ar = reader.nextLine().split(" ");
            relations[i][0] = Integer.parseInt(ar[0]);
            relations[i][1] = Integer.parseInt(ar[1]);
        }

        for (Integer integer : solution2(N, M, relations)) {
            System.out.print(integer + " ");
        }
    }

    private static List<Integer> solution2(int n, int m, int[][] relations) {
        List<Integer> res = new ArrayList<>();
        int[] indegree = new int[n];
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.putIfAbsent(i, new ArrayList<>());
        }

        for (int[] relation : relations) {
            // a -> b;
            int a = relation[0] - 1;
            int b = relation[1] - 1;
            graph.get(a).add(b);
            indegree[b]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
                res.add(i + 1);
            }
        }

        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            for (Integer neighbor : graph.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                    res.add(neighbor + 1);
                }
            }
        }

        return res;
    }

}
