package src;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

// CEL: Wykonanie algorytmu mr�wkego dla podanych parametr�w.
public class AL
{
	// CEL: Czytnik zada�.
	public static class CityReader
	{
		// REGION: FIELDS
		private int[][]cities; // Kordynaty miast -> (X, Y).
		private int numberOfCities; // Ilosc miast.
		// END REGION
		
		// REGION: CONSTRUCTOR
		public CityReader(String path)
		{
			// 1. Uzyskanie wskaznika do pliku.
			File f=new File(path);	
			
			// 2. Utworzenie czytnika do tego pliku.
			Scanner sc=null;
			try {
				sc=new Scanner(f);
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Wczytywanie pliku nie powiod�o si�!");
			}
			
			// 3. Pomini�cie trzech linii w pliku.
			for(int i=0;i<3; i++)
			{
				sc.nextLine();
			}
			
			// 4. Uzyskanie rozmiaru.
			numberOfCities=Integer.parseInt(sc.nextLine().split(" ")[1]);
		
			// 5. Pomonicie dw�ch linii.
			for(int i=0;i<2;i++)
			{
				sc.nextLine();
			}
			
			// 6. Utworzenie bufora.
			cities=new int[numberOfCities][2];
			
			// 7. Wpisywanie danych z bufora.
			for(int i=0;i<numberOfCities;i++)
			{
				String[] values=sc.nextLine().split(" ");
				cities[i][0]=Integer.parseInt(values[0]);
				cities[i][1]=Integer.parseInt(values[1]);
			}
		}
		// END REGION
		
		// REGION: PROPORTIES
		// CEL: Uzyskanie wspolrzednych.
		public int[][] getCities()
		{
			return cities;
		}

		// CEL: Uzyskanie ilosci miast.
		public int getNumberOfCities() 
		{
			return numberOfCities;
		}
		
		// CEL: Wypisanie danych.
		@Override
		public String toString()
		{
			String res="Number of cities: "+numberOfCities+"\n";
			for(int i=0;i<numberOfCities;i++)
			{
				res+=(i+1)+" "+cities[i][0]+" "+cities[i][1]+"\n";
			}
			return res;
		}
		// END REGION
	}
	
