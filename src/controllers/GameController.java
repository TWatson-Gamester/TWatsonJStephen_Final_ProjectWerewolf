package controllers;

import lib.ConsoleIO;
import models.Players;
import models.RoleName;
import sounds.Audio;

import javax.management.relation.Role;
import java.io.Console;
import java.util.ArrayList;

public class GameController {
    private static ArrayList<Players> originalCast;
    private static ArrayList<Players> villagePeople = new ArrayList<>();
    private static ArrayList<Players> graveyard = new ArrayList<>();
    private static ArrayList<Players> aliveCultMembers = new ArrayList<>();
    private static boolean isDay = false;
    private static int numberOfCultLeaders = 0;
    private static int dayNumber = 0;
    private static int werewolfKills = 1;
    private static final int defaultWerewolfKills = 1;
    private static String currentGhostChar;
    private static int spellcasterSilenced;
    private static int oldHagBanished;

    /**
     * Starts the game of Werewolf
     * @param players: the base state of the originalCast
     */
    public static void runGame(ArrayList<Players> players){

        ConsoleIO.promptForString("We are now going to be passing out the cards, however GM we need you to see the " +
                        "next piece of info and not the players, so hide this next part, Press ENTER to continue: ", true);
        originalCast = players;
        villagePeople.addAll(originalCast);

        for(Players player : originalCast){
            ConsoleIO.displayString("Player " + player.getSeatNumber() + ", Role: " + player.getCurrentRole());
        }
        ConsoleIO.promptForString("Press ENTER to Continue: ", true);
        ConsoleIO.clearScreen();
        ConsoleIO.promptForString("Okay we are now ready to start, GM if there is anything you would like to do for Day 0, do it now," +
                " Then press ENTER to start: ", true);
        ConsoleIO.clearScreen();

        if(searchForAliveRole(RoleName.CULT_LEADER)){
            aliveCultMembers.add(findPlayerByRole(RoleName.CULT_LEADER, villagePeople));
        }

        do{
            if(isDay){
                ConsoleIO.displayString("\nDay " + dayNumber);
                //show Graveyard
                dayTime();
                isDay = false;
            }else{
                dayNumber++;
                nightTime();
                isDay = true;
            }
        }while(!checkForWinCondition());
        //Reset in case for another game
        originalCast.clear();
        villagePeople.clear();
        graveyard.clear();
        aliveCultMembers.clear();
        dayNumber = 0;
        numberOfCultLeaders = 0;
        isDay = false;
        werewolfKills = defaultWerewolfKills;
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

        Audio.playSound("morning_time.wav");

        if(searchForDeadRoleGrave(RoleName.GHOST) && dayNumber > 1){
            if(!currentGhostChar.isBlank()) {
                ConsoleIO.promptForString("Last night everyone awoke to a vision of the character '" + currentGhostChar + "' Press ENTER to continue: ", true);
            }else{
                ConsoleIO.promptForString("Last night the ghost was tired so he decided not to give you anything. Press ENTER to continue: ", true);
            }
        }

        if(searchForAliveRole(RoleName.SPELLCASTER) || searchForDeadRoleGrave(RoleName.SPELLCASTER)){
            if(spellcasterSilenced != -1) {
                ConsoleIO.promptForString("The spellcaster chose to silence player " + spellcasterSilenced + " you must remain quiet and not make movements to influence the discussion until the next day." +
                        "\nThis includes if you are on trial. Press ENTER to continue: ", true);
            }
            spellcasterSilenced = -1;
        }

        if((searchForAliveRole(RoleName.OLD_HAG) || searchForDeadRoleGrave(RoleName.OLD_HAG)) && oldHagBanished != -1){
            ConsoleIO.promptForString("The Old Hag had enough of you and decided to banish player " + oldHagBanished + " you must IMMEDIATELY leave the room. You will be brought back tonight. Press ENTER to continue: ", true);

            oldHagBanished = -1;

        }



        //discussion time
        //run until the moderator presses enter
        ConsoleIO.promptForString("It is now discussion time, press ENTER: ", true);
        Audio.playSound("Discussion Time.wav");
        ConsoleIO.promptForString("Wait for music to end, then end Discussion time by pressing ENTER:", true);

        //put people on trial
        //ask who they want to put on trial
        //whoever has the most votes is put to trial
        //IF there are 3 or more people tied to be put on trial, say there is no conclusion and skip trial
        //IF nobody is put on trial, say nobody was put on trial, then skip the trial
        //IF 1 or 2 people are on trial run the trial

        int peopleOnTrial = ConsoleIO.promptForInt("\nHow many people are voted to be on trial: ", Integer.MIN_VALUE, Integer.MAX_VALUE);

        if (peopleOnTrial > 0 && peopleOnTrial < 3){
            if (peopleOnTrial == 1) {
                int playerOnTrial = ConsoleIO.promptForInt("Seat of who is on trial: ", 1, originalCast.size());
                votingTime(playerOnTrial);
            }else{
                int firstOnTrial = ConsoleIO.promptForInt("Seat of the first person on trial on trial: ", 1, originalCast.size());
                int secondOnTrial = ConsoleIO.promptForInt("Seat of the second person on trial on trial: ", 1, originalCast.size());
                votingTime(firstOnTrial, secondOnTrial);
            }
        }else if(peopleOnTrial >= 3){
            ConsoleIO.displayString("The Village couldn't decide on who to put on trial");
        }
        else{
            ConsoleIO.displayString("There will be no trial tonight");
        }
    }

