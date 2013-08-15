package a4;

import java.util.ArrayList;

/**
 * The data structure to hold processes and support swapping algorithms
 * @author Matthew Somers
 */
public class ProcessQueue 
{
	public static final int MEMORYSIZE = 100;
	private int[] memory;
	private ArrayList<Process> processmemory;
	int lastsearchindex;
	
	/**
	 * Constructor. Sets memory to "empty"
	 */
	public ProcessQueue()
	{
		memory = new int[MEMORYSIZE];
		for (int i = 0; i < MEMORYSIZE; i++)
			memory[i] = -1;
		processmemory = new ArrayList<Process>();
		lastsearchindex = 0;
	}
	
	/**
	 * Swapping algorithm for first fit
	 * @param p process to be swapped in
	 * @return 1 if successful, 0 if not
	 */
	public int firstfitadd(Process p)
	{
		int contiguousavailable = 0;
		for (int i = 0; i < MEMORYSIZE; i++)
		{
			if (memory[i] == -1)
				contiguousavailable++;
			
			if (contiguousavailable == p.getsize())
			{
				for (int j = (i-contiguousavailable+1); j <= i; j++)
					memory[j] = p.getid();
				processmemory.add(p);
				System.out.println("Swapping in process with ID " + p.getid());
				printmemory();
				return 1;
			}
			
			if (memory[i] != -1)
				contiguousavailable = 0;
		}
		
		return 0;
	}

	/**
	 * Swapping algorithm for next fit
	 * @param p process to be swapped in
	 * @return 1 if successful, 0 if not
	 */
	public int nextfitadd(Process p)
	{
		boolean oncearound = false;
		int contiguousavailable = 0;
		for (int i = lastsearchindex; i < MEMORYSIZE; i++)
		{
			if (memory[i] == -1)
				contiguousavailable++;
			
			if (contiguousavailable == p.getsize())
			{
				for (int j = (i-contiguousavailable+1); j <= i; j++)
					memory[j] = p.getid();
				processmemory.add(p);
				System.out.println("Swapping in process with ID " + p.getid());
				printmemory();
				return 1;
			}
			
			if (memory[i] != -1)
				contiguousavailable = 0;
			
			if (!oncearound && i == MEMORYSIZE-1) //wraparound once
			{
				oncearound = true;
				i = -1;
			}
			
			if (i == lastsearchindex-1)
				return 0; //failed
		}
		
		return 0;
	}
	
	/**
	 * Actually works exactly the same as firstfit for me.
	 * @param p The process to check.
	 * @return 1 if successful, 0 if not
	 */
	public int bestfitadd(Process p)
	{
		int contiguousavailable = 0;
		for (int i = 0; i < MEMORYSIZE; i++)
		{
			if (memory[i] == -1)
				contiguousavailable++;
			
			if (contiguousavailable == p.getsize())
			{
				for (int j = (i-contiguousavailable+1); j <= i; j++)
					memory[j] = p.getid();
				processmemory.add(p);
				System.out.println("Swapping in process with ID " + p.getid());
				printmemory();
				return 1;
			}
			
			if (memory[i] != -1)
				contiguousavailable = 0;
		}
		
		return 0;
	}
	
	/**
	 * Decrements all processes time in memory.
	 */
	public void timehappens()
	{
		for (int i = 0; i < processmemory.size(); i++)
		{
			processmemory.get(i).decrementtime();
			if (processmemory.get(i).gettimelength() == 0)
			{
				System.out.println("Removing process with ID " + processmemory.get(i).getid());
				for (int j = 0; j < MEMORYSIZE; j++)
				{
					if (memory[j] == processmemory.get(i).getid())
						memory[j] = -1;
				}
				
				processmemory.remove(i);
				printmemory();
			}
		}
	}
	
	/**
	 * Prints out current memory.
	 */
	public void printmemory()
	{
		for (int m = 0; m < MEMORYSIZE; m++)
		{
			if (memory[m] == -1)
				System.out.print("." + " ");
			else
				System.out.print(memory[m] + " ");
		}
		System.out.println();
	}
	
	/**
	 * Resets memory.
	 */
	public void clearMemory()
	{
		for (int i = 0; i < MEMORYSIZE; i++)
			memory[i] = -1;
		
		processmemory.clear();
		lastsearchindex = 0;
	}
}
