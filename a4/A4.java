package a4;

import java.util.Scanner;

/**
 * A simulator for various swapping and paging algorithms.
 * A simple command line interface is provided for selecting the
 * various algorithms.
 * As the algorithms are run, statistics for them are available in the
 * respective submenus.
 * @author Matthew Somers
 */
public class A4 
{
	/**
	 * The main menu of the program for swapping and paging.
	 * @param args not used
	 */
	public static void main(String[] args) 
	{
		Swapping swapper = new Swapping();
		Paging pager = new Paging();
		
		Scanner scan = new Scanner(System.in);
		String input = "";
		
		while (true)
		{
			System.out.println("Assignment 4 Main Menu");
			System.out.println("1. Swapping");
			System.out.println("2. Paging");
			System.out.println("3. exit application");
			System.out.print("Enter the number of your algorithm: ");
			
			input = scan.next();
			System.out.println();
			
			if (input.equals("1"))
				swapper.swapmenu();
			else if (input.equals("2"))
				pager.pagemenu();
			else if (input.equals("3"))
			{
				scan.close();
				return;
			}
			else
				System.out.println("Invalid input.\n");
		}
	}
}
