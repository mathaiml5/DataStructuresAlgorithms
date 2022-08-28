/*  The main JobScheduler parent class is extended by each scheduler implementation such as RR, FIFO and SJF that extend
 *  the JobSchedulerModel class to implement the relevant scheduling algorithms
 *
 *  @author: Vishak Srikanth
 *  @version: 11/01/2021
 */
import java.util.ArrayList;

public class JobSchedulerModel {

    //The list of jobs to be scheduled as an ArrayList (in original order in which they appear)
    ArrayList<Job> jobList = new ArrayList<Job>();

    //The minPQ implementation used to store the jobs as a min priority queue in ascending order of their key
    //Each scheduler subclass will have different job properties which it will use to sort e.g. FIFO uses arrivalTime
    // while SJF uses increases order of processing time to sort the jobs
    MinPQ<Job> jobPendingQ = new MinPQ<>();

    //An arraylist to hold the finished jobs
    ArrayList<Job> finishedJobs = new ArrayList<>();

    //A flag that is set to indicate that all jobs have been processed
    boolean bJobsProcessed = false;

    //The type of scheduler algorithm used: can be RR, SJF or FIFO
    String myQTpye = "Default";

    /** Constructor to create the schedule model object with the input jobs that need to be scheduled
     * @param jobList input list of jobs in order of their ids
     */
    public JobSchedulerModel(ArrayList<Job> jobList) {
        this.jobList = jobList;
        //create queue has no default implementation in parent class and only needs to be implmented in child classes
        // which extend JobSchedulerModel class
        createQueue();
    }

    //Getters and Setter methods
    public ArrayList<Job> getJobList() {
        return jobList;
    }
    public void setJobList(ArrayList<Job> jobList) {
        this.jobList = jobList;
    }
    public MinPQ<Job> getJobPendingQ() {
        return jobPendingQ;
    }

    public void setJobPendingQ(MinPQ<Job> jobPendingQ) {
        this.jobPendingQ = jobPendingQ;
    }

    public ArrayList<Job> getFinishedJobs() {
        return finishedJobs;
    }

    public void setFinishedJobs(ArrayList<Job> finishedJobs) {
        this.finishedJobs = finishedJobs;
    }

    public boolean isbJobsProcessed() {
        return bJobsProcessed;
    }



    public void setbJobsProcessed(boolean bJobsProcessed) {
        this.bJobsProcessed = bJobsProcessed;
    }

    public String getMyQTpye() {
        return myQTpye;
    }

    public void setMyQTpye(String myQTpye) {
        this.myQTpye = myQTpye;
    }

    public void createQueue() {
        //implemented in subclasses
    }


    /**
     * prints the details of the jobs simulated
     */
    public void printJobDetails() {

        System.out.println();
        System.out.println("************ JOB SIMULATION ***********************");
        System.out.println("---------------------------------------------------");
        System.out.println("Job Details:  Job ID, Arrival Time, Processing time");
        System.out.println("---------------------------------------------------");
        for (Job j : jobList) {
            System.out.println(j.toString());
        }

    }


    /**
     *  Prints the sorted queue of jobs (as applicable)
     */
    public void printBestQ() {
        assert bJobsProcessed;
            System.out.println();
            System.out.println("************ OPTIMAL " + myQTpye + " QUEUE **************");
            System.out.println("---------------------------------------------");
            System.out.println("\t  Job ID | \t Arrival Time|\t Proc time   | ");
            System.out.println("----------------------------------------------");

            for (Job currJob : finishedJobs) {

                if(currJob.getId()==0) continue;
                System.out.printf("%10d \t | %10d \t | %10d \t |", currJob.getId(), currJob.getArrivalTime (), currJob.getProcessingTime());
                System.out.println();
            }
            System.out.println("----------------------------------------------");
            System.out.println();

    }


    /**
     * prints the final job schedule
     */
    public void printFinishedSchedule(){
        assert bJobsProcessed;
        System.out.println("***************************************** BEST "+ myQTpye + " JOB SCHEDULE *************************************************************");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t  Job ID | \t Arrival Time|\t Proc time   | \t Start Time  | \t End Time    |\t Wait Time   |\t     TAT \t | Elapsed Time |");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        int currTAT = 0;
        int totalTAT = 0;
        int totalWaitTime = 0;
        for (Job currJob : finishedJobs) {
            currTAT = currJob.getProcessingTime() + currJob.getWaitingTime();
            totalTAT += currTAT;
            totalWaitTime += currJob.getWaitingTime();
            System.out.printf("%10d \t | %10d \t | %10d \t | %10d \t | %10d \t | %10d \t | %10d \t | %10d \t|", currJob.getId(), currJob.getArrivalTime(), currJob.getProcessingTime(),  currJob.getStartTime(),  currJob.getEndTime(), currJob.getWaitingTime(),  currTAT, currJob.getEndTime());
            System.out.println();
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t \t \t Average Waiting Time: %10.2f \t\t\t Average TAT: %10.2f", (totalWaitTime*1.0/ finishedJobs.size()), (totalTAT*1.0/finishedJobs.size()));
    }


    /**
     * Computes the schedule - only check is provided in parent class but needs to be implemented as per each scheduler algorithm
     */
    public void computeSchedule(){
        //Will be implemented in each subclass, here we just provide an empty function
        System.out.println("If you are here, you have not implemented the scheduler for your Queue subclass!");
    }
}