/*******************************/
//
// Prarin Behdarvandian
// CSS430
// Program 3 TestThread3a
// 11/12/2017
//	
/******************************/
import java.util.*;
import java.util.Date;

class TestThread3a extends Thread 
{
	public TestThread3a() 
	{
	}

	public void run( ) 
    {
    	recursiveFibonacci(25);
    	SysLib.exit();
    }

    public int recursiveFibonacci(int recFibNum)
    {
    	if(recFibNum == 0)
    	{
    		return 0;
    	}
    	else if(recFibNum == 1)
    	{
    		return 1;
    	}
    	else
    	{
    		return recursiveFibonacci(recFibNum -1) + recursiveFibonacci(recFibNum-2); //Recursive call for fibonacci sequence
    	}
    }
}
