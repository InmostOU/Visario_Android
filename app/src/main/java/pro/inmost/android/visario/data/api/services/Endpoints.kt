package pro.inmost.android.visario.data.api.services

/**
 * API endpoints
 *
 */
internal object Endpoints {
    const val SERVER_BASE_URL = "http://3.129.6.178:8081"

    const val MESSAGE_LIST = "/messages/list"
    const val MESSAGE_SEND = "/messages/send"
    const val MESSAGE_EDIT = "/messages/edit"
    const val MESSAGE_DELETE = "/messages/delete"
    const val MESSAGES_SESSION = "/messages/messaging-session"
    const val SESSION_CONNECT = "/connect"

    const val PROFILE_GET = "/user/profile"
    const val PROFILE_UPDATE = "/user/profile/update"
    const val PROFILE_UPLOAD_PHOTO = "/user/uploadUserPhoto"

    const val LOGIN = "/auth/login"
    const val REGISTER = "/auth/register"

    const val WS_CHANNELS = "/websocket/getPresignedUrl"

    const val MEETING_GET = "/meeting/getMeeting"
    const val MEETING_CREATE = "/meeting/create"
    const val MEETING_DELETE = "/meeting/deleteMeeting"
    const val MEETING_CREATE_ATTENDEE = "/meeting/createAttendee"
    const val MEETING_DELETE_ATTENDEE = "/meeting/deleteAttendee"
    const val MEETING_GET_ATTENDEE_INFO = "/meeting/getUserInfoByUserId"

    const val CHANNEL_LIST = "/channels/getChannelsMemberships"
    const val CHANNEL_CREATE = "/channels/create"
    const val CHANNEL_LEAVE = "/channels/leaveChannel"
    const val CHANNEL_FIND_BY_NAME = "/channels/findByName"
    const val CHANNEL_ADD_MEMBER = "/channels/addMemberToChannel"
    const val CHANNEL_GET_MEMBERS = "/channels/fetchUsersFromChannel"

    const val CONTACTS_GET = "/contact/getAllContacts"
    const val CONTACT_ADD = "/contact/add"
    const val CONTACT_FIND = "/contact/findUserByUsername"
    const val CONTACT_DELETE = "/contact/delete"
    const val CONTACT_EDIT = "/contact/edit"
}