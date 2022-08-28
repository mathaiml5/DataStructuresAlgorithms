import java.util.ArrayList;

public class FIFO extends JobSchedulerModel {


    public FIFO(ArrayList<Job> jobList) {
        super(jobList);
        myQTpye = "FIFO";
        createQueue();
//        sort();
    }

    public void createQueue(){
        jobPendingQ= new MinPQ(jobList.size(), new Job.SortbyArrivalTime());
        for(Job j: jobList){
            int myId = j.getId();
            int myArrvlTime = j.getArrivalTime();
            jobPendingQ.insert(j);
        }
    }





    public void computeSchedule(){
        int elapsedTime = 0;
        int prevEndTime = 0;
        int jobCount = 0;
        int currTAT =0;

        for(Job currJob : jobPendingQ){
            if(currJob.getId()==0) continue;
            int currArrTime = currJob.getArrivalTime();
            int currProcTime = currJob.getProcessingTime();
            //if either prior job is done before current job arrives or it is 1st job no wait time, can start immediately
                if(currArrTime < prevEndTime){
                    currJob.setStartTime(prevEndTime);
                    currJob.setWaitingTime(prevEndTime - currArrTime);

                }
                //otherwise
                else{
                    currJob.setStartTime(currArrTime);
                    currJob.setWaitingTime(0);

                }
            currJob.setEndTime(currJob.getStartTime() + currJob.getProcessingTime());
            elapsedTime = currJob.getEndTime();

            prevEndTime = elapsedTime;

            jobCount++;
            finishedJobs.add(currJob);

        }
        if(jobList.size() == jobCount) bJobsProcessed = true;
    }





}
