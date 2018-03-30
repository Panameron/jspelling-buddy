package panameron.jspellingbuddy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	static Scanner pInStream = new Scanner(System.in);
	static Scanner iInStream = new Scanner(System.in);

	public static void main(String[] args) {
		Main.Print( "::::::::::::::");
		Main.Print( "\tjSpellingBuddy");
		Main.Print( "\tBy Panameron");
		Main.Print( "::::::::::::::");
		Main.Print( "\n<<setup>> Enter a correctness threshold ( Recommended: 2; greater = more loose matching, more matches )");
		System.out.print( ">>>>>>>>> Threshold: " );
		int Threshold = iInStream.nextInt();

		HashMap iDict = Levenshtein.GenerateDict();
		while( true ){
			Main.Print( "\n>> Please enter a word. " );
			System.out.print( ">>>> " );
			String u_Input = pInStream.nextLine().trim().toLowerCase();

			if ( !Levenshtein.IsValidWord( u_Input )){
				ArrayList <String> ioMatches = new ArrayList< String >();
				for ( String PotentialWord : ( ArrayList< String > ) iDict.get( u_Input.charAt( 0 ) )){
					int tcScore = ( Math.abs( u_Input.length() - PotentialWord.length() ) < Threshold ) ? Levenshtein.Compare( u_Input, PotentialWord ) : 1024;
					if ( tcScore != 1024 && tcScore < Threshold ){
						ioMatches.add( tcScore + PotentialWord );
					}
				}

				if ( ioMatches.size() > 0 ){
					Collections.sort( ioMatches );
					Main.Print( "Did you mean:" );
					for ( String Match : ioMatches )
						Main.Print( "\t" + Match.substring( ( "" + Threshold ).length() ) + "?" );
				}else{
					Main.Print( "No match found. Is " + u_Input + " even English?");
				}
			}else{
				Main.Print( "That word is spelled correctly!" );
			}
		}
	}

	static void Print( Object ... Values ){
		String toPrint = "";
		for ( Object CurrentObject : Values )
			toPrint += CurrentObject.toString() + "\t";
		System.out.println( toPrint );
	}
}
