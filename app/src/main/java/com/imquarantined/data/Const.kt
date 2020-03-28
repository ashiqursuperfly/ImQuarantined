package com.imquarantined.data

import com.imquarantined.BuildConfig

class Const {

    object Config {
        const val calibrationPoints = 10
        const val locationRequestPeriodMillis = 10000L // TODO: before deploying it should be bgTaskPeriod/3
        const val backgroundTaskPeriod =  3 * locationRequestPeriodMillis
    }

    object Notification {
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
        const val BASE_URL = "https://imquarantined.herokuapp.com/api"

        object ENDPOINTS {
        }

        object Params {

            object GET {
            }

            object POST {
            }

            object HEADER {
            }
        }

    }

    object PrefProp {
        const val ACTION_DELETE = "action_delete"
        const val LOGIN_TOKEN = "login_token"
    }

}