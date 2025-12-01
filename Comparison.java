package Week_09;

public class Comparison {

    static int sharedCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 10;//10 normal threads
        int workload = 1000;//Threads count the time


        Thread[] normalThreads = new Thread[numberOfThreads];//create array for all threads

        long startTime = System.nanoTime();//Record start time

        for (int i = 0; i < numberOfThreads; i++) {//using loop to create and start 10 normal threads
            normalThreads[i] = new NormalThread(workload);
            normalThreads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            normalThreads[i].join();// make sure all the threads finished
        }


        long endTime = System.nanoTime();// Record end time


        double normalDuration = (endTime - startTime) / 1_000_000_000.0;// Calculate duration in seconds

        Thread[] syncThreads = new Thread[numberOfThreads];//10 Synchronized threads


        startTime = System.nanoTime();//Record start time


        for (int i = 0; i < numberOfThreads; i++) {// using loop to create and start 10 Synchronized threads
            syncThreads[i] = new SynchronizedThread(workload);
            syncThreads[i].start();
        }


        for (int i = 0; i < numberOfThreads; i++) {// make sure all the threads finished
            syncThreads[i].join();
        }


        endTime = System.nanoTime();// Record end time
        double syncDuration = (endTime - startTime) / 1_000_000_000.0;// Calculate duration in seconds

        System.out.printf("Normal thread = %.8f seconds%n", normalDuration);// print output for normal duration
        System.out.printf("Synchronized thread = %.8f seconds%n", syncDuration);// print output for Synchronized duration
    }


    static class NormalThread extends Thread {//normal thread
        int rounds;

        public NormalThread(int rounds) {
            this.rounds = rounds;
        }

        public void run() {
            for (int i = 0; i < rounds; i++) {
                sharedCounter++;
            }
        }
    }

    static class SynchronizedThread extends Thread {//Synchronized thread
        int rounds;

        public SynchronizedThread(int rounds) {
            this.rounds = rounds;
        }

        public void run() {
            for (int i = 0; i < rounds; i++) {
                synchronized (Comparison.class) {
                    sharedCounter++;
                }
            }
        }
    }
}