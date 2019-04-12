package com.jasu.algorithm;

import java.util.*;

/**
 * @author @Jasu
 * @date 2018-12-28 15:00
 */
public class PrimeNumber {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        boolean[] isprimer = new boolean[n + 1];

        Arrays.fill(isprimer, true);
        isprimer[0] = isprimer[1] = false;
        for (int i = 2; i * i <= n; i++) {
            if (isprimer[i]) {
                for (int j = i * 2; j <= n; j += i) {
                    isprimer[j] = false;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (isprimer[i]) {
                primes.add(i);
            }
        }
        System.out.println(primes.size());
    }

    public static List<Integer> sieveOfEratosthenes(int n) {
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
}
