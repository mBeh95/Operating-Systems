/*******************************/
//
// Prarin Behdarvandian
// CSS430
// Program 4 Test4
// 11/26/2017
//	
/******************************/
import java.util.*;
import java.util.Date;

class Test4 extends Thread 
{
	private int testNum;
    private String type;
    private byte rDataBuffer[]; // Read buffer
    private byte wDataBuffer[]; // Write buffer

    private Random randomNumber; //Random number variable
    private int[] pickedNumber; //The resulting random number

	private long beginTime; //Time tests begins
    private long finishTime; //Time tests finishes
    private long rTotalTime; // End time - start time
    private long wTotalTime;


    //beginTime = new Date( ).getTime( ); //Get start time

	public Test4( String[] args ) 
	{
        type = args[0];
    	testNum = Integer.parseInt(args[1]); // Get the test number

        rDataBuffer = new byte[512]; // Make the read dataBuffer be size of 512
        wDataBuffer = new byte[512]; // Make the write dataBuffer be size of 512
        //dataBuffer2 = new byte[512]; // Make the dataBuffer be size of 512
        randomNumber = new Random(); // Initialize randomNumber
        pickedNumber = new int[200];
	}

    public void run( ) 
    {
        SysLib.flush();
        if(testNum == 1)
        {
            if(type.equals("enabled"))
            {
                rAccessEnabled();
            }
            else if (type.equals("disabled"))
            {
                rAccessDisabled();
            }
            else
            {
                SysLib.cerr("Error: only enabled or disabled");
            }
        }
        else if (testNum == 2)
        {
            if(type.equals("enabled"))
            {
                lAccessEnabled();
            }
            else if (type.equals("disabled"))
            {
                lAccessDisabled();
            }
            else
            {
                SysLib.cerr("Error: only enabled or disabled");
            }
        }
        else if(testNum == 3)
        {
            if(type.equals("enabled"))
            {
                mAccessEnabled();
            }
            else if (type.equals("disabled"))
            {
                mAccessDisabled();
            }
            else
            {
                SysLib.cerr("Error: only enabled or disabled");
            }
        }
        else if(testNum == 4)
        {
            if(type.equals("enabled"))
            {
                adAccessEnabled();
            }
            else if (type.equals("disabled"))
            {
                adAccessDisabled();
            }
            else
            {
                SysLib.cerr("Error: only enabled or disabled");
            }
        }
        else
        {
            SysLib.cerr(" Error: number should be ranged from 1 to 4");
        }

        if(type.equals("enabled"))
        {
            SysLib.csync();
        }
        else if (type.equals("disabled"))
        {
            SysLib.sync();
        }
        SysLib.exit();
    }

