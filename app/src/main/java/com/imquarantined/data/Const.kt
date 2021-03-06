package com.imquarantined.data

import com.imquarantined.BuildConfig

class Const {

    object Config {
        const val calibrationPoints = 15
        const val locationRequestPeriodMillis = 1000L * 60 * 8 // make a location request every 7 minutes
        const val backgroundTaskPeriod =  1.5 * locationRequestPeriodMillis // do bg task a little slower to ensure the new location is always ready
    }

    object Notification {
        const val promptLoginChannelId = "prompt_login_notification_channel_id${BuildConfig.APPLICATION_ID}"
        const val promptLoginChannelName = "prompt_login_notification_channel_name${BuildConfig.APPLICATION_ID}"
        const val bgServiceChannelName = "bg_service_notification_channel_name${BuildConfig.APPLICATION_ID}"
        const val bgServiceChannelId = "bg_service_notification_channel_id${BuildConfig.APPLICATION_ID}"
        const val promptGpsOffWhileBgTaskChannelId = "prompt_gps_off_while_bg_task_id${BuildConfig.APPLICATION_ID}"
        const val promptGpsOffWhileBgTaskChannelName = "prompt_gps_off_while_bg_task_name${BuildConfig.APPLICATION_ID}"
    }

    object RequestResult {
    }

    object RequestCode {
        const val FIREBASE_AUTH = 1031
    }

    object Api {
        const val BASE_URL =  "http://imquarantined.herokuapp.com/api/" //"http://192.168.0.108:8000/api/"

        object ENDPOINTS {
            const val AUTH = BASE_URL+"auth"
            const val UPDATE_LOCATION = BASE_URL+"location/update"
            const val GET_PROFILE =  BASE_URL + "profile"
            const val GET_LEADERBOARD = BASE_URL + "leaderboard"
            const val GET_HOME = BASE_URL
        }

        object Params {

            object GET {
            }

            object POST {
                const val ID_TOKEN = "id_token"
                const val LOCATION_ARRAY = "locations"
                const val LAT = "lat"
                const val LONG = "long"
                const val ALTI = "alti"
                const val DATE_TIME = "date_time"

            }

            object HEADER {
                const val AUTHORIZATION = "Authorization"
            }
        }

        object Response {
            const val SUCCESS = "success"
            const val MESSAGE = "message"
            const val DATA = "data"
            const val USER_NAME = "user_name"
            const val PHOTO_URL = "photo_url"
            const val TOTAL_POINTS = "total_points"
            const val CUR_STREAK = "cur_streak"
            const val DAYS_QUARANTINED = "days_quarantined"
            const val HIGHEST_STREAK = "highest_streak"
            const val POSITION = "position"
            const val IS_USER = "is_user"
            const val TOP_USERS = "top"
            const val PROGRESS = "progress"
            const val HOUR = "hr"
            const val MINUTE = "min"
            const val SECOND = "sec"
            const val FAILED_AT = "failed_at"
        }

    }

    object PrefProp {
        const val ACTION_DELETE = "action_delete"
        const val LOGIN_TOKEN = "login_token"
    }

}