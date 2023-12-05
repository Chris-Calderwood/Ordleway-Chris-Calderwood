import java.io.FileNotFoundException;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Ordleway {

    // constants to allow colored text and backgrounds
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static int linearSearch (ArrayList<String> words, int startIndex, int endIndex, String attempt) {
        int wordPos;
        int rangeSize = (endIndex - startIndex) + 1;

        // if attempt equals word at startIndex, wordPos equals startIndex
        if (attempt.equals(words.get(startIndex))) {
            wordPos = startIndex;
        }
        // otherwise if all guesses exhausted and rangeSize equals 1, set wordPos to -1
        else if (rangeSize == 1) {
            wordPos = -1;
        }
        // otherwise, increment startIndex and set wordPos to new linear search call with updated startIndex
        else{
            startIndex += 1;
            wordPos = linearSearch(words, startIndex, endIndex, attempt);
        }

        return wordPos;
    }

    public static boolean foundMatch (int[] match) {
        for (int i=0; i<match.length; ++i) {
            if (match[i] != 1) {
                return false;
            }
        }
        return true;
    }

    public static int[] matchOrdleway (String target, String attempt){
        int[] match = new int[7];
        int[] sameCharCountTarget = new int[7];
        int[] sameCharCountAttempt = new int[7];
        for (int i = 0; i < 7; ++i) {
            // if characters match at same position, element of array equals one
            if (attempt.charAt(i) == (target.charAt(i))) {
                match[i] = 1;
            }
            for (int k=0; k<7; ++k) {
                if (target.charAt(i) == target.charAt(k) && i != k) {
                    sameCharCountTarget[i]++;
                }
                if (attempt.charAt(i) == attempt.charAt(k) && k < i) {
                    sameCharCountAttempt[i]++;
                }
            }
        }

        for (int j=0; j<7; j++){
            // if character of attempt does not match at same position, but is in target word, element of array
            // equals two
            for (int h = 0; h < 7; ++h) {

                if (attempt.charAt(j) == target.charAt(h) && match[h] != 1 && match[j] != 1 &&
                        sameCharCountTarget[h] >= sameCharCountAttempt[j]) {
                    match[j] = 2;
                    break;
                }
            }
        }
        // otherwise, element of array will simply stay 0 as it is automatically initialized with that value
        return match;
    }

    public static void printOrdleway(int[] match, String attempt) {
        // iterates through attempt letter by letter
        for (int i=0; i<7; ++i) {
            // if element of match for letter i in attempt equals one, meaning attempt letter at this position
            // equals target letter at same position, print letter with green background
            if (match[i] == 1) {
                System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + attempt.charAt(i));
            }
            // otherwise if element of match equals 2, meaning letter in element at this position is in target
            // word, but not at correct position, then print letter with yellow background
            else if (match[i] == 2) {
                System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + attempt.charAt(i));
            }
            // otherwise, print letter with grey background
            else {
                System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + attempt.charAt(i));
            }
        }
        System.out.println(ANSI_RESET);
    }

    public static void printKeyboard(int[] match, String attempt) {
        String keyboard = "qwertyuiopasdfghjklzxcvbnm";
        int count = 0;

        for (int i=0; i<keyboard.length(); i++){
            for (int j=0; j<7; j++) {
                if (keyboard.charAt(i) != attempt.charAt(j)){
                   count++;
                }
                else if (keyboard.charAt(i) == attempt.charAt(j) && match[j] == 1) {
                    count = -100;
                }
                else if (keyboard.charAt(i) == attempt.charAt(j) && match[j] == 2) {
                    count = 100;
                }
                else {
                    count = 0;
                }
            }
            if (count < -10) {
                System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + keyboard.charAt(i));
                System.out.print(ANSI_RESET + " ");
            }
            else if (count >= 100) {
                System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + keyboard.charAt(i));
                System.out.print(ANSI_RESET + " ");
            }
            /*else if (count != 0){
                System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + keyboard.charAt(i));
                System.out.print(ANSI_RESET + " ");h
            }*/
            else {
                System.out.print(ANSI_RESET);
                System.out.print(keyboard.charAt(i) + " ");
            }
            count = 0;
        }
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException{
        // prints error message and exits program if there is not exactly one argument in command line
        if (args.length != 1) {
            System.err.print("Invalid number of arguments.");
            System.exit(0);
        }

        Scanner scnr0 = new Scanner(System.in);
        // print welcome message
        System.out.println("Welcome to Ordleway!\n");
        System.out.println("-=-=-=-=-=-=- Rules -=-=-=-=-=-=-");
        System.out.println("Guess the Ordleway in 6 tries!");
        System.out.println("- Each guess must be a valid seven-letter Pig Latin word. Valid Pig Latin words are " +
                "formed as follows:");
        System.out.println("     - If the English word begins with a vowel, the word is left unchanged and the " +
                "suffix \"way\" is added to it.");
        System.out.println("     - If the English word begins with a single consonant directly followed by a vowel," +
                " the starting consonant is removed from the beginning \n       of the word, added to the end of the" +
                " word, and then the suffix \"ay\" is added to it.");
        System.out.println("     - If the English word begins with multiple consonants in a row, or a consonant" +
                " cluster, the consonant cluster is removed from the beginning \n       of the word, added to the" +
                " end of the word, and then the suffix \"ay\" is added to it.");
        System.out.println("- The color of the letters will change to show how close your guess was to the word.");
        System.out.println("     - If a letter is green, it is in the word and in the correct spot.");
        System.out.println("     - If a letter is yellow, it is in the word but in the wrong spot.");
        System.out.println("     - If a letter is gray, it is not in the word.\n");
        System.out.println("Would you like to see helpful hints? (y for yes, enter for no)");
        String response = scnr0.next();
        while (!response.equals("y")  && !response.equals("n")) {
            System.out.println("Invalid input. Please try again.");
            System.out.println("Would you like to see helpful hints? (y for yes, n for no)");
            response = scnr0.next();
        }

        if (response.equals("y")) {
            System.out.println("-=-=-=-=-=-=-=-=-=-Helpful Hints-=-=-=-=-=-=-=-=-=-");
            System.out.println("1. All solutions and valid guesses begin with a vowel.");
            System.out.println("2. All solutions and valid guesses end with the letters \"ay\".");
            System.out.println("3. Ordleway includes only 5-letter words that begin with a consonant in English," +
                    " which means when adding \"ay\" to the end, \n they are seven letters long in Pig Latin. ");
            System.out.println("4. Ordleway includes only 4-letter words that begin with a vowel in English," +
                    " which means when adding \"way\" to the end, \n they are seven letters long in Pig Latin.");
            System.out.println("5. It is a good idea to begin by guessing a word that ends in \"way\" to" +
                    " determine whether the target word begins with a vowel and is thus 4 letters long \n in " +
                    "English or whether it is 5 letters long and begins with a consonant.");
            System.out.println("6. Proper nouns are not included in Ordleway.");
            System.out.println("Good Luck!\n\n");
        }
        else {
            System.out.println();
        }


        // creating a new file called input that will access the first argument from the command line
        // and read from it using scnr
        String filename = args[0];
        File input = new File(filename);
        Scanner scnr = new Scanner(input);

        // creating new arraylist and adding all five-letter words from input file to it
        ArrayList<String> words = new ArrayList<>();
        while (scnr.hasNext()) {
            String word = scnr.nextLine();
            words.add(word);
        }

        // picking a random number and using it as an index to select a random word from arraylist created above
        Random randNum = new Random();
        int index = randNum.nextInt(words.size());
        String target = words.get(index);

        // assigning start and end index variables to use for binarysearch method
        int startIndex = 0;
        int endIndex = words.size() - 1;

        // declaring and initializing count and solved variables, which tell if the Ordleway was solved and how
        // many attempts it took to solve if it was solved
        int count = 0;
        boolean solved = false;

        int[] match = new int[7];
        String attempt = "attempt";

        for (int i=1; i<=6; ++i) {
            int isInList;

            Scanner scnr1 = new Scanner(System.in);

            System.out.println("Keyboard:");
            if (i == 1) {
                System.out.println("q w e r t y u i o p a s d f g h j k l z x c v b n m");
            }
            else {
                printKeyboard(match, attempt);
            }
            System.out.println("Enter guess #" + i + ": ");
            attempt = scnr1.nextLine();


            isInList = linearSearch(words, startIndex, endIndex, attempt);


            // while user word is not in list, output invalid input and request new input, check if new word
            // is a valid input
            while (isInList == -1) {
                System.out.println("Invalid word.");
                System.out.println("Enter guess #" + i + ": ");
                attempt = scnr1.nextLine();
                isInList = linearSearch(words, startIndex, endIndex, attempt);
            }

            /* calling matchOrdlway method to check user's attempt against the target word, stores output
            array of ints as match, then uses match and attempt to call printOrdleway method with correct
            background colors behind each letter in attempt */
            match = matchOrdleway(target, attempt);
            printOrdleway(match, attempt);

            // incrementing count
            count++;

            // uses foundMatch method to check if attempt is completely correct, if so, breaks from loop
            if (foundMatch(match)) {
                System.out.println("Correct!");
                solved = true;
                break;
            }
        }

        // if Ordleway was solved, print out message with how many tries it took to solve
        // if not solved, print out message with correct word
        if (solved) {
            System.out.println("You solved today's Ordleway in " + count + " tries!");
        }
        else {
            System.out.println("The correct word for today's Ordleway was " + target + ". Better luck next time!");
        }
    }
}
