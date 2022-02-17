package src;

// CEL: Importowanie dodatkowych kodów zród³owych.
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// CEL: Znalezienie optimum (minimum) podanej funkcji.
public class PSO {

	// CEL: Sprawdz czy wartosc w granicy.
	public static boolean isInRange(double lb, double rb, double x)
	{
		return (x>=lb && x<=rb)? true:false;
	}
	
	// CEL: Obliczenie funckji przystosowania.
	
	// A)
	public static double Sphere(double x1, double x2)
	{
		// Zwrócenie wnyiku.
		double min=-5.12d, max=-1*min;
		if(isInRange(min, max, x1) && isInRange(min, max, x2))
		{
			return Math.pow(x1, 2.0d)+Math.pow(x2, 2.0d);
		}
		return Double.MAX_VALUE;
	}

	// B)
	public static double Ackley(double x1, double x2)
	{
		double min=-32.768, max=-1*min;
		if(isInRange(min, max, x1) && isInRange(min, max, x2))
		{
			return -20*Math.exp(-0.2d*Math.sqrt(0.5*(Math.pow(x1, 2)+Math.pow(x2, 2)))-Math.exp(0.5*(Math.cos(2*3.14)*x1+Math.cos(2*3.14)*x2)+Math.E+20));
		}
		return Double.MAX_VALUE;
	}
	
	// C)
	public static double Grienwank(double x1, double x2)
	{
		double min=-200.0d, max=-1*min;
		if(isInRange(min, max, x1) && isInRange(min, max, x2))
		{
			return 1+(1.0d/4000.0d)*(Math.pow(x1, 2)+Math.pow(x2, 2))-(Math.cos(x1/Math.sqrt(1))*Math.cos(x2/Math.sqrt(2)));
		}
		return 0.0d;
	}
	
	// D)
	public static double Rastring(double x1, double x2)
	{
		double min=-5.12d, max=-1*min;
		if(isInRange(min, max, x1) && isInRange(min, max, x2))
		{
			double A=10;
			int n=2;
			return A*n+(Math.pow(x1, 2)-A*Math.cos(2*3.14*x1))*(Math.pow(x2, 2)-A*Math.cos(2*3.14*x2));
		}
		return Double.MAX_VALUE;
	}

	// E)
	public static double Rosenbrock(double x1, double x2)
	{
		double a=1, b=100;
		return Math.pow((a-x1),2)+b*Math.pow((x2-Math.pow(x2, 2)), 2);
	}
	
	// CEL: Zdefiniowanie klasy Rój.
	public static class Swarm
	{
		// REGION: Pola.
		ArrayList<Particle>particles; // Bufor przechowuj¹cy cz¹stki.
		int numberOfParticles; // Iloœæ cz¹stek w roju.
		// Wliczacj¹c s¹siedztwo.
		private double g1;		// Najlepsza do tej pory znaleziona pozycja w s¹siedztwie.
		private double g2; 		// Najlepsza do tej pory znaleziona pozycja w s¹siedztwie.
		private double gBest;	// Wartoœæ funkcji przystosowania gi.
		// END REGION
		
		// REGION: Konstruktor.
		// a) Podstawowy z zdefiniowan¹ iloœci¹ cz¹stek 40.
		public Swarm()
		{
			// 1. Zapisanie ilosci czasteczek.
			this.numberOfParticles=40;
			
			// 2. Utworzenie bufora na cz¹steczki.
			particles=new ArrayList<Particle>(numberOfParticles);
			
			// 3. Tworzenie cz¹stki oraz dodawanie jej do bufora.
			for(int i=0;i<numberOfParticles;i++)
			{
				Particle particle=new Particle();
				particles.add(particle);
			}
		}
		
		// b) Dostosowany do parametru.
		public Swarm(int numberOfParticles)
		{
			// 1. Zapisanie ilosci czasteczek.
			this.numberOfParticles=numberOfParticles;
			
			// 2. Utworzenie bufora na cz¹steczki.
			particles=new ArrayList<Particle>(numberOfParticles);
			
			// 3. Tworzenie cz¹stki oraz dodawanie jej do bufora.
			for(int i=0;i<numberOfParticles;i++)
			{
				Particle particle=new Particle();
				particles.add(particle);
			}
		}
		// END REGION
		
