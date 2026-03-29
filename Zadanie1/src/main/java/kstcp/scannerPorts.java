/*
 *  Koszalin 2004
 *  scannerPorts.java
 *  Przyklad skanera portow
 *  Dariusz Rataj (C)
 */

package kstcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class scannerPorts {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT_FROM = 1;
    private static final int DEFAULT_PORT_TO = 1023;
    private static final int DEFAULT_TIMEOUT_MS = 200;

    public static void main(String[] args) {
        ScanConfig config = parseArgs(args);
        if (config == null) {
            printUsage();
            return;
        }
        //kstcp.scannerPorts --host=127.0.0.1 --from=1 --to=5000 --timeout=200 --compare
        config.host = "127.0.0.1";
        config.portFrom = 1;
        config.portTo = 1023;
        config.timeoutMs = 200;
        config.compareMode = true;
        if (config.compareMode) {
            runComparison(config.host, config.portFrom, config.portTo, config.timeoutMs);
            return;
        }

        if (config.threads <= 1) {
            ScanResult result = scanIterative(config.host, config.portFrom, config.portTo, config.timeoutMs);
            printResult("ITERACYJNY", result);
        } else {
            ScanResult result = scanConcurrent(config.host, config.portFrom, config.portTo, config.timeoutMs, config.threads);
            printResult("WIELOWATKOWY (" + config.threads + " watki)", result);
        }
    }

    private static void runComparison(String host, int portFrom, int portTo, int timeoutMs) {
        System.out.println("Skanowanie hosta: " + host + " porty " + portFrom + "-" + portTo + ", timeout=" + timeoutMs + " ms");
        System.out.println("Porownanie czasu: iteracyjny vs 2/5/10 watkow");
        System.out.println();

        ScanResult iterative = scanIterative(host, portFrom, portTo, timeoutMs);
        ScanResult t2 = scanConcurrent(host, portFrom, portTo, timeoutMs, 2);
        ScanResult t5 = scanConcurrent(host, portFrom, portTo, timeoutMs, 5);
        ScanResult t10 = scanConcurrent(host, portFrom, portTo, timeoutMs, 10);

        printResult("ITERACYJNY", iterative);
        printResult("WIELOWATKOWY (2 watki)", t2);
        printResult("WIELOWATKOWY (5 watkow)", t5);
        printResult("WIELOWATKOWY (10 watkow)", t10);
    }

    private static ScanResult scanIterative(String host, int portFrom, int portTo, int timeoutMs) {
        List<Integer> openPorts = new ArrayList<>();
        long startNs = System.nanoTime();

        for (int port = portFrom; port <= portTo; port++) {
            if (isPortOpen(host, port, timeoutMs)) {
                openPorts.add(port);
            }
        }

        long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        Collections.sort(openPorts);
        return new ScanResult(openPorts, durationMs, portTo - portFrom + 1);
    }

    private static ScanResult scanConcurrent(String host, int portFrom, int portTo, int timeoutMs, int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        long startNs = System.nanoTime();

        try {
            for (int port = portFrom; port <= portTo; port++) {
                final int currentPort = port;
                futures.add(CompletableFuture.supplyAsync(() -> isPortOpen(host, currentPort, timeoutMs) ? currentPort : -1, executor));
            }

            List<Integer> openPorts = new ArrayList<>();
            for (CompletableFuture<Integer> future : futures) {
                try {
                    int port = future.get();
                    if (port != -1) {
                        openPorts.add(port);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (ExecutionException e) {
                    // pojedyncze zadanie moglo zakonczyc sie bledem; pomijamy port
                }
            }

            long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            Collections.sort(openPorts);
            return new ScanResult(openPorts, durationMs, portTo - portFrom + 1);
        } finally {
            executor.shutdownNow();
        }
    }

    private static boolean isPortOpen(String host, int port, int timeoutMs) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeoutMs);
            return true;
        } catch (UnknownHostException e) {
            System.err.println("Nieznany host: " + host);
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private static void printResult(String label, ScanResult result) {
        System.out.println("[" + label + "]");
        System.out.println("Liczba sprawdzonych portow: " + result.scannedPorts);
        System.out.println("Czas skanowania: " + result.durationMs + " ms");
        if (result.openPorts.isEmpty()) {
            System.out.println("Brak otwartych portow w podanym zakresie.");
        } else {
            System.out.println("Otwarte porty: " + result.openPorts);
        }
        System.out.println();
    }

    private static ScanConfig parseArgs(String[] args) {
        ScanConfig config = new ScanConfig();
        config.host = DEFAULT_HOST;
        config.portFrom = DEFAULT_PORT_FROM;
        config.portTo = DEFAULT_PORT_TO;
        config.timeoutMs = DEFAULT_TIMEOUT_MS;
        config.threads = 1;
        config.compareMode = false;

        for (String arg : args) {
            try {
                if (arg.startsWith("--host=")) {
                    config.host = arg.substring("--host=".length());
                } else if (arg.startsWith("--from=")) {
                    config.portFrom = parseInt(arg.substring("--from=".length()), "from");
                } else if (arg.startsWith("--to=")) {
                    config.portTo = parseInt(arg.substring("--to=".length()), "to");
                } else if (arg.startsWith("--timeout=")) {
                    config.timeoutMs = parseInt(arg.substring("--timeout=".length()), "timeout");
                } else if (arg.startsWith("--threads=")) {
                    config.threads = parseInt(arg.substring("--threads=".length()), "threads");
                } else if (arg.equals("--compare")) {
                    config.compareMode = true;
                } else if (!arg.startsWith("--")) {
                    config.host = arg; // zgodnosc wsteczna: pierwszy argument bez flagi to host
                } else {
                    System.err.println("Nieznany argument: " + arg);
                    return null;
                }
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }

        if (config.portFrom < 1 || config.portTo > 65535 || config.portFrom > config.portTo) {
            System.err.println("Nieprawidlowy zakres portow.");
            return null;
        }
        if (config.timeoutMs <= 0) {
            System.err.println("Timeout musi byc > 0.");
            return null;
        }
        if (config.threads <= 0) {
            System.err.println("Liczba watkow musi byc > 0.");
            return null;
        }
        return config;
    }

    private static int parseInt(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Nieprawidlowa wartosc dla " + fieldName + ": " + value, e);
        }
    }

    private static void printUsage() {
        System.out.println("Uzycie:");
        System.out.println("  java kstcp.scannerPorts [host]");
        System.out.println("  java kstcp.scannerPorts --host=127.0.0.1 --from=1 --to=1023 --timeout=200 --threads=5");
        System.out.println("  java kstcp.scannerPorts --host=127.0.0.1 --from=1 --to=1023 --timeout=200 --compare");
    }

    private static class ScanConfig {
        String host;
        int portFrom;
        int portTo;
        int timeoutMs;
        int threads;
        boolean compareMode;
    }

    private static class ScanResult {
        final List<Integer> openPorts;
        final long durationMs;
        final int scannedPorts;

        private ScanResult(List<Integer> openPorts, long durationMs, int scannedPorts) {
            this.openPorts = openPorts;
            this.durationMs = durationMs;
            this.scannedPorts = scannedPorts;
        }
    }

}  // koniec skanerPorts
