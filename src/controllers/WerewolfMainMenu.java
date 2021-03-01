package controllers;

import lib.ConsoleIO;

import java.io.*;

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

    }

    private static void makeCustom(){

    }

    private static void viewGames(){

    }

    private static void createPresets(){
        String dirName = "GameDirectory";
        String fileName = "Preset";
        createDirectory("GameDirectory");
        for(int i = 6; i <= 20; i++) {
            switch (i) {
                case 6:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" + i +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nWEREWOLF"
                    );
                    break;
                case 7:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" + i +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nHUNTER" +
                            "\nWEREWOLF"
                    );
                    break;
                case 8:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" + i +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nWEREWOLF"
                    );
                    break;
                case 9:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" + i +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF"
                    );
                    break;
                case 10:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" + i +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF"
                    );
                    break;
                case 11:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" + i + "\n" +
                            "VILLAGER\n" +
                            "VILLAGER\n" +
                            "VILLAGER\n" +
                            "VILLAGER\n" +
                            "SEER\n" +
                            "APPRENTICE_SEER\n" +
                            "HUNTER\n" +
                            "BODYGUARD\n" +
                            "CUPID\n" +
                            "WEREWOLF\n" +
                            "WEREWOLF"
                    );
                    break;
                case 12:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nCUPID" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF" +
                            "\nWOLF_CUB"
                    );
                    break;
                case 13:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nCUPID" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF" +
                            "\nWOLF_CUB" +
                            "\nTANNER"
                    );
                    break;
                case 14:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nCUPID" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF" +
                            "\nWOLF_CUB" +
                            "\nCURSED" +
                            "\nTANNER"
                    );
                    break;
                case 15:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nCUPID" +
                            "\nLYCAN" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF" +
                            "\nWOLF_CUB" +
                            "\nCURSED" +
                            "\nTANNER"
                    );
                    break;

                case 16:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nCUPID" +
                            "\nLYCAN" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF" +
                            "\nWOLF_CUB" +
                            "\nCURSED" +
                            "\nTANNER" +
                            "\nCULT_LEADER"
                    );
                    break;

                case 17:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
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

                case 18:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nCUPID" +
                            "\nLYCAN" +
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

                case 19:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nCUPID" +
                            "\nLYCAN" +
                            "\nMASON" +
                            "\nMASON" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF" +
                            "\nWOLF_CUB" +
                            "\nCURSED" +
                            "\nLONE_WOLF" +
                            "\nTANNER" +
                            "\nCULT_LEADER"
                    );
                    break;
                default:
                    writeTextToFile(dirName + "/" + fileName + i, "Preset " + i + "\n" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nVILLAGER" +
                            "\nSEER" +
                            "\nAPPRENTICE_SEER" +
                            "\nHUNTER" +
                            "\nBODYGUARD" +
                            "\nCUPID" +
                            "\nLYCAN" +
                            "\nMASON" +
                            "\nMASON" +
                            "\nWITCH" +
                            "\nWEREWOLF" +
                            "\nWEREWOLF" +
                            "\nWOLF_CUB" +
                            "\nCURSED" +
                            "\nLONE_WOLF" +
                            "\nTANNER" +
                            "\nCULT_LEADER"
                    );
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
