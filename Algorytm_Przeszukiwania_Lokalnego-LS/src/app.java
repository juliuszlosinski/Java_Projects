
public class app 
{
	// CEL: Testowanie algorytmu przeszukiwania lokalnego wariant 1.
	public static double Test_LocalSearch_1(LS.DataReader rd, LS.Local_Search.WRP modeStart, LS.Local_Search.WRZO modeNeighbor, double bestDistance)
	{
		// 1. Rozwiazywanie problemu przy pomocy Algorytmu Przeszukiwania Lokalnego.
		// 2.1. Uzyskanie sciezki.
		int[]route=LS.Local_Search.LocalSearch_1(modeStart, modeNeighbor, rd, 1000);
				
		// 2.2. Uzyskanie dlugosci sciezki.
		double distance=LS.CalcRoute(route, rd.GetCoords());
		
		// 3. Wypisanie wyniku.
		/*
		System.out.println("--------- ALGORYTM PRZESZUKIWANIA LOKALNEGO nr. 1 ------------");
		TS_Problem.PrintRouteWithInfo(route,rd.GetCoords());
		System.out.println("Worst by: "+(float)100*(distance-bestDistance)/bestDistance+" %");
		String beginMode="";
		String neighborMode="";
		switch(modeStart)
		{
		case Order:
				beginMode="Order";
			break;
		case Random:
				beginMode="Random";
			break;
		}
		switch(modeNeighbor)
		{
		case FirstBetter:
			neighborMode="First Better";
			break;
		case TheBest:
			neighborMode="The Best";
			break;
		}
		System.out.println("Begin solution: "+beginMode);
		System.out.println("Neighbor mode: "+neighborMode);
		System.out.println("------------------------------------------------------------\n");
		*/
		return distance;
	}
	
	// CEL: Testowanie algorytmu przeszukiwania lokalnego wariant 1 z wykorzystaniem parametryzowalnej populacji pocz¹tkowej.
	public static double Test_LocalSearch_1(LS.DataReader rd, LS.Local_Search.WRP modeStart, LS.Local_Search.WRZO modeNeighbor, double bestDistance, int[]Vc)
	{
		// 1. Rozwiazywanie problemu przy pomocy Algorytmu Przeszukiwania Lokalnego.
		// 2.1. Uzyskanie sciezki.
		int[]route=LS.Local_Search.LocalSearch_1(modeStart, modeNeighbor, rd, Vc,1000);
				
		// 2.2. Uzyskanie dlugosci sciezki.
		double distance=LS.CalcRoute(route, rd.GetCoords());
		
		// 3. Wypisanie wyniku.
		/*
		System.out.println("--------- ALGORYTM PRZESZUKIWANIA LOKALNEGO nr. 1 ------------");
		TS_Problem.PrintRouteWithInfo(route,rd.GetCoords());
		System.out.println("Worst by: "+(float)100*(distance-bestDistance)/bestDistance+" %");
		String beginMode="";
		String neighborMode="";
		switch(modeStart)
		{
		case Order:
				beginMode="Order";
			break;
		case Random:
				beginMode="Random";
			break;
		}
		switch(modeNeighbor)
		{
		case FirstBetter:
			neighborMode="First Better";
			break;
		case TheBest:
			neighborMode="The Best";
			break;
		}
		System.out.println("Begin solution: "+beginMode);
		System.out.println("Neighbor mode: "+neighborMode);
		System.out.println("------------------------------------------------------------\n");
		*/
		return distance;
	}
	
