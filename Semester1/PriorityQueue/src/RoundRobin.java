/*  The following implementation of Round Robin extends the JobSchedulerModel class to implement the relevant roundrobin methods
 *  Jobs are processed using a fixed time slice using an initial readyQueue (which holds all jobs that are ready to be processed)
 *  based on the order of arrival. The job at the front of readyQueue is removed and served similar to the FIFO algorithm.
 *  Each job is processed up to the pre-defined slice of time. If job can be completed within the allotted time it is complete and
 *  removed from the readyQueue. If the job cannot be completed in the allotted time, it is preempted by other job at the head of the
 *  readyQueue and then added to the end of the readyQueue and to be processed for the remaining time.
 *  This process continues until the queue is empty.
 *  Implementation details:
 * 1. A clock timer variable is initialized to zero and used to advance time
 * 2. A temporary copy of all the values of processing times for all the jobs is used and initialized to zero
 * 3. A boolean array is used to keep track of whether each job is completed or not.
 * 4. The readyQueue maintains an array of indices of the jobs that are in the queue awaiting further processing
 * 5. Clock timer counts up until 1st process arrives and then we add its index to the readyQueue array.
 * 6. The first process is executed until the specified time slice and we check whether any other process have arrived
 *    during this period. If any job arrivals exist, the indices of those jobs are added to readyQueue using the updateReadyQueue method
 * 7. After above steps if a job is complete, we update the endTime and TAT and calculate its wait time using (TAT - processingTime)
 * 8. Then the next job from the readyQueue array is executed, otherwise, we move the currently executed process to end of readyQueue
 *   (called preemption) when the time slice expires.
 * 9. We repeat steps 5-8 until all the jobs have been completely executed. in a scenario where there are some processes left but
 *    they have not arrived yet, then we just advance the clock until those jobs arrive as during this idle period no jobs can be processed
 *
 *  @author: Vishak Srikanth
 *  @version: 11/01/2021
 */

import java.util.ArrayList;

public class RoundRobin extends JobSchedulerModel{

    private ArrayList<Job> inProcessJobs = new ArrayList<Job>();
    private int timeSlice = 0;

    public RoundRobin(ArrayList<Job> jobList, int s) {
        super(jobList);
        timeSlice = s;
    }

    //Main method which computes the Round Robin Schedule
    public void computeSchedule() {

        //Initialize local variables for slice, clock Timer, Total # of jobs, and index of max process in readyQueue
        int numJobs, slice, clockTimer = 0, maxProccessIndex = 0;
        //Initialize
        float avgWaitTime = 0, avgTAT = 0;

        slice = timeSlice;

        numJobs = jobList.size();

        //We use arrays indexed by the order in which the jobs are originally created
        int arrivalTime[] = new int[numJobs];
        int processingTime[] = new int[numJobs];
        int waitTime[] = new int[numJobs];
        int TAT[] = new int[numJobs];
        int readyQueue[] = new int[numJobs];
        int originalProcessingTimeCopy[] = new int[numJobs];
        int jobId[] = new int[numJobs];
        boolean jobCompleteFlag[] = new boolean[numJobs];

        //Counter to read job list
        int id = 0;

        //Get the relevant arrays populated from the Job objects in the jobs list
        for(Job j: jobList) {
            arrivalTime[id] = j.getArrivalTime();
            processingTime[id] = j.getProcessingTime();
            originalProcessingTimeCopy[id] = processingTime[id];
            jobId[id]= j.getId();
            //Initialize the ready readyQueue and jobCompleteFlag job ids array
            readyQueue[id] = 0;
            jobCompleteFlag[id] = false;
            id++;
        }

        Object[] currJobList = jobList.toArray();
        System.out.println();
        System.out.println("******************* BEST ROUND ROBIN JOB SCHEDULE FOR TIMESLICE = " + timeSlice + " * *****************************************");
        //Run the clock timer until 1st process arrives
        while(clockTimer < arrivalTime[0])
            clockTimer++;

        //Set the ready queue to 1st job
        readyQueue[0] = 1;

        //
        while(true){
            //First set the all Jobs processed flag to true
            boolean allJobsProcessedFlag = true;

            //loop thru' all jobs to check any of the jobs have not been processed
            for(int i = 0; i < numJobs; i++){
                if(originalProcessingTimeCopy[i] != 0){
                    allJobsProcessedFlag = false;
                    break;
                }
            }

            //break out of master while loop if all jobs have been processed
            if(allJobsProcessedFlag)
                break;

            //Loop over all job indices when it is already in readyQueue (indicated by readyQueue[i] == 1)
            for(int i = 0; (i < numJobs) && (readyQueue[i] != 0); i++){
                //time counter
                int ctr = 0;

                //Advance to 1st job's arrival time and build readyQueue until all jobs have arrived
                //Execute the first job for time slice and during that slice, we check
                //whether any other process has arrived or not and if it has then we add the index in the queue
                //and update the readyQueue
                while((ctr < slice) && (originalProcessingTimeCopy[readyQueue[0]-1] > 0)){
                    originalProcessingTimeCopy[readyQueue[0]-1] -= 1;
                    clockTimer += 1;
                    ctr++;

                    //Updating the readyQueue until all the jobs have arrived
                    checkNewArrival(clockTimer, arrivalTime, numJobs, maxProccessIndex, readyQueue);
                }


                // Set the current job's index (currIdx), check it has been processed
                int currIdx = readyQueue[0]-1;
                if((originalProcessingTimeCopy[currIdx] == 0) && (jobCompleteFlag[currIdx] == false)){
                    Job currJob = (Job) currJobList[currIdx];
                    currJob.setEndTime(clockTimer);
                    currJob.setbCompleted(true);
                    finishedJobs.add(currJob);
                    //TAT currently stores exit times
//                    System.out.println("JobId: " + currIdx + " Completed at time = " + clockTimer + "with TAT = " + TAT[currIdx] );
                    TAT[currIdx] = clockTimer;
//                    System.out.println("JobId: " + jobId[currIdx] + " Completed at time = " + clockTimer + "with TAT = " + TAT[currIdx] );
                    jobCompleteFlag[currIdx] = true;
                }

                //Checks there are any jobs being processed
                boolean idle = true;
                //If last job in readyQueue is being processed
                if(readyQueue[numJobs -1] == 0){
                    for(int k = 0; k < numJobs && readyQueue[k] != 0; k++){
                        //Check job complete status for all prior jobs
                        if(jobCompleteFlag[readyQueue[k]-1] == false){
                            idle = false;
                        }
                    }
                }
                //If none of the jobs are being processed, means we are in idle state
                else
                    idle = false;

                //If we are in idle state check for new jobs arriving
                if(idle){
                    clockTimer++;
                    checkNewArrival(clockTimer, arrivalTime, numJobs, maxProccessIndex, readyQueue);
                }

                //Maintaining the entries of processes after each premption in the ready Queue
                queueUpdateAfterPreemption(readyQueue, numJobs);
            }
        }
        //We need to subtract arrival time from the current value of TAT which holds the end time of job
        //then compute wait time from TAT and processing time
        for(int i = 0; i < numJobs; i++){
            TAT[i] = TAT[i] - arrivalTime[i];
            waitTime[i] = TAT[i] - processingTime[i];
        }

        System.out.print("\nJob Id |\tArrival Time |\tProcessing Time |\tWait Time |\tTurnAround Time |"
                + "\n");
        for(int i = 0; i < numJobs; i++){
            System.out.printf("%8d \t | %8d \t | %8d \t | %8d \t | %8d \t | \n", (i+1), arrivalTime[i], processingTime[i], waitTime[i],  TAT[i]);
        }
        for(int i = 0; i< numJobs; i++){
            avgWaitTime += waitTime[i];
            avgTAT += TAT[i];
        }
        System.out.print("\nAverage waitTime time : "+(avgWaitTime / numJobs)
                +"\nAverage Turn Around Time : "+(avgTAT / numJobs));
    }


