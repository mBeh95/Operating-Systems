/*******************************/
//
// Prarin Behdarvandian
// CSS430
// Program 3 TestThread3b
// 11/12/2017
//	
/******************************/
import java.util.*;
import java.util.Random;


class TestThread3b extends Thread 
{
    private byte dataBuffer[]; //
    private Random randomNumber; //Random number variable
    private int pickedNumber; //The resulting random number

	public TestThread3b() 
	{
		dataBuffer = new byte[512]; // Make the dataBuffer be size of 512
		randomNumber = new Random(); // Initialize randomNumber
	}

	public void run()
	{
		for(int i = 0; i < 100;i++)
		{
			pickedNumber = randomNumber.nextInt(1000); //Give me a random number from 0 to 99
			//SysLib.cout(""+ pickedNumber+"\n");
			SysLib.rawread(pickedNumber, dataBuffer); //Call rawread
			SysLib.rawwrite(pickedNumber, dataBuffer); //Call rawwrite
		}

		SysLib.exit();
	}
}