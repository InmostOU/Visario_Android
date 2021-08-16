package pro.inmost.android.visario.ui.entities.profile

import pro.inmost.android.visario.domain.entities.user.Profile

fun ProfileUI.toDomainProfile() = Profile(
    id = id,
    userUrl = userUrl,
    firstName = firstName,
    lastName = lastName,
    username = username,
    about = about,
    birthdate = birthdate,
    phoneNumber = phoneNumber,
    image = image,
    email = email,
    showEmailTo = showEmailTo.name,
    showPhoneNumberTo = showPhoneNumberTo.name
)

fun Profile.toUIProfile() = ProfileUI(
    id = id,
    userUrl = userUrl,
    firstName = firstName,
    lastName = lastName,
    username = username,
    about = about,
    birthdate = birthdate,
    phoneNumber = phoneNumber,
    image = image,
    email = email,
    showEmailTo = AllowedProfileViewers.valueOf(showEmailTo),
    showPhoneNumberTo = AllowedProfileViewers.valueOf(showPhoneNumberTo),
    showBirthdateTo = AllowedProfileViewers.EVERYONE
)