    /**
     * Goes through each night time even and sees if it needs to wake that player up or not
     * Switch Cases:
     *      Seer - Investigates the players that are alive for a werewolf
     *      Werewolf / Werewolves kill a player
     */
    private static void nightTime(){
        Audio.playSound("NighttimeAnnouncement.wav");
        ConsoleIO.promptForString("The time is now 10:00 P.M.\nAs such, it is now officially nighttime." +
                "\nOk then...sweet dreams, everyone! Goodnight, sleep tight, dont let the werewolves bite...\n" +
                "Press Enter to continue: ", true);
        ConsoleIO.clearScreen();
        ArrayList<Players> playersToKill = new ArrayList<>();
        String[] menuOptions = new String[villagePeople.size()];
        for(int i = 0; i < villagePeople.size(); i++){
            menuOptions[i] = "Player " + villagePeople.get(i).getSeatNumber();
        }

        if(dayNumber == 1){
            //Cupid
            if(searchForAliveRole(RoleName.CUPID)){
                ConsoleIO.displayString("Cupid, please choose 2 players to fall in love");
                ConsoleIO.displayString("Seat number of the first person to fall in love");
                villagePeople.get(ConsoleIO.promptForMenuSelection(menuOptions, false)-1).setLovers(true);
                ConsoleIO.displayString("Seat number of the second person to fall in love");
                villagePeople.get(ConsoleIO.promptForMenuSelection(menuOptions, false)-1).setLovers(true);
            }
            ConsoleIO.clearScreen();

            //Apprentice Seer
            if(searchForAliveRole(RoleName.APPRENTICE_SEER)){
                ConsoleIO.promptForString("GM, have the Seer stick out their thumb, and wake up the Apprentice Seer, then press ENTER: ", true);
                ConsoleIO.clearScreen();
            }

            //Masons
            if(searchForAliveRole(RoleName.MASON)){
                ConsoleIO.promptForString("GM, please wake up the Masons so they can see each other, then press ENTER: ", true);
                ConsoleIO.clearScreen();
            }

            //Ghost
            if(searchForAliveRole(RoleName.GHOST)){
                ConsoleIO.promptForString("GM, notify the ghost that they have died",true);
                sendToGrave(findPlayerByRole(RoleName.GHOST, villagePeople),false);
            }
        }

        //Cult Leader
        if(aliveCultMembers.size() > 0){
            if(numberOfCultLeaders < 3){
                ConsoleIO.displayString("GM, silently wake up player " + aliveCultMembers.get(0).getSeatNumber() + " as they are the cult leader");
                ConsoleIO.displayString("Cult Leader please choose someone to indoctrinate into the church of the llama.");
                int playerToIndoctrinate = ConsoleIO.promptForMenuSelection(menuOptions, false);
                if(!villagePeople.get(playerToIndoctrinate - 1).isCult()){
                    aliveCultMembers.add(villagePeople.get(playerToIndoctrinate - 1));
                    villagePeople.get(playerToIndoctrinate - 1).setCult(true);
                }
            }else{
                ConsoleIO.displayString("GM, wake up the 'Cult Leader' so they can choose someone to indoctrinate into the church of the llama.");
            }
            ConsoleIO.clearScreen();
        }

        //Ghost
        if(searchForDeadRoleGrave(RoleName.GHOST)){
            if(dayNumber > 1) {
                currentGhostChar = ConsoleIO.promptForString("Ghost, What character would you like to output for the players: ", true);
                ConsoleIO.clearScreen();
            }
        }

        //Spellcaster
        if(searchForAliveRole(RoleName.SPELLCASTER)){
            ConsoleIO.displayString("Spellcaster, Who would you like to silence today?");
            spellcasterSilenced = villagePeople.get(ConsoleIO.promptForMenuSelection(menuOptions,false) - 1).getSeatNumber();
            ConsoleIO.clearScreen();
        }

        //Old Hag
        if(searchForAliveRole(RoleName.OLD_HAG)){
            ConsoleIO.displayString("Old Hag, Who do you wish to banish");
            oldHagBanished = villagePeople.get(ConsoleIO.promptForMenuSelection(menuOptions, false) - 1).getSeatNumber();
            ConsoleIO.clearScreen();
        }

        //Cursed
        if(searchForAliveRole(RoleName.CURSED)){
            ConsoleIO.promptForString("GM, please wake up the cursed so they can see there role, then press ENTER: ", true);
            Players cursed = findPlayerByRole(RoleName.CURSED, villagePeople);
                    if (cursed.isVillage()) {
                        ConsoleIO.promptForString("You are on the villagers side.", true);
                    } else {
                        ConsoleIO.promptForString("You are on the werewolves side.", true);
                    }
        }else if(searchForDeadRoleGrave(RoleName.CURSED)){
            ConsoleIO.promptForString("GM, please wake up the 'Cursed' so they can see their role, then press ENTER: ", true);
        }
        ConsoleIO.clearScreen();

        //Seer
        if(searchForAliveRole(RoleName.SEER)){
            searchPlayer(RoleName.SEER, menuOptions);
        }else if(searchForAliveRole(RoleName.APPRENTICE_SEER)){
            searchPlayer(RoleName.APPRENTICE_SEER, menuOptions);
        }
        else if(searchForDeadRoleGrave(RoleName.SEER)){
            ConsoleIO.promptForString("GM, wake up the 'Seer' and have them 'search a player', then press ENTER: ", true);
        }
        ConsoleIO.clearScreen();

        //Aura Seer
        if(searchForAliveRole(RoleName.AURA_SEER)){
            searchPlayer(RoleName.AURA_SEER, menuOptions);
        }else if(searchForDeadRoleGrave(RoleName.AURA_SEER)){
            ConsoleIO.promptForString("GM, wake up the 'Aura Seer' and have them 'search a player', then press ENTER: ", true);
        }
        ConsoleIO.clearScreen();

        //Sorceress
        if(searchForAliveRole(RoleName.SORCERESS)){
            searchPlayer(RoleName.SORCERESS, menuOptions);
        } else if(searchForDeadRoleGrave(RoleName.SORCERESS)){
            ConsoleIO.promptForString("GM, wake up the 'Sorceress' and have them 'search a player', then press ENTER: ", true);
        }
        ConsoleIO.clearScreen();

        //Werewolf
        if(dayNumber == 1){
            ConsoleIO.promptForString("GM, wake up the 'Werewolf / Werewolves' and have them look for each other," +
                    "They don't kill this night ,then press ENTER: ", true);
            if(searchForAliveRole(RoleName.MINION)) {
                ConsoleIO.promptForString("Gm, have the Werewolf Team all stick out their thumbs, and have the Minion Player wake up and look around" +
                        ", then press ENTER: ", true);
            }
        } else {
            for(int i = 0; i < werewolfKills; i++){
                ConsoleIO.displayString("Werewolf / Werewolves please choose a player to eliminate");
                int playerToRemove = ConsoleIO.promptForMenuSelection(menuOptions, false);
                if(villagePeople.get(playerToRemove-1).getCurrentRole().getName() == RoleName.CURSED) {
                    villagePeople.get(playerToRemove-1).setVillage(false);
                }else{
                    playersToKill.add(villagePeople.get(playerToRemove - 1));
                }
            }
            werewolfKills = defaultWerewolfKills;
        }
        ConsoleIO.clearScreen();

        //Witch
        if(searchForAliveRole(RoleName.WITCH)){
            ConsoleIO.displayString("Witch please choose one of the options");
            String[] witchMenu = new String[2];
            Players witch = findPlayerByRole(RoleName.WITCH, villagePeople);
            if(witch.getCurrentRole().isAbility1()){
                witchMenu[0] = "Save a Player";
            } else{
                witchMenu[0] = "Already used";
            }
            if(witch.getCurrentRole().isAbility2()){
                witchMenu[1] = "Kill a Player";
            } else{
                witchMenu[1] = "Already used";
            }
            int witchMenuOption = ConsoleIO.promptForMenuSelection(witchMenu, true);
            switch (witchMenuOption){
                case 1:
                    ConsoleIO.displayString("Witch please choose a player to cast the spell on");
                    int playerToSave = ConsoleIO.promptForMenuSelection(menuOptions, false);
                    playersToKill.remove(villagePeople.get(playerToSave - 1));
                    witch.getCurrentRole().setAbility1(false);
                    break;
                case 2:
                    ConsoleIO.displayString("Witch please choose a player to cast the spell on");
                    int playerToRemove = ConsoleIO.promptForMenuSelection(menuOptions, false);
                    playersToKill.add(villagePeople.get(playerToRemove - 1));
                    witch.getCurrentRole().setAbility2(false);
                    break;
                case 0:
                    ConsoleIO.promptForString("GM, still say which player would you like to cast your spell on, then press ENTER: ", true);
                    break;
            }
        }else if(searchForDeadRoleGrave(RoleName.WITCH)){
            ConsoleIO.promptForString("GM, wake up the 'Witch' and have them 'not use an ability', then press ENTER: ", true);
        }
        ConsoleIO.clearScreen();

        //Bodyguard
        if(searchForAliveRole(RoleName.BODYGUARD)){
            ConsoleIO.displayString("Bodyguard please choose a player to protect, you can not pick the same player twice in a row");
            int playerToSave = ConsoleIO.promptForMenuSelection(menuOptions, false);
            playersToKill.remove(villagePeople.get(playerToSave - 1));
        } else if(searchForDeadRoleGrave(RoleName.BODYGUARD)){
            ConsoleIO.promptForString("GM, wake up the 'Bodyguard' and have them 'protect a player', then press ENTER: ", true);
        }

        //Sends all players that have been killed to the graveyard
        for(Players deadMan : playersToKill){
            sendToGrave(deadMan, false);
        }
    }

