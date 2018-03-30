package panameron.jspellingbuddy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Levenshtein {
	static ClassLoader classLoader = Levenshtein.class.getClassLoader();
	static File file = new File(classLoader.getResource("dict.dat").getFile());
	static HashMap< Object, ArrayList< String > > iDictHash = new HashMap< Object, ArrayList< String > >();

	public static HashMap GenerateDict(){
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				char iCurrentChar = line.charAt( 0 );
				if( !iDictHash.containsKey( iCurrentChar )){
					iDictHash.put( iCurrentChar, new ArrayList< String >() );
				}
				ArrayList< String > t_CurrentSet = iDictHash.get( iCurrentChar );
				t_CurrentSet.add( line.replace( '\n', ' ' ).trim() );
			}
		}catch( IOException e ){}

		return iDictHash;
	}

	public static boolean IsValidWord( String vWord ){
		return iDictHash.get( vWord.charAt( 0 ) ).contains( vWord );
	}

	public static int Compare(String a, String b) {
		a = a.toLowerCase();
		b = b.toLowerCase();

		int [] costs = new int [b.length() + 1];
		for (int j = 0; j < costs.length; j++)
			costs[j] = j;
		for (int i = 1; i <= a.length(); i++) {
			costs[0] = i;
			int nw = i - 1;
			for (int j = 1; j <= b.length(); j++) {
				int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
				nw = costs[j];
				costs[j] = cj;
			}
		}
		return costs[b.length()];
	}
}
