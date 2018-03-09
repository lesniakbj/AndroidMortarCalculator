package com.squadfinder.brend.squadandroidcalculator.domain;

import com.squadfinder.brend.squadandroidcalculator.domain.enums.GameMode;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.Team;

/**
 * Created by brend on 3/6/2018.
 */

public class Layer {
    private int id;
    private String layerName;
    private Team teamOne;
    private int teamOneTickets;
    private Team teamTwo;
    private int teamTwoTickets;
    private GameMode gameMode;
    private int numberOfFlags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public Team getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(Team teamOne) {
        this.teamOne = teamOne;
    }

    public int getTeamOneTickets() {
        return teamOneTickets;
    }

    public void setTeamOneTickets(int teamOneTickets) {
        this.teamOneTickets = teamOneTickets;
    }

    public Team getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(Team teamTwo) {
        this.teamTwo = teamTwo;
    }

    public int getTeamTwoTickets() {
        return teamTwoTickets;
    }

    public void setTeamTwoTickets(int teamTwoTickets) {
        this.teamTwoTickets = teamTwoTickets;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }

    @Override
    public String toString() {
        return "Layer{" +
                "id=" + id +
                ", layerName='" + layerName + '\'' +
                ", teamOne=" + teamOne +
                ", teamOneTickets=" + teamOneTickets +
                ", teamTwo=" + teamTwo +
                ", teamTwoTickets=" + teamTwoTickets +
                ", gameMode=" + gameMode +
                ", numberOfFlags=" + numberOfFlags +
                '}';
    }
}
