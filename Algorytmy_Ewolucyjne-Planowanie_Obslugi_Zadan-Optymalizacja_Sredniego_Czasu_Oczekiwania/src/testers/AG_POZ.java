package testers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class AG_POZ 
{
	// Obiekt do generowanie liczb pseudo losowych.
	private static Random rand=new Random();
	
	// Funkcja celu/ przystosowania ~ obliczenie sredniego czasu oczekiwania -> MIN.
	public static float Function(Task[]chromoson)
	{
		// TODO: Oblicz wartosc funkcji celu/ przystosowania.
		
		// 1. Suma roznic czasu rozpoczecia i czasu przybycia.
		float sum=0;
		
		// 2. Obecna pozycja w chromosonie.
		int index=0;
		
		// 3. Czas rozpoczecia czyli po prostu obecny czas, na poczatku jest 0.
		int ai=0;
		
		// 4. Wykonuj dopóki wszystkie pozycje w chromosonie nie zostana odwiedzone.
		while(index<chromoson.length)
		{
			// 5. Obecne zadanie w chromosonie, ktore odpowiada pozycji.
			Task current=chromoson[index];
			
			// 6. Czas potrzebny do wykonania obecnego zadania.
			float pi;

			pi = current.getNeededTime();
			
			// 7. Czas przybycia zadania.
			float ri=current.getArrivalTime();

			// 8. Jezeli czas przybycia zadania jest mniejszy lub rowny obecnemu czasowi to wykonaj go.
			if(ri<=ai)
			{
				index++;
				sum+=(ai-ri);
				ai+=pi;
			}
			// 9. Jezeli nie to poprostu inkrementuj czas.
			else
			{
				ai++;
			}
			
			// Adnotacja: Kiedy na przyklad zadanie przyjdzie pozniej a obecny czas jest np. 2 [s]
			// a nastepne zadanie bedzie mialo czas przybycia np. 4 [s] to bedziemy musieli poczekac
			// 2 sekundy czyli warunek wykona sie dwa razy.
		}
		
		// 10. Podziel sume przez ilosc zadañ w chromosonie.
		sum/=chromoson.length;
		
		// 11. Zwroc wynik funkcji przystosowania.
		return sum;
	}
	
	// Drukowanie chromosonu.
	public static void PrintChromoson(Task[]chromoson)
	{
		// TODO: Drukowanie choromsonu.
		for(int i=0;i<chromoson.length;i++)
		{
			// 1. Drukowanie zadania z chromsonu.
			System.out.println(chromoson[i]);
		}
	}
	
	// Tworzenie chromosonu.
	public static Task[] CreateChromoson_Using_File(String path)
	{
		// TODO: Wczytanie zadana z pliku oraz utworzenie z niego chromosonu.
		
		// 1. Utworzenie pliku.
		File file=new File(path);
		
		// 2. Zadeklarowanie chromosonu.
		Task[]tasks=null;
		try
		{
			// 3. Utworzenie obiektu do wczytywania linii z pliku.
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			// 4. Licznik wierszy w pliku.
			int number_of_lines=0;

			// 5. Obecna linia.
		    String line;
		    
		    // 6. Zlicz ilosc wierszy w pliku.
		    while ((line = br.readLine()) != null)
			{
		    	number_of_lines++;
			}
		    
		    
		    // 8. Utworz zmienna do sledzenia obecnego wiersza.
		    int currentRow=0;
		    
		    // 9. Utworzenie pustego chromosonu o okreslonej wielkosci.
		    tasks=new Task[number_of_lines-1];
		    
		    // 10. Aktualna pozycja w chromosonie.
		    int index=0;
		    
		    // 11. Wyczyszczenie obecnej linii.
		    line=null;
		    
		    // 12. Utworzenie nowego kursora do wczytywania.
		    br=new BufferedReader(new FileReader(file));
		    
		    // 13. Wczytywanie.
			while ((line = br.readLine()) != null)
			{
				// 14. Rozne od wiersza pierwszego.
				if(currentRow!=0)
				{
					// 15. Podziel wiersz na lancuchy znakow.
					String[]sub=line.split("\\s+");
					
					// 16. Uzyskanie numer zadania.
					int number=Integer.parseInt(sub[0]);
					
					// 17. Uzyskanie czasu przybycia zadania.
					float arrivalTime=Float.parseFloat(sub[1]);
					
					// 18. Uzyskanie czasu potrzebnego do wykonania.
					float neededTime=Float.parseFloat(sub[2]);
					
					// 19. Utworzenie zadania oraz dodanie go do tabeli.
					tasks[index++]=new Task(number,arrivalTime,neededTime);
				}
				// 15. Inkrementacja pozycji w wierszach.
				currentRow++;
			}
		}
		catch (Exception e)
		{
			// 1. Jezeli sie nie powiodlo to wypisz komunikat.
			e.printStackTrace();
		}
		
		return tasks; // Zwroc liste z zadaniami.
	}
	
	// Tworzenie chromsonu oraz permutowanie go.
	public static Task[] CreateChromoson(String path)
	{
		// TODO: Utworzenie chromosonu wykorzystujac podana sciezke do pliku oraz jego zpermutowanie.
		
		// 1. Utworzenie chromosonu.
		Task[]chromoson=CreateChromoson_Using_File(path);
		
		// 2. Permutacja chromosonu.
		Permute_Chromoson(chromoson);

		// 3. Zwrocenie chromsonu.
		return chromoson;
	}
	
	// Permutaowanie chromosonu.
	public static void Permute_Chromoson(Task[]tasks)
	{
		// TODO: Roszada zadañ w tablicy.
		
		// 1. Utworzenie obiektu, ktory umozliwi nam generowanie losowych liczb.
		Random rand=new Random();
		
		// 2. Uzyksanie informacji na temat dlugosci tablicy.
		int n=tasks.length;
		
		// 3. Zamiana miejsca elementów tabeli.
		for(int i=0;i<n;i++)
		{
			// 3.1 Index elementu zamienianego, który nie jest i.
			int index=i+rand.nextInt(n-i);
			
			// 3.2 Zamiana elementów.
			Task tmp=tasks[i];
			tasks[i]=tasks[index];
			tasks[index]=tmp;
		}
	}
	
	// Populacja -------------------------------------------------------]
	
	// Tworzenie populacji.
	public static Task[][] CreatePopulation(String path, int size)
	{
		// TODO: Utworz populacje zgodnie z sciezka do pliku oraz podanym razmiarem populacji.
		
		// 1. Uzyskanie informacji na temat ilosci potrzebnych column;
		int number_of_columns=CreateChromoson_Using_File(path).length;
		
		// 2. Utworzenie pustej populacji.
		Task[][]population=new Task[size][number_of_columns];
		
		// 3. Wypelnienie tej populacji nowymi chromosonami.
		for(int i=0;i<size;i++)
		{
			population[i]=CreateChromoson(path);
		}
		
		// 4. Zwrocenie populacji.
		return population;
	}

	// Drukowanie populacji.
	public static void PrintPopulation(Task[][]population)
	{
		// TODO: Drukowanie populacji.
		for(int i=0;i<population.length;i++)
		{
			System.out.println((i+1)+" chromoson:------------------------------------------]\n");
			for(int j=0;j<population[i].length;j++)
			{
				System.out.println(population[i][j]);
			}
		}
	}
	
	// Operatory genetyczne -------------------------------------------]
	
	// Operator mutacji chromosonu.
	public static void Mute(Task[]chromoson, float pMax)
	{	
		// 1. Przechodzenie przez chromson.
		for(int i=0;i<chromoson.length;i++)
		{
			// 1.1 Losowanie pierwszego prawdopodobienstwa.
			float pC=rand.nextFloat();
			
			// 1.2 Losowanie drugiego prawdopodobienstwa.
			float pR=rand.nextFloat();
			
			// 1.3 Losowanie pozycji w chromosonie.
			int index=rand.nextInt(chromoson.length);
			
			// 1.4 Jezeli oba prawodopobienstwa sa mniejsze badz rowne to zamien ten obecny element ze elementem z pozycji losowo wygenerowanej.
			if(pC <= pMax && pR <= pMax)
			{
				Task tmp=chromoson[i];
				chromoson[i]=chromoson[index];
				chromoson[index]=tmp;
			}
		}
	}

	// Krzy¿owanie z czesciowym odwzorowaniem (ang. partially mapped crossover - PMX).
	public static void PMX(Task[]P1, Task[]P2, Task[]S1, Task[]S2)
	{
		// 1. Wygenerowanie granic ciecia.
		int LB=rand.nextInt(P1.length);
		int RB=rand.nextInt(P2.length);
		int N=P1.length;
		
		if(LB>RB)
		{
			int tmp=LB;
			LB=RB;
			RB=tmp;
		}
		
		// OGOLNA IDEA:
		// Na poczatku tworzymy zbior ze wszystkimi elementami rodzica 1 oraz 2.
		// Potem przepisujemy elementy ze srodka do potomkow oraz dodaj je do
		// zbioru z elementami potomkow. Potem przepisujemy z pozycji rodzicow
		// na pozycje potomkow jezeli element wczesniej nie bylo wykorzystany (kazdy element dodajemy do zbiorow w zaleznosci od potomka).
		// Na koncu robimy roznice zbiorow elementow np. potomku 1 z elementami przepisanymi na poczatku rodzica 1.
		// Roznica, ktora nam zostanie a w tym jej elementy wyladuja na wolnych pozycjach potomka.
		// 
		// Ufff...
		
		
		// 2. Zbior do przechowywanie numerow elementow pierwszego potomka.
		Stack<Integer>S1_elements=new Stack<Integer>();
		
		// 3. Zbior do przechowywanie wszystkich elementow pierwszego rodzica.
		Stack<Task>all_P1_elements=new Stack<Task>();
		
		// 4. Do przechowywanie wolnych pozycji w potomku 1.
		Stack<Integer>free_S1_Positions=new Stack<Integer>();
		
		// 5. Zbior do przechowywania wszystkich elementow drugiego potomka.
		Stack<Integer>S2_elements=new Stack<Integer>();
		
		// 7. Zbior do przechowywania wszystkich elementow drugiego rodzica.
		Stack<Task>all_P2_elements=new Stack<Task>();
		
		// 8. Zbior do przechowywania wolnych pozycji w potomku 2.
		Stack<Integer>free_S2_Positions=new Stack<Integer>();
		
		// 9. Wypelnianie zbiorow z elementami rodzicow.
		for(int i=0;i<N;i++)
		{
			// 9.1 Wypelnianie zbioru z elementami pierwszego rodzica.
			all_P1_elements.push(P1[i]);
			
			// 9.2 Wypelnianie zbioru z elementami drugiego rodzica.
			all_P2_elements.push(P2[i]);
		}
		
		// 10. Wypelnianie srodka potomkow elementami z rodzicow.
		for(int i=LB;i<=RB;i++)
		{
			// 10.1 Umieszczenie elementu rodzica 2 w potomku 1.
			S1[i]=P2[i];
			
			// 10.2 Dodanie elementu potomka 1 do jego zbioru z uzytymi.
			S1_elements.push(S1[i].getNumber());
			
			// 10.3 Umieszczenie elementu rodzica 1 w potomku 2.
			S2[i]=P1[i];
			
			// 10.4 Dodanie elementu potomka 2 do jego z uzytymi.
			S2_elements.push(S2[i].getNumber());
		}

		// 11. Wypelnianie lewej strony oraz prawej strony elementami rodzicow.
		for(int i=0;i<N;i++)
		{
			// 11.1 Jezeli jestesmy z lewej lub prawej strony.
			if(i<LB || i >RB)
			{
				// 11.2 Jezeli potomek 1 nie zawiera juz elementu takiego jak w rodzicu 1.
				if(!S1_elements.contains(P1[i].getNumber()))
				{
					// 11.2.1 Umiesc go w potomku 1.
					S1[i]=P1[i];
					
					// 11.2.2 Dodaj go do elementow potomka 1.
					S1_elements.push(S1[i].getNumber());
				}
				// 11.3 Jezeli nie to dodaj pozycje do pozycji wolnych potomka 1.
				else 
				{
					// 11.3.1 Dodawanie pozycji do zbioru z pustymi pozycjami.
					free_S1_Positions.push(i);
				}
				
				// 11.4 Jezeli potomek 2 nie zawiera elementu z rodzica 2 to dodaj go.
				if(!S2_elements.contains(P2[i].getNumber()))
				{
					// 11.4.1 Umieszczenie elementu w potomku 2.
					S2[i]=P2[i];
					
					// 11.4.2 Dodanie elementu do zbioru z elementami potomka 2.
					S2_elements.push(S2[i].getNumber());
				}
				// 11.5 Jezeli nie to dodaj ta pozycje jako miejsc puste.
				else 
				{
					// 11.5.1 Dodawnie tej pozycji do zbioru z pustymi pozycjami potomka 2.
					free_S2_Positions.push(i);
				}
			}
		}
		
		// 12. Usuwanie elementow uzytych przez potomka 1 ze wszystkich elementow rodzica 1.
		for(Integer i : S1_elements)
		{
			// 12.1 Deklaracja elementu do usuniecia.
			Task toRemove=null;
			
			// 12.2 Szukanie elementu do usuniecia.
			for(Task task: all_P1_elements)
			{
				// 12.2 Znalezienie zadania.
				if(task.getNumber()==i)
				{
					// 12.3 Przypisywanie go do zadania do usuniecia.
					toRemove=task;
				}
			}
			
			// 12.4 Usuniecie zadania z elementów rodzica 1.
			all_P1_elements.remove(toRemove);
		}
		
		// 13. Usuwanie elementow uzytych przez potomka 2 ze wszystkich elementow rodzica 2.
		for(Integer i : S2_elements)
		{
			// 13.1 Deklaracj elementu do usuniecia.
			Task toRemove=null;
			
			// 13.2 Znajdowanie elementu z potomka 2 w elementach rodzica 2.
			for(Task task: all_P2_elements)
			{
				// 13.3 Znaleziono.
				if(task.getNumber()==i)
				{
					// 13.4 Przypisanie go do elementu do usuniecia.
					toRemove=task;
				}
			}
			
			// 13.5 Usuniecia elementu ze zbioru ze wszystkimi elementami rodzica 2.
			all_P2_elements.remove(toRemove);
		}
		
		
		// 14. Wpisywanie elementow, ktore zostaly w zbiorze z elementami rodzica 1 na wolne pozycje potomka 1.
		while(free_S1_Positions.size()!=0)
		{
			// 14.1 Zdobywanie wolnej pozycji w potomku 1.
			int index=free_S1_Positions.pop();
			
			// 14.2 Zdobywanie elementu.
			Task task=all_P1_elements.pop();
			
			// 14.3 Przypisywanie elementu.
			S1[index]=task;
		}
		
		// 15. Wpisywanie elementow, ktory zostaly w zbiorze z elementami rodzica 2 na wolne pozycje potomka 2.
		while(free_S2_Positions.size()!=0)
		{
			// 15.1 Zdobywanie wolnej pozycji w potomku 2.
			int index=free_S2_Positions.pop();
			
			// 15.2 Uzyskanie elementu.
			Task task=all_P2_elements.pop();
			
			// 15.3 Przypisywanie elementu.
			S2[index]=task;
		}
	}
	
	// Krzyzowanie z zachowaniem porzadku (ang. order crossover - OX).
	public static void OX(Task[]P1, Task[]P2, Task[]S1, Task[]S2)
	{
		// 1. Wygenerowanie lewej bariery.
		int LB=rand.nextInt(P1.length);
		
		// 2. Wygenerowanie prawej bariery.
		int RB=rand.nextInt(P2.length);
		
		// 3. Uzyskanie informacji na temat dlugosci chromosonu.
		int N=P1.length;
		
		// 4. Zamiania miejscami jezeli lewa granica jest wieksza niz prawa.
		if(LB>RB)
		{
			int tmp=LB;
			LB=RB;
			RB=tmp;
		}
		
		// 5. Zbior, ktory bedzie przechowywal identyfikatory elementow, ktore zawiera potomek 1.
		Stack<Integer>S1_elements=new Stack<Integer>();
		
		// 6. Zbior, ktory bedzie przechowywal identyfiaktory elementow, ktore zawiera potomek 2.
		Stack<Integer>S2_elements=new Stack<Integer>();
		
		// 7. Zbior, ktory bedzie zawieral elementy rodzica 1.
		Stack<Task>P1_elements=new Stack<Task>();
		
		// 8. Zbior, ktory bedzie zawieral elementy rodzica 2.
		Stack<Task>P2_elements=new Stack<Task>();
		
		// 9. Wypelnianie zbiorow rodzicow.
		for(int i=0;i<N;i++)
		{
			// 9.1 Wypelnianie zbioru rodzica 1.
			P1_elements.push(P1[i]);
			
			// 9.2 Wypelnianie zbioru rodzica 2.
			P2_elements.push(P2[i]);
		}
		
		// 10. Wypelnianie srodkow potomokow elemantami z rodzicow (S1<->P1, S1<->P1), oraz dodawanie ich do zbiorow.
		for(int i=LB;i<=RB;i++)
		{
			// 10.1 Umieszczenie elementu rodzica 1 w potomku 1.
			S1[i]=P1[i];
			
			// 10.2 Dodanie elementu potomka 1 do zbioru elementów potomka 1.
			S1_elements.push(S1[i].getNumber());
			
			// 10.3 Umieszczenie elementu rodzica 2 w potomku 2.
			S2[i]=P2[i];
			
			// 10.4 Umieszczenie elementu potomka 2 do zbioru elementow potomka 2.
			S2_elements.push(S2[i].getNumber());
		}

		// 11. Usuwanie elementow potomka 1 ze zbioru, ktory zawiera elementy rodzica 2 -> Roznica zbiorow.
		for(Integer id: S1_elements)
		{
			// 11.1 Deklaracja zadania do usuniecia.
			Task toRemove=null;
			
			// 11.2 Szukanie zadania.
			for(Task task: P2_elements)
			{
				// 11.3 Znaleziono zadanie.
				if(task.getNumber()==id)
				{
					// 11.4 Przypisanie go do zadania do usuniecia.
					toRemove=task;
				}
			}
			
			// 11.5 Usuniecie elementu ze zbioru elementow, ktore zawiera rodzic 2.
			P2_elements.remove(toRemove);
		}
		

		// 12. Usuwanie elementow potomka 2 ze zbioru elementów, które zawiera rodzic 1 -> Roznica zbiorow.
		for(Integer id: S2_elements)
		{
			// 12.1 Deklaracja zadania do usuniecia.
			Task toRemove=null;
			
			// 12.2 Szukanie zadania.
			for(Task task: P1_elements)
			{
				// 12.3 Znaleziono zadanie.
				if(task.getNumber()==id)
				{
					// 12.4 Przypisanie zadania.
					toRemove=task;
				}
			}
			
			// 12.5 Usuniecie zadania.
			P1_elements.remove(toRemove);
		}
		
		// 13. Wypelnianie prawej strony elementami ze zbiorow rodziców.
		for(int i=RB+1;i<N;i++)
		{
			// 13.1 Wypelniamy prawa strone potomka 1 elementami ze zbioru rodzica 2 (Ktore zostaly po roznicy).
			S1[i]=P2_elements.pop();
			
			// 13.2 Wypelniamy prawa strone potomka 2 elementami ze zbioru rodzica 1 (Ktore zostaly po roznicy).
			S2[i]=P1_elements.pop();
		}
		
		// 14. Wyplenianie lewej strony elementami ze zbiorow rodziców.
		for(int i=0;i<LB;i++)
		{
			// 14.1 Wypelniamy lewa strone potomka 1 elemenetami ze zbioru rodzica 2 (Ktore zostaly po roznicy).
			S1[i]=P2_elements.pop();
			
			// 14.2 Wypelniamy prawa strone potomka 2 elementami ze zbioru rodzica 1 (Ktore zostaly po roznicy).
			S2[i]=P1_elements.pop();
		}

		// 15. Koniec.
	}
	
	//  Selekcja chromosonów ------------------------------------------]
	
	// Selekcja przy pomocy ruletki.
	public static Task[] RollChromoson(Task[][]population)
	{
		// 1. Deklaracja tablic do przechowywania informacji oraz zmiennej do przechowywania sumy.
		
		float[]FunctionResults=new float[population.length]; // Wyniki funkcji przystosowania.
		float[]procents=new float[population.length]; // Procenty w funkcji przystosowania.
		float sum =0; // Suma wszystkich.
		
		// 2. Uzyskaj wartosci funkcji przystosowania dla kazdego chromosonu z populacji.
		
		for(int i=0;i<population.length;i++)
		{
			// 2.1 Uzyskanie wartosci funkcji przystosowania.
			FunctionResults[i]=Function(population[i]);
			
			// 2.2 Obliczenie sumy.
			sum+=FunctionResults[i];
		}
		
		// 3. Wpisywanie procentow do tablicy, zeby sprawdzic jaki procent stanowi rozwiazanie.
		
		for(int i=0;i<population.length;i++)
		{
			// 3.1 Wpisywanie do tablicy ilorazu danej wartosci funkcji przystosowania oraz sumy.
			procents[i]=FunctionResults[i]/sum;
		}
		
		// 4. Uzyskanie osobnika.
		
		Random rand=new Random(); // Obiekt do generowania liczb pseudo losowych.
		
		float selected=rand.nextFloat(); // Wybranie losowego %
		int index=0; // Poczatkowy index.
		float LB=0; // Lewa bariera.
		float RB=procents[index]; // Prawa bariera.
		int i=0; // Aktualny chromoson.
		
		while(index<population.length)
		{
			// 4.1 Jezeli prawdopodobienstwa jest w tym przedziale to zwroc ten chromson.
			if(selected > LB && selected < RB)
			{
				// 4.2 Zwracanie chromsonu.
				return population[i];
			}
			// 4.2 Lewa bariera staje sie prawa.
			LB=RB;
			
			// 4.3 Prawa sie przesuwana.
			RB=LB+procents[index++];
			
			// 4.4 Idziemy do kolejnego chromosonu.
			i++;
		}
		
		// Jezeli nic sie nie udalo znalezc to zwroc pierwszy chromson.
		return population[0];
	}

	// Algortym genetyczny, ktory wykorzystuje PMX --------------------]
	
	// Punkt wejsciowy.
	public static Task[] AG(String path, int size_of_population, int ev)
	{
		// 1. Rozpoczecie dzialania algorytmu genetycznego w celu minimalizacji czasu sredniego oczekiwania zadan :)
		try 
		{
			return AG_Algo(path,size_of_population,ev);
		} catch (IOException e) 
		{
			return null;
		}
	}
	
	// Glowny algorytm, ktory jest wykorzystywany wiersz wy¿ej.
	public static Task[] AG_Algo(String path, int size_of_population, int ev) throws IOException
	{
		// 0. Utworzenie obiektu do generowania liczb pseudo losowych.
		Random rand=new Random();
		
		// 1. Inicjacja - wybór poczatkowej populacji chromsonow.------------------]
		Task[][]population=CreatePopulation(path,size_of_population);
		
		// 2. Najlepszy chromoson z najlepszych oraz jego czas.
		Task[]the_best_chromoson=population[0];
		float the_best_time=Function(the_best_chromoson);
		
		// 3. Plik z najlepszymi obecnymi chromosonami.
		FileWriter current_best_solutions=new FileWriter("Current_Best_Solutions.txt"); // Plik z obecnymi najlepszymi funkcjami przystosowania.
		
		// 4. Plik z najlepszymi z najlepszych chromosonami.
		FileWriter best_solutions=new FileWriter("Best_Solutions.txt");
		
		// + Plik ze srednia chromosonow.
		FileWriter avg_solutions=new FileWriter("AVG_Solutions.txt");
		
		// + Plik zawierajacy oœ X czyli ev, ilosc ewoluacji.
		FileWriter ev_axis=new FileWriter("EV_X.txt");
		
		// 5. Poczatkowa ilosc iteracji algorytmu.
		int i=0;
		while(true)
		{
			// 2. Ocena przystosowania chromsonow w populacji.--------------------]
			
			// 1.1 Aktualny najlepszy chromson oraz jego wartosci funkcji przystosowania.
			Task[]current_best_chromoson=population[0];
			float current_best_time=Function(current_best_chromoson);
			
			// + Zmienna posiadajaca srednia chromosonow z populacji.
			float avg_time=0;
			
			// 1.2 Sprawdzanie chromosonow w populacji.
			for(int j=0;j<population.length;j++)
			{
				// 1.3 Obecny czas oczekiwania/ wartosc funkcji przystosowania.
				float currentTime=Function(population[j]);
				
				// + Dodawanie do œredniej.
				avg_time+=currentTime;
				
				if(currentTime < current_best_time) // Minimalizujemy !!!!!
				{
					// 1.4 Zapisujemy obecny najlepszy chromoson.
					current_best_chromoson=population[j];
					current_best_time=currentTime;
				}
			}
			// + Obliczanie sredniej.
			avg_time=avg_time/size_of_population; 
			
			// 2. Czy jest nowy najlepszy z najlepszych chromson, jesli tak to go zapisz.
			if(current_best_time<the_best_time) // Minimalizujem !!!!!!
			{
				// 2.1 Ustanowienie nowego najlepszego chromsonu. 
				the_best_chromoson=current_best_chromoson;
				the_best_time=current_best_time;
			}
			
			// + Zapisywanie do plikow.
			current_best_solutions.append(current_best_time+"\n");
			best_solutions.append(the_best_time+"\n");
			avg_solutions.append(avg_time+"\n");
			ev_axis.append(i+"\n");
			
			// 3. Warunek zatrzymania.-----------------------------------------------]
			if(i>=ev)
			{
				// 3.1 Zakoncz program, poniewaz ilosc iteracji jest rowna badz wieksza ilosci ewoulacji. 
				break;
			}
			else
			{
				// 4. Selekcja chromosonow.
				for(int j=0;j<size_of_population;j++)
				{
					population[j]=RollChromoson(population);
				}
				// 5. Zastosowanie operatorow genetycznych.
				// a) Krzyzowanie.
				for(int j=0;j<size_of_population;j++)
				{
					float rand_value=rand.nextFloat();
					if(rand_value<=0.6f)
					{
						int rand_index_of_chromoson=rand.nextInt(size_of_population);
						int size=the_best_chromoson.length;
						Task[]S1=new Task[size];
						Task[]S2=new Task[size];
						PMX(population[j],population[rand_index_of_chromoson],S1,S2);
						population[j]=S1;
						population[rand_index_of_chromoson]=S2;
					}
				}
				// b) Mutacja.
				for(int j=0;j<size_of_population;j++)
				{
					Mute(population[j],0.25f);
				}
			}
			// Inkrementowanie ilosci iteracji aglorytymu czyli przechodzimy znowu na poczatek algorytmu.
			i++;
		}
		
		// Zamkniecie plikow, musi byc czysto.
		current_best_solutions.close();
		best_solutions.close();
		avg_solutions.close();
		ev_axis.close();
		
		// + Utworzenie oraz zapisanie do pliku najlepszy czas sredni oczekiwania.
		FileWriter bestSolutionFinal=new FileWriter("The_Best_Solution_Time_Final.txt");
		bestSolutionFinal.append(Function(the_best_chromoson)+"\n");
		bestSolutionFinal.close();
				
		// + Utworzenie oraz zapisanie do pliku najlepszej kombinacji u³o¿enia zadañ.
		FileWriter The_Best_Combination=new FileWriter("The_Best_Combination.txt");
		for(int j=0;j<the_best_chromoson.length;j++)
		{
			The_Best_Combination.append(the_best_chromoson[j]+"\n");
		}
		The_Best_Combination.close();
		
		// 6. Wyprowadzenie najlepszego chromosonu :)
		return the_best_chromoson;
	}

	// Algortym, ktory wykorzystuje OX --------------------------------]
	
	// Punkt wejsciowy.
	public static Task[] AG_Using_OX(String path, int size_of_population, int ev)
	{
		// 1. Rozpoczecie dzialania algorytmu genetycznego w celu minimalizacji czasu sredniego oczekiwania zadan :)
		try 
		{
			return AG_Algo_Using_OX(path,size_of_population,ev);
		} catch (IOException e) 
		{
			return null;
		}
	}
	
	// Glowny algorytm, ktory jest wkorz
	public static Task[] AG_Algo_Using_OX(String path, int size_of_population, int ev) throws IOException
	{
		// 0. Utworzenie obiektu do generowania liczb pseudo losowych.
		Random rand=new Random();
		
		// 1. Inicjacja - wybór poczatkowej populacji chromsonow.------------------]
		Task[][]population=CreatePopulation(path,size_of_population);
		
		// 2. Najlepszy chromoson z najlepszych oraz jego czas.
		Task[]the_best_chromoson=population[0];
		float the_best_time=Function(the_best_chromoson);
		
		// 3. Plik z najlepszymi obecnymi chromosonami.
		FileWriter current_best_solutions=new FileWriter("Current_Best_Solutions.txt"); // Plik z obecnymi najlepszymi funkcjami przystosowania.
		
		// 4. Plik z najlepszymi z najlepszych chromosonami.
		FileWriter best_solutions=new FileWriter("Best_Solutions.txt");
		
		// + Plik ze srednia chromosonow.
		FileWriter avg_solutions=new FileWriter("AVG_Solutions.txt");
		
		// + Plik zawierajacy oœ X czyli ev, ilosc ewoluacji.
		FileWriter ev_axis=new FileWriter("EV_X.txt");
		
		// 5. Poczatkowa ilosc iteracji algorytmu.
		int i=0;
		while(true)
		{
			// 2. Ocena przystosowania chromsonow w populacji.--------------------]
			
			// 1.1 Aktualny najlepszy chromson oraz jego wartosci funkcji przystosowania.
			Task[]current_best_chromoson=population[0];
			float current_best_time=Function(current_best_chromoson);
			
			// + Zmienna posiadajaca srednia chromosonow z populacji.
			float avg_time=0;
			
			// 1.2 Sprawdzanie chromosonow w populacji.
			for(int j=0;j<population.length;j++)
			{
				// 1.3 Obecny czas oczekiwania/ wartosc funkcji przystosowania.
				float currentTime=Function(population[j]);
				
				// + Dodawanie do œredniej.
				avg_time+=currentTime;
				
				if(currentTime < current_best_time) // Minimalizujemy !!!!!
				{
					// 1.4 Zapisujemy obecny najlepszy chromoson.
					current_best_chromoson=population[j];
					current_best_time=currentTime;
				}
			}
			// + Obliczanie sredniej.
			avg_time=avg_time/size_of_population; 
			
			// 2. Czy jest nowy najlepszy z najlepszych chromson, jesli tak to go zapisz.
			if(current_best_time<the_best_time) // Minimalizujem !!!!!!
			{
				// 2.1 Ustanowienie nowego najlepszego chromsonu. 
				the_best_chromoson=current_best_chromoson;
				the_best_time=current_best_time;
			}
			
			// + Zapisywanie do plikow.
			current_best_solutions.append(current_best_time+"\n");
			best_solutions.append(the_best_time+"\n");
			avg_solutions.append(avg_time+"\n");
			ev_axis.append(i+"\n");
			
			// 3. Warunek zatrzymania.-----------------------------------------------]
			if(i>=ev)
			{
				// 3.1 Zakoncz program, poniewaz ilosc iteracji jest rowna badz wieksza ilosci ewoulacji. 
				break;
			}
			else
			{
				// 4. Selekcja chromosonow.
				for(int j=0;j<size_of_population;j++)
				{
					population[j]=RollChromoson(population);
				}
				// 5. Zastosowanie operatorow genetycznych.
				// a) Krzyzowanie.
				for(int j=0;j<size_of_population;j++)
				{
					float rand_value=rand.nextFloat();
					if(rand_value<=0.6f)
					{
						int rand_index_of_chromoson=rand.nextInt(size_of_population);
						int size=the_best_chromoson.length;
						Task[]S1=new Task[size];
						Task[]S2=new Task[size];
						OX(population[j],population[rand_index_of_chromoson],S1,S2);
						//PrintChromoson(S1);
						population[j]=S1;
						population[rand_index_of_chromoson]=S2;
					}
				}
				// b) Mutacja.
				for(int j=0;j<size_of_population;j++)
				{
					Mute(population[j],0.25f);
				}
			}
			// Inkrementowanie ilosci iteracji aglorytymu czyli przechodzimy znowu na poczatek algorytmu.
			i++;
		}
		
		// Zamkniecie plikow, musi byc czysto.
		current_best_solutions.close();
		best_solutions.close();
		avg_solutions.close();
		ev_axis.close();
		
		// + Utworzenie oraz zapisanie do pliku najlepszy czas sredni oczekiwania.
		FileWriter bestSolutionFinal=new FileWriter("The_Best_Solution_Time_Final.txt");
		bestSolutionFinal.append(the_best_time+"\n");
		bestSolutionFinal.close();
		
		// + Utworzenie oraz zapisanie do pliku najlepszej kombinacji u³o¿enia zadañ.
		FileWriter The_Best_Combination=new FileWriter("The_Best_Combination.txt");
		for(int j=0;j<the_best_chromoson.length;j++)
		{
			The_Best_Combination.append(the_best_chromoson[j]+"\n");
		}
		The_Best_Combination.close();
		
		// 6. Wyprowadzenie najlepszego chromosonu :)
		return the_best_chromoson;
	}
	
}


































