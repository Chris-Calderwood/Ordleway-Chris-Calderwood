import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EnglishToPigLatin {
    public static void main(String[] args) throws FileNotFoundException {
        // prints error message and exits program if there is not exactly one argument in command line
        if (args.length != 1) {
            System.err.print("Invalid number of arguments.");
            System.exit(-1);
        }

        // creating a new file called input that will access the first argument from the command line
        // and read from it using scnr
        String filename = args[0];
        File input = new File(filename);
        Scanner scnr = new Scanner(input);

        // creating new arraylist and adding all five-letter words from input file to it
        ArrayList<String> englishWords = new ArrayList<>();
        while (scnr.hasNext()) {
            String word = scnr.nextLine();
            char b = word.charAt(0);
            if (b == 'a' || b == 'e' || b == 'i' || b == 'o' || b == 'u') {
                if (word.length() == 4) {
                    englishWords.add(word);
                }
            }
            else {
                if (word.length() == 5) {
                    englishWords.add(word);
                }
            }

        }



        ArrayList<String> pigLatinWords = new ArrayList<>();

        for(int i=0; i<englishWords.size(); i++){
            String wordToModify = englishWords.get(i);
            char c = wordToModify.charAt(0);

            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                String word = wordToModify.concat("way");
                pigLatinWords.add(word);
            }
            else {
                for (int j=0; j<wordToModify.length(); j++) {
                    char d = wordToModify.charAt(j);
                    if (d == 'a' || d == 'e' || d == 'i' || d == 'o' || d == 'u') {
                        String substring1 = wordToModify.substring(0, j);
                        String temp = wordToModify.replaceFirst(substring1, "");
                        temp = temp.concat(substring1);
                        String word = temp.concat("ay");
                        pigLatinWords.add(word);
                        break;
                    }
                }
            }
        }

        try {
            FileWriter out = null; // create new FileWriter object called out
            out = new FileWriter("pigLatinWords.unsorted.txt", false); // initialize out to output to text file
            for (int h=0; h<pigLatinWords.size(); h++) { // loop writes all elements of sorted array list in text file
                out.write(pigLatinWords.get(h) + "\n");
            }
            out.close(); // close FileWriter object h
        }
        catch (IOException e) {
            throw new RuntimeException(e); // if there is an IOException, output error message
        }

    }
}
