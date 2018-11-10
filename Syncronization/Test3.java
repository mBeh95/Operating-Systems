/*******************************/
//
// Prarin Behdarvandian
// CSS430
// Program 3 Test3
// 11/12/2017
//	
/******************************/
import java.util.*;
import java.util.Date;

class Test3 extends Thread 
{
	private int pairNum;

	private long beginTime; //Time tests begins
    private long finishTime; //Time tests finishes
    private long totalTime; // End time - start time

	public Test3( String[] args ) 
	{
    	pairNum = Integer.parseInt( args[0] ); // Get the pari number
    	beginTime = new Date( ).getTime( ); //Get start time
	}

    public void run( ) 
    {
    	for(int i = 0; i < pairNum; i++) // Loop the same number as the pair number
    	{
    		SysLib.exec( SysLib.stringToArgs("TestThread3a")); // Execute TestThread3a
			SysLib.exec( SysLib.stringToArgs("TestThread3b")); // Execute TestThread3b
    	}
    	for(int i = 0; i < pairNum*2; i++) // call join for each execution
    	{
    		SysLib.join(); // Join
    	}

    	finishTime = new Date( ).getTime( ); //Get end time

    	totalTime = finishTime - beginTime; // Get the total time

    	//SysLib.cout("Beginning time is:" + beginTime + " ms.\n"); // Output start time
    	//SysLib.cout("Finishing time is:" + finishTime + " ms.\n"); //Output end time
    	SysLib.cout("The elapsed time is:" + totalTime + " ms.\n"); //Output total time
    	//SysLib.cout( "\nTest3 is completed\n" );
    	SysLib.exit();
    }
}