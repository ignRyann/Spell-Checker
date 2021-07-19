import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class SpellChecker {

    private StringArray dictionary;
    private String sentence;
    private String[] sentenceArray;
    private StringArray incorrectWords;


    public SpellChecker(){
        loadDictionary();

        // Continuously loops until the user wishes to close the program
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Would you like to run the SpellChecker? ( Y to continue ): ");
            String userOption = scan.nextLine();
            if (userOption.equalsIgnoreCase("y")){
                runSpellChecker();
            }else{
                break;
            }
        }
        System.out.println("\nThe SpellChecker will now close, thank you.");

    }

    // Runs the spell checker
    private void runSpellChecker(){
        getSentence();
        findIncorrectWords();
        findSimilarWords();
        saveSentence();
    }

    // Loads the dictionary 'words' into a StringArray
    private void loadDictionary(){
        dictionary = new StringArray();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("words"));
            String row;
            while (((row = fileReader.readLine()) != null)){
                dictionary.add(row);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieves the sentence to be spell checked upon
    private void getSentence(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease enter your sentence: ");
        sentence = scanner.nextLine();
        sentenceArray = sentence.split("\\b");
    }

    // Appends each word that is not present within the dictionary to 'incorrectWords'
    private void findIncorrectWords(){
        // Removing all punctuation from the sentence
        String newSentence = sentence.replaceAll("\\p{Punct}", " ");
        // Splitting up the words
        String[] wordArray = newSentence.split("\\s+");

        // Checking to see which words are not in the dictionary and appending them to 'incorrectWords'
        incorrectWords = new StringArray();
        for (String word : wordArray){
            if (!dictionary.contains(word)){
                incorrectWords.add(word);
            }
        }

        // Prints out a list of the words not present within the dictionary
        System.out.println("\nThe following words were found not to be present in the dictionary: ");
        System.out.println(Arrays.toString(incorrectWords.convertToArray()));
    }

    // For each word in 'incorrectWords', uses Levenshtein's Algorithm to find words similar to it
    // The user is then asked to select one of the similar words to replace the incorrectWord with
    private void findSimilarWords(){
        for (String incorrectWord : incorrectWords.convertToArray()){
            StringArray similarWords = new StringArray();
            similarWords.add(incorrectWord);

            // Printing out words similar to incorrectWord and appending to the StringArray
            System.out.println("\nThe suggested options to replace " + incorrectWord + " are as follows:");
            System.out.println(similarWords.size() - 1 + ". " + incorrectWord);

            for (String dictionaryWord : dictionary.convertToArray()){
                int distance = findDistance(incorrectWord, dictionaryWord, incorrectWord.length(), dictionaryWord.length());
                if ( distance < 2){

                    if (Character.isUpperCase(incorrectWord.charAt(0))){
                        dictionaryWord = dictionaryWord.substring(0,1).toUpperCase() + dictionaryWord.substring(1).toLowerCase();
                    } else {
                        dictionaryWord = dictionaryWord.substring(0,1).toLowerCase() + dictionaryWord.substring(1).toLowerCase();
                    }

                    similarWords.add(dictionaryWord);
                    System.out.println(similarWords.size() - 1 + ". " + dictionaryWord);
                }
            }

            int option = getOption(similarWords.size() - 1);
            swap(incorrectWord, similarWords.get(option));
            System.out.println("'" + incorrectWord + "' has been swapped out for '" + similarWords.get(option) + "'");

        }
    }

    // Saves the edited sentence to a file
    private void saveSentence(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWould you like to save the sentence to a file? ( Y / N )");
        String saveSentenceOption = scanner.nextLine();
        System.out.println();
        if (!saveSentenceOption.equalsIgnoreCase("y")){
            return;
        }

        // Retrieving the name to save the file as
        System.out.println("Please enter the name of the file you wish to save the sentence to: ");
        String fileName = scanner.nextLine();

        String filePath = "sentences/" + fileName + ".txt";
        try {
            Files.createDirectories(Paths.get("sentences/"));
            if (new File(filePath).createNewFile()){
                FileWriter myWriter = new FileWriter(filePath);
                for (String component : sentenceArray){
                    myWriter.write(component);
                }
                myWriter.close();
            }

            System.out.println("The edited sentence has been saved to the file " + fileName + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an error saving the sentence to a file.");
        }
        System.out.println();

    }

    // Retrieves a valid option
    private int getOption(int maxOption){
        System.out.print("Which word in the suggestions would you like to replace it with? ( 0 - " + (maxOption) + " ): ");
        Scanner scan = new Scanner(System.in);

        // Ensures an integer has been selected
        while (!scan.hasNextInt()){
            scan.next();
            System.out.print("Please make sure to input an integer.\n");
            System.out.print("Which word in the suggestions would you like to replace it with? ( 0 - " + (maxOption) + " ): ");
        }

        // Reruns through the function if the integer is not of one of the option
        int option = scan.nextInt();
        if ((option < 0) || (option > maxOption)) {
            System.out.print("Please choose from one of the options available.\n");
            option = getOption(maxOption);
        }

        return option;
    }

    // Swaps the incorrectWord with the newWord selected
    private void swap(String incorrectWord, String newWord){
        for (int i = 0; i < sentenceArray.length; i++){
            if (sentenceArray[i].equals(incorrectWord)){
                sentenceArray[i] = newWord;
                break;
            }
        }
    }

    // Finds the minimum out of the 3 integers
    private int min(int x, int y, int z){
        if (x <= y && x <= z){
            return x;
        } else if ( y <= x && y <= z){
            return y;
        } else {
            return z;
        }
    }

    // Recursively calculate Levenshtein's distance between s1 and s2
    // m is the length of s1
    // n is the length of s2
    private int findDistance(String s1, String s2, int m ,int n){
        // Create a table to store results of subProblems
        int[][] dp = new int[m + 1][n + 1];

        // Fill d[][] in bottom up manner
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // If first string is empty, only option is
                // to insert all characters of second string
                if (i == 0) {
                    dp[i][j] = j; // Min. operations = j

                    // If second string is empty, only option is
                    // to remove all characters of second string
                } else if (j == 0) {
                    dp[i][j] = i; // Min. operations = i

                    // If last characters are same, ignore last
                    // char and recur for remaining string
                } else if ( Character.toLowerCase(s1.charAt(i - 1)) == Character.toLowerCase(s2.charAt(j - 1)) ){
                    dp[i][j] = dp[i - 1][j - 1];

                    // If the last character is different,
                    // consider all possibilities and find the
                    // minimum
                } else {
                    dp[i][j] = 1
                            + min(dp[i][j - 1], // Insert
                            dp[i - 1][j], // Remove
                            dp[i - 1][j - 1]); // Replace
                }
            }
        }

        return dp[m][n];
    }


}