	// CEL: Uzyskaj dystans pomi�dzy punktami.
	public static double getDistance(int[][]cords, int first, int second)
	{
		double x1=cords[first][0];
		double y1=cords[first][1];
		double x2=cords[second][0];
		double y2=cords[second][1];
		return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2,2));
	}
	
	// Klasa do przechowywania mr�wki.
	public static class Ant
	{
		// POLA:
		ArrayList<Integer>route; // Scie�ka.
		int numberOfCities; // Ilo�� w�z��w.

		// KONSTRUTKOR:
		public Ant(int numberOfCities)
		{
			route=new ArrayList<Integer>(numberOfCities);
			this.numberOfCities=numberOfCities;
		}

		// WLASCIWOSCI:
		// CEL: Uzyskanie ilosci miast.
		public int getNumberOfCities()
		{
			return numberOfCities;
		}
		
		// CEL: Uzyskanie sciezki.
		public ArrayList<Integer> getRoute()
		{
			return route;
		}
		
		// METODY:
		// CEL: Dodanie miasta do sciezki.
		public void AddCity(int city)
		{
			route.add(city);
		}
		
		// CEL: Ustawienie pocz�tkowego miasta.
		public void SetStartCity(int city)
		{
			if(route.size()==0)
			{
				AddCity(city);
			}
		}
		
		// CEL: Uzyskaj nieodwiedzone miasta.
		private ArrayList<Integer>Ni()
		{
			// 1. Utworzenie bufora.
			ArrayList<Integer>Ni=new ArrayList<Integer>();
			
			// 2. Wype�nianie bufora.
			for(int i=0;i<numberOfCities;i++)
			{
				if(!route.contains(i))
				{
					Ni.add(i);
				}
			}
			return Ni;
		}
		
		// CEL: Uzyskanie obecnie odwiedzanego miasta.
		private Integer getCurrentCity()
		{
			return route.get(route.size()-1);
		}
		
		// CEL: Przejscie do nast�pnego miasta.
		public void GoToAnotherCity(double[][] feromons,int[][]cities, double alfa, double beta)
		{
			if(route.size()<=numberOfCities) 
			{
				// 1. Uzyskanie listy nieodwiedzonych miast.
				ArrayList<Integer>Ni=Ni();
				
				// 2. Obliczenie mianownika.
				double den=0;
				int currentCity=getCurrentCity();
				for(Integer otherCity: Ni)
				{
					double t=Math.pow(feromons[currentCity][otherCity], alfa);
					den+=t*Math.pow(1/getDistance(cities, currentCity, otherCity), beta);
				}
				
				// 3. Znalezenie najlepszego.
				double gen_prob=Math.random(); // Generowanie prawdopodbie�stwa.
				double lb=0; // Lewa granica.
				double rb=0; // Prawa granica.
				for(Integer otherCity: Ni) // Przechodzenie prze miasta.
				{
					// 3.1 Obliczenie prawdopobie�stwa dla danego miasta.
					double t=Math.pow(feromons[currentCity][otherCity], alfa);
					double prob=t*Math.pow((1/getDistance(cities, currentCity, otherCity))/den, beta);

					// 3.2 Ustawianie nowych granic.
					lb=rb;
					rb+=prob;
					
					// 3.3 Sprawdzenie czy miesci w tej granicy.
					if(gen_prob>=lb && gen_prob<=rb)
					{
						// 3.3.1 Dodanie miasta.
						AddCity(otherCity);
					}
				}
				
			}
		}
		
		// CEL: Drukowanie sciezki.
		public void PrintRoute()
		{
			String res="Route:\n";
			for(Integer i:route)
			{
				res+=i+", ";
			}
			System.out.println(res);
		}
		
		// CEL: Ocena mr�wki.
		public double getEval(CityReader cr)
		{
			// 1. Utworzenie zmiennej do przechowywania sumy dystans�w.
			double sum=0;
			
			// 2. Przechodzenie przez �cie�ke.
			for(int i=1;i<getRoute().size();i++)
			{
				sum+=getDistance(cr.getCities(), getRoute().get(i-1), getRoute().get(i));
			}
			return sum;
		}

		// CEL: Zwrocenie sciezki.
		@Override
		public String toString()
		{
			String res="Route:\n";
			for(Integer i:route)
			{
				res+=i+", ";
			}
			return res;
		}
	}
	
	// Klasa do przechowywania koloni.
	public static class Colony
	{
		// Pola:
		Ant[]ants; // Mrowki, ktore sa w koloni.
		int numberOfCities; // Ilosc miast.
		double[][]feromons; // Feromony.
		
		// Konstruktor:
		public Colony(int numberOfCities)
		{
			ants=new Ant[numberOfCities];
			this.numberOfCities=numberOfCities;
			for(int i=0;i<numberOfCities;i++)
			{
				ants[i]=new Ant(numberOfCities);
			}
			feromons=new double[numberOfCities][numberOfCities];
		}
		
		// Obliczanie paremtru delta tij.
		public double getDeltaTij(int[][] cities, double Q, int first, int second)
		{
			int sum=0;
			for(int i=0;i<numberOfCities;i++)
			{
				int x=0;
				int y=0;
				for(int j=0;j<ants[i].getRoute().size();j++)
				{
					if(x==1 && (y==second || y==first))
					{
						sum+=Q/getDistance(cities, first, second);
					}
					if(x==first || x==second)
					{
						x=1;
					}
					else
					{
						x=0;
						y=0;
					}
				}
			}
			return sum;
		}
		
		public double getDeltaTij_v2(int[][] cities, double Q, int first, int second)
		{
			int sum=0;
			for(int i=0;i<numberOfCities;i++)
			{
				int x=0;
				int y=0;
				for(int j=0;j<ants[i].getRoute().size();j++)
				{
					if(x==1 && (y==second || y==first))
					{
						sum+=getDistance(cities, first, second);
					}
					if(x==first || x==second)
					{
						x=1;
					}
					else
					{
						x=0;
						y=0;
					}
				}
			}
			return Q/sum;
		}
		
		// Aktualizowanie feromon�w.
		public void UpdateFeromons(int[][] cities, double Q, double p, int mode)
		{
			if(mode==1) 
			{
				for(int i=0;i<numberOfCities;i++)
				{
					for(int j=0;j<numberOfCities;j++)
					{
						double delta_tij=getDeltaTij(cities, Q, i, j);
						feromons[i][j]=(1-p)*feromons[i][j]+delta_tij;
					}
				}
			}
			else if(mode==2)
			{
				for(int i=0;i<numberOfCities;i++)
				{
					for(int j=0;j<numberOfCities;j++)
					{
						double delta_tij=getDeltaTij_v2(cities, Q, i, j);
						feromons[i][j]=(1-p)*feromons[i][j]+delta_tij;
					}
				}
			}
		}
		
		// Drukowanie tras mrowek.
		public void PrintAnts()
		{
			for(int i=0;i<numberOfCities;i++)
			{
				System.out.println("Ant nr.:"+i);
				System.out.println(ants[i]);
				System.out.println();
			}
		}
		
		// Inicjalizacja feromon�w.
		public void InitializeFeromons()
		{
			for(int i=0;i<numberOfCities;i++)
			{
				for(int j=0;j<numberOfCities;j++)
				{
					feromons[i][j]=1;
				}
			}
		}
		
		// Drukowanie feromon�w
		public void PrintFeromons()
		{
			for(int i=0;i<numberOfCities;i++)
			{
				System.out.println("Feromon nr.:"+(i+1));
				for(int j=0;j<numberOfCities;j++)
				{
					System.out.print(feromons[i][j]+", ");
				}
			}
		}
		
		// CEL: Uzyskaj najlepsze rozwi�zanie
		public Ant getTheBest(CityReader cr)
		{
			// 1. Znalezienie najlepszego rozwiazania.
			double best_eval=ants[0].getEval(cr);
			Ant best=ants[0];
			
			// 2. Przechodzzenie przez kolonie mr�wek.
			for(Ant ant:ants)
			{
				// 3. Sprawdzanie czy obecna warto�� jest lepsza (mniejsza) od wcze�niejszej.
				double current_eval=ant.getEval(cr);
				if(current_eval<best_eval)
				{
					// 3.1 Ustawienie nowego najlepszego.
					best=ant;
					best_eval=current_eval;
				}
			}
			
			// 4. Zwr�� najlepsze rozwi�zanie.
			return best;
		}
		
		// Uzyskanie mr�wek.
		public Ant[] getAnts() {
			return ants;
		}

		public double[][]getFeromons()
		{
			return feromons;
		}
		
		public int getNumberOfCities() {
			return numberOfCities;
		}
	}
	
	// CEL: Wykonanie algorytmu.
	public static Ant AL_v1(CityReader cr, int MAX_ITER)
	{
		// 0. Ustalanie paremetr�w.
		double p=0.5f, alfa=1.0f, beta=2.5f, Q=100.0f;
		int K = cr.getNumberOfCities();
		
		// 1. Utworzenie kolonii.
		Colony colony=new Colony(K);
		
		// 2. Inicjalizacja feromon�w.
		colony.InitializeFeromons();
		
		// 3. Umieszczenia mr�wek w w�z�ach pocz�tkowych.
		for(int k=0; k<K; k++)
		{
			colony.getAnts()[k].SetStartCity(k);
		}
		
		// 4. Zewn�trzna p�tla.
		for(int i=0; i<MAX_ITER; i++)
		{
			// 4.1 Wewn�trzna p�tla.
			for(int k=0; k<K; k++)
			{
				// 4.1.1 Wybieranie probabilistycznie nast�pnego wierzcho�ka na �cie�ce.
				colony.getAnts()[k].GoToAnotherCity(colony.getFeromons(), cr.getCities(), alfa, beta);
			}
			// 4.2 Aktualizacja feromon�w na �cie�kach.
			colony.UpdateFeromons(cr.getCities(), Q, p, 1);
		}

		// 5. Zwr�� najlepsze rozwi�zanie
		return colony.getTheBest(cr);
	}
	
	// CEL: Wykonanie algorytmu.
	public static Ant AL_v2(CityReader cr, int MAX_ITER)
	{
		// 0. Ustalanie paremetr�w.
		double p=0.5f, alfa=1.0f, beta=2.5f, Q=100.0f;
		int K = cr.getNumberOfCities();

		// 1. Utworzenie kolonii.
		Colony colony=new Colony(K);
		
		// 2. Inicjalizacja feromon�w.
		colony.InitializeFeromons();
		
		// 3. Zewn�trzna p�tla.
		for(int i=0; i<MAX_ITER; i++)
		{
			System.out.println("i: "+i);
			// 3.1 Wewn�trzna p�tla.
			for(int k=0; k<K; k++)
			{
				// 3.1.1 Ustawianie mr�wek w miejsca pocz�tkowych.
				colony.getAnts()[k].SetStartCity(k);
				
				System.out.println("k: "+k);;
				
				// 3.1.2 Wype�nienia scie�ek mr�wek dop�ki nie zbuduje rozwi�zania.
				while(colony.getAnts()[k].getRoute().size()<K-1)
				{
					// 3.1.2.1 Dodanie miasta do �cie�ki.
					colony.getAnts()[k].GoToAnotherCity(colony.getFeromons(), cr.getCities(), alfa, beta);
				}
			}
			
			// 3.2 Akutalizowanie feromonu na �cie�kach.
			colony.UpdateFeromons(cr.getCities(), Q, p, 2);
		}

		// 4. Zwr�cenie najlepszego rozwi�zania.
		return colony.getTheBest(cr);
	}
	
	// CEL: Zapisywanie wynik�w do plik�w.
	public static void Writer(int mode, int times, int MAX_ITER, double optim, String src, String dest, String err)
	{
		// 1. Utworzenie obiektu do wczytania pliku.
		CityReader city=new CityReader(src);
		
		// 2. Utworzenie obiekt�w do zapisywania.
		FileWriter eval_writer=null, error_writer=null;
		
		// 3. Utworzenie parametr�w pomocniczych.
		double sum_er=0.0d;
		
		// 4. Operacja na plikach.
		try
		{
			// 4.1 Otworzenie plik�w.
			eval_writer=new FileWriter(dest);
			error_writer=new FileWriter(err);
			
			// 4.1 Sprawdzenie jaki tryb jest wprowadzony.
			switch(mode)
			{
			case 1:
				// 4.2 Zapisywanie do plik�w.
				for(int i=0;i<times;i++)
				{
					// 4.2.1 Zapisanie warto�ci funkcji przystowania.
					double eval=AL_v1(city, MAX_ITER).getEval(city);
					eval_writer.append(eval+"\n");
					
					// 4.2.2 Zapisanie warto�ci b��du wzgl�dnego.
					double er=Math.abs(optim-eval)/eval;
					sum_er+=er;
					er*=100.0f;
					error_writer.append(er+"\n");
				}
				break;
			case 2:
				// 4.2 Zapisywanie do plik�w.
				for(int i=0;i<times;i++)
				{
					// 4.2.1 Zapisanie warto�ci funkcji przystowania.
					double eval=AL_v2(city, MAX_ITER).getEval(city);
					eval_writer.append(eval+"\n");
					
					// 4.2.2 Zapisanie warto�ci b��du wzgl�dnego.
					double er=Math.abs(optim-eval)/eval;
					sum_er+=er;
					er*=100.0f;
					error_writer.append(er+"\n");
				}
				break;
			}
			
			// 4.3 Dopisanie �redniej b��du wzgl�dnego.
			error_writer.append(sum_er/times+"\n");
			
			// 4.4 Zamkni�cie plik�w.
			eval_writer.close();
			error_writer.close();
		}catch(Exception e){}
	}
	
	// CEL: Generowanie oraz zapisywanie wynik�w.
	public static void GenerateAndSave()
	{
		// 1. Nazwy plik�w zr�d�owych.
		String[]src= {
			"kroA100", "kroA150", "kroA200", 
			"kroB100", "kroB150", "kroB200",
			"kroC100", 
			"kroD100",
			"kroE100"
		};
		double[]optims= {
				21282, 26524, 29368, 22141, 26130, 29437, 20749, 21294, 22068
		};
		
		// 2. Nazwy plik�w z warto�ciami funkcja przystosowania dla wersji 1 algorytmu.
		String[]evals_V1=new String[src.length];
		
		// 3. Nazwy plik�w z warto�ciami b��d�w dla wersji 1 alogrytmu.
		String[]errors_V1=new String[src.length];
		
		// 4. Nazwy plik�w z warto�cami funkcjami przystoswania dla wersji 2 algorytmu.
		String[]evals_V2=new String[src.length];
		
		// 5. Nazwy plik�w z warto�ciami b��d�w dla wersj 2 algorytmu.
		String[]errors_V2=new String[src.length];
		
		// 6. Tworzernie nazw tych plik�w.
		for(int i=0;i<src.length;i++)
		{
			
			evals_V1[i]="src/"+src[i]+"_RES_V1.tsp";
			errors_V1[i]="src/"+src[i]+"_ERRORS_V1.tsp";
			
			evals_V2[i]="src/"+src[i]+"_RES_V2.tsp";
			errors_V2[i]="src/"+src[i]+"_ERRORS_V2.tsp";
			
			src[i]="src/"+src[i]+".tsp";
		}
		
		// 7. Utworzenie parametru pomocniczego.
		int MAX_ITER=100; // Ilosc maksymalnej ilo�ci iteracji.
		int times=10; // Ilosc wynikow do uzyskania.
		 
		// 8. Generowanie oraz zapisywanie wynik�w.
		
		// 8.1 Generowanie wynik�w dla wersji 1:
		System.out.println("Wersja 1:");
		for(int i=0;i<src.length;i++)
		{
			System.out.println("Generuje dla: "+evals_V1[i]);
			Writer(1, times, MAX_ITER, optims[i], src[i], evals_V1[i], errors_V1[i]);
			System.out.println("Wygenerowano dla: "+evals_V1[i]);
		}

		// 8.2 Generowanie wynik�w dla wersji 2:
		System.out.println("Wersja 2:");
		for(int i=0;i<src.length;i++)
		{
			System.out.println("Generuje dla: "+evals_V2[i]);
			Writer(2, times, MAX_ITER, optims[i], src[i], evals_V2[i], errors_V2[i]);
			System.out.println("Wygenerowano dla: "+evals_V2[i]);
		}
	}
	
	public static void main(String[]args)
	{
		// Wykonanie probramu.
		GenerateAndSave();
		
		/*
		CityReader cr=new CityReader("src/kroA150.tsp");
		Colony colony=new Colony(cr.getNumberOfCities());
		colony.InitializeFeromons();
		Ant ant=new Ant(cr.getNumberOfCities());
		ant.SetStartCity(0);
		for(int i=0;i<ant.getNumberOfCities();i++)
		{
			ant.GoToAnotherCity(colony.getFeromons(), cr.getCities(), 1, 2);
		}
		ant.PrintRoute();
		System.out.println(ant.getEval(cr));
		ev(ant, cr);
		*/
		
		/*
		Ant an=AL_v1(cr, 100);
		an.PrintRoute();
		System.out.println(an.getEval(cr));
		*/
	}
}
/*
 *  Author: Juliusz Losinski 46155 
 */
