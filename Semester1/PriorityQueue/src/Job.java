/*  The main Job class that is used in all the scheduler implementations that extend the JobSchedulerModel class to implement
*   the relevant scheduling algorithms
 *  Job objects have arrival time, start time, end time, processing time, TAT, waiting times as their main properties
 *  the boolean flag bCompleted is used to indicate that the job had completed
 *  The job class has various comparator classes that are useful to sort the job objects by processingTime, arrivalTime or ids.
 *  @author: Vishak Srikanth
 *  @version: 11/01/2021
 */
import java.util.Comparator;

public class Job implements Comparable {
    private int arrivalTime;
    private int id;
    private int processingTime;
    private int remainingTime;
    private int waitingTime;
    private int startTime;
    private int endTime;
    private int TAT;
    private boolean bCompleted = false;


    /** Constructor for job: needs a jod id, its arrival time and its processing time
     * @param id job id
     * @param arrivalTime when the job arrived for processing
     * @param processingTime how long the job takes to process
     */
    public Job(int id, int arrivalTime, int processingTime) {
        this.arrivalTime = arrivalTime;
        this.id = id;
        this.processingTime = processingTime;
        remainingTime = processingTime;
    }


    /**
     * Getters and setters for each member variable
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String toString() {
        return "Job ID: " + id + " Arrival time: " + arrivalTime + " ProcTime: " + processingTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getTAT() {
        return TAT;
    }

    public void setTAT(int TAT) {
        this.TAT = TAT;
    }

    public boolean isbCompleted() {
        return bCompleted;
    }

    public void setbCompleted(boolean bCompleted) {
        this.bCompleted = bCompleted;
    }


    //Need to implement compareTo method for comparing 2 jobs!
    @Override
    public int compareTo(Object o) {
        return 0;
    }


    /**
     * Comparator class for sorting jobs by their arrivalTime
     */
    public static class SortbyArrivalTime implements Comparator<Job> {
        // Used for sorting in ascending order of
        // arrival time
        public int compare(Job a, Job b) {
            return (new Integer(a.arrivalTime)).compareTo(b.arrivalTime);
        }
    }

    /**
     * Comparator class for sorting jobs by their processingTime
     */
    public static class SortbyProcessingTime implements Comparator<Job> {
        // Used for sorting in ascending order of
        // processingTime
        public int compare(Job a, Job b) {
            return (new Integer(a.processingTime)).compareTo(b.processingTime);
        }
    }

    /**
     * Comparator class for sorting by job id
     */
    public static class SortbyId implements Comparator<Job> {
        @Override
        public int compare(Job a, Job b) {
            return (new Integer(a.id)).compareTo(b.id);
        }
    }

}
