import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
//        System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
  
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

  public static double totalSentiment(String fileName) {
    String file_content = textToString(fileName);
    double total = 0;
    String current_word;
    int space_ind;
    double word_score;
    double average;
    double count = 0;
    double adjusted_word_score;
    double min = -3.49;
    double max = 2.81;

    while (file_content.indexOf(" ") != -1) {
      space_ind = file_content.indexOf(" ");
      current_word = file_content.substring(1, space_ind);
      file_content = file_content.substring(space_ind + 1);
      word_score = sentimentVal(current_word);
      total += word_score;
      count += 1;
    }
    return total;

  }
  public static int starRating(String fileName) {
    double score = totalSentiment(fileName);
    int star;

    if (score<0) {
      star=1;
    }
    else if (score < 3) {
      star = 2;
    }
    else if (score < 6) {
      star = 3;
    }
    else if (score < 9) {
      star = 4;
    }
    else {
      star = 5;
    }

    return star;

  }

  public static String fakeReview(String filename) {
    int score = starRating(filename);
    String file_content = textToString(filename);
    int space_ind;
    int add = 0;
    String current_word;
    String new_content ="";

    if(score >= 3) {
      //this will be to replace with negative adjectives
      while (file_content.contains("*")) {
        if (file_content.contains(" ")) {
          space_ind = file_content.indexOf(" ");
          add = 1;
        }
        else {
          space_ind = file_content.length();
          add = 0;
        }
        current_word = file_content.substring(0, space_ind);
        if (Character.compare(current_word.charAt(0), '*') == 0) {
          String current_adj = randomNegativeAdj();
          if (current_word.substring(current_word.length() - 1).equals("y")) {
            while (current_adj.substring(current_adj.length() - 1).equals("y") == false) {
              current_adj = randomNegativeAdj();
              System.out.println(current_adj);
              System.out.println(current_word);
              System.out.println();
            }
          }
          current_word = current_adj;
        }
        file_content = file_content.substring(space_ind + add);
        new_content += current_word + " ";
      }
      new_content += file_content;
      return new_content;
    }
    else {
      while (file_content.contains("*")) {
        if (file_content.contains(" ")) {
          space_ind = file_content.indexOf(" ");
          add = 1;
        }
        else {
          space_ind = file_content.length();
          add = 0;
        }
        current_word = file_content.substring(0, space_ind);
        if (Character.compare(current_word.charAt(0), '*') == 0) {
          current_word = randomPositiveAdj();
        }
        file_content = file_content.substring(space_ind + add);
        new_content += current_word + " ";
      }
      return new_content;
    }
  }
}
