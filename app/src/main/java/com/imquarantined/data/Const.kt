package com.imquarantined.data

class Const {

    object Misc {

        const val CalibrationPoints = 10
        const val LocationRequestPeriodMillis = 10000L //
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