    /**
     * This method takes in a Players Role and switches off of that to figure out information about their fellow players,
     * should only be used by the players that actually have a role that lets them investigates.
     * @param searcher: the player / role that is investigating something about a player
     */
    private static void searchPlayer(RoleName searcher, String[] menuOptions){
        int searchedPerson;
        switch (searcher){
            case APPRENTICE_SEER:
                ConsoleIO.displayString("GM, please wake up the Apprentice Seer for this night, they are now considered the Seer");
            case SEER:
                ConsoleIO.displayString("Seer please choose a player to investigate");
                searchedPerson = ConsoleIO.promptForMenuSelection(menuOptions,false);
                if(villagePeople.get(searchedPerson - 1).getCurrentRole().getName() == RoleName.LYCAN){
                    ConsoleIO.displayString("Player " + villagePeople.get(searchedPerson - 1).getSeatNumber() + " is on the Werewolf team");
                }else if(villagePeople.get(searchedPerson-1).isVillage()){
                    ConsoleIO.displayString("Player " + villagePeople.get(searchedPerson - 1).getSeatNumber() + " is on the Village team");
                } else{
                    ConsoleIO.displayString("Player " + villagePeople.get(searchedPerson - 1).getSeatNumber() + " is on the Werewolf team");
                }
                break;
            case SORCERESS:
                ConsoleIO.displayString("Sorceress please choose a player to investigate");
                searchedPerson = ConsoleIO.promptForMenuSelection(menuOptions, false);
                Players thePersonSearched = villagePeople.get(searchedPerson - 1);
                if(thePersonSearched.getCurrentRole().getName() == RoleName.SEER || thePersonSearched.getCurrentRole().getName() == RoleName.APPRENTICE_SEER){
                    ConsoleIO.displayString("Player " + thePersonSearched.getSeatNumber() + " is a Seer!!!");
                } else{
                    ConsoleIO.displayString("Player " + thePersonSearched.getSeatNumber() + " is not a Seer");
                }
                break;
            case AURA_SEER:
                ConsoleIO.displayString("Aura Seer please choose a player to investigate");
                searchedPerson = ConsoleIO.promptForMenuSelection(menuOptions,false);
                if(villagePeople.get(searchedPerson - 1).getCurrentRole().getName() == RoleName.VILLAGER || villagePeople.get(searchedPerson - 1).getCurrentRole().getName() == RoleName.WEREWOLF){
                    ConsoleIO.displayString("Player " + villagePeople.get(searchedPerson - 1).getSeatNumber() + " is either a Vilager or a Werewolf");
                } else{
                    ConsoleIO.displayString("Player " + villagePeople.get(searchedPerson - 1).getSeatNumber() + " is not a Vilager or a Werewolf");
                }
                break;
            default:
                ConsoleIO.displayString("How did you even get here???");
        }
        ConsoleIO.promptForString("Press Enter to Continue", true);
    }