	// CEL: Testowanie algorytmu przeszukiwania lokalnego wariant 2.
	public static double Test_LocalSearch_2(LS.DataReader rd, LS.Local_Search.WRP modeStart, LS.Local_Search.WRZO modeNeighbor, double bestDistance)
	{
		// 1. Rozwiazywanie problemu przy pomocy Algorytmu Przeszukiwania Lokalnego.
		// 2.1. Uzyskanie sciezki.
		int[]route=LS.Local_Search.LocalSearch_2(modeStart, modeNeighbor, rd, 100, 1000);
				
		// 2.2. Uzyskanie dlugosci sciezki.
		double distance=LS.CalcRoute(route, rd.GetCoords());
		
		// 3. Wypisanie wyniku.
		/*
		System.out.println("--------- ALGORYTM PRZESZUKIWANIA LOKALNEGO nr. 2 ------------");
		TS_Problem.PrintRouteWithInfo(route,rd.GetCoords());
		System.out.println("Worst by: "+(float)100*(distance-bestDistance)/bestDistance+" %");
		String beginMode="";
		String neighborMode="";
		switch(modeStart)
		{
		case Order:
				beginMode="Order";
			break;
		case Random:
				beginMode="Random";
			break;
		}
		switch(modeNeighbor)
		{
		case FirstBetter:
			neighborMode="First Better";
			break;
		case TheBest:
			neighborMode="The Best";
			break;
		}
		System.out.println("Begin solution: "+beginMode);
		System.out.println("Neighbor mode: "+neighborMode);
		System.out.println("------------------------------------------------------------\n");
		*/
		
		// 4. Zwrocenie wyniku.
		return distance;
	}
	
	// CEL: Testowanie algorytmu przsszukiwania lokalnego wariant 2 z wykorzystaniem parametryzowanlnej populacji poczatkowej.
	public static double Test_LocalSearch_2(LS.DataReader rd, LS.Local_Search.WRP modeStart, LS.Local_Search.WRZO modeNeighbor, double bestDistance, int[]Vc)
	{
		// 1. Rozwiazywanie problemu przy pomocy Algorytmu Przeszukiwania Lokalnego.
		// 2.1. Uzyskanie sciezki.
		int[]route=LS.Local_Search.LocalSearch_2(modeStart, modeNeighbor, rd, 100, 1000, Vc);
				
		// 2.2. Uzyskanie dlugosci sciezki.
		double distance=LS.CalcRoute(route, rd.GetCoords());
		
		/*
		// 3. Wypisanie wyniku.
		System.out.println("--------- ALGORYTM PRZESZUKIWANIA LOKALNEGO nr. 2 ------------");
		TS_Problem.PrintRouteWithInfo(route,rd.GetCoords());
		System.out.println("Worst by: "+(float)100*(distance-bestDistance)/bestDistance+" %");
		String beginMode="";
		String neighborMode="";
		switch(modeStart)
		{
		case Order:
				beginMode="Order";
			break;
		case Random:
				beginMode="Random";
			break;
		}
		switch(modeNeighbor)
		{
		case FirstBetter:
			neighborMode="First Better";
			break;
		case TheBest:
			neighborMode="The Best";
			break;
		}
		System.out.println("Begin solution: "+beginMode);
		System.out.println("Neighbor mode: "+neighborMode);
		System.out.println("------------------------------------------------------------\n");
		*/
		
		// 4. Zwrocenie wyniku.
		return distance;
	}
	
	/// CEL: Wykorzystanie algorytmu zach³annego.
	public static void GreedySearch_BestNeighbor(LS.DataReader rd, double bestDistance)
	{
		// 1. Uzyskanie sciezki.
		int[]route=LS.GreedySearch_BestNeighbor.TheBestNeighbor(rd);
				
		// 2.	Uzyskan dlugosci sciezki.
		double distance=LS.CalcRoute(route, rd.GetCoords());
		
		// 3. Wypisanie wyniku z przeszukiwania zach³annego.
		System.out.println("----------- ALGORYTM PRZESZUKIWANIA ZACHLANNEGO ------------");
		LS.PrintRouteWithInfo(route, rd.GetCoords());
		System.out.println("Worst by: "+(float)100*(distance-bestDistance)/bestDistance+" %");
		System.out.println("------------------------------------------------------------");
	}
	
