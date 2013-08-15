package a4;

/**
 * A page for paging simulations.
 * @author Matthew Somers
 */
public class Page 
{
	private int timeputintophysical;
	private int timelastused;
	private boolean referenced;
	private int id;
	
	/**
	 * Constructor
	 * @param id2
	 */
	public Page(int id2)
	{
		timeputintophysical = 0;
		timelastused = 0;
		referenced = false;
		id = id2;
	}
	
	/**
	 * Toggles whether current page is being referenced.
	 */
	public void togglereferenced()
	{
		if (referenced == true)
			referenced = false;
		else
			referenced = true;
	}
	
	/**
	 * Setter for time last used
	 * @param time
	 */
	public void settimelastused(int time)
	{
		timelastused = time;
	}
	
	/**
	 * Setter for when it is put into physical memory last.
	 * @param time
	 */
	public void settimeputintophysical(int time)
	{
		timeputintophysical = time;
	}
	
	/**
	 * Getter for time last used
	 * @return time last used
	 */
	public int gettimelastused() { return timelastused; }
	
	/**
	 * Getter for last time it was put into physical memory
	 * @return last time put into physical memory
	 */
	public int gettimeputintophysical() { return timeputintophysical; }
	
	/**
	 * Getter for whether it is being referenced
	 * @return true if being referenced, false if not
	 */
	public boolean getreferenced() { return referenced; }
	
	/**
	 * Getter for this page's ID
	 * @return id
	 */
	public int getid() { return id; }
}
