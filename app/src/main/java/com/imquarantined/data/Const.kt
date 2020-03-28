package com.imquarantined.data

class Const {

    object Misc {

        const val calibrationPoints = 10
        const val locationRequestPeriodMillis = 10000L // TODO: before deploying it should be bgTaskPeriod/3
        const val backgroundTaskPeriod =  3 * locationRequestPeriodMillis
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