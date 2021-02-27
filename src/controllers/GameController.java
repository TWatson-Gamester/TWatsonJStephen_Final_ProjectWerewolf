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

    /**
     * This method takes in a Players Role and switches off of that to figure out information about their fellow players,
     * should only be used by the players that actually have a role that lets them investigates.
     * @param searcher: the player / role that is investigating something about a player
     */
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

    /**
     * Outputs the players that have died in the game, and if the player's role is allowed to be revealed
     * @return The String of the players that have died
     */
    private static String outputGraveyard(){
        StringBuilder igor = new StringBuilder("The current dead players are: \n");
        if(graveyard.size() != 0){
            for(Players deadPeople : graveyard){
                igor.append("Player ").append(deadPeople.getSeatNumber()).append(" Role: ");
                if(deadPeople.getOpenGrave()){
                    igor.append(deadPeople.getCurrentRole());
                } else{
                    igor.append("Unknown");
                }
                igor.append('\n');
            }
        }else{
            igor.append("Igor hasn't had to do anything yet, he is very happy\n");
        }
        return igor.toString();
    }

    /**
     * Removes player from villagePeople and adds them to the graveyard, also sets if their
     * role is allowed to be seen by the other players
     * @param player: the player that is getting sent to the graveyard
     * @param openGrave: is the player's role to be revealed
     */
    private static void sendToGrave(Players player, boolean openGrave){
        villagePeople.remove(player);
        graveyard.add(player);
        if(openGrave){
            player.setOpenGrave(true);
        }else{
            player.setOpenGrave(false);
        }
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
