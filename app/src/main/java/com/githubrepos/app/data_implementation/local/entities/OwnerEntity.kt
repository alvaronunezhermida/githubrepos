package com.githubrepos.app.data_implementation.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.githubrepos.domain.Owner

@Entity
data class OwnerEntity(
    val login: String,
    @PrimaryKey(autoGenerate = false) val id: Int,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String,
    val url: String,
    val htmlUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val organizationsUrl: String,
    val reposUrl: String,
    val eventsUrl: String,
    val receivedEventsUrl: String,
    val type: String,
    val siteAdmin: Boolean
)