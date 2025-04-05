package com.bcit.termproject2

class  Repository(private val teamDao: TeamDao, private val playerDao: PlayerDao) {
    fun getAllTeams(): List<Team> {
        return teamDao.getAllTeams()
    }

    fun getTeamById(id: Int): Team? {
        return teamDao.getTeamById(id)
    }

    fun getTeamByName(name: String): Team? {
        return teamDao.getTeamByName(name)
    }

    fun getTeamByCountry(country: String): Team? {
        return teamDao.getTeamByCountry(country)
    }

    fun getRandomTeam(): Team? {
        return teamDao.getRandomTeam()
    }

    fun getRankedTeams(): List<Team> {
        return teamDao.getRankedTeams()
    }

    fun insertTeam(team: Team) {
        teamDao.insert(team)
    }

    fun deleteTeam(team: Team) {
        teamDao.delete(team)
    }

    fun getAllPlayers(): List<Player> {
        return playerDao.getAllPlayers()
    }

    fun getPlayerById(id: Int): Player? {
        return playerDao.getPlayerById(id)
    }

    fun getPlayerByName(name: String): Player? {
        return playerDao.getPlayerByName(name)
    }

    fun getPlayerByTeamAndPosition(team: String, position: String): Player? {
        return playerDao.getPlayerByTeamAndPosition(team, position)
    }

    fun getPlayerByTeam(team: String): Player? {
        return playerDao.getPlayerByTeam(team)
    }

    fun getPlayerByPosition(position: String): Player? {
        return playerDao.getPlayerByPosition(position)
    }

    fun getPlayerByNumber(number: Int): Player? {
        return playerDao.getPlayerByNumber(number)
    }

    fun getPlayerByCountry(country: String): Player? {
        return playerDao.getPlayerByCountry(country)
    }

    fun getPlayerByGoals(goals: Int): Player? {
        return playerDao.getPlayerByGoals(goals)
    }

    fun getRankedPlayers(): List<Player> {
        return playerDao.getRankedPlayers()
    }

    fun getRandomPlayer(): Player? {
        return playerDao.getRandomPlayer()
    }

    fun insertPlayer(player: Player) {
        playerDao.insert(player)
    }

    fun deletePlayer(player: Player) {
        playerDao.delete(player)
    }
}