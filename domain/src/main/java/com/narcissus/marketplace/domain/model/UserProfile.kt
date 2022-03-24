package com.narcissus.marketplace.domain.model

data class UserProfile(
    val id: String,
    val name: String?,
    val email: String,
    val iconUrl: String?
)

val dummyUser = UserProfile(
    "some id",
    "Joe Ordinary",
    "example@gmail.com",
    "https://301-1.ru/uploads/image/ha-ha-ya-zdes-zhivu_pOLMNliEp9.jpeg",
)
