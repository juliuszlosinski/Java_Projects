import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

// CEL: Implementacja Algorytmu Symulowanego Wy¿arzania.
public class SA 
{
	// CEL: Problem wydzielenia zadañ pomiêdzy pracownikami.
	static class TaskProblem
	{
		static int idZad1;
		static int idZad2;
		static int idPrac1;
		static int idPrac2;
		
		// Publiczna flaga, który mówi ¿e ka¿dy pracownik musi mieæ jedno zadanie.
		public static boolean ONE_WORKER_ONE_JOB=true;
		
		// CEL: Czytnik zadañ.
		public static class TasksReader
		{
			// POLA:
			private int[][]tasks; // Zadania
			private int numberOfWorkers; // Ilosc pracowników.
			
			// KONSTRUKTOR:
			public TasksReader(String path)
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
					System.out.println("Wczytywanie pliku nie powiod³o siê!");
				}

				// 3. Uzyskanie ilosci pracowników.
				numberOfWorkers=Integer.parseInt(sc.nextLine().split(" ")[1]);
				
				// 4. Utworzenie bufora z pracownikami.
				tasks=new int[numberOfWorkers][numberOfWorkers];

				// 5. Wypelnianie buforów.
				int currentRow=0;
				int currentColumn=0;
				while(sc.hasNextLine())
				{
					String[]values=sc.nextLine().split(" ");
					for(int i=1;i<values.length;i++)
					{
						if(currentColumn>=numberOfWorkers)
						{
							currentColumn=0;
							currentRow++;
						}
						tasks[currentRow][currentColumn]=Integer.parseInt(values[i]);
						currentColumn++;
					}
				}
				
				// 6. Zakoñczenie dzia³ania algorytmu.
			}
		
			// WLASCIWOSCI:
			// CEL: Zwrocenie ilosci pracownikow.
			public int getNumberOfWorkers()
			{
				return numberOfWorkers;
			}
			
			// CEL: Zwrocenie ilosc zadañ.
			public int getNumberOfTasks()
			{
				return numberOfWorkers;
			}
			
			// CEL: Zwrocenie buforu z zadaniami.
			public int[][] getTasks()
			{
				return tasks;
			}
		
