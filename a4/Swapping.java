package a4;
import java.util.*;

/**
 * Section of program for swapping processes
 * @author Matthew Somers
 */
public class Swapping
{
	private double firstfitsuccessful;
	private double nextfitsuccessful;
	private double bestfitsuccessful;
	private ArrayList<Process> spareprocesses;
	private ProcessQueue pq;
	public static final int PROCESSES = 100;
	public static final int DELAY = 100; //1 tenth of a second = 1 second in simulation
	
	/**
	 * Constructor for swapping. Sets average values first time.
	 */
	public Swapping()
	{
		firstfitsuccessful = 0;
		nextfitsuccessful = 0;
		bestfitsuccessful = 0;
		spareprocesses = new ArrayList<Process>();
		pq = new ProcessQueue();
	}
	
	/**
	 * Main interface for swapping.
	 */
	public void swapmenu()
	{
		Scanner scan = new Scanner(System.in);
		String input = "";
		
		while (true)
		{
			System.out.println("Swapping Menu");
			System.out.println("1. First Fit");
			System.out.println("2. Next Fit");
			System.out.println("3. Best Fit");
			System.out.println("4. view stats");
			System.out.println("5. return to main menu");
			System.out.print("Enter the number of your swapping algorithm: ");
			
			input = scan.next();
			System.out.println();

			if (input.equals("5"))
				return;
			else if (input.equals("4"))
				viewswapstats();
			else if (input.equals("1") || input.equals("2") || input.equals("3"))
				swapit(input);
			else
				System.out.println("Invalid input.\n");
		}
	}
	
	/**
	 * Method responsible for getting the swapping on.
	 * @param choice the previous selection of algorithm
	 */
	public void swapit(String choice)
	{
		int success = 0;

		for (int i = 0; i < 5; i++)
		{
			for (int p = 0; p < PROCESSES; p++)
				spareprocesses.add(new Process(p));
			
			for (int j = 0; j < 60; j++)
			{
				pq.timehappens();
				
				if (choice.equals("1"))
					success += pq.firstfitadd(spareprocesses.remove(0));
				else if (choice.equals("2"))
					success += pq.nextfitadd(spareprocesses.remove(0));
				else
					success += pq.bestfitadd(spareprocesses.remove(0));
				
				try
				{
					Thread.sleep(DELAY);
				}
				catch(Exception e) {}
			}
			
			System.out.println();
			pq.clearMemory();
			spareprocesses.clear();
		}
		
		if (choice.equals("1"))
			firstfitsuccessful = (success / 5.00);
		else if (choice.equals("2"))
			nextfitsuccessful = (success / 5.00);
		else
			bestfitsuccessful = (success / 5.00);
	}
	
	/**
	 * Reports the swap stats.
	 */
	public void viewswapstats()
	{
		System.out.println("\n\n\n\nAverage swapping successes:");
		
		System.out.print("First Fit: ");
		if (firstfitsuccessful == 0)
			System.out.println("Not run yet.");
		else
			System.out.println(firstfitsuccessful);
		
		System.out.print("Next Fit: ");
		if (nextfitsuccessful == 0)
			System.out.println("Not run yet.");
		else
			System.out.println(nextfitsuccessful);
		
		System.out.print("Best Fit: ");
		if (bestfitsuccessful == 0)
			System.out.println("Not run yet.");
		else
			System.out.println(bestfitsuccessful);
		
		System.out.println();
	}
}
