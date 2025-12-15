package com.cristian.calendarapp.data

import android.content.Context
import com.cristian.calendarapp.data.local.dao.ProfileDAO
import com.cristian.calendarapp.data.local.dao.TeamDAO
import com.cristian.calendarapp.data.local.entities.ProfileEntity
import com.cristian.calendarapp.data.local.entities.TeamEntity
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
 class SupabaseSync @Inject constructor(
    val postgrest : Postgrest,
    val profileDAO: ProfileDAO,
    val teamDAO: TeamDAO,

)  {

    suspend operator fun invoke(currentUserId : String ) {
        this.currentUserId = currentUserId
        getProfile()
        getTeams()
    }

    private lateinit var currentUserId : String


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


    }

    private suspend fun getTeams() {
        val supabaseResult = postgrest.from(
            schema = "public",
            table = "user_teams"

        ).select(
            columns = Columns.raw("""
                team_id,
                user_id,
                team ( id, name, description )
            """.trimIndent())
        ) {
            filter {
                eq("user_id", currentUserId)
            }
        }.decodeList<TeamEntity>()

        if(supabaseResult.isNotEmpty()) {
            supabaseResult.forEach { team ->
                teamDAO.insertTeam(team.apply { this.isSynchronized = true })
            }
        }
    }
}