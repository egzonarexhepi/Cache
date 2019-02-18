import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Test<T> {

	static boolean oneCache;

	static Cache firstCache;
	static Cache secondCache;

	static double NH1 = 0;
	static double NH2 = 0;
	static double NM1 = 0;
	static double NM2 = 0;

	static double global;
	static double globalHits;
	static double globalHitRatio;
	static double references1;
	static double references2;
	static double cache1ratio;
	static double cache2ratio;

	static String inputFile = "";
	static int cacheSizeOne = 0;
	static int cacheSizeTwo = 0;

	public static void main(String args[]) {

		if( args.length > 0 && args[0].equals( "Test" ) ) {
			if (args[1].equals("1")) {
				oneCache = true;
				cacheSizeOne = Integer.valueOf(args[2]);
				inputFile = (args[3]);
				makeFirstCache(cacheSizeOne);

			}else if (args[1].equals("2")){
				oneCache = false;
				cacheSizeOne = Integer.valueOf(args[2]);
				cacheSizeTwo = Integer.valueOf(args[3]);
				inputFile = (args[4]);
				makeFirstCache(cacheSizeOne);
				makeSecondCache(cacheSizeTwo);
			} else {
				System.out.println("Please submit correct command line args.");
				System.exit(0);
			}

		}else {
			System.out.println("Please submit correct command line args.");
			System.exit(0);
		}

		if (oneCache == false) {
			if (cacheSizeTwo <= cacheSizeOne) {
				System.out.println(	"Please submit correct command line args. Cache two must have > size than cache one.");
				System.exit(0);
			}
			if (cacheSizeTwo <= 0) {
				System.out.println("Please submit correct command line args. Cache size two must be > 0.");
				System.exit(0);
			} 
		}

		if (cacheSizeOne <= 0) {
			System.out.println("Please submit correct command line args. Cache size one must be > 0.");
			System.exit(0);	
		}

		fileReader();

		globalHits = NH1 + NH2;
		globalHitRatio = globalHits/global; 
		cache1ratio = NH1/global;
		cache2ratio = NH2/NM1;

		printResults();


	}

	private static void makeFirstCache(int cacheSize) {

		firstCache = new Cache(cacheSize);

	}

	private static void makeSecondCache(int cacheSize2) {

		secondCache = new Cache(cacheSize2);

	}

	private static void printResults() {

		System.out.println("First level cache with " + cacheSizeOne + " entries has been created");

		if (oneCache == false) {
			System.out.println("Second level cache with " + cacheSizeTwo + " entries has been created");
		}

		System.out.println();
		System.out.println("The number of global references: " + (int)global);
		System.out.println("The number of global cache hits: " + (int)globalHits);
		System.out.println("The global hit ratio: " + globalHitRatio);

		System.out.println();
		System.out.println("The number of 1st level references: " + (int)global);
		System.out.println("The number of 1st level cache hits: " + (int)NH1);
		System.out.println("The 1st level cache hit ratio: " + cache1ratio);

		if (oneCache == false) {
			System.out.println();
			System.out.println("The number of 2nd level references: " + (int)NM1);
			System.out.println("The number of 2nd level cache hits: " + (int)NH2);
			System.out.println("The 2nd level cache hit ratio: " + NH2/NM1);
		}

	}

	private static void fileReader() {
		BufferedReader fileRead = null;
		String line;

		try {
			fileRead = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Please submit a valid file.");
			System.exit(0);
		}

		try {
			while ((line = fileRead.readLine()) != null) {

				StringTokenizer stringTokenizer = new StringTokenizer(line, " \t");

				while (stringTokenizer.hasMoreTokens()) {				   
					String object = stringTokenizer.nextToken();
					hitOrMiss(object);
				}

			}
		} catch (IOException e) {
			System.out.println("Unable to read file. Please submit a valid file.");
			System.exit(0);

		}

	}

	private static void hitOrMiss(String object1) {
		global++;
		if (firstCache.hasObject(object1) == true) {
			firstCache.removeObject(object1);
			firstCache.addObject(object1);
			if (oneCache == false	) {
				secondCache.removeObject(object1);
				secondCache.addObject(object1);
			}
			NH1++;
		}
		else {
			NM1++;
			if (oneCache == false	) {
				if (secondCache.hasObject(object1) == true) {
					if (firstCache.isFull()) {
						firstCache.removeLastObject();
						firstCache.addObject(object1);
					} else {
						firstCache.removeObject(object1);
						firstCache.addObject(object1);
					}
					secondCache.removeObject(object1);
					secondCache.addObject(object1);
					NH2++;
				} else {
					if (firstCache.isFull() && secondCache.isFull()	) {
						firstCache.removeLastObject();
						firstCache.addObject(object1);
						secondCache.removeLastObject();
						secondCache.addObject(object1);
					}
					else if (secondCache.isFull()){
						secondCache.removeLastObject();
						secondCache.addObject(object1);	
						firstCache.removeObject(object1);
						firstCache.addObject(object1);
					} 
					else if (firstCache.isFull()) {
						firstCache.removeLastObject();
						firstCache.addObject(object1);
						secondCache.removeObject(object1);
						secondCache.addObject(object1);	
					} else {
						firstCache.removeObject(object1);
						firstCache.addObject(object1);
						secondCache.removeObject(object1);
						secondCache.addObject(object1);	
					}
					NM2++;
				}
			}
		}
	}
}




