package a4;

import java.util.*;

/**
 * Simulation of different paging algorithms.
 * @author Matthew Somers
 */
public class Paging 
{
	public static final int PHYSICALSIZE = 4;
	public static final int DISKSIZE = 6;
	private ArrayList<Page> physpages;
	private ArrayList<Page> diskpages;
	private int reference;
	private double fifohitratio;
	private double secondchancehitratio;
	private double LRUhitratio;
	private double randomhitratio;
	
	/**
	 * Constructor.
	 */
	public Paging()
	{
		physpages = new ArrayList<Page>();
		diskpages = new ArrayList<Page>();
		reference = 0;
		fifohitratio = 0;
		secondchancehitratio = 0;
		LRUhitratio = 0;
		randomhitratio = 0;
	}
	
	/**
	 * Main interface for selection of algorithms.
	 */
	public void pagemenu()
	{
		Scanner scan = new Scanner(System.in);
		String input = "";

		while (true)
		{
			System.out.println("Paging Menu");
			System.out.println("1. First in, first out");
			System.out.println("2. Second Chance");
			System.out.println("3. Least Recently Used");
			System.out.println("4. Random Pick");
			System.out.println("5. view stats");
			System.out.println("6. return to main menu");
			System.out.print("Enter the number of your paging algorithm: ");
			
			input = scan.next();
			System.out.println();
			if (input.equals("6"))
				return;
			else if (input.equals("5"))
				viewpagestats();
			else if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4"))
				pageit(input); //start
			else
				System.out.println("Invalid input.\n");
		}
	}
	
	/**
	 * Method that actually does the work of paging some memory.
	 * @param choice the previous algorithm selection
	 */
	public void pageit(String choice)
	{	
		int i;
		int current;
		int hits = 0;
		for (int runs = 0; runs < 5; runs++)
		{
			for (i = 0; i < (PHYSICALSIZE + DISKSIZE); i++)
				diskpages.add(new Page(i));
			
			for (int r = 0; r < 100; r++)
			{
				current = -1;
				nextreference();
				System.out.println("\nReference for page: " + reference);
				System.out.print("Pages in physical memory: ");
				for (i = 0; i < physpages.size(); i++)
				{
					System.out.print(physpages.get(i).getid());
					if (i != (physpages.size()-1))
						System.out.print(", ");
					
					if (physpages.get(i).getid() == reference)
					{
						if (!physpages.get(i).getreferenced())
							physpages.get(i).togglereferenced();
						
						current = i;
						physpages.get(i).settimelastused(r);
						
						hits++;
					}
				}
				
				System.out.println();
				
				if (current == -1) // not in physical pages atm
				{
					replace(choice, r);
				}
			}
			
			System.out.println();
			clearmemory(); //after each "run"
		}
			if (choice.equals("1"))
				fifohitratio = (hits / 500.00);
			else if (choice.equals("2"))
				secondchancehitratio = (hits / 500.00);
			else if (choice.equals("3"))
				LRUhitratio = (hits / 500.00);
			else if (choice.equals("4"))
				randomhitratio = (hits / 500.00);
	}
	
	/**
	 * The method responsible for replacing a physical page with a disk page.
	 * @param choice previously selected algorithm
	 * @param time what time the clock is at
	 */
	public void replace(String choice, int time)
	{
		for (int j = 0; j < diskpages.size(); j++)
		{
			if (diskpages.get(j).getid() == reference)
			{
				diskpages.get(j).togglereferenced();
				diskpages.get(j).settimelastused(time);
				diskpages.get(j).settimeputintophysical(time);
				
				if (physpages.size() < PHYSICALSIZE) //physical not full yet
				{
					System.out.println("Page " + diskpages.get(j).getid() + " added to physical memory.");
					physpages.add(diskpages.get(j));
					diskpages.remove(j);
					
				}
				
				else //algorithmzz
				{
					int replaceindex = -1;
					if (choice.equals("1"))
						replaceindex = FIFO();
					else if (choice.equals("2"))
						replaceindex = secondchance(time);
					else if (choice.equals("3"))
						replaceindex = LRU();
					else if (choice.equals("4"))
						replaceindex = randompick();
					
					if (replaceindex != -1)
					{
						System.out.println("Page " + diskpages.get(j).getid() + " added to physical memory.");
						physpages.get(replaceindex).togglereferenced();
						System.out.println("Evicted page " + physpages.get(replaceindex).getid());
						
						//copy
						Page physcopy = physpages.get(replaceindex);
						physpages.set(replaceindex, diskpages.get(j));
						diskpages.set(j, physcopy);
					}
					
					else
					{
						System.out.println("A page was given a second chance! Reference request discarded.\n");
					}
				}
			}
		}	
	}
	
