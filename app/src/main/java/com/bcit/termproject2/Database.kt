package com.bcit.termproject2

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction


@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val country: String,
    val wins: Int,
    val losses: Int,
    val ties: Int
)
@Entity(tableName = "players",
    foreignKeys = [ForeignKey(
        entity = Team::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("teamId"),
        onDelete = ForeignKey.CASCADE
    )])
data class Player(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val position: String,
    val number: Int,
    val teamId: Int,
    val country: String,
    val birthday: String,
    val goals: Int,
)


data class TeamWithPlayers(
    @Embedded val team: Team,
    @Relation(
        parentColumn = "id",
        entityColumn = "teamId"
    )
    val players: List<Player>
)


@Dao
interface TeamDao {
    @Insert
    fun insert(team: Team)

    @Query("SELECT * FROM teams WHERE id = :id")
    fun getTeamById(id: Int): Team?

    //get team by name
    @Query("SELECT * FROM teams WHERE name = :name")
    fun getTeamByName(name: String): Team?

    //get team by country
    @Query("SELECT * FROM teams WHERE country = :country")
    fun getTeamByCountry(country: String): Team?

    @Query("SELECT * FROM teams")
    fun getAllTeams(): List<Team>

    //get random team
    @Query("SELECT * FROM teams ORDER BY RANDOM() LIMIT 1")
    fun getRandomTeam(): Team?

    //rank teams by wins
    @Query("SELECT * FROM teams ORDER BY wins DESC")
    fun getRankedTeams(): List<Team>

    @Delete
    fun delete(team: Team)
}
@Dao
interface PlayerDao {
    @Insert
    fun insert(player: Player)

    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayerById(id: Int): Player?

    //get player by name
    @Query("SELECT * FROM players WHERE name = :name")
    fun getPlayerByName(name: String): Player?

    //get player by team and position
    @Query("SELECT * FROM players WHERE teamId = :team AND position = :position")
    fun getPlayerByTeamAndPosition(team: String, position: String): Player?

    //get player by team
    @Query("SELECT * FROM players WHERE teamId = :team")
    fun getPlayerByTeam(team: String): Player?

    //get player by position
    @Query("SELECT * FROM players WHERE position = :position")
    fun getPlayerByPosition(position: String): Player?

    //get player by number
    @Query("SELECT * FROM players WHERE number = :number")
    fun getPlayerByNumber(number: Int): Player?

    //get player by country
    @Query("SELECT * FROM players WHERE country = :country")
    fun getPlayerByCountry(country: String): Player?

    //get player by team and number
    @Query("SELECT * FROM players WHERE teamId = :team AND number = :number")
    fun getPlayerByTeamAndNumber(team: String, number: Int): Player?

    //get player by goals
    @Query("SELECT * FROM players WHERE goals = :goals")
    fun getPlayerByGoals(goals: Int): Player?

    //rank players by goals
    @Query("SELECT * FROM players ORDER BY goals DESC")
    fun getRankedPlayers(): List<Player>

    //get random player
    @Query("SELECT * FROM players ORDER BY RANDOM() LIMIT 1")
    fun getRandomPlayer(): Player?

    @Query("SELECT * FROM players")
    fun getAllPlayers(): List<Player>

    @Delete
    fun delete(player: Player)

    @Transaction
    @Query("SELECT * FROM teams WHERE id = :id")
    fun getTeamWithPlayers(id: Int): TeamWithPlayers

}

@Database(entities = [Team::class, Player::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao
}

// Singleton Database
object Database {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database")
            .allowMainThreadQueries()
             .build()
        }
        return instance!!
    }
}