package a4;

import java.util.Random;

/**
 * A process to be used in swapping simulation.
 * @author Matthew Somers
 *
 */
public class Process 
{
	private int id;
	private int timelength;
	private int size;
	
	/**
	 * Simple constructor.
	 * @param id2 unique ID for this process
	 */
	public Process(int id2)
	{
		Random randomGenerator = new Random();
		timelength = randomGenerator.nextInt(5) + 1;
		int temp = randomGenerator.nextInt(4);
		if (temp == 0)
			size = 4;
		else if (temp == 1)
			size = 8;
		else if (temp == 2)
			size = 16;
		else
			size = 32;
		
		id = id2;
		
	}
	
	/**
	 * Getter for id
	 * @return id
	 */
	public int getid() { return id; }
	
	/**
	 * Getter for process time length.
	 * @return process time length
	 */
	public int gettimelength() { return timelength; }
	
	/**
	 * Getter for process memory size.
	 * @return process memory size
	 */
	public int getsize() { return size; }
	
	/**
	 * Decrements time (duh)
	 */
	public void decrementtime()
	{
		timelength--;
	}
}
