import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class ParallelMatrixSearch {
	private static final int MATRIX_SIZE = 20000;
	private static final int THREAD_COUNT = 4;
	private static final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
	private static final int TARGET = 256; // Número a buscar

	private static int indexI = -1;
	private static int indexE = -1;



	public static void main(String[] args) {
		Random random = new Random(System.currentTimeMillis());
		// Inicializar la matriz con valores aleatorios
		for (int i = 0; i < MATRIX_SIZE; i++)
			for (int e = 0; e < MATRIX_SIZE; e++)
				matrix[i][e] = random.nextInt();

		matrix[MATRIX_SIZE-1][MATRIX_SIZE-1] = 256;
		//indice donde se encuentra el target en la matrix

		// Medir el tiempo de ejecución de la búsqueda secuencial
		long startTime = System.currentTimeMillis();

		sequentialSearch();

		long endTime = System.currentTimeMillis();
		if (indexI == -1 && indexE == -1)
			System.out.println("No se encontro el target");
		else
			System.out.println("Resultado búsqueda secuencial: " + matrix[indexI][indexE]);
		System.out.println("Tiempo búsqueda secuencial: " + (endTime - startTime) + "ms");

		// Medir el tiempo de ejecución de la búsqueda paralela
		indexI = -1;
		indexE = -1;

		startTime = System.currentTimeMillis();

		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

		Runnable[] searchers = new Runnable[THREAD_COUNT];

		//No consegui hacer que el bucle con el AtomicIntenger funcionara, asi que lo tengo de esta forma
		searchers[0] = () -> {
			parallelSearch(0, MATRIX_SIZE / THREAD_COUNT);
		};
		searchers[1] = () -> {
			parallelSearch(MATRIX_SIZE / THREAD_COUNT, MATRIX_SIZE / THREAD_COUNT);
		};
		searchers[2] = () -> {
			parallelSearch((MATRIX_SIZE * 2) / THREAD_COUNT, MATRIX_SIZE / THREAD_COUNT);
		};
		searchers[3] = () -> {
			parallelSearch((MATRIX_SIZE * 3) / THREAD_COUNT, MATRIX_SIZE / THREAD_COUNT);
		};

		for (int i = 0; i < THREAD_COUNT; i++)
			executor.execute(searchers[i]);

		executor.shutdown();

		endTime = System.currentTimeMillis();
		if (indexI == -1 && indexE == -1)
			System.out.println("No se encontro el target");
		else
			System.out.println("Resultado búsqueda paralela: " + matrix[indexI][indexE]);
		System.out.println("Tiempo búsqueda paralela: " + (endTime - startTime) + "ms");

	}

	private static void sequentialSearch() {
		for (int i = 0; i < MATRIX_SIZE; i++)
		{
			for (int e = 0; e < MATRIX_SIZE; e++)
			{
				if (matrix[i][e] == TARGET)
				{
					indexI = i;
					indexE = e;
					return;
				}
			}
		}
	}

	private static synchronized void parallelSearch(int start, int limit) {

		for (int i = start; i < limit; i++)
		{
			for (int e = 0; e < MATRIX_SIZE; e++)
			{
				if (indexI != -1 || indexE != -1)
					return;

				if (matrix[i][e] == TARGET)
				{
					indexI = i;
					indexE = e;
					return;
				}
			}
		}
	}
}