	/**
	 * First in, first out algorithm for paging
	 * @return index of physical page to be replaced
	 */
	public int FIFO()
	{
		int lowesttime = 999999;
		int replaceindex = -1;
		for (int k = 0; k < PHYSICALSIZE; k++)
		{
			if (physpages.get(k).gettimeputintophysical() < lowesttime)
			{
				lowesttime = physpages.get(k).gettimeputintophysical();
				replaceindex = k;
			}
		}
		return replaceindex;
	}
	
	/**
	 * Second chance paging algorithm
	 * @param time what time the clock is at
	 * @return index of physical page to be replaced
	 */
	public int secondchance(int time)
	{
		int lowesttime = 999999;
		int replaceindex = -1;
		for (int k = 0; k < PHYSICALSIZE; k++)
		{
			if (physpages.get(k).gettimeputintophysical() < lowesttime)
			{
					lowesttime = physpages.get(k).gettimeputintophysical();
					replaceindex = k;
			}
		}
		
		if (physpages.get(replaceindex).getreferenced()) //second chance
		{
			physpages.get(replaceindex).settimelastused(time);
			physpages.get(replaceindex).togglereferenced();
			replaceindex = -1;
		}
		return replaceindex;
	}
	
	/**
	 * Least recently used algorithm for paging
	 * @return index of physical page to be replaced
	 */
	public int LRU()
	{
		int longesttime = 999999;
		int replaceindex = -1;
		for (int k = 0; k < PHYSICALSIZE; k++)
		{
			if (physpages.get(k).gettimelastused() < longesttime)
			{
				longesttime = physpages.get(k).gettimelastused();
				replaceindex = k;
			}
		}
		return replaceindex;
	}
	
	/**
	 * Randomly picks a physical page to be replaced.
	 * @return index of physical page to be replaced
	 */
	public int randompick()
	{
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(physpages.size());
	}
	
	/**
	 * Reports stats for the paging algorithms.
	 */
	public void viewpagestats()
	{
		System.out.println("\n\n\n\nAverage paging algorithm hit ratios:");
		
		System.out.print("First In, First Out: ");
		if (fifohitratio == 0)
			System.out.println("Not run yet.");
		else
			System.out.println(fifohitratio);
		
		System.out.print("Second Chance: ");
		if (secondchancehitratio == 0)
			System.out.println("Not run yet.");
		else
			System.out.println(secondchancehitratio);
		
		System.out.print("Least Recently Used: ");
		if (LRUhitratio == 0)
			System.out.println("Not run yet.");
		else
			System.out.println(LRUhitratio);
		
		System.out.print("Random: ");
		if (randomhitratio == 0)
			System.out.println("Not run yet.");
		else
			System.out.println(randomhitratio);
		
		System.out.println();
	}
	
	/**
	 * Resets paging memory.
	 */
	public void clearmemory()
	{
		physpages.clear();
		diskpages.clear();
		reference = 0;
	}
	
	/**
	 * Selects which is the next reference according to specified rules of assignment.
	 * 70% chance to be next to or the same as current reference.
	 */
	public void nextreference()
	{
		Random randomGenerator = new Random();
		int chance = randomGenerator.nextInt(10);
		if (chance < 8)
		{
			chance = randomGenerator.nextInt(3);
			if (chance == 0)
			{
				if (reference == 0)
					reference = 9; //wraparound
				else
					reference--;
			}
			else if (chance == 2)
			{
				if (reference == 9)
					reference = 0; //wraparound
				else
					reference++;
			}
			//else, stays same
		}
		else //random greater than 1
		{
			reference = randomGenerator.nextInt(8) + 2;
		}
	}
}
