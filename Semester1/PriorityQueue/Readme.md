# Assignment 8

## Priority Queue

### Job Scheduler
You have been asked to test the effectiveness of the following different scheduling algorithms for different types of queuing applications.
1) First-In-First-Out (FIFO): The jobs are processed in the arriving order. The job at the front of the queue is served until it has completed. That job is then removed from the queue. The next job at the front of the queue is served until it has completed and then it is removed from the queue. This process is continued until the queue is empty.
2) Shortest-Job-First (SJF): The job with the shortest processing time is processed first until it has completed. That job is then removed from the queue. The next job with smallest processing is then served until it has completed and then it is removed from the queue. This process is continued until the queue is empty.
3) Round-Robin (RR): Jobs are processed using a fixed time-slice. The jobs are initially kept in a queue based on the order of arrival. The job at the front of the queue is removed and served similar to the FIFO algorithm. However, unlike the FIFO algorithm, each job is served up to the pre-defined slice of time. If job can be completed within the allotted time it is fully served and removed from the queue. If the job cannot be completed in the allotted time, it is served for the allotted time and then added to the end of the queue to be served later for the remaining time. This process is continued until the queue is empty.

The total turnaround time is the total time a job spends in the system: Turnaround time = processing time + waiting time (time spend in queue).
For example, it the work load is 15 units, but it spends 50 units of time in queue, waiting to be processed, then the turnaround time is equal to 65 units.

A) Write a program to test the effectiveness of these algorithms. Create 100 jobs, each with a random processing time of 0 to 100 time units. Then process the jobs according to the above scheduling methods. You can use an ArrayList for the FIFO and RR scheduling schemes. Implement the SJF using a binary heap (priority heap). Use a time slice of 20 units. Compare the average turnaround time for the different strategies.

B) Experiment with different time slices for the RR strategy and compare the results. Use time slices in increments of 5 units and see if there are any differences in the average turnaround time.

## Implementation

### MinPQ
The implementation of Minimum Priority Queue is adapted from Sedgewick's Algorithms 4th Ed. textbook (pp 320). Modified implementation follows the textbook code for MaxPQ but simplifies the indexed implementation API  shown in the textbook to remove member variables for associating a key with its index and other API methods that are not used for the various job scheduling algorithms

### Job
The main Job class that is used in all the scheduler implementations that extend the JobSchedulerModel class to implement the relevant scheduling algorithms. Job objects have arrival time, start time, end time, processing time, TAT, waiting times as their main properties the boolean flag bCompleted is used to indicate that the job had completed. The job class has various comparator classes that are useful to sort the job objects by processingTime, arrivalTime or ids. 

### JobSchedulerModel
The main JobScheduler parent class is extended by each scheduler implementation such as RR, FIFO and SJF to implement the relevant scheduling algorithms

### ScheduleTester
The main schdule tester class generates a fixed number of jobs with random arrival and processing times options and schedules them with FIFO, SJF and RR algorithms with various slice times

### RoundRobin
RoundRobin extends the JobSchedulerModel class to implement the relevant roundrobin methods:
Jobs are processed using a fixed time slice using an initial readyQueue (which holds all jobs that are ready to be processed) based on the order of arrival. The job at the front of readyQueue is removed and served similar to the FIFO algorithm. Each job is processed up to the pre-defined slice of time. If job can be completed within the allotted time it is complete and removed from the readyQueue. If the job cannot be completed in the allotted time, it is preempted by other job at the head of the readyQueue and then added to the end of the readyQueue and to be processed for the remaining time. This process continues until the queue is empty.                                                                                        
Implementation details:                                                                                                                 
1. A clock timer variable is initialized to zero and used to advance time                                                                
2. A temporary copy of all the values of processing times for all the jobs is used and initialized to zero                               
3. A boolean array is used to keep track of whether each job is completed or not.                                                        
4. The readyQueue maintains an array of indices of the jobs that are in the queue awaiting further processing                            
5. Clock timer counts up until 1st process arrives and then we add its index to the readyQueue array.                                    
6. The first process is executed until the specified time slice and we check whether any other process have arrived during this period. If any job arrivals exist, the indices of those jobs are added to readyQueue using the updateReadyQueue method 
7. After above steps if a job is complete, we update the endTime and TAT and calculate its wait time using (TAT - processingTime) 
8. Then the next job from the readyQueue array is executed, otherwise, we move the currently executed process to end of readyQueue (called preemption) when the time slice expires.                                                                                       
9. We repeat steps 5-8 until all the jobs have been completely executed. In a scenario where there are some processes left but they have not arrived yet, then we just advance the clock until those jobs arrive as during this idle period no jobs can be processed 

### FIFO
Implements FIFO scheduler

### SJF 
Implements Shortest-Job-First (SJF) scheduler
