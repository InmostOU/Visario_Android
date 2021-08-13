package pro.inmost.android.visario.data.api.services

internal object Endpoints {
    const val MESSAGE_LIST = "messages/list"
    const val MESSAGE_SEND = "messages/send"
    const val MESSAGES_SESSION = "/messages/messaging-session"
    const val SESSION_CONNECT = "connect"

    const val GET_CURRENT_USER_PROFILE = "user/profile"
    const val UPDATE_PROFILE = "/user/profile/update"

    const val LOGIN = "auth/login"
    const val REGISTER = "auth/register"

    const val CHANNEL_LIST = "channels/getChannelsMemberships"

    const val GET_CONTACTS = "/contact/getAllContacts"
    const val ADD_CONTACT = "/contact/add"
    const val FIND_CONTACT = "/contact/findUserByUsername"
    const val DELETE_CONTACT = "/contact/delete"
    const val EDIT_CONTACT = "/contact/edit"


    const val SECRET_KEY = ""
    const val ACCESS_KEY_ID = ""
}