import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF extends JobSchedulerModel {

    public void multi_criteria_sort() {

        Comparator<Job> sjfComparator = Comparator.comparing(Job::getProcessingTime)
                .thenComparing(Job::getArrivalTime);

        Collections.sort(jobList, sjfComparator);

    }

    public SJF(ArrayList<Job> jobList) {
        super(jobList);
        myQTpye = "SJF";
        createQueue();
//        sort();
//        multi_criteria_sort();
    }


    public void createQueue() {

        jobPendingQ = new MinPQ(jobList.size(), new Job.SortbyProcessingTime());
        for (Job j : jobList) {
            int myId = j.getId();
            int myProcTime = j.getProcessingTime();
            jobPendingQ.insert(j);
        }

    }


    //    public ArrayList<Job> getPriorJobsWithCompletion(Job c){
//        int currArrTime = c.getArrivalTime();
//        ArrayList<Job> priorJobs = new ArrayList<Job>();
//        for(Job j : jobList){
//            if(j.getArrivalTime() < currArrTime && (j.getArrivalTime() + j.getProcessingTime() < currArrTime)){
//                priorJobs.add(j);
//            }
//        }
//        return priorJobs;
//    }
//
    public void computeSchedule() {
        int elapsedTime = 0;
        int prevEndTime = 0;
        int jobCount = 0;


        for (Job currJob : jobPendingQ) {
            if (currJob.getId() == 0) continue;
            int currArrTime = currJob.getArrivalTime();
            int currProcTime = currJob.getProcessingTime();
            //if either prior job is done before current job arrives or it is 1st job no wait time, can start immediately
            if (currArrTime < prevEndTime) {
                currJob.setStartTime(prevEndTime);
                currJob.setWaitingTime(prevEndTime - currArrTime);

            }
            //otherwise
            else {
                currJob.setStartTime(currArrTime);
                currJob.setWaitingTime(0);

            }
            currJob.setEndTime(currJob.getStartTime() + currJob.getProcessingTime());
            elapsedTime = currJob.getEndTime();

            prevEndTime = elapsedTime;

            jobCount++;
            finishedJobs.add(currJob);

        }
        if (jobList.size() == jobCount) bJobsProcessed = true;
    }


//    public void printScheduleTable(){
//        int elapsedTime = 0;
//        System.out.println();
//        System.out.println("***************************************** BEST SJF JOB SCHEDULE *************************************************************");
//        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("\t  Job ID | \t Arrival Time|\t Proc time   | \t Start Time  | \t End Time    |\t Wait Time   |\t     TAT \t | Elapsed Time |");
//        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
//        int prevEndTime = 0;
//        int currStartTime = 0;
//        int currEndTime =0;
//        int currWaitTime = 0;
//        int totalWaitTime = 0;
//        int currTAT =0;
//        int totalTAT =0;
//        int jobCount = 0;
//        ArrayList<Job> jobsProcessed = new ArrayList<Job>();
//        for(int i : pq){
//            if(i==0) continue;
//            Job currJob = jobList.get(i-1);
//            if(!jobsProcessed.isEmpty() && jobsProcessed.contains(currJob)) continue;
//            int currArrTime = currJob.getArrivalTime();
//            int currProcTime = currJob.getProcessingTime();
//            if(i==1){
////                ArrayList<Job> pj = getJobsWithPriorArrivalTime(currJob);
////                if(! pj.isEmpty()){
////
////                }
//            }
//
//
//
//
//            //if either prior job is done before current job arrives or it is 1st job no wait time, can start immediately
//                if(currArrTime < prevEndTime){
//                    currStartTime = prevEndTime;
//                    currWaitTime = prevEndTime - currArrTime;
//                }
//                //otherwise
//                else{
//                    currStartTime = currArrTime;
//                    currWaitTime = 0;
//                }
//
//            currEndTime = currStartTime + currProcTime;
//            elapsedTime = currEndTime;
//            prevEndTime = elapsedTime;
//            currTAT = currProcTime + currWaitTime;
//            System.out.printf("%10d \t | %10d \t | %10d \t | %10d \t | %10d \t | %10d \t | %10d \t | %10d \t|", currJob.getId(), currArrTime, currProcTime,  currStartTime ,  currEndTime , currWaitTime,  currTAT, elapsedTime);
//            totalWaitTime += currWaitTime;
//            totalTAT += currTAT;
//            jobCount++;
//            System.out.println();
//        }
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
//        System.out.printf("\t \t \t Average Waiting Time: %10.2f \t\t\t Average TAT: %10.2f", (totalWaitTime*1.0/jobCount), (totalTAT*1.0/jobCount));
//    }


}
