package com.bcit.termproject2

import androidx.compose.runtime.toMutableStateList
import org.slf4j.MDC.clear
import java.util.Collections.addAll

class PlayersState(private val repository: Repository) {
    var players = repository.getAllPlayers().toMutableStateList()

    fun addPlayer(player: Player) {
        players.add(player)
        repository.insertPlayer(player)
    }
    fun removePlayer(player: Player) {
        players.remove(player)
        repository.deletePlayer(player)
    }
    fun refreshPlayers() {
        clear()
        addAll(mutableListOf(repository.getAllPlayers()))
    }
}

class TeamsState(private val repository: Repository) {
    var teams = repository.getAllTeams().toMutableStateList()

    fun addTeam(team: Team) {
        teams.add(team)
        repository.insertTeam(team)
    }
    fun removeTeam(team: Team) {
        teams.remove(team)
        repository.deleteTeam(team)
    }
}