	public static void LocalSearch_1(LS.DataReader rd, double bestDistance, int times, int[]greedySolution)
	{
		// Algorytm Przeszukiwanie lokalne 1:
		// LP: 1
		// 1. Rozwiazanie poczatkowe: Trasa 0-1-2-...
		// 2. Rozwiazanie z s¹siedztwa: Pierwsze uzyskane rozwi¹zanie lepsze od Vc.
		System.out.println("=============== Local Search nr. 1 ===============]");
		System.out.println("--------------------------]");
		System.out.println("LP: 1");
		double sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_1(rd,LS.Local_Search.WRP.Order,LS.Local_Search.WRZO.FirstBetter,bestDistance);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 2
		// 1. Rozwiazanie poczatkowe: Trasa 0-1-2-...
		// 2. Rozwiazanie z s¹siedztwa: Najlepsze rozwiazanie sposrod wszystkich s¹siadów Vc.
		System.out.println("--------------------------]");
		System.out.println("LP: 2");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_1(rd,LS.Local_Search.WRP.Order,LS.Local_Search.WRZO.TheBest,bestDistance);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 3
		// 1. Rozwiazanie poczatkowe: Trasa wygenerowana losowo.
		// 2. Rozwiazanie z s¹siedztwa: Pierwsze uzyskane rozwi¹zanie lepsze od Vc.
		System.out.println("--------------------------]");
		System.out.println("LP: 3");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_1(rd,LS.Local_Search.WRP.Random,LS.Local_Search.WRZO.FirstBetter,bestDistance);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 4
		// 1. Rozwiazanie poczatkowe: Trasa wygenerowana losowo.
		// 2. Rozwiazanie z s¹siedztwa: Najlepsze rozwiazanie sposrod wszystkich s¹siadów Vc.
		System.out.println("--------------------------]");
		System.out.println("LP: 4");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_1(rd,LS.Local_Search.WRP.Random,LS.Local_Search.WRZO.TheBest,bestDistance);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 5
		// 1. Rozwiazanie poczatkowe: Trasa powsta³a poprzez algorytm konkstrukcyjny najbli¿szego s¹siada.
		// 2. Rozwi¹zanie z s¹siedztwa: Pierwsze uzyskane rozwi¹zanie lepsze od Vc.
		int[]greedySolution_first=new int[greedySolution.length];
		int[]greedySolution_second=new int[greedySolution_first.length];
		for(int i=0;i<greedySolution.length;i++)
		{
			greedySolution_first[i]=greedySolution[i];
			greedySolution_second[i]=greedySolution_first[i];
		}
		System.out.println("--------------------------]");
		System.out.println("LP: 5");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_1(rd,LS.Local_Search.WRP.Random,LS.Local_Search.WRZO.FirstBetter,bestDistance, greedySolution_first);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 6
		// 1. Rozwiazanie poczatkowe: Trasa powsta³a poprzez algorytm konkstrukcyjny najbli¿szego s¹siada.
		// 2. Rozwi¹zanie z s¹siêdztwa: Najlepsze rozwi¹zanie spoœród wszystkich s¹siadów,
		System.out.println("--------------------------]");
		System.out.println("LP: 6");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_1(rd,LS.Local_Search.WRP.Random,LS.Local_Search.WRZO.TheBest,bestDistance, greedySolution_second);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		System.out.println("==================================================]");
	}
	