    /**
     * Looks for to see if the Player is alive in the game
     * @param roleWanted: The role that we are searching for
     * @return: Is the player alive
     */
    private static boolean searchForAliveRole(RoleName roleWanted){
        for(Players wanted : villagePeople){
            if(wanted.getCurrentRole().getName() == roleWanted){
                return true;
            }
        }
        return false;
    }

    /**
     * Looks for to see if the Player is dead and that it's a closed grave
     * @param roleWanted: The role that we are searching for
     * @return: Is the player dead and is openGrave
     */
    private static boolean searchForDeadRoleGrave(RoleName roleWanted){
        for(Players wanted : graveyard){
            if(wanted.getCurrentRole().getName() == roleWanted && !wanted.getOpenGrave()){
                return true;
            }
        }
        return false;
    }

    /**
     * Looks for a specific Player from a ArrayList
     * @param roleWanted: the role that you are searching for
     * @param listToSearch: the list you would like to look from
     * @return: The Player that you are searching for, if player is not in that list returns a empty Player
     */
    private static Players findPlayerByRole(RoleName roleWanted, ArrayList<Players> listToSearch){
        Players searchedPlayer = new Players();
        for(Players playerWanted : listToSearch){
            if(playerWanted.getCurrentRole().getName() == roleWanted){
                searchedPlayer = playerWanted;
            }
        }
        return searchedPlayer;
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
        player.setDead(true);
        player.setOpenGrave(openGrave);
        if(player.isCult()){
            aliveCultMembers.remove(player);
            numberOfCultLeaders++;
        }

        //If player killed was the Hunter
        if(player.getCurrentRole().getName() == RoleName.HUNTER){
            player.setOpenGrave(true);
            String[] menuOptions = new String[villagePeople.size()];
            for(int i = 0; i < villagePeople.size(); i++){
                menuOptions[i] = "Player " + villagePeople.get(i).getSeatNumber();
            }
            ConsoleIO.displayString("\nThe Hunter has been killed, please let that player eliminate another player");
            int playerToRemove = ConsoleIO.promptForMenuSelection(menuOptions, false);
            sendToGrave(villagePeople.get(playerToRemove - 1), openGrave);
            ConsoleIO.clearScreen();
        }

        if(player.getCurrentRole().getName() == RoleName.WOLF_CUB){
            werewolfKills = 2;
        }


        //This needs to kill the Lover after we get out of the loop
        if(player.isLovers()){
            player.setLovers(false);
            for (Players current : villagePeople){
                if(current.isLovers()){
                    current.setLovers(false);
                    sendToGrave(current, openGrave);
                    break;
                }
            }
            ConsoleIO.displayString("Like Romeo and Juliet the lovers have died a very sad, and very tragic death.");
        }

        if(player.getCurrentRole().getName() == RoleName.TANNER){
            player.setWon(true);
        }
    }

