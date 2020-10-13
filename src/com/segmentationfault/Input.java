/**class used to get all sorts of input.*/

package com.segmentationfault;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Input {

    public static final Scanner sc = new Scanner(System.in);

    /**gets an integer from the user between min and max (inclusive), checks for exceptions*/
    public static int getIntInput(int min, int max){
        int input = 0;
        boolean done = false;

        while(!done){
            try{
                input = sc.nextInt(); //gets input, may have an exception if user enters a string
                if(input>=min && input<=max && input!=0){ //checks if its in the range
                    sc.nextLine();
                    done = true;
                }
                else if(input == 0){
                    System.exit(0);
                }
                else{
                    System.out.println("Type in a valid number between "+min+" and "+max);
                }
            }
            catch(InputMismatchException e){ //catches case user types in a string
                sc.nextLine();
                System.out.println("Please type in a valid number.");
            }
        }
        return input;
    }

    /**gets a string from the user, checks for exceptions*/
    public static String getStringInput(){
        String input = "";
        boolean done = false;

        while(!done){
            try{
                input = sc.nextLine(); //gets input, may have an exception if user does not type in a string
                if(input.equals("")){ //checks for empty string
                    System.out.println("You have to type in something!");
                    continue;
                }
                else if(input.toLowerCase().equals("exit")){
                    System.exit(0);
                }
                //sc.nextLine();
                done = true;
            }
            catch(InputMismatchException e){ //in case user does not type in a string
                sc.nextLine();
                System.out.println("Please type in a valid string.");
            }
        }
        return input;
    }

    /**gets a valid path from the user*/
    public static String getValidPath(){
        boolean done = false;
        String input = getStringInput();
        File file = new File(input);
        while(!done){
            if(file.exists()){
                done = true;
            }
            else{
                System.out.println("That path was invalid, please type in a valid path or 'exit' to exit.");
                input = getStringInput();
                file = new File(input);
            }
        }
        return input;
    }

}
