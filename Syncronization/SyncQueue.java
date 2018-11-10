/*******************************/
//
// Prarin Behdarvandian
// CSS430
// Program 3 SyncQueue
// 11/12/2017
//	
/******************************/
import java.util.*;
import java.io.*;

public class SyncQueue
{
	private QueueNode[] queue; // Make the array

	public SyncQueue()
	{
		queue = new QueueNode [10]; //Initialize array
		for (int i = 0; i < 10; i++)
        {
            queue[i] = new QueueNode(); //Initialize all QueueNode children
        }
	}

	public SyncQueue(int condMax)
	{
		queue = new QueueNode [condMax];
		for (int i = 0; i < condMax; i++)
        {
            queue[i] = new QueueNode();
        }
	}

	public int enqueueAndSleep(int condition)
	{
		if(condition > -1) //Check to make sure condition is valid (e.g. 0 or above)
		{
			int child = queue[condition].sleep(); //Call on the sleep function in QueueNode Class
			return child; //Return the noisy child
		}
		else
		{
			return -1; //return error if condition not valid
		}
	}

	public void dequeueAndWakeup(int condition)
	{
		if(condition > -1)
		{
			dequeueAndWakeup(condition, 0); //Call on the dequeueAndWakeup with two parameters with tid = 0;
		}
	}

	public void dequeueAndWakeup(int condition, int tid)
	{
		if(condition > -1)
		{
			queue[condition].wakeup(tid); //Call on the wakeup function in QueueNode Class
		}
	}
}