    public void rAccessEnabled()
    {
        for(int i = 0; i < 200; i++)
        {
            pickedNumber[i] = Math.abs(randomNumber.nextInt(1000));
        }

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.cread(pickedNumber[i], rDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        rTotalTime = finishTime - beginTime; // Get the total time

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.cwrite(pickedNumber[i], wDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        wTotalTime = finishTime - beginTime; // Get the total time


        if(Arrays.equals(wDataBuffer, rDataBuffer)) //Verify the two buffers are equal
        {
            SysLib.cout("Correctness Verified!\n");
        }

        //Output average time for both Read and write
        SysLib.cout("Read time average: " + (rTotalTime / 200) +" miliseconds \n");
        SysLib.cout("Write time average: " + (wTotalTime / 200) +" miliseconds \n");
        

    }

    public void rAccessDisabled()
    {
        for(int i = 0; i < 200; i++)
        {
            pickedNumber[i] = Math.abs(randomNumber.nextInt(1000));
        }

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.rawread(pickedNumber[i], rDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        rTotalTime = finishTime - beginTime; // Get the total time

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.rawwrite(pickedNumber[i], wDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        wTotalTime = finishTime - beginTime; // Get the total time


        if(Arrays.equals(wDataBuffer, rDataBuffer)) //check verification
        {
            SysLib.cout("Correctness Verified!\n");
        }

        //Output average time for both Read and write
        SysLib.cout("Read time average: " + (rTotalTime / 200) +" miliseconds \n");
        SysLib.cout("Write time average: " + (wTotalTime / 200) +" miliseconds \n");
    }

    public void mAccessEnabled()
    {
        for(int i = 0; i < 200; i++)
        {
            int chance = Math.abs(randomNumber.nextInt(10)); //get a range of 0 to 9, 9 being the 10% random
            if(chance < 9)
            {
                pickedNumber[i] = Math.abs(randomNumber.nextInt(10)); //Give the 0 to 9 range
            }
            else
            {
                pickedNumber[i] = Math.abs(randomNumber.nextInt(1000));
            }
        }

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            
            SysLib.cread(pickedNumber[i], rDataBuffer);

        }

        finishTime = new Date( ).getTime( ); //Get end time
        rTotalTime = finishTime - beginTime; // Get the total time

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            
            SysLib.cwrite(pickedNumber[i], wDataBuffer);
        
        }

        finishTime = new Date( ).getTime( ); //Get end time
        wTotalTime = finishTime - beginTime; // Get the total time


        if(Arrays.equals(wDataBuffer, rDataBuffer)) //Check verification
        {
            SysLib.cout("Correctness Verified!\n");
        }

        //Output average times
        SysLib.cout("Read time average: " + (rTotalTime / 200) +" miliseconds \n");
        SysLib.cout("Write time average: " + (wTotalTime / 200) +" miliseconds \n");
    }

    public void mAccessDisabled()
    {
        for(int i = 0; i < 200; i++)
        {
           int chance = Math.abs(randomNumber.nextInt(10)); //get a range of 0 to 9, 9 being the 10% random
            if(chance < 9)
            {
                pickedNumber[i] = Math.abs(randomNumber.nextInt(10));
            }
            else
            {
                pickedNumber[i] = Math.abs(randomNumber.nextInt(1000)); //random ID
            }
        }

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            
            SysLib.rawread(pickedNumber[i], rDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        rTotalTime = finishTime - beginTime; // Get the total time

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.rawwrite(pickedNumber[i], wDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        wTotalTime = finishTime - beginTime; // Get the total time


        if(Arrays.equals(wDataBuffer, rDataBuffer))// Verification check
        {
            SysLib.cout("Correctness Verified!\n");
        }

        //Avergae times output
        SysLib.cout("Read time average: " + (rTotalTime / 200) +" miliseconds \n");
        SysLib.cout("Write time average: " + (wTotalTime / 200) +" miliseconds \n");
    }
    public void lAccessEnabled()
    {
        for(int i = 0; i < 200; i++)
        {
            pickedNumber[i] = Math.abs(randomNumber.nextInt(10)); //Localized 0 to 9
        }

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.cread(pickedNumber[i], rDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        rTotalTime = finishTime - beginTime; // Get the total time

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.cwrite(pickedNumber[i], wDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        wTotalTime = finishTime - beginTime; // Get the total time

        if(Arrays.equals(wDataBuffer, rDataBuffer)) //Check Verification
        {
            SysLib.cout("Correctness Verified!\n");
        }

        //Average time output
        SysLib.cout("Read time average: " + (rTotalTime / 200) +" miliseconds \n");
        SysLib.cout("Write time average: " + (wTotalTime / 200) +" miliseconds \n");
    }

    public void lAccessDisabled()
    {
        for(int i = 0; i < 200; i++)
        {
            pickedNumber[i] = Math.abs(randomNumber.nextInt(10));
        }

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.rawread(pickedNumber[i], rDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        rTotalTime = finishTime - beginTime; // Get the total time

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.rawwrite(pickedNumber[i], wDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        wTotalTime = finishTime - beginTime; // Get the total time

        if(Arrays.equals(wDataBuffer, rDataBuffer))
        {
            SysLib.cout("Correctness Verified!\n");
        }

        SysLib.cout("Read time average: " + (rTotalTime / 200) +" miliseconds \n");
        SysLib.cout("Write time average: " + (wTotalTime / 200) +" miliseconds \n");
    }
    public void adAccessEnabled()
    {
       for(int i = 0; i < 200; i += 2) // set up so that it goes from one end of the size to other and back
        {
            //example of the array = 0 999 1 998
            pickedNumber[i] = i;
            pickedNumber[i+1] = 999 - i;
        }

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.cread(pickedNumber[i], rDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        rTotalTime = finishTime - beginTime; // Get the total time

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.cwrite(pickedNumber[i], wDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        wTotalTime = finishTime - beginTime; // Get the total time

        if(Arrays.equals(wDataBuffer, rDataBuffer))
        {
            SysLib.cout("Correctness Verified!\n");
        }

        SysLib.cout("Read time average: " + (rTotalTime / 200) +" miliseconds \n");
        SysLib.cout("Write time average: " + (wTotalTime / 200) +" miliseconds \n");
    }

    public void adAccessDisabled()
    {
        for(int i = 0; i < 200; i+=2)
        {
            pickedNumber[i] = i;
            pickedNumber[i+1] = 999 - i;
        }

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.rawread(pickedNumber[i], rDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        rTotalTime = finishTime - beginTime; // Get the total time

        beginTime = new Date( ).getTime( ); //Get start time
        for(int i = 0; i < 200; i++)
        {
            SysLib.rawwrite(pickedNumber[i], wDataBuffer);
        }

        finishTime = new Date( ).getTime( ); //Get end time
        wTotalTime = finishTime - beginTime; // Get the total time


        if(Arrays.equals(wDataBuffer, rDataBuffer))
        {
            SysLib.cout("Correctness Verified!\n");
        }

        SysLib.cout("Read time average: " + (rTotalTime / 200) +" miliseconds \n");
        SysLib.cout("Write time average: " + (wTotalTime / 200) +" miliseconds \n");
    }
}