package controllers;

import lib.ConsoleIO;
import models.Players;
import models.RoleName;
import models.Roles;

import java.util.ArrayList;

public class GameController {
    private static ArrayList<Players> originalCast;
    private static ArrayList<Players> villagePeople;
    private static ArrayList<Players> graveyard;
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
        for(int i = 0; i < 3; i++) {
            dayTime();
            dayNumber++;
        }
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
    private static void dayTime(){
        ConsoleIO.displayString("Day " + dayNumber + "\nDiscussion Time");

        //show Graveyard
        ConsoleIO.displayString(outputGraveyard());

        //discussion time
            //run until the moderator presses enter
        ConsoleIO.promptForString("Press enter when discussion time is over.",true);

        //put people on trial
            //ask who they want to put on trial
            //whoever has the most votes is put to trial
            //IF there are 3 or more people tied to be put on trial, say there is no conclusion and skip trial
            //IF nobody is put on trial, say nobody was put on trial, then skip the trial
            //IF 1 or 2 people are on trial run the trial

        int peopleOnTrial = ConsoleIO.promptForInt("How many people are voted to be on trial", Integer.MIN_VALUE, Integer.MAX_VALUE);

        if(peopleOnTrial > 0 && peopleOnTrial < 3){
            if(peopleOnTrial == 1){

                int playerOnTrial = ConsoleIO.promptForInt("Seat of who is on trial: ", 1, villagePeople.size() + graveyard.size());
                int votesToKill = ConsoleIO.promptForInt("Votes to kill player " + playerOnTrial, 0, villagePeople.size());
                votesToKill -= ConsoleIO.promptForInt("Votes to save player " + playerOnTrial, 0, villagePeople.size());

                if(votesToKill > 0){
                    ConsoleIO.displayString("Player " + playerOnTrial + " has been sent to the graveyard.");
                    sendToGrave(originalCast.get(playerOnTrial - 1),true);
                }

            }else{

                int firstOnTrial = ConsoleIO.promptForInt("Seat of the first person on trial on trial: ", 1, villagePeople.size() + graveyard.size());
                int votesToKillPlayer1 = ConsoleIO.promptForInt("Votes to kill " + firstOnTrial, 0, villagePeople.size());
                votesToKillPlayer1 -= ConsoleIO.promptForInt("Votes to save " + firstOnTrial, 0, villagePeople.size());

                int secondOnTrial = ConsoleIO.promptForInt("Seat of the second person on trial on trial: ", 1, villagePeople.size() + graveyard.size());
                int votesToKillPlayer2 = ConsoleIO.promptForInt("Votes to kill " + secondOnTrial, 0, villagePeople.size());
                votesToKillPlayer2 -= ConsoleIO.promptForInt("Votes to save " + secondOnTrial, 0, villagePeople.size());

                if(votesToKillPlayer1 > 0 || votesToKillPlayer2 > 0){

                    if(votesToKillPlayer1 > votesToKillPlayer2){
                        ConsoleIO.displayString("Player " + firstOnTrial + " has been sent to the graveyard.");
                        sendToGrave(originalCast.get(firstOnTrial - 1),true);
                    }else if(votesToKillPlayer1 < votesToKillPlayer2){
                        ConsoleIO.displayString("Player " + secondOnTrial + " has been sent to the graveyard.");
                        sendToGrave(originalCast.get(secondOnTrial - 1),true);
                    }else{
                        ConsoleIO.displayString("There was a tie so nobody was killed, igor is pleased");
                    }
                }
            }
        }else{
            ConsoleIO.displayString("There will be no trial tonight.");
        }


        //vote people off the island
            //See how many people are on trial
                //IF 1
                    //get votes to kill and votes to save
                    //see if more to kill or more to save
                //IF 2
                    //get votes to kill or save for player 1
                    //get votes to kill or save player 2
                    //make sure at least one of them has at least 1 vote to kill
                        //IF at least 1 has more than 1 vote to kill
                            //see who has more votes and set them to be killed
                            //if they are tied nobody dies
                        //ELSE
                            //both people live

            //IF someone dies, send to graveyard
            //ELSE continue


        //show Graveyard
        ConsoleIO.displayString(outputGraveyard());

        ConsoleIO.displayString("The time is now 10:00 P.M.\nAs such, it is now officially nighttime." +
                "\nOk then...sweet dreams, everyone! Goodnight, sleep tight, dont let the werewolves bite...");
    }

    private static void nightTime(){

    }

    private static void searchPlayer(RoleName searcher){

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
