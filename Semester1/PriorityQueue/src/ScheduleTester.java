/*  The main schdule tester class generates a fixed number of jobs with random arrival and processing times options and
 *  schedules them with FIFO, SJF and RR algorithms with various slice times
 *
 *  @author: Vishak Srikanth
 *  @version: 11/01/2021
*/

import java.util.*;
import java.util.stream.IntStream;

public class ScheduleTester {

    //Main job list
    public static ArrayList<Job> jobList = new ArrayList<Job>();
    //Numm of jobs to simulate, and range of values to be randomly generated for job arrival and processing times
    public static final int numSim = 100;
    public static final int jobArrivalTimeRange = 100;
    public static final int jobProcessingTimeRange = 100;

//Unused random generator to ensure ids are unique, instead can use simple sequential array
//    public static int getRandomInt(int min, int max) {
//        Random random = new Random();
//
//        return random.nextInt((max - min)) + min;
//    }
//
//
//
//    public static int[] getRandomNonRepeatingIntegers(int size, int min,
//                                                                   int max) {
//        ArrayList<Integer> numbers = new ArrayList<Integer>();
//
//        while (numbers.size() < size) {
//            int random = getRandomInt(min, max);
//
//            if (!numbers.contains(random)) {
//                numbers.add(random);
//            }
//        }
//
//        int[]  intArr = numbers.stream()
//                .mapToInt(Integer::intValue)
//                .toArray();
//        Arrays.sort(intArr);
//        return intArr;
//    }

    /**
     * Print the details of simulated jobs as a table
     */
    public static void printJobDetails() {

        System.out.println();
        System.out.println("************ JOB SIMULATION ***********************");
        System.out.println("---------------------------------------------------");
        System.out.println("Job Details:  Job ID, Arrival Time, Processing time");
        System.out.println("---------------------------------------------------");
        for (Job j : jobList) {
            System.out.println(j.toString());
        }

    }

    //main method to test various schedules
    public static void main(String[] args) {

        //Simulate a fixed number of jobs with random range of processing and arrival times
        int[] idsArray = IntStream.range(1, numSim+1).toArray(); //getRandomNonRepeatingIntegers(numSim, 1, numSim+1); //IntStream.generate(() -> new Random().nextInt(numSim)).limit(numSim).toArray();
        int[] arrivalTimesArray = IntStream.generate(() -> new Random().nextInt(jobArrivalTimeRange)).limit(numSim).toArray();
        int[] procTimesArray = IntStream.generate(() -> new Random().nextInt(jobProcessingTimeRange)).limit(numSim).toArray();

        //Example test
        idsArray = new int[]{1, 2, 3, 4, 5};
        arrivalTimesArray = new int[]{0, 1, 2, 3, 4};//{0, 10, 10, 80, 85};
        procTimesArray = new int[]{5, 3, 1, 2, 3}; //{85, 30, 35, 20, 50};

        int nJobs = idsArray.length;

        //build job list
        for (int i = 0; i < nJobs; i++) {
            jobList.add(new Job(idsArray[i], arrivalTimesArray[i], procTimesArray[i]));
        }

        //print details of simulated jobs
        ScheduleTester.printJobDetails();

        //Schedule with FIFO algorithm
        FIFO myFIFO = new FIFO(jobList);
        myFIFO.computeSchedule();
        myFIFO.printBestQ();
        myFIFO.printFinishedSchedule();
//
        SJF mySJF = new SJF(jobList); // new Job.SortbyProcTime;
        mySJF.computeSchedule();
        mySJF.printBestQ();
        mySJF.printFinishedSchedule();

//        mySJF.printJobDetails();
          int sl = 2;
////        for (int sl = 5; sl <= 50; sl++) {
            RoundRobin roundRobin = new RoundRobin(jobList, sl);
//            roundRobin.printBestQ();
            roundRobin.computeSchedule();
//            roundRobin.printFinishedSchedule();
////        }

//            SJF_Example mySJF2 = new SJF_Example(jobList); // new Job.SortbyProcTime;
//            mySJF2.calculateSJFSchedule();
//            mySJF2.printBestQ();
//            mySJF2.printFinishedSchedule();


    }
}
