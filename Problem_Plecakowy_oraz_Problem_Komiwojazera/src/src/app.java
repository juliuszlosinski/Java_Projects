package src;

public class app
{
	public static void main(String[]args)
	{
		System.out.println("======= PROBLEM PLECAKOWY =====]");
		String src="src/binpack1.txt";
		
		Ex1.Test_Next_Fit(src);
		System.out.println();
		
		Ex1.Test_First_Fit(src);
		System.out.println();
		
		Ex1.Test_Best_Fit(src);
		System.out.println();
		
		Ex1.Test_Worst_Fit(src);
		System.out.println();
		
		Ex1.Test_First_Fit_Decreasing(src);
		System.out.println();
		
		Ex1.Test_First_Fit_Increasing(src);
		System.out.println("=================================]\n");
		
		System.out.println(">>>>>>>> PROBLEM KOMIWOJAZERA <<<<<<<<<");
		Ex2.TheBestNeighbor("src/berlin52tsp.sec", "src/berlin52opttour.sec");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}