		// REGION: Metody.
		// CEL: Ustawienie cz¹stek w pozycjach pocz¹tkowych oraz ich prêdkoœci.
		public void setStartPositions_And_Velocities()
		{
			// 1. Przechodzenie przez cz¹stki.
			for(Particle p: particles)
			{
				// 1.1 Ustawienie cz¹stki.
				p.setStartPosition_And_StartVelocity();
			}
		}
		
		// CEL: Aktualizowanie prêdkoœci i pozycji.
		public void Update_Velocity_And_Positions(double q1, double q2)
		{
			for(Particle p: particles)
			{
				p.Update_Velocity_And_Position(g1, g2, q1, q2);
			}
		}
		
		// END REGION

		// REGION: W³aœciwoœci.
		public ArrayList<Particle> getParticles()
		{
			return particles;
		}

		public int getNumberOfParticles()
		{
			return numberOfParticles;
		}

		
		public double getG1()
		{
			return g1;
		}

		
		public void setG1(double g1)
		{
			this.g1 = g1;
		}

		
		public double getG2()
		{
			return g2;
		}

		
		public void setG2(double g2)
		{
			this.g2 = g2;
		}

		
		public double getgBest()
		{
			return gBest;
		}
		

		public void setgBest(double gBest) 
		{
			this.gBest = gBest;
		}
		// END REGION
		
		// REGION: Aktualizuj najlepsze wartoœci cz¹stek.
		public void Update_Bests(String mode)
		{
			for(Particle p: particles)
			{
				p.Update_Bests(this, mode);
			}
		}
	}
	
	// CEL: Zdefiniowanie klasy Cz¹stka.
	public static class Particle
	{
		// REGION: Pola.
		// Pozycje cz¹stki.
		private double x1; // Pozycja cz¹stki na p³aszczyznie OX.
		private double x2; // Pozycja cz¹stki na p³aszczyznie OY.

		// Prêdkoœæ cz¹stki.
		private double v1; // Prêdkoœæ cz¹stki na p³aszczyznie OX.
		private double v2; // Prêdkoœæ cz¹œtki na p³aszczyznie OY.
		
		// Wartoœci funkcji przystosowania (miara oceny) xi.
		private double y;
		
		// Wliczaj¹cy tylko cz¹stkê.
		private double p1;		// Najlepsza do tej pory znaleziona pozycja.
		private double p2;		// Najlepsza do tej pory znalezion pozycja.
		private double pBest;	// Wartoœæ funkcji przystosowania dla pi.

		// Dodatkowe:
		private Random r=new Random();
		
		// END REGION
		
		// REGION: Konstruktor.
		public Particle()
		{
			// 1. Inicjalizowanie pól obiektu.
			x1=Double.MAX_VALUE;
			x2=Double.MAX_VALUE;
			v1=Double.MAX_VALUE;
			v2=Double.MAX_VALUE;
			p1=Double.MAX_VALUE;
			p2=Double.MAX_VALUE;
			pBest=Double.MAX_VALUE;
			y=Double.MAX_VALUE;
		}
		// END REGION

		// REGION: W³aœciwoœci.
		public double getX1() {
			return x1;
		}

		public void setX1(double x1) {
			this.x1 = x1;
		}

		public double getX2() {
			return x2;
		}

		public void setX2(double x2) {
			this.x2 = x2;
		}

		public double getV1() {
			return v1;
		}

		public void setV1(double v1) {
			this.v1 = v1;
		}

		public double getV2() {
			return v2;
		}