	public static void LocalSearch_2(LS.DataReader rd, double bestDistance, int times, int[]greedySolution)
	{
		// Algorytm Przeszukiwanie lokalne 1:
		// LP: 1
		// 1. Rozwiazanie poczatkowe: Trasa 0-1-2-...
		// 2. Rozwiazanie z s¹siedztwa: Pierwsze uzyskane rozwi¹zanie lepsze od Vc.
		System.out.println("\n>>>>>>>>>> Local Search nr. 2  <<<<<<<<<<");
		System.out.println("--------------------------]");
		System.out.println("LP: 1");
		double sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_2(rd,LS.Local_Search.WRP.Order,LS.Local_Search.WRZO.FirstBetter,bestDistance);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 2
		// 1. Rozwiazanie poczatkowe: Trasa 0-1-2-...
		// 2. Rozwiazanie z s¹siedztwa: Najlepsze rozwiazanie sposrod wszystkich s¹siadów Vc.
		System.out.println("--------------------------]");
		System.out.println("LP: 2");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_2(rd,LS.Local_Search.WRP.Order,LS.Local_Search.WRZO.TheBest,bestDistance);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 3
		// 1. Rozwiazanie poczatkowe: Trasa wygenerowana losowo.
		// 2. Rozwiazanie z s¹siedztwa: Pierwsze uzyskane rozwi¹zanie lepsze od Vc.
		System.out.println("--------------------------]");
		System.out.println("LP: 3");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_2(rd,LS.Local_Search.WRP.Random,LS.Local_Search.WRZO.FirstBetter,bestDistance);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 4
		// 1. Rozwiazanie poczatkowe: Trasa wygenerowana losowo.
		// 2. Rozwiazanie z s¹siedztwa: Najlepsze rozwiazanie sposrod wszystkich s¹siadów Vc.
		System.out.println("--------------------------]");
		System.out.println("LP: 4");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_2(rd,LS.Local_Search.WRP.Random,LS.Local_Search.WRZO.TheBest,bestDistance);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 5
		// 1. Rozwiazanie poczatkowe: Trasa powsta³a poprzez algorytm konkstrukcyjny najbli¿szego s¹siada.
		// 2. Rozwi¹zanie z s¹siedztwa: Pierwsze uzyskane rozwi¹zanie lepsze od Vc.
		int[]greedySolution_first=new int[greedySolution.length];
		int[]greedySolution_second=new int[greedySolution_first.length];
		for(int i=0;i<greedySolution.length;i++)
		{
			greedySolution_first[i]=greedySolution[i];
			greedySolution_second[i]=greedySolution_first[i];
		}
		
		System.out.println("--------------------------]");
		System.out.println("LP: 5");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_2(rd,LS.Local_Search.WRP.Random,LS.Local_Search.WRZO.FirstBetter,bestDistance, greedySolution_first);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		
		// LP: 6
		// 1. Rozwiazanie poczatkowe: Trasa powsta³a poprzez algorytm konkstrukcyjny najbli¿szego s¹siada.
		// 2. Rozwi¹zanie z s¹siêdztwa: Najlepsze rozwi¹zanie spoœród wszystkich s¹siadów,
		System.out.println("--------------------------]");
		System.out.println("LP: 6");
		sum=0;
		for(int i=0;i<times;i++)
		{
			sum+=Test_LocalSearch_2(rd,LS.Local_Search.WRP.Random,LS.Local_Search.WRZO.TheBest,bestDistance, greedySolution_second);
		}
		sum/=times;
		System.out.println("Average distance: "+sum);
		System.out.println("--------------------------]\n");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public static void main(String[]args)
	{
		// 1. Utworzenie obiektu atrybutami problemu, ktore zostaly odczytane z pliku.
		LS.DataReader rd = new LS.DataReader("src/berlin52.tsp");
				
		// 2. Obliczenie najlepszego dystansu.
		String src="src/berlin52opttour.sec";
		double bestDistance = LS.GetBestDistanceFromFile(rd, src);
	
		// 3. Okreslenie ilosci przypadkow w celu obliczenia srednich.
		int times=25;
		
		// 4. Obliczenie najlepszej sciezki przy u¿yciu Algorytmu Zach³annego - Najlepszego S¹siada.
		int[]greedySolution=LS.GreedySearch_BestNeighbor.TheBestNeighbor(rd);
		
		// 4. Wyswietlanie oraz obliczanie srednich dystansow dla algorytmu 1.
		LocalSearch_1(rd, bestDistance, times, greedySolution);
		
		// 5. Wyswietlanie oraz obliczanie srednich wartosci dla algorytmu 2.
		LocalSearch_2(rd, bestDistance, times, greedySolution);
	}
}


