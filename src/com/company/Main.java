package com.company;

import controllers.GameController;
import models.Players;
import models.RoleName;
import models.Roles;

import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        GameController.runGame(generateGame());
    }


    private static ArrayList<Players> generateGame(){
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

        return null;
    }
}
