/*******************************/
//
// Prarin Behdarvandian
// CSS430
// Program 4 Cache
// 11/26/2017
//	
/******************************/
import java.util.*;
import java.io.*;

public class Cache
{
	//Set up a class for the Cache entry items
	private class CacheItems
	{
		byte[] buffer;
		int refBit;
		int dBit;
		int bID;

		public CacheItems()
		{
			
		}
	}

	CacheItems[] arrItems;
	private int victim;
	private int oldVictim;
	private int arrSize;
	private int bSize;

	//Initialize the constructor
	public Cache(int blockSize, int cacheBlocks)
	{
		arrItems = new CacheItems[cacheBlocks];
		arrSize = cacheBlocks;
		bSize = blockSize;
		victim = 0;
		oldVictim = 0;

		for(int i = 0; i < cacheBlocks; i++)
		{
			arrItems[i] = new CacheItems();
			arrItems[i].buffer = new byte[blockSize];
			arrItems[i].bID = -1;
			arrItems[i].dBit = 0;
			arrItems[i].refBit = 0;
		}
	}

	public synchronized boolean read(int blockID, byte buffer[])
	{
		if( blockID > -1) //Make sure the ID given is valid
		{
			// Finding the requested cache item in the array
			for(int i = 0; i < arrSize; i++)
			{
				if(arrItems[i].bID == blockID)
				{
					System.arraycopy(arrItems[i].buffer, 0, buffer, 0, bSize);
					arrItems[i].refBit = 1;
					return true;
				}
			}

			//Finding an empty spot in the cache, if requested item is not in array

			for(int i = 0; i < arrSize; i++)
			{
				if(arrItems[i].bID == -1)
				{
					//Read data from the disk into this cache block
					// Copy from the block id buffer to the parameter buffer
					// Set all variables from the cache item equal to 
					// Parameters given
					// Set reference bit to 1, since it was accessed 
					SysLib.rawread(blockID, arrItems[i].buffer);
					System.arraycopy(arrItems[i].buffer, 0, buffer, 0, bSize);
					arrItems[i].bID = blockID;
					arrItems[i].dBit = 0;
					arrItems[i].refBit = 0;
					return true;
				}

			}

			victim = condtionsForVictimSearch();

			if(arrItems[victim].dBit == 1 )
			{
				SysLib.rawwrite(arrItems[victim].bID, arrItems[victim].buffer);
				arrItems[victim].dBit = 0;
			}

			SysLib.rawread(blockID, arrItems[victim].buffer);
			System.arraycopy(arrItems[victim].buffer, 0, buffer, 0, bSize);
			arrItems[victim].bID = blockID;
			arrItems[victim].refBit = 1;
		}
		else //Return false if ID is not valid
		{
			return false;
		}
		return true;
	}

	public synchronized boolean write(int blockID, byte buffer[])
	{
		if( blockID > -1) //Make sure the ID given is valid
		{
			// Finding the requested cache item in the array
			for(int i = 0; i < arrSize; i++)
			{
				if(arrItems[i].bID == blockID)
				{
					System.arraycopy(buffer, 0, arrItems[i].buffer, 0, bSize);
					arrItems[i].refBit = 1;
					arrItems[i].dBit = 1;
					return true;
				}
			}

			//Finding an empty spot in the cache, if requested item is not in array

			for(int i = 0; i < arrSize; i++)
			{
				if(arrItems[i].bID == -1)
				{
					// Copy from the block id buffer to the parameter buffer
					// Set all variables from the cache item equal to 
					// Parameters given
					// Set reference bit to 1, since it was accessed 
					
					System.arraycopy(buffer, 0, arrItems[i].buffer, 0, bSize);
					arrItems[i].bID = blockID;
					arrItems[i].dBit = 1;
					arrItems[i].refBit = 1;
					return true;
				}

			}


			//Not in the array and no empty spots
			//Find a victim in the array to replace
			//With the requested Cache Item

			victim = condtionsForVictimSearch();

			if(arrItems[victim].dBit == 1 )
			{
				SysLib.rawwrite(arrItems[victim].bID, arrItems[victim].buffer);
				arrItems[victim].dBit = 0;
			}

			System.arraycopy(buffer, 0, arrItems[victim].buffer, 0, bSize);
			arrItems[victim].bID = blockID;
			arrItems[victim].dBit = 1;
			arrItems[victim].refBit = 1;
		}
		else //Return false if ID is not valid
		{
			return false;
		}
		return true; // wouldnt compile otherwise
	}

	private int condtionsForVictimSearch()
	{
		while(true)
		{
			//First Condition state refBit = 0, dBit = 0
			for(int i = oldVictim; i < (oldVictim + arrSize); i++)
			{
				if(arrItems[i % arrSize].refBit == 0 && arrItems[i% arrSize].dBit == 0)
				{
					oldVictim =  (i + 1) % arrSize;
					return i % arrSize;
				}
			}

			//Second Condition state refBit = 0, dBit = 1
			for(int i = oldVictim; i < (oldVictim + arrSize); i++)
			{
				if(arrItems[i % arrSize].refBit == 0 && arrItems[i% arrSize].dBit == 1)
				{
					oldVictim =  (i + 1) % arrSize;
					return i % arrSize;
				}
				else
				{
					arrItems[i % arrSize].refBit = 0;	
				}
			}

			// At this point since the last two conditions involve refBit = 1
			// So we just loop back and have it check again
		}
	}

	public synchronized void sync()
	{
		for(int i = 0; i < arrSize; i++)
		{
			if(arrItems[i].dBit == 1)
			{
				SysLib.rawwrite(arrItems[i].bID, arrItems[i].buffer);
				arrItems[i].dBit = 0;
			}
		}

		SysLib.sync();
	}

	public synchronized void flush()
	{
		for(int i = 0; i < arrSize; i++)
		{
			if(arrItems[i].dBit == 1)
			{
				SysLib.rawwrite(arrItems[i].bID, arrItems[i].buffer);
			}
			arrItems[i].bID = -1;
			arrItems[i].dBit = 0;
			arrItems[i].refBit = 0;
		}
		SysLib.sync();
	}

}


