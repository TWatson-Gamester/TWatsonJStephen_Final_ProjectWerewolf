package controllers;

import lib.ConsoleIO;

import java.io.*;

import java.util.ArrayList;

public class WerewolfMainMenu {

    public static void run() {
        boolean continueLoop = true;
        String[] menuOptions = {
                "Play Game",
                "Make a Custom Game",
                "View Games"
        };
        do {
            int menuOption = ConsoleIO.promptForMenuSelection(menuOptions, true);
            switch (menuOption){
                case 0:
                    continueLoop = false;
                    break;
                case 1:
                    playGame();
                    break;
                case 2:
                    makeCustom();
                    break;
                case 3:
                    viewGames();
                    break;
            }
        } while(continueLoop);
        ConsoleIO.displayString("Thank you for playing our game.");
    }

    private static void playGame(){
        int playerCount = ConsoleIO.promptForInt("How many Players are there: ", 6, 6);
        boolean isCustomGame = ConsoleIO.promptForBoolean("Is this going to be a Custom Game? Y for yes, N for no", "Y", "N");
        String fileName = "GameDirectory/";
        if(isCustomGame){
            String customGameName = ConsoleIO.promptForString("What is the file name: ", true);
            //Add Logic for the custom game play
        } else{
            //This will be the code underneath this
        }
        fileName += "Preset " + playerCount;
        //Get whatever game we are playing / read the file

        //List the roles that will be in the game for that file

        ConsoleIO.promptForString("Press ENTER to start the game: ",true);
        ConsoleIO.clearScreen();

        //Add this later
//        GameController.runGame();
    }

    private static void makeCustom(){
        String nameOfFile = ConsoleIO.promptForString("What would you like to name this file: ", false);
        //Check to see if this is an allowed file name
        int numberOfPlayers = ConsoleIO.promptForInt("How many players are there: ", 6, 20);
        String[] availableRoles = {
            "VILLAGER +1",
            "WEREWOLF -6",
            "SEER +7",
            "HUNTER +3",
            "BODYGUARD +3",
            "APPRENTICE_SEER +4",
            "CUPID -3",
            "WOLF_CUB -8",
            "TANNER -2",
            "CURSED -3",
            "LYCAN -1",
            "CULT_LEADER +1",
            "MASON +2",
            "LONE_WOLF -5",
            "WITCH +4"
        };
        ArrayList<String> addedRoles = new ArrayList<>();
        for(int i = 0; i < numberOfPlayers; i++){
            int menuSelection = ConsoleIO.promptForMenuSelection(availableRoles, false);
            addedRoles.add(availableRoles[menuSelection-1]);
        }
        //Make the file here
    }

    private static void viewGames(){
        //Not sure how we want to implement this yet
    }

    private static void createPresets(){
        String dirName = "GameDirectory";
        String fileName = "Preset";
        createDirectory("GameDirectory");
        for(int i = 6; i <= 20; i++) {
            writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" + i);
            switch (i) {
                case 16:
                    addTextToFile(dirName + "/" + fileName + i,
                            "CULT_LEADER"
                    );
                case 15:
                    addTextToFile(dirName + "/" + fileName + i,
                            "LYCAN"
                    );
                case 14:
                    addTextToFile(dirName + "/" + fileName + i,
                            "CURSED"
                    );
                case 13:
                    addTextToFile(dirName + "/" + fileName + i,
                            "TANNER"
                    );
                case 12:
                    addTextToFile(dirName + "/" + fileName + i,
                            "WOLF_CUB"
                    );
                case 11:
                    addTextToFile(dirName + "/" + fileName + i,
                            "CUPID"
                    );
                case 10:
                    addTextToFile(dirName + "/" + fileName + i,
                            "APPRENTICE_SEER"
                    );
                case 9:
                    addTextToFile(dirName + "/" + fileName + i,
                            "WEREWOLF"
                    );
                case 8:
                    addTextToFile(dirName + "/" + fileName + i,
                            "BODYGUARD"
                    );
                case 7:
                    addTextToFile(dirName + "/" + fileName + i,
                            "HUNTER"
                    );
                case 6:
                    addTextToFile(dirName + "/" + fileName + i,
                            "VILLAGER" +
                                    "\nVILLAGER" +
                                    "\nVILLAGER" +
                                    "\nVILLAGER" +
                                    "\nSEER" +
                                    "\nWEREWOLF");
                    break;

                case 20:
                    addTextToFile(dirName + "/" + fileName + i,
                            "WITCH"
                    );
                case 19:
                    addTextToFile(dirName + "/" + fileName + i,
                            "LONE_WOLF"
                    );
                case 18:
                    addTextToFile(dirName + "/" + fileName + i,
                            "LYCAN"
                    );
                case 17:
                    addTextToFile(dirName + "/" + fileName + i,
                            "VILLAGER" +
                                    "\nVILLAGER" +
                                    "\nVILLAGER" +
                                    "\nVILLAGER" +
                                    "\nSEER" +
                                    "\nAPPRENTICE_SEER" +
                                    "\nHUNTER" +
                                    "\nBODYGUARD" +
                                    "\nCUPID" +
                                    "\nMASON" +
                                    "\nMASON" +
                                    "\nWEREWOLF" +
                                    "\nWEREWOLF" +
                                    "\nWOLF_CUB" +
                                    "\nCURSED" +
                                    "\nTANNER" +
                                    "\nCULT_LEADER"
                    );
                    break;
            }
        }
    }

    private static void createDirectory(String directory) {
        File file = new File(".", directory);
        file.mkdir();
    }

    private static void writeTextToFile(String filePath, String text){

        try(FileWriter myWriter = new FileWriter(filePath)){
            myWriter.write(text);
        }catch(IOException ioe){
            System.out.println("An error has occurred");
        }
    }

    public static void addTextToFile(String filePath, String text){

        text = readTextFromFile(filePath) + "\n" + text;

        try(FileWriter myWriter = new FileWriter(filePath)){
            myWriter.write(text);
        }catch(IOException ioe){
            System.out.println("An error has occurred");
        }

    }

    private static String readTextFromFile(String filePath){

        String returnString = null;
        StringBuilder sb = new StringBuilder();

        try(BufferedReader inputRead = new BufferedReader(new FileReader(filePath))){

            char[] inputBuffer = new char[50];
            int charRead;

            while ((charRead = inputRead.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                sb.append(readString);
            }
            returnString = sb.toString();

        }catch (IOException ioe){
            System.out.println("An error has occurred");
        }

        return returnString;
    }
}
