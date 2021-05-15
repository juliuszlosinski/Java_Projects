package testers;

public class Task 
{
	// Pola:
	private int number; // Nr zadania.
	private float arrival_time; // Czas przybycia zadania.
	private float needed_time; // Czas wykonanaia zadania.
	
	// Konstruktory:
	public Task(int number, float arrival_time, float neededTime)
	{
		// TODO: 1. Utworzenie zadania wykorzystujac parametry wejsciowe.
		this.number=number;
		this.arrival_time=arrival_time;
		this.needed_time=neededTime;
	}
	
	// Wlasciwosci:
	public int getNumber()
	{
		// TODO: 1. Zwroc numer zadania.
		return number;
	}
	
	public float getArrivalTime()
	{
		// TODO: 1. Zwroc czas przybycia.
		return arrival_time;
	}
	
	public float getNeededTime()
	{
		// TODO: 1. Zwroc czas potrzeby do wykonania zadania.
		return needed_time;
	}

	// Metody:
	@Override
	public String toString()
	{
		//return "Number: "+number+", Arrival time: "+arrival_time+", Needed time: "+needed_time+"\n";
		return number+" " ;
	}
}
