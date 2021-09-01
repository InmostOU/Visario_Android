package pro.inmost.android.visario.data.entities.profile

import pro.inmost.android.visario.domain.entities.user.Profile

fun ProfileData.toDomainProfile() = Profile(
    id = id,
    userUrl = userArn,
    firstName = firstName,
    lastName = lastName,
    username = username,
    about = about,
    birthdate = birthday,
    phoneNumber = phoneNumber,
    image = image,
    email = email,
    showEmailTo = showEmailTo,
    showPhoneNumberTo = showPhoneNumberTo
)

fun Profile.toDataProfile() = ProfileData(
    id = id,
    userArn = userUrl,
    firstName = firstName,
    lastName = lastName,
    username = username,
    about = about,
    birthday = birthdate,
    phoneNumber = phoneNumber,
    image = image,
    email = email,
    showEmailTo = showEmailTo,
    showPhoneNumberTo = showPhoneNumberTo
)