		public void setV2(double v2) {
			this.v2 = v2;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public double getP1() {
			return p1;
		}

		public void setP1(double p1) {
			this.p1 = p1;
		}

		public double getP2() {
			return p2;
		}

		public void setP2(double p2) {
			this.p2 = p2;
		}

		public double getpBest() {
			return pBest;
		}

		public void setpBest(double pBest) {
			this.pBest = pBest;
		}

		public Random getR() {
			return r;
		}

		public void setR(Random r) {
			this.r = r;
		}
		// END REGION
		
		// REGION: Metody.
		// CEL: Generowanie losowej wartoœci.
		private double genRandomValue_(double rangeMin, double rangeMax)
		{
			return rangeMin+(rangeMax-rangeMin)*r.nextDouble();
		}
		
		private double genRandomValue(double rangeMin, double rangeMax)
		{
			return (r.nextDouble()*(rangeMax-rangeMin))+rangeMin;
		}
		
		// CEL: Ustawienie losowej pozycji oraz prêdkoœci.
		public void setStartPosition_And_StartVelocity()
		{	
			// 1. Ustawianie losowych pozycji.
			x1=genRandomValue(-5.12d, 5.12);
			x2=genRandomValue(-5.12d, 5.12);
			
			// 2. Ustawianie prêdkoœci.
			v1=0;
			v2=0;
		}
		
		// CEL: Obliczenie jej funkcji przystosowania.
		public double getEval(String name)
		{
			double res=0;
			switch(name)
			{
			case "sphere":
				res=Sphere(x1, x2);
				break;
			case "ackley":
				res=Ackley(x1, x2);
				break;
			case "grienwank":
				res=Grienwank(x1, x2);
				break;
			case "rastring":
				res=Rastring(x1, x2);
				break;
			case "rosenbrock":
				res=Rosenbrock(x1, x2);
				break;
			}
			return res;
		}
	
		// CEL: Generuj liczby pseudolosowe.
		private double GenRandom(double min, double max)
		{
			return (ThreadLocalRandom.current().nextDouble()*(max-min))+min;
		}
		
		// CEL: Aktualiozwnaie prêdkoœci oraz pozycji.
		public void Update_Velocity_And_Position(double g1, double g2, double q1, double q2)
		{
			// 1. Aktualizowanie prêkoœci.
			v1=v1 + GenRandom(0, q1)*(p1-x1)+GenRandom(0, q2)*(g1-x1);
			v2=v2 + GenRandom(0, q1)*(p2-x2)+GenRandom(0, q2)*(g2-x2);
			
			// 2. Aktualizowanie po³o¿enia.
			x1=x1+v1;
			x2=x2+v2;
		}
		
		// CEL: Aktualizowanie paremetrów mrówki.
		public void Update_Bests(Swarm swarm, String mode)
		{
			// 1. Okreœlenie obecnej wartoœci funkcji przystosowani w aktualnej pozycji x1, x2.
			y=getEval(mode);
		
			// 2. Jeœli y jest lepsze ni¿ pbest to wtedy uaktualnij pbesti oraz p.
			if(y<pBest)
			{
				// 2.1 Zapiysywanie wyniku.
				pBest=y;
				
				// 2.2 Zapisywanie pozycji.
				p1=x1;
				p2=x2;
			}
			
			// 3. Jeœli y jest lepsze ni¿ gbest to wtedy auktualnij gbest oraz g.
			if(y<swarm.getgBest())
			{
				// 3.1 Zapisywanie wyniku.
				swarm.setgBest(y);
				
				// 3.2 Zapisywanie pozycji.
				swarm.setG1(x1);
				swarm.setG2(x2);
			}
		}
		// END REGION
	}

	// CEL: Zdefinianie algorytmu.
	public static void PSO(String mode)
	{
		// 1. Utworzenie wartoœci pomocniczych algorytmu.
		int MAX_ITER=100; 				// Iloœæ iteracji.
		int swarm_size=40;				// Wielkoœæ roju.
		double Q1=0.2d, Q2=0.2d;		// Dodatkowe parametry.
		
		// 2. Utworzenie roju.
		Swarm swarm=new Swarm(swarm_size);
		
		// 3. Inicjalzacja pozycji oraz prêdkoœci w sposób losowy. 
		swarm.setStartPositions_And_Velocities();
		
		// 4. Dopóki nie zachodzi warunek stopu:
		for(int i=0;i<MAX_ITER;i++)
		{
			// a) Dla ka¿dej cz¹stki i:
			swarm.Update_Bests(mode);
			
			// b) Dla ka¿dej cz¹tki i:
			swarm.Update_Velocity_And_Positions(Q1, Q2);
		}
		
		// 5. Drukowanie najlepszego.
		System.out.println(swarm.getgBest());
	}
	
	public static void main(String[]args)
	{
		// 1. Rozpoczêcie dzia³ania alogrytmu.
		String[] modes= {"sphere", "ackley", "grienwank", "rastring", "rosenbrock"};
		for(String mode: modes)
		{
			System.out.println("Mode: "+mode);
			PSO(mode);
		}
	}
}
