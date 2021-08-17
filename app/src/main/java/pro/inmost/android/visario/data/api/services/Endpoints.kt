package pro.inmost.android.visario.data.api.services

internal object Endpoints {
    const val MESSAGE_LIST = "messages/list"
    const val MESSAGE_SEND = "messages/send"
    const val MESSAGES_SESSION = "/messages/messaging-session"
    const val SESSION_CONNECT = "connect"

    const val PROFILE_GET = "/user/profile"
    const val PROFILE_UPDATE = "/user/profile/update"

    const val LOGIN = "auth/login"
    const val REGISTER = "auth/register"

    const val CHANNEL_LIST = "/channels/getChannelsMemberships"
    const val CHANNEL_CREATE = "/channels/create"
    const val CHANNEL_LEAVE = "/channels/leaveChannel"
    const val CHANNEL_FIND_BY_NAME = "/channels/findByName"
    const val CHANNEL_ADD_MEMBER = "/channels/addMemberToChannel"

    const val CONTACTS_GET = "/contact/getAllContacts"
    const val CONTACT_ADD = "/contact/add"
    const val CONTACT_FIND = "/contact/findUserByUsername"
    const val CONTACT_DELETE = "/contact/delete"
    const val CONTACT_EDIT = "/contact/edit"


    const val SECRET_KEY = ""
    const val ACCESS_KEY_ID = ""
}