    /**
     * This method takes in who is put on trial and figures out of they will be killed or not
     * @param player this is the player who has been put on trial
     */
    private static void votingTime(int player){
        int votesToKill = ConsoleIO.promptForInt("Votes to kill player " + player + ": ", 0, villagePeople.size() - 1);
        votesToKill -= ConsoleIO.promptForInt("Votes to save player " + player + ": ", 0, villagePeople.size() - 1);

        if (votesToKill > 0){
            ConsoleIO.displayString("Player " + player + " has been sent to the graveyard.");
            sendToGrave(originalCast.get(player - 1), true);
        }else{
            ConsoleIO.displayString("There was a tie so nobody was killed, Igor is pleased");
        }
    }

    /**
     * Takes in two players who have been put up on trial and decides who will die
     * @param player1 the first player on trial
     * @param player2 the second player on trial
     */
    private static void votingTime(int player1, int player2){

        int votesToKillPlayer1 = ConsoleIO.promptForInt("Votes to kill player " + player1 + ": ", 0, villagePeople.size()-2);
        votesToKillPlayer1 -= ConsoleIO.promptForInt("Votes to save player " + player1 + ": ", 0, villagePeople.size()-2);


        int votesToKillPlayer2 = ConsoleIO.promptForInt("Votes to kill player " + player2 + ": ", 0, villagePeople.size()-2);
        votesToKillPlayer2 -= ConsoleIO.promptForInt("Votes to save player " + player2 + ": ", 0, villagePeople.size()-2);

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

    /**
     * Checks if either of the teams have won
     * @return if the game needs to end
     */
    private static boolean checkForWinCondition(){
        boolean endGame = false;
        StringBuilder littleTimmy = new StringBuilder();
        int villageTeam = 0;
        int werewolfTeam = 0;
        for(Players player : villagePeople){
            if(player.isVillage()){
                villageTeam++;
            } else{
                werewolfTeam++;
            }
        }
        //If Werewolf Team has met victory conditions
        if(villagePeople.size() == 0 || werewolfTeam >= villageTeam){
            endGame = true;
            Audio.playSound("WerewolfVictory.wav");
            if(werewolfTeam == 1 && searchForAliveRole(RoleName.LONE_WOLF)){
                littleTimmy.append("Lone Wolf Win!").append('\n');
                for(Players player : originalCast){
                    if(player.getCurrentRole().getName() == RoleName.LONE_WOLF){
                        player.setWon(true);
                    }
                    if(player.hasWon()){
                        littleTimmy.append("Player ").append(player.getSeatNumber()).append(" Role: ").append(player.getCurrentRole()).append('\n');
                    }
                }
            }else {
                littleTimmy.append("Werewolves Win!").append('\n');
                for (Players player : originalCast) {
                    if (!player.isVillage() && player.getCurrentRole().getName() != RoleName.LONE_WOLF) {
                        player.setWon(true);
                    }
                    if (player.hasWon()) {
                        littleTimmy.append("Player ").append(player.getSeatNumber()).append(" Role: ").append(player.getCurrentRole()).append('\n');
                    }
                }
            }
            //If Village Team has met victory conditions
        }else if(werewolfTeam == 0){
            endGame = true;
            Audio.playSound("VillageVictory.wav");
            littleTimmy.append("Village Wins!").append('\n');
            for(Players player : originalCast){
                if(player.isVillage() && player.getCurrentRole().getName() != RoleName.TANNER && player.getCurrentRole().getName() != RoleName.CULT_LEADER){
                    player.setWon(true);
                }
                if(player.hasWon()){
                    littleTimmy.append("Player ").append(player.getSeatNumber()).append(" Role: ").append(player.getCurrentRole()).append('\n');
                }
            }
        }

        //Check for Cult Victory
        if(aliveCultMembers.size() == villagePeople.size()){
            if(endGame){
                littleTimmy.append("\nThe Cult also wins!\n");
            }else{
                littleTimmy.append("Cult wins!\n");
                Audio.playSound("CultOnlyWin.wav");
            }
            endGame = true;
            for(Players player : originalCast){
                if(player.isCult()){
                    player.setWon(true);
                }
                if(player.hasWon() && player.isCult()){
                    littleTimmy.append("Player ").append(player.getSeatNumber()).append(" Role: ").append(player.getCurrentRole()).append("\n");
                }
            }
        }

        littleTimmy.append('\n').append(outputGraveyard());
        ConsoleIO.displayString(littleTimmy.toString());
        return endGame;
    }
}