    /** Updates the readyQueue with next job
     * @param queue current readyQueue
     * @param timer current clock time
     * @param arrival Array of arrival times indexed by job index as in original list
     * @param n number of jobs
     * @param maxProccessIndex Highest process index being processed
     */
    public static void updateReadyQueue(int queue[], int timer, int arrival[], int n, int maxProccessIndex){
        int zeroIndex = -1;
        for(int i = 0; i < n; i++){
            if(queue[i] == 0){
                zeroIndex = i;
                break;
            }
        }
        if(zeroIndex == -1)
            return;
        queue[zeroIndex] = maxProccessIndex + 1;
    }


    /** This function checks and adds any new jobs that have arrived when another job is being processed
     * @param timer currentClocktime
     * @param arrival: array of jobs arrival times
     * @param n number of job
     * @param maxProccessIndex: highest job index being processed
     * @param queue : readyQueue
     */
    public static void checkNewArrival(int timer, int arrival[], int n, int maxProccessIndex,int queue[]){
        //check if current time is smaller than previous job's arrival time => set new arrival to false
        if(timer <= arrival[n-1]){
            boolean newJobArrived = false;
            //then check from the last job to check if its arrival time is less than current time
            for(int j = (maxProccessIndex+1); j < n; j++){
                if(arrival[j] <= timer){
                    //When this max job index < current index then we have a new job Arrival!!
                    if(maxProccessIndex < j){
                        maxProccessIndex = j;
                        newJobArrived = true;
                    }
                }
            }
            //If a new job arrived when this is being processed add the index of the arriving process to readyQueue
            if(newJobArrived)
                updateReadyQueue(queue,timer,arrival,n, maxProccessIndex);
        }
    }

    /** Maintains the readyQueue properly after every job slice preemption.
     * @param queue readyQueue that holds the job indices
     * @param n : ready queue limit until preemption
     */
    public static void queueUpdateAfterPreemption(int queue[], int n){

        for(int i = 0; (i < n-1) && (queue[i+1] != 0) ; i++){
            int temp = queue[i];
            queue[i] = queue[i+1];
            queue[i+1] = temp;
        }
    }


    /**
     * Overriding the print schedule method as startTime is not that relevant due to the multiple start/stop cycles
     */
    public void printFinishedSchedule(){
//        assert bJobsProcessed;
        System.out.println("******************* BEST ROUND ROBIN JOB SCHEDULE FOR TIMESLICE = " + timeSlice + " * *****************************************");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t  Job ID | \t Arrival Time|\t Proc time   |\t End Time    |\t Wait Time   |\t     TAT \t | Elapsed Time |");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        int currTAT = 0;
        int totalTAT = 0;
        int totalWaitTime = 0;
        for (Job currJob : finishedJobs) {
            currTAT = currJob.getProcessingTime() + currJob.getWaitingTime();
            totalTAT += currTAT;
            totalWaitTime += currJob.getWaitingTime();
            System.out.printf("%10d \t | %10d \t | %10d \t | %10d \t | %10d \t | %10d \t | %10d \t|", currJob.getId(), currJob.getArrivalTime(), currJob.getProcessingTime(),  currJob.getEndTime(), currJob.getWaitingTime(),  currTAT, currJob.getEndTime());
            System.out.println();
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t \t \t Average Waiting Time: %10.2f \t\t\t Average TAT: %10.2f", (totalWaitTime*1.0/ finishedJobs.size()), (totalTAT*1.0/finishedJobs.size()));
    }

}
