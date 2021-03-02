package controllers;

import lib.ConsoleIO;
import models.Players;
import models.RoleName;
import models.Roles;

import java.io.*;

import java.util.ArrayList;

public class WerewolfMainMenu {

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
        String fileName = "GameDirectory/";
        if(isCustomGame){
            String customGameName = ConsoleIO.promptForString("What is the file name: ", true);
            //Add Logic for the custom game play
        } else{
            //This will be the code underneath this
        }
        fileName += "Preset " + playerCount;
        //Get whatever game we are playing / read the file

        ArrayList<Players> playersThatArePlaying = new ArrayList<>();
        ArrayList<Roles> rolesInPlay = new ArrayList<>();

        //List the roles that will be in the game for that file
        ConsoleIO.displayString("Roles that are in the game:");
        for(Roles r : rolesInPlay){
            ConsoleIO.displayString(r.toString());
        }

        ConsoleIO.promptForString("Press ENTER to start the game: ",true);
        ConsoleIO.clearScreen();

        GameController.runGame(playersThatArePlaying);
    }

    /**
     * Makes a custom game that can be used for games
     */
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
                addToArray(currentCharacter);

            }
        }catch(IOException ioe){ConsoleIO.displayString("The file could not be found");}

    }

    private static void addToArray(String roleToAdd){

        switch (roleToAdd) {
            case "VILLAGER":
                Roles villager = new Roles();
                villager.setName(RoleName.VILLAGER);
                Players villagerPlayer = new Players();
                villagerPlayer.setCurrentRole(villager);
                villagerPlayer.setVillage(true);
                rolesInGame.add(villagerPlayer);
                break;
            case "SEER":
                Roles seer = new Roles();
                seer.setName(RoleName.SEER);
                Players seerPlayer = new Players();
                seerPlayer.setCurrentRole(seer);
                seerPlayer.setVillage(true);
                rolesInGame.add(seerPlayer);
                break;
            case "WEREWOLF":
                Roles werewolf = new Roles();
                werewolf.setName(RoleName.WEREWOLF);
                Players werewolfPlayer = new Players();
                werewolfPlayer.setCurrentRole(werewolf);
                werewolfPlayer.setVillage(false);
                rolesInGame.add(werewolfPlayer);
                break;
            case "CULT_LEADER":
                Roles cultLeader = new Roles();
                cultLeader.setName(RoleName.CULT_LEADER);
                Players cultLeaderPlayer = new Players();
                cultLeaderPlayer.setCurrentRole(cultLeader);
                cultLeaderPlayer.setVillage(true);
                rolesInGame.add(cultLeaderPlayer);
                break;
            case "LYCAN":
                Roles lycan = new Roles();
                lycan.setName(RoleName.LYCAN);
                Players lycanPlayer = new Players();
                lycanPlayer.setCurrentRole(lycan);
                lycanPlayer.setVillage(true);
                rolesInGame.add(lycanPlayer);
                break;
            case "CURSED":
                Roles cursed = new Roles();
                cursed.setName(RoleName.CURSED);
                Players cursedPlayer = new Players();
                cursedPlayer.setCurrentRole(cursed);
                cursedPlayer.setVillage(true);
                rolesInGame.add(cursedPlayer);
                break;
            case "TANNER":
                Roles tanner = new Roles();
                tanner.setName(RoleName.TANNER);
                Players tannerPlayer = new Players();
                tannerPlayer.setCurrentRole(tanner);
                tannerPlayer.setVillage(true);
                rolesInGame.add(tannerPlayer);
                break;
            case "WOLF_CUB":
                Roles wolfCub = new Roles();
                wolfCub.setName(RoleName.WOLF_CUB);
                Players wolfCubPlayer = new Players();
                wolfCubPlayer.setCurrentRole(wolfCub);
                wolfCubPlayer.setVillage(false);
                rolesInGame.add(wolfCubPlayer);
                break;
            case "CUPID":
                Roles cupid = new Roles();
                cupid.setName(RoleName.CUPID);
                Players cupidPlayer = new Players();
                cupidPlayer.setCurrentRole(cupid);
                cupidPlayer.setVillage(true);
                rolesInGame.add(cupidPlayer);
                break;
            case "APPRENTICE_SEER":
                Roles apprenticeSeer = new Roles();
                apprenticeSeer.setName(RoleName.APPRENTICE_SEER);
                Players apprenticeSeerPlayer = new Players();
                apprenticeSeerPlayer.setCurrentRole(apprenticeSeer);
                apprenticeSeerPlayer.setVillage(true);
                rolesInGame.add(apprenticeSeerPlayer);
                break;
            case "BODYGUARD":
                Roles bodyguard = new Roles();
                bodyguard.setName(RoleName.BODYGUARD);
                Players bodyguardPlayer = new Players();
                bodyguardPlayer.setCurrentRole(bodyguard);
                bodyguardPlayer.setVillage(true);
                rolesInGame.add(bodyguardPlayer);
                break;
            case "HUNTER":
                Roles hunter = new Roles();
                hunter.setName(RoleName.HUNTER);
                Players hunterPlayer = new Players();
                hunterPlayer.setCurrentRole();
                hunterPlayer.setVillage(true);
                rolesInGame.add(hunterPlayer);
                break;
            case "WITCH":
                Roles witch = new Roles();
                witch.setName(RoleName.WITCH);
                Players witchPlayer = new Players();
                witchPlayer.setCurrentRole(witch);
                witchPlayer.setVillage(true);
                rolesInGame.add(witchPlayer);
                break;
            case "LONE_WOLF":
                Roles loneWolf = new Roles();
                loneWolf.setName(RoleName.LONE_WOLF);
                Players loneWolfPlayer = new Players();
                loneWolfPlayer.setCurrentRole(loneWolf);
                loneWolfPlayer.setVillage(true);
                rolesInGame.add(loneWolfPlayer);
                break;
            case "MASON":
                Roles mason = new Roles();
                mason.setName(RoleName.MASON);
                Players masonPlayer = new Players();
                masonPlayer.setCurrentRole(mason);
                masonPlayer.setVillage(true);
                rolesInGame.add(masonPlayer);
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
    public static void addTextToFile(String filePath, String text){

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
