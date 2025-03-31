package com.bcit.termproject2

import androidx.compose.runtime.toMutableStateList

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
        addAll(repository.getAllPlayers())
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