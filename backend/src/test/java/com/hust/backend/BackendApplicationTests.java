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

    public static void main(String[] args) {
//        getCardValidEPassInBotID(3, 10000, 5, Arrays.asList("0$1,2,3,4,5$9000", "1$0,1,2,3,4,5$11000",
//        "2$4,5,6$10000"));
        System.out.println(recommend(10, 5, 2, Arrays.asList(
                "2,1025722315,1",
                "3,3521787618,1",
                "4,3744207299,1",
                "5,2887615731,1",
                "6,7733645785,1",
                "2,8658116773,2",
                "3,9694421687,2",
                "4,3178535113,2",
                "5,7951492845,2",
                "6,6394495983,2",
                "2,6679661617,3",
                "3,3197628,3",
                "4,8730463169,3",
                "5,860324446,3",
                "6,8398111795,3",
                "2,2907613732,4",
                "3,161071142,4",
                "4,80317009,4",
                "5,2280097495,4",
                "6,2650478088,4",
                "2,5289034912,5",
                "3,8692644299,5",
                "4,5203499920,5",
                "5,9661211576,5",
                "6,2673385847,5",
                "2,2187236873,6",
                "3,452620375,6",
                "4,6287610035,6",
                "5,8244820783,6",
                "6,6287609573,6",
                "2,2153707994,7",
                "3,4351958938,7",
                "4,4225830280,7",
                "5,5968696817,7",
                "6,9680132281,7",
                "2,1809864184,8",
                "3,6790155124,8",
                "4,4784831295,8",
                "5,8592445788,8",
                "6,4893940205,8",
                "2,867639719,9",
                "3,6676142314,9",
                "4,6563350758,9",
                "5,5024888725,9",
                "6,41561741,9",
                "2,9228285979,10",
                "3,2434847108,10",
                "4,2985387153,10",
                "5,1839037738,10",
                "6,9479564267,10"
        )));
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
