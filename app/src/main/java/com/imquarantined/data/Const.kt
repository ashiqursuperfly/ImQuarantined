package com.imquarantined.data

import com.imquarantined.BuildConfig

class Const {

    object Config {
        const val calibrationPoints = 10
        const val locationRequestPeriodMillis = 10000L // TODO: before deploying it should be bgTaskPeriod/3
        const val backgroundTaskPeriod =  3 * locationRequestPeriodMillis
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
        const val BASE_URL = "http://imquarantined.herokuapp.com/api/"

        object ENDPOINTS {
            const val AUTH = BASE_URL+"auth"
            const val UPDATE_LOCATION = BASE_URL+"location/update"
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
            const val USER_NAME = "name"
            const val FAILED_AT = "failed_at"
        }

    }

    object PrefProp {
        const val ACTION_DELETE = "action_delete"
        const val LOGIN_TOKEN = "login_token"
    }

}