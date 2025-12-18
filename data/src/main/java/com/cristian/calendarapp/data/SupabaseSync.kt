package com.cristian.calendarapp.data

import android.util.Log
import com.cristian.calendarapp.data.local.dao.ProfileDAO
import com.cristian.calendarapp.data.local.dao.TeamDAO
import com.cristian.calendarapp.data.local.entities.ProfileEntity
import com.cristian.calendarapp.data.local.entities.TeamEntity
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.decodeOldRecord
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
 class SupabaseSync @Inject constructor(
    val scope : CoroutineScope,
    val postgrest : Postgrest,
    val realtime : Realtime,
    val profileDAO: ProfileDAO,
    val teamDAO: TeamDAO,

) {
     companion object {
         var isExecuted : Boolean = false
     }

    suspend operator fun invoke(currentUserId: String) {
      if(!isExecuted) {
          this.currentUserId = currentUserId
          getProfile()
          getTeams()
          uploadTeams()
          uploadProfile()
          isExecuted = true
      }

    }

    private lateinit var currentUserId: String


    private suspend fun getProfile() {
        val supabaseResult = postgrest.from(
            schema = "public",
            table = "user_details"
        ).select {
            filter {
                eq("user_id", currentUserId)
            }
        }.decodeList<ProfileEntity>()

        supabaseResult.firstOrNull()?.let { profile ->
            profileDAO.updateProfile(profile.apply { this.isSynchronized = true })
        }

        listenToChanges(
            channelName = "user_details",
            schemaName = "public",
            updateCb = { action ->
                val profile = action.decodeRecord<ProfileEntity>().apply { this.isSynchronized = true }
                profileDAO.updateProfile(profile)
            }
        )


    }

    fun uploadProfile() {
        scope.launch {
            profileDAO.getProfile().collect { profile ->
                if(!profile.isSynchronized) {
                    postgrest.from(
                        schema = "public",
                        table = "user_details"
                    ).upsert(profile)
                }
            }
        }
    }



    private fun listenToChanges(
        channelName : String,
        schemaName : String = "public",
        deleteCb : suspend (PostgresAction.Delete) -> Unit = {},
        insertCb : suspend (PostgresAction.Insert) -> Unit = {},
        updateCb : suspend (PostgresAction.Update) -> Unit = {}
    ) {
        val channel = realtime.channel(channelName)
        val flow = channel.postgresChangeFlow<PostgresAction>(schema = schemaName)
        scope.launch {
            channel.subscribe()
            flow.filter { action -> action !is PostgresAction.Select }.collect { action ->
                when (action) {
                    is PostgresAction.Delete -> {
                        deleteCb(action)

                    }

                    is PostgresAction.Insert -> {
                        insertCb(action)
                    }

                    is PostgresAction.Update -> {
                        updateCb(action)
                    }

                    is PostgresAction.Select -> TODO()
                }
            }
        }
    }

    private suspend fun getTeamById(id : String) : TeamEntity {
        return postgrest.from("team").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<TeamEntity>()
    }

     fun getTeams() {
        scope.launch {
            val result = postgrest.from("team").select().decodeList<TeamEntity>()
            if(result.isNotEmpty()) {
                result.forEach { team ->
                    teamDAO.insertTeam(team.apply { this.isSynchronized = true })
                }
            }
        }
        listenToChanges(
            channelName = "team",
            deleteCb = {
               val decode = it.decodeOldRecord<Map<String, String>>()
                decode["id"]?.let { id ->
                    teamDAO.deleteTeamById(id)

                }
            },
            insertCb = {
                val decode = it.decodeRecord<Map<String, String>>()

                decode["id"]?.let { id ->
                    val team =   getTeamById(id)

                    teamDAO.insertTeam(team.apply { this.isSynchronized = true })
                }

            },
            updateCb = {
                val decode = it.decodeRecord<Map<String, String>>()
                decode["id"]?.let { id ->
                   val team = getTeamById(id)
                    teamDAO.insertTeam(team.apply { this.isSynchronized = true })
                }
            }
        )

    }

    fun uploadTeams() {
       scope.launch {
           teamDAO.getTeams().collect { teams ->
               teams.forEach { team ->
                   Log.i("TEAM", team.id)
                   if(!team.isSynchronized) {
                     postgrest.from(
                           schema = "public",
                           table = "team"
                       ).insert(team)

                       postgrest.from(
                           schema = "public",
                           table = "user_teams"
                       ).insert(
                           buildJsonObject {
                               put("team_id", team.id)
                               put("user_id", currentUserId)
                           }
                       )
                   }
               }
           }
       }
    }


}