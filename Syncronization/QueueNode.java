/*******************************/
//
// Prarin Behdarvandian
// CSS430
// Program 3 QueueNode
// 11/12/2017
//	
/******************************/
import java.util.*;
import java.io.*;

public class QueueNode
{
	private Vector<Integer> vectorQueue;

	public QueueNode()
	{
		vectorQueue = new Vector<>();
	}

	public synchronized int sleep()
	{
		if(vectorQueue.isEmpty())//Check to see if vectorQueue is empty
		{
			try
			{
				wait(); //Make the parent wait
			}
			catch(InterruptedException e){}
		}
		return vectorQueue.remove(0); //Return and remove the first element AKA the child who woke up the thread
		
	}

	public synchronized void wakeup(int tid)
	{
		vectorQueue.add(tid); //Add the child to the vectorQueue
		notify(); //Notify Parent
	} 
}