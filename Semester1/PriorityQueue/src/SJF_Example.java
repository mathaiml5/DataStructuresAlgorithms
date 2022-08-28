import java.util.ArrayList;

public class SJF_Example extends JobSchedulerModel{


    public SJF_Example(ArrayList<Job> jobList) {
        super(jobList);
        myQTpye = "SJF EXAMPLE 2";
    }

    public void calculateSJFSchedule()
    {

        int totalJobCount = jobList.size();
        Job[] jobs = new Job[totalJobCount];
        int pid[] = new int[totalJobCount]; //JobId for each job
        int arrivalTime[] = new int[totalJobCount]; // arrivalTime array holds arrival times of all jobs
        int processingTime[] = new int[totalJobCount]; // processingTime array holds processing time for each job
        int completionTime[] = new int[totalJobCount]; // completionTime array holds finish time for each job
        int TAT[] = new int[totalJobCount]; // TAT array holds turn around time for each job
        int WaitingTime[] = new int[totalJobCount];  //WaitingTime array waiting time for each job
        int jobCompleteFlag[] = new int[totalJobCount];  // jobCompleteFlag means it is flag it checks process is completed or not
        int systemTime =0, completedJobCount =0;
        float avgWaitTime =0, avgTAT =0;

        int pCount = 0;
        for(Job j : jobList){
            jobs[pCount] = j;
            pid[pCount] = j.getId();
            arrivalTime[pCount] = j.getArrivalTime();
            processingTime[pCount] = j.getProcessingTime();
            pCount++;
        }

        boolean a = true;
        while(true)
        {
            int c = totalJobCount, min=Integer.MAX_VALUE;
            if (completedJobCount == totalJobCount) // total no of process = completed process loop will be terminated
                break;
            for (int i = 0; i< totalJobCount; i++)
            {
                /*
                 * If i-th job arrival time <= system time and job is not finished and its processing time is smallest <min
                 * That job will be executed first
                 */
                if ((arrivalTime[i] <= systemTime) && (jobCompleteFlag[i] == 0) && (processingTime[i]<min))
                {
                    min= processingTime[i];
                    c=i;
                }
            }
            /* If c == totalJobCount means c value can not updated because no process arrival time < system time so we increase the system time */
            if (c == totalJobCount)
                systemTime++;
            else
            {

                Job currJob = jobs[c];
                completionTime[c]= systemTime + processingTime[c];
                systemTime += processingTime[c];
                TAT[c]= completionTime[c]- arrivalTime[c];
                WaitingTime[c]= TAT[c]- processingTime[c];
                jobCompleteFlag[c]=1;


                currJob.setTAT(TAT[c]);
                currJob.setWaitingTime(WaitingTime[c]);
                currJob.setEndTime(completionTime[c]);
                currJob.setbCompleted(true);

                finishedJobs.add(currJob);
                completedJobCount++;
            }
        }
        System.out.println("\npid  arrival brust  complete turn waiting");
        for(int i = 0; i< totalJobCount; i++)
        {
            avgWaitTime += WaitingTime[i];
            avgTAT += TAT[i];
            System.out.println(pid[i]+"\t"+ arrivalTime[i]+"\t"+ processingTime[i]+"\t"+ completionTime[i]+"\t"+ TAT[i]+"\t"+ WaitingTime[i]);
        }
        System.out.println ("\naverage tat is "+ (float)(avgTAT / totalJobCount));
        System.out.println ("average WaitingTime is "+ (float)(avgWaitTime / totalJobCount));
    }
}
