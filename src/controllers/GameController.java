package controllers;

import lib.ConsoleIO;
import models.Players;
import models.RoleName;

import java.util.ArrayList;

public class GameController {
    private static ArrayList<Players> villagePeople;
    private static ArrayList<Players> graveyard;
    private static boolean isDay = false;

    public static void runGame(){

    }

    private static void dayTime(){

    }

    private static void nightTime(){

    }

    private static void searchPlayer(RoleName searcher){
        String[] menuOptions = new String[villagePeople.size()];
        for(int i = 0; i < villagePeople.size(); i++){
            menuOptions[i] = "Player " + villagePeople.get(i).getSeatNumber();
        }
        int searchedPerson;
        switch (searcher){
            case SEER:
                searchedPerson = ConsoleIO.promptForMenuSelection(menuOptions,false);
                if(villagePeople.get(searchedPerson).isVillage()){
                    ConsoleIO.displayString("Is on Village team");
                } else{
                    ConsoleIO.displayString("Is on Werewolf team");
                }
                break;
            default:
                ConsoleIO.displayString("How did you even get here???");
        }
    }

    private static String outputGraveyard(){
        String returnString = "";
        return null;
    }

    private static void sendToGrave(Players player, boolean openGrave){

    }

    private static void discussionTime(){

    }

    private static void trialTime(){

    }

    private static void votingTime(Players player){

    }

    private static void votingTime(Players player1, Players player2){

    }

}