			// CEL: Wypisanie.
			@Override
			public String toString()
			{
				String buff="";
				buff="Number of workers: "+numberOfWorkers+"\n";
				buff+="Tasks: \n";
				for(int i=0;i<numberOfWorkers;i++)
				{
					for(int j=0;j<numberOfWorkers;j++)
					{
						buff+=tasks[i][j]+" ";
					}
					buff+="\n";
				}
				return buff;
			}
		}
		
		// CEL: Obliczanie wartoœci funkcji przystosowania.
		public static int Eval(int[][]workers, int[][]tasks, int numberOfWorkers, int numberOfTasks)
		{
			// 1. Utworzenie parametru na sumê.
			int sum=0;
			for(int i=0; i<numberOfWorkers;i++)
			{
				for(int j=0; j<numberOfTasks;j++)
				{
					// 2. Sprawdzenie czy pracownik wykonuje to zadanie.
					if(workers[i][j]==1)
					{
						// 3. Dodanie zadania do sumy.
						sum+=tasks[i][j];
					}
				}
			}
			// 4. Zwrocenie wyniku.
			return sum;
		}
		
		// CEL: Wypisanie pracownikow/ rozwiazania.
		public static void PrintWorkers(int[][]workers, int numberOfWorkers, int numberOfTasks)
		{
			// 1. Wypisywanie rozwiazania.
			System.out.println("Workers: \n");
			for(int i=0;i<numberOfWorkers;i++)
			{
				System.out.print("W"+(i+1)+": ");
				for(int j=0;j<numberOfTasks;j++)
				{
					System.out.print(workers[i][j]+" ");
				}
				System.out.println();
			}
		}
		
		// CEL: Operator zamiany pracowników w rozwi¹zaniu. Nowe zale¿ne rozwi¹zanie jest zale¿ne od wczeœniejszego.
		public static int[][] SwapTasksOperator(int[][]workers, int numberOfWorkers, int numberOfTasks)
		{
			// 0. Generowanie kopii.
			int[][]buff=new int[numberOfWorkers][numberOfTasks];
			for(int i=0;i<numberOfWorkers;i++)
			{
				for(int j=0;j<numberOfTasks;j++)
				{
					buff[i][j]=workers[i][j];
				}
			}
			
			// 1. Utworzenie obiektu do generowania liczb pseudolosowych.
			Random rand=new Random();
			
			// 2. Generowanie dwóch ID pracowników.
			// 2.1 Podstawowe generowanie.
			int firstPos=rand.nextInt(numberOfWorkers);
			int	secondPos=rand.nextInt(numberOfWorkers);
			
			// 2.2 Zapewnienie, ze wartosci nie sa takie same.
			while(firstPos==secondPos)
			{
				secondPos=rand.nextInt(numberOfWorkers);
			}
			
			// 3. Zamiana wartosci.
			for(int i=0; i<numberOfTasks ;i++)
			{
				int tmp=buff[firstPos][i];
				buff[firstPos][i]=buff[secondPos][i];
				buff[secondPos][i]=tmp;
			}
			
			// 4. Zwrocenie wyniku.
			return buff;
		}
		
		// CEL: Operator zamiany pracowników w rozwi¹zaniu. Nowe zale¿ne rozwi¹zanie jest zale¿ne od wczeœniejszego.
		public static Ruch wybierz_rozwiazanie_z_otoczenia(int[][]workers, int numberOfWorkers, int numberOfTasks)
		{
			// 0. Generowanie kopii.
			int[][]buff=new int[numberOfWorkers][numberOfTasks];
			for(int i=0;i<numberOfWorkers;i++)
			{
				for(int j=0;j<numberOfTasks;j++)
				{
					buff[i][j]=workers[i][j];
				}
			}
			
			// 1. Utworzenie obiektu do generowania liczb pseudolosowych.
			Random rand=new Random();
			
			// 2. Generowanie dwóch ID pracowników.
			// 2.1 Podstawowe generowanie.
			int firstPos=rand.nextInt(numberOfWorkers);
			int	secondPos=rand.nextInt(numberOfWorkers);
			
			// 2.2 Zapewnienie, ze wartosci nie sa takie same.
			while(firstPos==secondPos)
			{
				secondPos=rand.nextInt(numberOfWorkers);
			}
			
			int idTask1=-1;
			int idTask2=-1;
			int idWorker1=firstPos;
			int idWorker2=secondPos;
			
			// 3. Zamiana wartosci.
			for(int i=0; i<numberOfTasks ;i++)
			{
				if(buff[firstPos][i]==1)
				{
					idTask1=i;
				}
				if(buff[secondPos][i]==1)
				{
					idTask2=i;
				}
				int tmp=buff[firstPos][i];
				buff[firstPos][i]=buff[secondPos][i];
				buff[secondPos][i]=tmp;
			}
			
			// 4. Zwrocenie wyniku.
			return new Ruch(idTask1, idTask2, idWorker1, idWorker2, buff);
		}

		// CEL: Operator zamiany pracowników w rozwi¹zaniu. Nowe zale¿ne rozwi¹zanie jest zale¿ne od wczeœniejszego.
		public static Ruch wybierz_rozwiazanie_z_otoczenia_zmodyfikowane(int[][]workers, int numberOfWorkers, int numberOfTasks)
		{
			// 0. Generowanie kopii.
			int[][]buff=new int[numberOfWorkers][numberOfTasks];
			for(int i=0;i<numberOfWorkers;i++)
			{
				for(int j=0;j<numberOfTasks;j++)
				{
					buff[i][j]=workers[i][j];
				}
			}
			
			// 1. Utworzenie obiektu do generowania liczb pseudolosowych.
			Random rand=new Random();
			
			// 2. Generowanie dwóch ID pracowników.
			// 2.1 Podstawowe generowanie.
			int firstPos=rand.nextInt(numberOfWorkers);
			int	secondPos=rand.nextInt(numberOfWorkers);
			
			// 2.2 Zapewnienie, ze wartosci nie sa takie same.
			while(firstPos==secondPos)
			{
				secondPos=rand.nextInt(numberOfWorkers);
			}
			
			int idTask1=-1;
			int idTask2=-1;
			int idWorker1=firstPos;
			int idWorker2=secondPos;
			
			// 3. Zamiana wartosci.
			for(int i=0; i<numberOfTasks ;i++)
			{
				if(buff[firstPos][i]==1)
				{
					idTask1=i;
				}
				if(buff[secondPos][i]==1)
				{
					idTask2=i;
				}
				int tmp=buff[firstPos][i];
				buff[firstPos][i]=buff[secondPos][i];
				buff[secondPos][i]=tmp;
			}
			
			// 4. Zwrocenie wyniku.
			return new Ruch(idTask1, idTask2, idWorker1, idWorker2, buff);
		}

		// CEL: Drukowania bufora.
		public static void PrintBuffor(int[]buff)
		{
			for(int i=0;i<buff.length;i++)
			{
				System.out.print(buff[i]+" ");
			}
			System.out.println();
		}
		
		// CEL: Generowania rozwiazanie/ pracownikow.
		public static int[][] GenerateWorkers(int numberOfWorkers, int numberOfTasks)
		{
			// 1. Utworzenie bufora na pracowników.
			int[][]workers=new int[numberOfWorkers][numberOfTasks];
			int[]checked=new int[numberOfWorkers];
			
			// 2. Utworzenie obiektu do genero11wania liczb pseudo losowych.
			Random rand=new Random();
			
			// 3. Uzupe³nianie bufora.
			if(ONE_WORKER_ONE_JOB) // TRYB JEDEN PRACOWNIK -> JEDNO ZADANIE
			{
				for(int i=0;i<numberOfTasks;i++)
				{
					// 3.1 Generowanie ID pracownika.
					int idWorker=rand.nextInt(numberOfTasks);
					
					while(checked[idWorker]==1)
					{
						idWorker=rand.nextInt(numberOfTasks);
					}
					
					// 3.2 Przypisanie zadania pracownikowi.
					checked[idWorker]=1;
					workers[idWorker][i]=1;
				}
			}
			else	// TRYB JEDEN PRACOWNIK -> JEDNO/ WIELE ZADAÑ
			{
				for(int i=0;i<numberOfTasks;i++)
				{
					// 3.1 Generowanie identyfikatora pracownika.
					int idWorker=rand.nextInt(numberOfTasks);
					while(workers[idWorker][i]==1)
					{
						idWorker=rand.nextInt(numberOfTasks);
					}
					
					// 3.2 Przypisanie zadania pracownikowi.
					workers[idWorker][i]=1;
				}
			}
			
			// 4. Zwrocenie wyniku.
			return workers;
		}

		private static class Ruch
		{
			// POLA:
			private int idPrac1;
			private int idPrac2;
			private int idZad1;
			private int idZad2;
			private int[][]result;
		
			// KONSTRUTKOR:
			public Ruch(int idPrac1, int idPrac2, int idZad1, int idZad2, int[][] result)
			{
				this.idPrac1 = idPrac1;
				this.idPrac2 = idPrac2;
				this.idZad1 = idZad1;
				this.idZad2 = idZad2;
				this.result=result;
			}

			public Ruch() {}
			
			// WLASCIWOSCI:
			public int getIdPrac1()
			{
				return idPrac1;
			}

			public int getIdPrac2() 
			{
				return idPrac2;
			}

			public int getIdZad1()
			{
				return idZad1;
			}

			public int getIdZad2()
			{
				return idZad2;
			}
			
			public int[][] getResult()
			{
				return result;
			}
			
			// CEL: Sprawdzenie czy ruchu s¹ takie same.
			public static boolean isTheSame(Ruch r1, Ruch r2)
			{
				if((r1.idPrac1 == r2.idPrac1 && r1.idPrac2 == r2.idPrac2)||(r1.idPrac1 == r2.idPrac2 && r1.idPrac2 == r2.idPrac1))
				{
					if((r1.idZad1 == r2.idZad1 && r2.idZad2 == r2.idZad2)||(r1.idZad2 == r2.idZad1 && r1.idZad1 == r2.idZad2))
					{
						return true;
					}
				}
				return false;
			}
			
		}

		// CEL: Sprawdzenie listy tabu.
		public static boolean checkTabuList(Queue<Ruch> tabuList, Ruch ruch1)
		{
			for(Ruch ruch2: tabuList)
			{
				if(Ruch.isTheSame(ruch1, ruch2))
				{
					return true;
				}
			}
			
			return false;
		}
		
		// CEL: Wykonanaie algorytmu Tabu Search:
		public static int[][] TabuSearch(TasksReader wr,int maxDlugoscListy,int maxKadencja, int stop)
		{
			// 0. Zmienna sledzace kadencje.
			int obecnaKad=0;
			
			// 1. Utworzenie listy tabu.
			Queue<Ruch> tabuList=new LinkedList<Ruch>();
			
			// 2. Wybranie rozwi¹zania pocz¹tkowego.
			int[][]Vc=GenerateWorkers(wr.getNumberOfWorkers(), wr.getNumberOfTasks());
			
			// 3. Podstawowe zapisanie najlepszego rozwi¹znaia.
			int[][]best=Vc.clone();
			double best_eval=Eval(Vc, wr.getTasks(), wr.getNumberOfWorkers(), wr.getNumberOfTasks());
			
			// 4. Pêtla:
			for(int i=0;i<stop;i++)
			{
				// 4.1 Wybranie rozwi¹zania z otoczenia, który nie jest na liœcie tabu.
				int[][]Vn=null;
				Ruch ruch=wybierz_rozwiazanie_z_otoczenia(Vc,wr.getNumberOfWorkers(), wr.getNumberOfTasks());
				while(checkTabuList(tabuList, ruch))
				{
					ruch=wybierz_rozwiazanie_z_otoczenia(Vc,wr.getNumberOfWorkers(), wr.getNumberOfTasks());
				}
				Vc=ruch.getResult();
				
				// 4.2 Sprawdzenie czy nowe rozwi¹zanie jest lepsze od obecnego najlepszego.
				double Vn_eval=Eval(Vc, wr.getTasks(), wr.getNumberOfWorkers(), wr.getNumberOfTasks());
				if(Vn_eval <best_eval)
				{
					best=Vc;
					best_eval=Vn_eval;
				}
				
				// 4.3 Uaktualnianie listy tabu.
				tabuList.add(ruch);
				
				// 4.4 Uaktualnianie kadencji.
				obecnaKad++;
				if(obecnaKad>=maxKadencja)
				{
					tabuList.poll();
					obecnaKad=0;
				}
				
				// 4.5 Usuwanie elementu najstarszego jezeli dlugosc list zostala przekroczona.
				if(tabuList.size()>maxDlugoscListy)
				{
					tabuList.poll();
				}
			}
			
			// 5. Zwrocenie wyniku.
			return best;
		}
		
		// CEL: Wykonanie algorytmu przeszukiwania Tabu:
		public static void TabuSearch_2()
		{
			Queue<Ruch> tabuList= new LinkedList<Ruch>();
		}
		
		// CEL: Wykonanaie algorytmu Tabu Search:
		public static int[][] TabuSearch_Zmodyfikowane(TasksReader wr,int maxDlugoscListy,int maxKadencja, int stop)
		{
			// 0. Zmienna sledzace kadencje.
			int obecnaKad=0;
			
			// 1. Utworzenie listy tabu.
			Queue<Ruch> tabuList=new LinkedList<Ruch>();
			
			// 2. Wybranie rozwi¹zania pocz¹tkowego.
			int[][]Vc=GenerateWorkers(wr.getNumberOfWorkers(), wr.getNumberOfTasks());
			
			// 3. Podstawowe zapisanie najlepszego rozwi¹znaia.
			int[][]best=Vc.clone();
			double best_eval=Eval(Vc, wr.getTasks(), wr.getNumberOfWorkers(), wr.getNumberOfTasks());
			
			// 4. Pêtla:
			int max_wewn_iter=20;
			for(int i=0;i<stop;i++)
			{
				// 4.1 Wybranie rozwi¹zania z otoczenia, który nie jest na liœcie tabu.
				Ruch ruch=null;
				for(int j=0;j<max_wewn_iter;j++)
				{
					ruch=wybierz_rozwiazanie_z_otoczenia(Vc,wr.getNumberOfWorkers(), wr.getNumberOfTasks());
					while(checkTabuList(tabuList, ruch))
					{
						ruch=wybierz_rozwiazanie_z_otoczenia(Vc,wr.getNumberOfWorkers(), wr.getNumberOfTasks());
					}
					Vc=ruch.getResult();
					
					// 4.2 Sprawdzenie czy nowe rozwi¹zanie jest lepsze od obecnego najlepszego.
					double Vn_eval=Eval(Vc, wr.getTasks(), wr.getNumberOfWorkers(), wr.getNumberOfTasks());
					if(Vn_eval <best_eval)
					{
						best=Vc;
						best_eval=Vn_eval;
					}
				}
				
				// 4.3 Uaktualnianie listy tabu.
				tabuList.add(ruch);
				
				// 4.4 Uaktualnianie kadencji.
				obecnaKad++;
				if(obecnaKad>=maxKadencja)
				{
					tabuList.poll();
					obecnaKad=0;
				}
				
				// 4.5 Usuwanie elementu najstarszego jezeli dlugosc list zostala przekroczona.
				if(tabuList.size()>maxDlugoscListy)
				{
					tabuList.poll();
				}
			}
			
			// 5. Zwrocenie wyniku.
			return best;
		}
		
		// CEL: Obliczenie sredniej.
		public static double Avg_Eval(String path, int dlugoscListyTabu, int kadencja, int MAX_ITER, int numberOfTests)
		{
			// 1. Ustawianie parametrów.
			TasksReader wr=new TasksReader(path);
			
			// 2. Obliczanie najlepszego rozwi¹zania.
			double avg=0;
			for(int i=0; i<numberOfTests;i++) 
			{
				int[][]best_solution=TabuSearch(wr, dlugoscListyTabu, kadencja, MAX_ITER);
				double solution=Eval(best_solution, wr.getTasks(), wr.getNumberOfWorkers(), wr.getNumberOfTasks());
				avg+=solution;
			}
		
			// 3. Zwrócenie wyniku.
			return avg/numberOfTests;
		}
		
		// CEL: Testowanie listy tabu.
		public static void Test_TabuSearch(String path, int dlugoscListyTabu, int kadencja, int MAX_ITER)
		{
			// 1. Ustawianie parametrów.
			TasksReader wr=new TasksReader(path);
			
			// 2. Obliczanie najlepszego rozwi¹zania.
			int[][]best_solution=TabuSearch(wr, dlugoscListyTabu, kadencja, MAX_ITER);
			double solution=Eval(best_solution, wr.getTasks(), wr.getNumberOfWorkers(), wr.getNumberOfTasks());

			// 3. Wypisywanie rozwiaznia.
			System.out.println("Dlugosc listy tabu: "+dlugoscListyTabu);
			System.out.println("Wartosc kadencji: "+kadencja);
			System.out.println("Ilosc iteracji/ MAX_ITER: "+MAX_ITER);
			System.out.println("Eval: "+solution);
		}
		
		// CEL: Testowanie listy tabu.
		public static void Test_TabuSearch_Zmodyfikowane(String path, int dlugoscListyTabu, int kadencja, int MAX_ITER)
		{
			// 1. Ustawianie parametrów.
			TasksReader wr=new TasksReader(path);
			
			// 2. Obliczanie najlepszego rozwi¹zania.
			int[][]best_solution=TabuSearch_Zmodyfikowane(wr, dlugoscListyTabu, kadencja, MAX_ITER);
			double solution=Eval(best_solution, wr.getTasks(), wr.getNumberOfWorkers(), wr.getNumberOfTasks());

			// 3. Wypisywanie rozwiaznia.
			System.out.println("Dlugosc listy tabu: "+dlugoscListyTabu);
			System.out.println("Wartosc kadencji: "+kadencja);
			System.out.println("Ilosc iteracji/ MAX_ITER: "+MAX_ITER);
			System.out.println("Eval: "+solution);
		}
		
		// CEL: Wykonanie zadania 1.
		public static void Zad1()
		{
			System.out.println("----------- Zad. 1: --------");
			String path="src/assign100.txt";
			int dlugoscListyTabu=50;
			int kadencja=50;
			int MAX_ITER=1000;
			Test_TabuSearch(path, dlugoscListyTabu, kadencja, MAX_ITER);
			System.out.println("-----------------------------");
		}
		
		// CEL: Wykonanie zadania 1.
		public static void Zad1_Zmodyfikowane()
		{
			System.out.println("----------- Zad. 1 Zmodyfikowane: --------");
			String path="src/assign100.txt";
			int dlugoscListyTabu=50;
			int kadencja=50;
			int MAX_ITER=1000;
			Test_TabuSearch_Zmodyfikowane(path, dlugoscListyTabu, kadencja, MAX_ITER);
			System.out.println("-----------------------------");
		}
		
		// CEL: Wykonanie zadania 2.
		public static void Zad2()
		{
			System.out.println("----------- Zad. 2: --------");
			String path="src/assign100.txt";
			int dlugoscListyTabu=50;
			int kadencja=50;
			int MAX_ITER=1000;
			int numberOfTests=10;
			System.out.println("Dlugosc listy tabu: "+dlugoscListyTabu);
			System.out.println("Wartosc kadencji: "+kadencja);
			System.out.println("Ilosc iteracji/ MAX_ITER: "+MAX_ITER);
			double avg_solution=Avg_Eval(path, dlugoscListyTabu, kadencja, MAX_ITER, numberOfTests);
			System.out.println("Œrednia wartoœæ Eval: "+avg_solution);
			System.out.println("-----------------------------");
		}
		
		// CEL: Wykonanie zadania 3.
		public static void Zad3_4(String zad, int MAX_ITER)
		{
			System.out.println("----------- Zad. "+zad+": --------");
			System.out.println("--> MAX_ITER: "+MAX_ITER+" <--");
			String path="src/assign100.txt";
			int dlugoscListyTabu=50;
			int kadencja=50;
			System.out.println("------ A) ------]");
			dlugoscListyTabu=5000;
			kadencja=5000;
			Test_TabuSearch(path, dlugoscListyTabu, kadencja, MAX_ITER);
			System.out.println("------ B) -------]");
			dlugoscListyTabu=2000;
			kadencja=2000;
			Test_TabuSearch(path, dlugoscListyTabu, kadencja, MAX_ITER);
			System.out.println("------ C) --------]");
			dlugoscListyTabu=200;
			kadencja=200;
			Test_TabuSearch(path, dlugoscListyTabu, kadencja, MAX_ITER);
			System.out.println("------ D) --------]");
			dlugoscListyTabu=1000;
			kadencja=200;
			Test_TabuSearch(path, dlugoscListyTabu, kadencja, MAX_ITER);
			System.out.println("------ E) --------]");
			dlugoscListyTabu=200;
			kadencja=1000;
			Test_TabuSearch(path, dlugoscListyTabu, kadencja, MAX_ITER);
			System.out.println("-----------------------------");
		}
	}
	
	// CEL: Problem plecakowy.
	static class BackPackProblem
	{
		// CEL: Typ danych do przechowywania parametrów zadania.
		static class BackPackReader
		{
			int n_numberOfItems; // Liczba przedmiotów.
			int B_sizeOfBackpack; // Pojemnoœæ plecaka.
			
			double[]w_valuesOfItems; // Wartoœci przedmiotów ~ wi
			double[]s_sizeOfItems; // Rozmiary przedmiotów ~ si
			
			public BackPackReader(String path)
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
					System.out.println("Wczytywanie pliku nie powiod³o siê!");
				}
				
				// 3. Uzyskiwanie danych.
				String[]buff=sc.nextLine().split(" ");
				
				
				// 3.1 Uzyskanie ilosci elementów.
				n_numberOfItems = Integer.parseInt(buff[0]);
				
				// 3.2 Uzyskanie pojemnosci plecaka.
				B_sizeOfBackpack = Integer.parseInt(buff[1]);
				
				// 3.3 Utworzenie buforów.
				w_valuesOfItems=new double[n_numberOfItems];
				s_sizeOfItems=new double[n_numberOfItems];
				
				// 3.4 Wczytywanie wartosci do buforow.
				int i=0;
				while(sc.hasNextLine())
				{
					// 3.4.1 Podzielenie ci¹gu znaków.
					buff=sc.nextLine().split(" ");
					
					// 3.4.2 Konwertowanie wartoœci.
					try {
					w_valuesOfItems[i]=Double.parseDouble(buff[0]);
					s_sizeOfItems[i]=Double.parseDouble(buff[1]);
					}catch(Exception e)
					{
						continue;
					}
					// 3.4.3 Inkrementacja.
					i++;
				}
			}

			// WLASCIWOSCI:
			public int getN_numberOfItems() {
				return n_numberOfItems;
			}

			public int getB_sizeOfBackpack() {
				return B_sizeOfBackpack;
			}

			public double[] getW_valuesOfItems() {
				return w_valuesOfItems;
			}

			public double[] getS_sizeOfItems() {
				return s_sizeOfItems;
			}
		}
		
		// CEL: Utworzenie drzewa przeszukiwañ binarnych.
		static class BST
		{
			private Node head;
			
			public BST() {};
			
			public void Add(int key, double value)
			{
				if(head==null)
				{
					head=new Node(key, value ,null, null);
				}
				else
				{
					Add(key, value, head);
				}
			}
			
			public void Print()
			{
				Print(head);
			}

			private void Print(Node node)
			{
				if(node==null)
				{
					return;
				}
				
				if(node.right!=null)
				{
					Print(node.right);
				}
				
				theBest.add(node);
				
				if(node.left!=null)
				{
					Print(node.left);
				}
			}
			
			private void FillTheBest(Node node)
			{
				if(node==null)
				{
					return;
				}
				
				if(node.right!=null)
				{
					Print(node.right);
				}
				
				theBest.add(node);
				
				if(node.left!=null)
				{
					Print(node.left);
				}
			}
			
			Queue<Node> theBest = new LinkedList<Node>();
			boolean set=false;
			
			private double TakeTheBest(Node node)
			{
				if(!set)
				{
					set=true;
					FillTheBest(head);
				}
				return theBest.poll().value;
			}
			
			
			public double TakeTheBest()
			{
				return TakeTheBest(head);
			}
			
			private void Add(double key, double value, Node node)
			{
				if(node.getKey()>key)
				{
					if(node.getLeft()==null)
					{
						node.setLeft(new Node(key, value, null, null));
					}
					else
					{
						Add(key, value, node.left);
					}
				}
				else if(node.getKey()<key)
				{
					if(node.getRight()==null)
					{
						node.setRight(new Node(key, value, null, null));
					}
					else
					{
						Add(key, value, node.right);
					}
				}
			}
		}
		
		// CEL: Utworzenie wiêz³a.
		static class Node
		{
			private double key;
			private double value;
			private Node left;
			public void setRight(Node right) {
				this.right=right;
			}

			public void setLeft(Node left) {
				this.left = left;
			}
			private Node right;
			
			public Node(double key, double value, SA.BackPackProblem.Node left, SA.BackPackProblem.Node right) {
				super();
				this.key = key;
				this.value = value;
				this.left = left;
				this.right = right;
			}

			public double getKey() {
				return key;
			}
			public double getValue() {
				return value;
			}
			public Node getLeft() {
				return left;
			}
			public Node getRight() {
				return right;
			}
			
		}
		
		// CEL: Obiekt ruchu.
		static class Ruch
		{
			int taskRemove;
			int taskTaken;
			
			public Ruch(int taskRemove, int taskTaken)
			{
				this.taskTaken=taskTaken;
				this.taskRemove=taskRemove;
			}
			
			public int getTaskRemove()
			{
				return taskRemove;
			}

			public int getTaskTaken()
			{
				return taskTaken;
			}
		}
		
		// CEL: Obiekt, który przechowuje rozwi¹zanie oraz ruch.
		static class Tuple
		{
			int[]solution;
			Ruch ruch;
			
			public Tuple(int[] solution, SA.BackPackProblem.Ruch ruch)
			{
				super();
				this.solution = solution;
				this.ruch = ruch;
			}

			public int[] getSolution()
			{
				return solution;
			}
			
			public Ruch getRuch()
			{
				return ruch;
			}
		}
		
		// CEL: Sprawdzenie czy lista zawiera dany ruch.
		public static boolean ContainsTabu(Queue<Ruch> tabu, Ruch first)
		{
			for(Ruch second: tabu)
			{
				if(first.getTaskRemove() == second.getTaskRemove())
				{
					if(first.getTaskTaken() == first.getTaskTaken())
					{
						return true;
					}
				}
				if(first.getTaskRemove()==second.getTaskTaken())
				{
					if(first.getTaskTaken()==second.getTaskRemove())
					{
						return true;
					}
				}
			}
			return false;
		}
		
		// CEL: Obliczanie funkcji przystosowania.
		public static int Eval(int[] solution, BackPackReader rd)
		{
			// 1. Utworzenie zmiennej do przechowywania sumy.
			int sum=0;
			
			// 2. Utworzenie zmiennej do rpzechowywania obecnej.
			int currentWeight=0;
			
			// 3. Obliczenie wartosci sumy.
			for(int i=0;i<rd.getN_numberOfItems();i++)
			{
				// 3.1 Dodawanie elementu do sumy.
				sum+=solution[i]*rd.getW_valuesOfItems()[i];
				
				// 3.2 Dodanie wagi do obecnej sumy wag.
				currentWeight+=solution[i]*rd.getS_sizeOfItems()[i];
				
				// 3.3 Sprawdz
				if(currentWeight>rd.getB_sizeOfBackpack())
				{
					//System.out.println(sum);
					return -1;
				}
			}
			
			// 4. Zwrocenie wyniku.
			return sum;
		}
		
		// CEL: Drukowanie bufora.
		public static void PrintBuffer(int[]buff)
		{
			for(int i=0; i<buff.length;i++)
			{
				System.out.print(buff[i]+" ");
			}
		}
		
		// CEL: Drukowanie rozwi¹znia.
		public static void PrintSolution(int[]solution, BackPackReader rd)
		{
			System.out.println("-----------------------------------------]");
			System.out.println("Rozmiar plecaka: "+rd.getB_sizeOfBackpack());
			System.out.println("Ilosc przedmiotow: "+rd.getN_numberOfItems());
			System.out.print("Wektor rozwi¹zania: ");
			PrintBuffer(solution);
			System.out.println();
			System.out.println("Wartoœæ funkcji przystosowania: "+Eval(solution, rd));
			System.out.println("-----------------------------------------]");
		}
		
		// CEL: Dobór rozwi¹zania pocz¹tkowego w sposób losowy - WARIANT 1.
		public static int[] wybierz_rozwiazanie_poczatkowe_Wariant_1(BackPackReader rd)
		{
			// 1. Utworzenie pustego rozwi¹zania.
			int[] solution = new int [rd.getN_numberOfItems()];
			int wage=0;
			
			// 2. Wype³nianie rozwi¹zania.
			for(int i=0; i<rd.getN_numberOfItems(); i++)
			{
				solution[i]=(int)Math.round(Math.random());
				
				wage+=rd.getS_sizeOfItems()[i];
				
				//System.out.println("Size: "+rd.getB_sizeOfBackpack());
				
				if(wage>rd.getB_sizeOfBackpack())
				{
					//System.out.println("Wage: "+wage+", BCK: "+rd.getB_sizeOfBackpack());
					//PrintBuffer(solution);

					solution[i]=0;
					return solution;
				}
			}
			
			//PrintBuffer(solution);
			
			// 3. Zwrocenie rozwiaznia.
			return solution;
		}
		
		// CEL: Dobór rozwi¹zania pocz¹tkowego w sposób wi/si - WARIANT 2.
		public static int[] wybierz_rozwiazanie_poczatkowe_Wariant_2(BackPackReader rd)
		{
			// 1. Utworzenie hashMapy ~ wi/si.
			BST rank = new BST();
			
			for(int i=0; i<rd.getN_numberOfItems();i++)
			{
				double value = rd.getW_valuesOfItems()[i]/rd.getS_sizeOfItems()[i];
				rank.Add(i, value);
			}
			
			// 2. Uzyskanie najlepszych.
			double waga=0;
			int[]solution=new int[rd.getN_numberOfItems()];
			while(true)
			{
				int index= (int)rank.TakeTheBest();
				//System.out.println(index);
				try {
				if(waga+rd.getS_sizeOfItems()[index]<=rd.getB_sizeOfBackpack())
				{
					solution[index]=1;
					waga+=rd.getS_sizeOfItems()[index];
				}
				else
				{
					break;
				}
				}catch(Exception e)
				{
					break;
				}
			}
			
			//System.out.println(Eval(solution, rd));
			
			// 3. Zwrocenie wyniku.
			return solution;
		}
		
		// CEL: Dobór rozwi¹zania pocz¹tkowego w sposób losowy - WARIANT 3.
		public static int[] wybierz_rozwiazanie_poczatkowe_Wariant_3(BackPackReader rd)
		{
			return wybierz_rozwiazanie_poczatkowe_Wariant_1(rd);
		}
		
		// CEL: Dobór rozwi¹zania pocz¹tkowego w sposób wi/si - WARIANT 4.
		public static int[] wybierz_rozwiazanie_poczatkowe_Wariant_4(BackPackReader rd)
		{
			return wybierz_rozwiazanie_poczatkowe_Wariant_2(rd);
		}
		
		// CEL: Wybranie rozwi¹zania z otoczenia - WARIANT 1.
		public static Tuple wybierz_rozwiazanie_z_otoczenia_Wariant_1(int[]solution, BackPackReader rd)
		{
			// 1. Utworzenie tymczasowego rozwiazania.
			int[]res=new int[rd.getN_numberOfItems()];
			
			// 2. Przepisanie elementów z rozwi¹zania do nowego rozwiazania.
			for(int i=0;i<solution.length; i++)
			{
				res[i]=solution[i];
			}
			
			//PrintBuffer(res);
			
			// 3. Stosowanie operatorów.
			Random r=new Random();
			
			// 3.1 Usuniecie losowego przedmiotu z plecaka.
			int index= r.nextInt(res.length);
			res[index]=0;
			
			// 3.2 Szukanie lepszego rozwiaznia.
			int prevEval = Eval(res, rd);
			int taken=0;
			double wage=0;
			for(int i=0; i<res.length;i++)
			{
				wage+=res[i]*rd.getS_sizeOfItems()[i];
				if(wage<=rd.getB_sizeOfBackpack())
				{
					res[i]=1;
					int currentEval = Eval(res, rd);
					if(currentEval>prevEval)
					{
						prevEval=currentEval;
						taken=i;
					}
					else
					{
						res[i]=0;
					}
				}
			}
			
			//System.out.println("");
			//PrintBuffer(res);
			
			//System.out.println(Eval(res, rd));
			
			return new Tuple(res, new Ruch(index, taken));
		}
		
		// CEL: Wybranie rozwi¹zania z otoczenia - WARIANT 2.
		public static Tuple wybierz_rozwiazanie_z_otoczenia_Wariant_2(int[]solution, BackPackReader rd)
		{
			return wybierz_rozwiazanie_z_otoczenia_Wariant_1(solution, rd);
		}
		
		// CEL: Wybranie rozwi¹zania z otoczenia - WARIANT 3.
		public static Tuple wybierz_rozwiazanie_z_otoczenia_Wariant_3(int[]solution, BackPackReader rd)
		{
			// 0. Utworznie obiektu do generowania liczb pseudolosowych.
			Random rand=new Random();
			
			// 1. Utworzenie tymczasowego rozwiazania.
			int[]res=new int[rd.getN_numberOfItems()];
			for(int i=0;i<rd.getN_numberOfItems();i++)
			{
				res[i]=solution[i];
			}
			
			// 2. Wygenerowanie dwóch pozycji.
			int first=rand.nextInt(rd.getN_numberOfItems());
			int second=rand.nextInt(rd.getN_numberOfItems());
			
			// 3. Zamiana dwóch elementów -> SWAP.
			int tmp=res[first];
			res[first]=res[second];
			res[second]=tmp;
			
			// 4. Walidacja rozwiazania oraz jego zwrocenie.
			int wage=0;
			for(int i=0;i<rd.getN_numberOfItems();i++)
			{
				wage+=rd.getS_sizeOfItems()[i];
			}
			if(wage<rd.getB_sizeOfBackpack())
			{
				return new Tuple(res, new Ruch(first, second));
			}
			return new Tuple(solution, new Ruch(first, second));
		}
		
		// CEL: Wybranie rozwi¹zania z otoczenia - WARIANT 3.
		public static Tuple wybierz_rozwiazanie_z_otoczenia_Wariant_4(int[]solution, BackPackReader rd)
		{
			// 1. Utworzenie obiektu do generowanie liczb pseudolosowych.
			Random rand = new Random();
			
			// 2. Przepisanie wartosci obecnego rozwiazania do bufora.
			int[]res=new int[rd.getN_numberOfItems()];
			for(int i=0;i<rd.getN_numberOfItems();i++)
			{
				res[i]=solution[i];
			}
			
			// 3. Generowanie indeksu przedmiotu z rozwiazania.
			int index=rand.nextInt(rd.getN_numberOfItems());
			
			// 4. Generowanie przesuniecia.
			int offset = rand.nextInt(Math.abs(rd.getN_numberOfItems()-index));
			
			// 5. Zamiania elementów.
			int tmp=res[index];
			res[index]=res[index+offset];
			res[index+offset]=tmp;
			
			// 6. Walidacja rozwiazania oraz jego zwrocenie.
			double wage=0;
			for(int i=0;i<rd.getN_numberOfItems();i++)
			{
				double cW=res[i]*rd.getS_sizeOfItems()[i];
				wage+=cW;
				if(wage>rd.getB_sizeOfBackpack())
				{
					return new Tuple(solution, new Ruch(index, index+offset));
				}
			}
			return new Tuple(res, new Ruch(index, index+offset));
		}
		
		// CEL: Wykonanie algorytmu przeszukiwania TABU.
		public static int[] TabuSearch(int idWariantu, BackPackReader rd, int MAX_DLUGOSC_LISTY, int MAX_KADENCJA, int MAX_ITER)
		{
			// 1. Utworzenie listy tabu.
			Queue<Ruch> tabuList = new LinkedList<Ruch>();
			
			// 2. Wybieranie rozwi¹zania pocz¹tkowego.
			int[]Vc=null;
			
			switch(idWariantu)
			{
				case 1:
					Vc=wybierz_rozwiazanie_poczatkowe_Wariant_1(rd);
				break;
				
				case 2:
					Vc=wybierz_rozwiazanie_poczatkowe_Wariant_2(rd);
				break;
				
				case 3:
					Vc=wybierz_rozwiazanie_poczatkowe_Wariant_3(rd);
				break;
				
				case 4:
					Vc=wybierz_rozwiazanie_poczatkowe_Wariant_4(rd);
				break;
			}
			
			// 3. Najlepsze rozwi¹zanie.
			int[]best = Vc;
			int bestEval=Eval(Vc, rd);
			
			int obecnaKad=0;
			// 4. Pêtla.
			for(int i=0; i<MAX_ITER; i++, obecnaKad++)
			{
				// 4.1 Sprawdzanie kadencji.
				if(obecnaKad>=MAX_KADENCJA)
				{
					tabuList.poll();
					obecnaKad=0;
				}
				
				// 4.2 Usuwanie najstarszego jezeli dlugosc listy zostala przekroczona.
				if(tabuList.size()>MAX_DLUGOSC_LISTY)
				{
					tabuList.poll();
				}
				
				// 4.3 Wybranie rozwi¹zania z otoczenia.
				Tuple rozw = null;
				switch(idWariantu)
				{
					case 1:
						rozw=wybierz_rozwiazanie_z_otoczenia_Wariant_1(Vc, rd);
					break;
					
					case 2:
						rozw=wybierz_rozwiazanie_z_otoczenia_Wariant_2(Vc, rd);
					break;
					
					case 3:
						rozw=wybierz_rozwiazanie_z_otoczenia_Wariant_3(Vc, rd);
					break;
						
					case 4:
						rozw=wybierz_rozwiazanie_z_otoczenia_Wariant_4(Vc, rd);
					break;
				}
				
				// 4.4 Zapisanie rozwiaznia.
				int[]Vn = rozw.getSolution();
				
				//PrintBuffer(rozw.getSolution());
				
				// 4.5 Obliczenie obecnego rozwiania.
				int currentEval=Eval(Vn, rd);
				
				//System.out.println("Current eval: "+currentEval);
				
				// 4.6 Sprawdzenie czy taki ruch jest na liscie tabu.
				boolean contains = ContainsTabu(tabuList, rozw.getRuch());
				
				// 4.7 Jezeli taki ruch jest.
				if(contains)
				{
					// 4.8 Sprawdz czy daje nam lepszy wynik.
					if(currentEval>bestEval)
					{
						// 4.9 Ustaw nowego najlepszego
						bestEval=currentEval;
						best=Vn;
						Vc=Vn;
					}
					// 4.8 Jezeli nie to nie bierz takiego rozwiazania pod uwage.
					else
					{
						continue;
					}
				}
				// 4.7 Jezel nie ma takiego.
				else
				{
					// 4.8 Porownaj czy daje lepsze rezultaty.
					if(currentEval>bestEval)
					{
						// 4.8.1 Zapisz nowy najleszpy najlepszy.
						bestEval=currentEval;
						best=Vn;
					}
					
					// 4.10 Zapisz rozwi¹zanie.
					Vc=Vn;
					
					// 4.11 Dodaj do listy.
					tabuList.add(rozw.getRuch());
				}
				
			}
			
			// 5. Zwrocenie najlepszego wyniku.
			return best;
		}
		
		// CEL: Wykonanie wariantu oraz zapisanie ich wyników do wydzielonego pliku.
		public static void Wykonaj_Wariant(int idWariantu, String src, String dest, String err, double optim, int MAX_DLUGOSC_LISTY, int MAX_KADENCJA, int MAX_ITER) throws IOException
		{
			// 1. Utworzenie obiektu do wczytywania.
			BackPackReader rd = new BackPackReader(src);
			
			String[]buff_src=src.split("/");
			String directories_src=buff_src[0]+"/"+buff_src[1]+"/"+buff_src[2];
			
			System.out.println("Sciezka: "+src);
			
			String[]buff_dest=dest.split("/");
			String directories_dest=buff_dest[0]+"/"+buff_dest[1]+"/"+buff_dest[2];
			
			String[]buff_err=err.split("/");
			String directories_err=buff_err[0]+"/"+buff_err[1]+"/"+buff_err[2];
			
			File theDir = new File(directories_src);
			if (!theDir.exists()){
				theDir.setWritable(true);
			    theDir.setReadable(true);
				theDir.setExecutable(true);
			    theDir.mkdirs();
			}
			theDir=new File(directories_dest);
			if (!theDir.exists()){
				theDir.setWritable(true);
			    theDir.setReadable(true);
				theDir.setExecutable(true);
			    theDir.mkdirs();
			}
			theDir=new File(directories_err);
			if (!theDir.exists()){
			    theDir.setReadable(true);
				theDir.setWritable(true);
				theDir.setExecutable(true);
			    theDir.mkdirs();
			}
			theDir=null;
			
			// 2. Utworzenie zapisownika.
			FileWriter writer=null;
			writer=new FileWriter(dest);
			FileWriter writer_e=null;
			writer_e=new FileWriter(err);
	
			// 3. Wykonywanie algorytmu.
			double avg=0, avg_e=0;
			int m=10;
			for(int i=0;i<m;i++)
			{
				int[]res=null;
				switch(idWariantu)
				{
				case 1:
					res=TabuSearch(1,rd, MAX_DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
					break;
				case 2:
					res=TabuSearch(2,rd, MAX_DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
					break;
				case 3:
					res=TabuSearch(3,rd, MAX_DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
					break;
				case 4:
					res=TabuSearch(4,rd, MAX_DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
					break;
				}
				int ev=Eval(res, rd);
				String _ev=ev+"";
				_ev=_ev.replace('.', ',');
				writer.append(_ev+"\n");
				double value=Math.abs(optim-ev)/ev;
				String _value=value*100.0f+"";
				_value=_value.replace('.', ',');
				writer_e.append(_value+"\n");
				avg_e+=value;
				avg+=ev;
			}
			avg/=m;
			avg_e/=m;
			String _avg=avg+"";
			_avg=_avg.replace('.', ',');
			writer.append(_avg+"\n");
			String _avg_e=avg_e*100.0f+"";
			_avg_e=_avg_e.replace('.', ',');
			writer_e.append(_avg_e+"\n");
			
			// 4. Zamkniecie zapisowanika.
			writer.close();
			writer_e.close();
		}
		
		// CEL: Wykonanie okreslonych wariantow oraz zapisanie ich do wydzielonych plików w okreœlonych podkatologach.
		public static void Wykonaj_Male()
		{
			// 1. Pochodzenia plikow.
			String[]src= {
					"src/Male_Dane/f1_l-d_kp_10_269", "src/Male_Dane/f2_l-d_kp_20_878", "src/Male_Dane/f3_l-d_kp_4_20", "src/Male_Dane/f4_l-d_kp_4_11", "src/Male_Dane/f5_l-d_kp_15_375", "src/Male_Dane/f6_l-d_kp_10_60", "src/Male_Dane/f7_l-d_kp_7_50", "src/Male_Dane/f8_l-d_kp_23_10000", "src/Male_Dane/f9_l-d_kp_5_80", "src/Male_Dane/f10_l-d_kp_20_879",	
				};
			
			// 2. Tworzenie plikow
			int idWariantu=1;
			int max=4;
			while(idWariantu<=max)
			{
				String katalog=null;
				switch(idWariantu)
				{
					case 1:
						katalog="Male_Wariant_1";
						break;
					case 2:
						katalog="Male_Wariant_2";
						break;
					case 3:
						katalog="Male_Wariant_3";
						break;
					case 4:
						katalog="Male_Wariant_4";
						break;
				}
	
				// 2. Cele plikow.
				String[]dest= {
						"src/"+katalog+"/Funkcja_Celu/EVAL-f1_l-d_kp_10_269.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f2_l-d_kp_20_878.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f3_l-d_kp_4_20.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f4_l-d_kp_4_11.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f5_l-d_kp_15_375.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f6_l-d_kp_10_60.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f7_l-d_kp_7_50.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f8_l-d_kp_23_10000.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f9_l-d_kp_5_80.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-f10_l-d_kp_20_879.txt",	
				};
				String[]err= {
						"src/"+katalog+"/Blad/ERROR-f1_l-d_kp_10_269.txt", "src/"+katalog+"/Blad/ERROR-f2_l-d_kp_20_878.txt", "src/"+katalog+"/Blad/ERROR-f3_l-d_kp_4_20.txt", "src/"+katalog+"/Blad/ERROR-f4_l-d_kp_4_11.txt", "src/"+katalog+"/Blad/ERROR-f5_l-d_kp_15_375.txt", "src/"+katalog+"/Blad/ERROR-f6_l-d_kp_10_60.txt", "src/"+katalog+"/Blad/ERROR-f7_l-d_kp_7_50.txt", "src/"+katalog+"/Blad/ERROR-f8_l-d_kp_23_10000.txt", "src/"+katalog+"/Blad/ERROR-f9_l-d_kp_5_80.txt", "src/"+katalog+"/Blad/ERROR-f10_l-d_kp_20_879.txt",	
				};
				
				// 3. Najlepsze wartosci.
				double[]best_wariant= {
					295, 1024, 35, 23, 481.0694, 52, 107, 9767, 130, 1025	
				};
				// 3. Parametry.
				int DLUGOSC_LISTY=50;
				int MAX_KADENCJA=50;
				int MAX_ITER=10000;
				
				// 4. Wykonanie algorytmu.
				for(int i=0;i<src.length;i++)
				{
					try {
						switch(idWariantu)
						{
						case 1:
							Wykonaj_Wariant(idWariantu,src[i], dest[i], err[i], best_wariant[i], DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
							break;
						case 2:
							Wykonaj_Wariant(idWariantu, src[i], dest[i], err[i], best_wariant[i], DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
							break;
						case 3:
							Wykonaj_Wariant(idWariantu, src[i], dest[i], err[i], best_wariant[i], DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
							break;
						case 4:
							Wykonaj_Wariant(idWariantu, src[i], dest[i], err[i], best_wariant[i], DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
							break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				idWariantu++;
			}
			System.out.println("Wyniki dla malych danych zosta³y utworzone!");
		}
		
		// CEL: Wykonanie okreslonych wariantow oraz zapisanie ich do wydzielonych plików w okreœlonych podkatologach.
		public static void Wykonaj_Duze()
		{
			// 1. Pochodzenia plikow.
			String[]src= {
					"src/Duze_Dane/knapPI_1_100_1000_1", "src/Duze_Dane/knapPI_1_200_1000_1", "src/Duze_Dane/knapPI_1_500_1000_1", "src/Duze_Dane/knapPI_1_1000_1000_1", "src/Duze_Dane/knapPI_1_2000_1000_1", "src/Duze_Dane/knapPI_1_5000_1000_1", "src/Duze_Dane/knapPI_1_10000_1000_1"	
				};
			
			// 2. Tworzenie plikow
			int idWariantu=1;
			int max=4;
			while(idWariantu<=max)
			{
				String katalog=null;
				switch(idWariantu)
				{
					case 1:
						katalog="Duze_Wariant_1";
						break;
					case 2:
						katalog="Duze_Wariant_2";
						break;
					case 3:
						katalog="Duze_Wariant_3";
						break;
					case 4:
						katalog="Duze_Wariant_4";
						break;
				}
	
				// 2. Cele plikow.
				String[]dest= {
						"src/"+katalog+"/Funkcja_Celu/EVAL-knapPI_1_100_1000_1.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-knapPI_1_200_1000_1.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-knapPI_1_500_1000_1.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-knapPI_1_1000_1000_1.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-knapPI_1_2000_1000_1.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-knapPI_1_5000_1000_1.txt", "src/"+katalog+"/Funkcja_Celu/EVAL-knapPI_1_10000_1000_1.txt"
				};
				String[]err= {
						"src/"+katalog+"/Blad/ERROR-knapPI_1_100_1000_1.txt", "src/"+katalog+"/Blad/ERROR-knapPI_1_200_1000_1.txt", "src/"+katalog+"/Blad/ERROR-knapPI_1_500_1000_1.txt", "src/"+katalog+"/Blad/ERROR-knapPI_1_1000_1000_1.txt", "src/"+katalog+"/Blad/ERROR-knapPI_1_2000_1000_1.txt", "src/"+katalog+"/Blad/ERROR-knapPI_1_5000_1000_1.txt", "src/"+katalog+"/Blad/ERROR-f7_l-d_kp_7_50.txt", "src/"+katalog+"/Blad/ERROR-knapPI_1_10000_1000_1.txt"	
				};
				
				// 3. Najlepsze wartosci.
				double[]best_wariant= {
					295, 1024, 35, 23, 481.0694, 52, 107, 9767, 130, 1025	
				};
				// 3. Parametry.
				int DLUGOSC_LISTY=50;
				int MAX_KADENCJA=50;
				int MAX_ITER=10000;
				
				// 4. Wykonanie algorytmu.
				for(int i=0;i<src.length;i++)
				{
					try {
						switch(idWariantu)
						{
						case 1:
							Wykonaj_Wariant(idWariantu,src[i], dest[i], err[i], best_wariant[i], DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
							break;
						case 2:
							Wykonaj_Wariant(idWariantu, src[i], dest[i], err[i], best_wariant[i], DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
							break;
						case 3:
							Wykonaj_Wariant(idWariantu, src[i], dest[i], err[i], best_wariant[i], DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
							break;
						case 4:
							Wykonaj_Wariant(idWariantu, src[i], dest[i], err[i], best_wariant[i], DLUGOSC_LISTY, MAX_KADENCJA, MAX_ITER);
							break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				idWariantu++;
			}
			System.out.println("Wyniki dla duzych danych zosta³y utworzone!");
		}
		
		// CEL: Testowanie.
		public static void main(String[]args)
		{
			Wykonaj_Male();
			Wykonaj_Duze();
		}
	}
}

// Autor: Juliusz Losinski 46155
// Data: 14.12.2021
