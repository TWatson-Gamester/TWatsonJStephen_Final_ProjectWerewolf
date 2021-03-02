package controllers;

import lib.ConsoleIO;
import models.Players;
import models.RoleName;
import models.Roles;

import java.io.*;

import java.util.ArrayList;
import java.util.Random;

public class WerewolfMainMenu {

    private static ArrayList<Roles> rolesInGame = new ArrayList<>();
    private static ArrayList<Players> playersInGame = new ArrayList<>();
    private static final String dirName = "GameDirectory";

    /**
     * To start up the process of the game
     */
    public static void run() {
        boolean continueLoop = true;
        String[] menuOptions = {
                "Play Game",
                "Make a Custom Game",
                "View Games"
        };

        createPresets();

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

    /**
     * Sets up the game for playing
     */
    private static void playGame(){
        int playerCount = ConsoleIO.promptForInt("How many Players are there: ", 6, 6);
        boolean isCustomGame = ConsoleIO.promptForBoolean("Is this going to be a Custom Game? Y for yes, N for no", "Y", "N");
        if(isCustomGame){
            String customGameName = ConsoleIO.promptForString("What is the file name: ", true);
            if(new File(dirName + "/" + customGameName).exists()){
                readRolesFromFile(customGameName);
            }else{
                ConsoleIO.displayString("The file you searched for does not exist, we have given you " +
                        "the default " + playerCount + " player game");
                readRolesFromFile("Preset" + playerCount);
            }
            //Add Logic for the custom game play
        } else{
            //This will be the code underneath this
            readRolesFromFile("Preset" + playerCount);
        }

        //List the roles that will be in the game for that file
        ConsoleIO.displayString("Roles that are in the game:");
        for(Roles r : rolesInGame){
            ConsoleIO.displayString(r.toString());
        }

        //randomize

        ConsoleIO.promptForString("Press ENTER to start the game: ",true);
        ConsoleIO.clearScreen();

        GameController.runGame(playersInGame);
    }

    private static void randomizeCharacters(){
        Random rng = new Random();
        int totalRoles = rolesInGame.size() - 1;
        for(int i = 0; i < totalRoles; i++){
            int roleToAddAndRemove = rng.nextInt(rolesInGame.size());
            addToPlayerArray(rolesInGame.get(roleToAddAndRemove));
            rolesInGame.remove(roleToAddAndRemove);
        }
    }

    /**
     * Makes a custom game that can be used for games
     */
    private static void makeCustom(){
        boolean invalidName = true;

        do {
            String nameOfFile = ConsoleIO.promptForString("What would you like to name this file: ", false);
            //Check to see if this is an allowed file name
            if(new File(dirName + "/" + nameOfFile).exists()){
                ConsoleIO.displayString("The file you are attempting to create already exists, please enter another name");
            }else{
                ConsoleIO.displayString("Your file \"" + nameOfFile + "\" has been created in the \"" + dirName + "\" folder");
                invalidName = false;
            }
        }while(invalidName);

        int numberOfPlayers = ConsoleIO.promptForInt("How many players are there: ", 6, 20);
        String[] availableRoles = {
            "VILLAGER",
            "WEREWOLF",
            "SEER",
            "HUNTER",
            "BODYGUARD",
            "APPRENTICE_SEER",
            "CUPID",
            "WOLF_CUB",
            "TANNER",
            "CURSED",
            "LYCAN",
            "CULT_LEADER",
            "MASON",
            "LONE_WOLF",
            "WITCH"
        };
        ArrayList<String> addedRoles = new ArrayList<>();
        for(int i = 0; i < numberOfPlayers; i++){
            int menuSelection = ConsoleIO.promptForMenuSelection(availableRoles, false);
            addedRoles.add(availableRoles[menuSelection-1]);
        }
        //Make the file here
    }

    /**
     * Shows the players what is in the games
     */
    private static void viewGames(){
        //Not sure how we want to implement this yet
    }

    /**
     * Creates the preset games
     */
    private static void createPresets(){
        String fileName = "Preset";
        createDirectory(dirName);
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

    /**
     * Gets roles from a selected file
     *
     */
    private static void readRolesFromFile(String fileName){

        try{
            BufferedReader buffy = new BufferedReader(new FileReader(dirName + "/" + fileName));
            buffy.readLine();
            String players = buffy.readLine();
            int numOfCharacters = Integer.valueOf(players);

            for(int i = 0; i < numOfCharacters; i++){

                String currentCharacter = buffy.readLine();
                addToRolesArray(currentCharacter);

            }
        }catch(IOException ioe){ConsoleIO.displayString("The file could not be found");}

    }

    /**
     * Adds Players to the players array list
     * @param roleToAdd the role of the player you with to add to the list (Roles)
     */
    private static void addToPlayerArray(Roles roleToAdd){
        switch (roleToAdd.getName()) {
            case VILLAGER:
            case SEER:
            case CULT_LEADER:
            case LYCAN:
            case CURSED:
            case TANNER:
            case CUPID:
            case APPRENTICE_SEER:
            case BODYGUARD:
            case HUNTER:
            case WITCH:
            case MASON:
                Players villagePlayer = new Players();
                villagePlayer.setCurrentRole(roleToAdd);
                villagePlayer.setVillage(true);
                playersInGame.add(villagePlayer);
                break;
            case LONE_WOLF:
            case WOLF_CUB:
            case WEREWOLF:
                Players werewolfPlayer = new Players();
                werewolfPlayer.setCurrentRole(roleToAdd);
                werewolfPlayer.setVillage(false);
                playersInGame.add(werewolfPlayer);
                break;
        }
    }

    /**
     * Adds Roles to the roles array list
     * @param roleToAdd the role of the player you would like to add to the game (String)
     */
    private static void addToRolesArray(String roleToAdd){
        switch (roleToAdd) {
            case "VILLAGER":
                Roles villager = new Roles();
                villager.setName(RoleName.VILLAGER);
                rolesInGame.add(villager);
                break;
            case "SEER":
                Roles seer = new Roles();
                seer.setName(RoleName.SEER);
                rolesInGame.add(seer);
                break;
            case "WEREWOLF":
                Roles werewolf = new Roles();
                werewolf.setName(RoleName.WEREWOLF);
                rolesInGame.add(werewolf);
                break;
            case "CULT_LEADER":
                Roles cultLeader = new Roles();
                cultLeader.setName(RoleName.CULT_LEADER);
                rolesInGame.add(cultLeader);
                break;
            case "LYCAN":
                Roles lycan = new Roles();
                lycan.setName(RoleName.LYCAN);
                rolesInGame.add(lycan);
                break;
            case "CURSED":
                Roles cursed = new Roles();
                cursed.setName(RoleName.CURSED);
                rolesInGame.add(cursed);
                break;
            case "TANNER":
                Roles tanner = new Roles();
                tanner.setName(RoleName.TANNER);
                rolesInGame.add(tanner);
                break;
            case "WOLF_CUB":
                Roles wolfCub = new Roles();
                wolfCub.setName(RoleName.WOLF_CUB);
                rolesInGame.add(wolfCub);
                break;
            case "CUPID":
                Roles cupid = new Roles();
                cupid.setName(RoleName.CUPID);
                rolesInGame.add(cupid);
                break;
            case "APPRENTICE_SEER":
                Roles apprenticeSeer = new Roles();
                apprenticeSeer.setName(RoleName.APPRENTICE_SEER);
                rolesInGame.add(apprenticeSeer);
                break;
            case "BODYGUARD":
                Roles bodyguard = new Roles();
                bodyguard.setName(RoleName.BODYGUARD);
                rolesInGame.add(bodyguard);
                break;
            case "HUNTER":
                Roles hunter = new Roles();
                hunter.setName(RoleName.HUNTER);
                rolesInGame.add(hunter);
                break;
            case "WITCH":
                Roles witch = new Roles();
                witch.setName(RoleName.WITCH);
                rolesInGame.add(witch);
                break;
            case "LONE_WOLF":
                Roles loneWolf = new Roles();
                loneWolf.setName(RoleName.LONE_WOLF);
                rolesInGame.add(loneWolf);
                break;
            case "MASON":
                Roles mason = new Roles();
                mason.setName(RoleName.MASON);
                rolesInGame.add(mason);
        }
    }

    /**
     * Needs to be finished
     * @param directory
     */
    private static void createDirectory(String directory) {
        File file = new File(".", directory);
        file.mkdir();
    }

    /**
     * Needs to be finished
     * @param filePath
     * @param text
     */
    private static void writeTextToFile(String filePath, String text){

        try(FileWriter myWriter = new FileWriter(filePath)){
            myWriter.write(text);
        }catch(IOException ioe){
            System.out.println("An error has occurred");
        }
    }

    /**
     * Needs to be finished
     * @param filePath
     * @param text
     */
    private static void addTextToFile(String filePath, String text){

        text = readTextFromFile(filePath) + "\n" + text;

        try(FileWriter myWriter = new FileWriter(filePath)){
            myWriter.write(text);
        }catch(IOException ioe){
            System.out.println("An error has occurred");
        }

    }

    /**
     * Needs to be finished
     * @param filePath
     * @return
     */
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
