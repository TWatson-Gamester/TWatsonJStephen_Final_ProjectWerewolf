package controllers;

import lib.ConsoleIO;
import models.Players;
import models.RoleName;
import models.Roles;

import java.util.ArrayList;

public class GameController {
    private static ArrayList<Players> originalCast = new ArrayList<>();
    private static ArrayList<Players> villagePeople = new ArrayList<>();
    private static ArrayList<Players> graveyard = new ArrayList<>();
    private static boolean isDay = false;
    private static int dayNumber = 0;

    public static void runGame(){

        Roles villager = new Roles();
        villager.setName(RoleName.VILLAGER);
        Roles werewolf = new Roles();
        werewolf.setName(RoleName.WEREWOLF);
        Roles seer = new Roles();
        seer.setName(RoleName.SEER);

        Players villagerPlayer = new Players();
        villagerPlayer.setCurrentRole(villager);
        villagerPlayer.setDead(false);
        villagerPlayer.setVillage(true);

        Players werewolfPlayer = new Players();
        werewolfPlayer.setCurrentRole(werewolf);
        werewolfPlayer.setDead(false);
        werewolfPlayer.setVillage(true);

        Players seerPlayer = new Players();
        seerPlayer.setCurrentRole(seer);
        seerPlayer.setDead(false);
        seerPlayer.setVillage(true);

        originalCast.add(villagerPlayer);
        originalCast.add(villagerPlayer);
        originalCast.add(villagerPlayer);
        originalCast.add(villagerPlayer);
        originalCast.add(werewolfPlayer);
        originalCast.add(seerPlayer);

        villagePeople = originalCast;
        dayTime();

    }

    /**
     * This method will run everything for you during the day, including:
     * Showing the graveyard,
     * Discussion time for the players,
     * Putting people on Trial (if needed),
     * Killing people at the trial (if needed),
     * Showing the graveyard a second time,
     * Returning to the game for nighttime,
     */
    private static void dayTime() {
        ConsoleIO.displayString("\nDay " + dayNumber);

        //show Graveyard
        ConsoleIO.displayString("\n" + outputGraveyard());

        //discussion time
        //run until the moderator presses enter
        ConsoleIO.promptForString("It is now discussion time\n" +
                "Press enter when discussion time is over.", true);
        ConsoleIO.displayString("");

        //put people on trial
        //ask who they want to put on trial
        //whoever has the most votes is put to trial
        //IF there are 3 or more people tied to be put on trial, say there is no conclusion and skip trial
        //IF nobody is put on trial, say nobody was put on trial, then skip the trial
        //IF 1 or 2 people are on trial run the trial

        int peopleOnTrial = ConsoleIO.promptForInt("How many people are voted to be on trial", Integer.MIN_VALUE, Integer.MAX_VALUE);

        if (peopleOnTrial > 0 && peopleOnTrial < 3) {
            if (peopleOnTrial == 1) {
                int playerOnTrial = ConsoleIO.promptForInt("Seat of who is on trial: ", 1, originalCast.size());
                votingTime(playerOnTrial);
            } else {
                int firstOnTrial = ConsoleIO.promptForInt("Seat of the first person on trial on trial: ", 1, originalCast.size());
                int secondOnTrial = ConsoleIO.promptForInt("Seat of the second person on trial on trial: ", 1, originalCast.size());
                votingTime(firstOnTrial, secondOnTrial);
            }
        } else {
            ConsoleIO.displayString("There will be no trial tonight");
        }

        //show Graveyard
        ConsoleIO.displayString("\n" + outputGraveyard());

        ConsoleIO.displayString("The time is now 10:00 P.M.\nAs such, it is now officially nighttime." +
                "\nOk then...sweet dreams, everyone! Goodnight, sleep tight, dont let the werewolves bite...\n");
    }

    /**
     * Goes through each night time even and sees if it needs to wake that player up or not
     * Switch Cases:
     *      Seer - Investigates the players that are alive for a werewolf
     *      Werewolf / Werewolves kill a player
     */
    private static void nightTime(){
        //Seer
        for(Players seer : villagePeople){
            if(seer.getCurrentRole().getName() == RoleName.SEER){
                searchPlayer(RoleName.SEER);
            }
        }
        //Werewolf
        String[] menuOptions = new String[villagePeople.size()];
        for(int i = 0; i < villagePeople.size(); i++){
            menuOptions[i] = "Player " + villagePeople.get(i).getSeatNumber();
        }
        ConsoleIO.displayString("Werewolf / Werewolves please choose a player to eliminate");
        int playerToRemove = ConsoleIO.promptForMenuSelection(menuOptions, false);
        sendToGrave(originalCast.get(playerToRemove-1),false);
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
                ConsoleIO.displayString("Seer please choose a player to investigate");
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
                    igor.append(deadPeople.getCurrentRole().getName());
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
        player.setOpenGrave(openGrave);
    }

    private static void discussionTime(){

    }

    private static void trialTime(){

    }

    private static void votingTime(int player) {
        int votesToKill = ConsoleIO.promptForInt("Votes to kill player " + player + ": ", 0, villagePeople.size());
        votesToKill -= ConsoleIO.promptForInt("Votes to save player " + player + ": ", 0, villagePeople.size());

        if (votesToKill > 0) {
            ConsoleIO.displayString("Player " + player + " has been sent to the graveyard.");
            sendToGrave(originalCast.get(player - 1), true);
        } else {
            ConsoleIO.displayString("There was a tie so nobody was killed, Igor is pleased");
        }
    }

    private static void votingTime(int player1, int player2) {

        int votesToKillPlayer1 = ConsoleIO.promptForInt("Votes to kill player" + player1 + ": ", 0, villagePeople.size());
        votesToKillPlayer1 -= ConsoleIO.promptForInt("Votes to save player" + player1 + ": ", 0, villagePeople.size());


        int votesToKillPlayer2 = ConsoleIO.promptForInt("Votes to kill player " + player2 + ": ", 0, villagePeople.size());
        votesToKillPlayer2 -= ConsoleIO.promptForInt("Votes to save player " + player2 + ": ", 0, villagePeople.size());

        if (votesToKillPlayer1 > 0 || votesToKillPlayer2 > 0) {

            if (votesToKillPlayer1 > votesToKillPlayer2) {
                ConsoleIO.displayString("Player " + player1 + " has been sent to the graveyard.");
                sendToGrave(originalCast.get(player1 - 1), true);
            } else if (votesToKillPlayer1 < votesToKillPlayer2) {
                ConsoleIO.displayString("Player " + player2 + " has been sent to the graveyard.");
                sendToGrave(originalCast.get(player2 - 1), true);
            } else {
                ConsoleIO.displayString("There was a tie so nobody was killed, Igor is pleased");
            }
        } else {
            ConsoleIO.displayString("Neither player had enough votes to kill so nobody will die. Igor is pleased.");
        }
    }

}