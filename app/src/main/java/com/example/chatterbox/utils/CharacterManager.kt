package com.example.chatterbox.utils

import com.example.chatterbox.R

data class Character(
    val characterName: String? = null,
    val assistantId: String? = null,
    val statusMessage: String? = null,
    val profileImage: Int? = null,
    val backImage: Int? = null,
    var isFavorite: Boolean = false
)

object CharacterManager {
    private val IRON_MAN = Character(
        "아이언맨",
        "asst_wOV61PAkQH42T1EzyFP8Lwbd",
        statusMessage = "I'm Iron Man",
        profileImage = R.drawable.ironman_profile2,
        backImage = R.drawable.rectangle_1
    )
    private val SNOW_WHITE = Character(
        "백설공주",
        "asst_F9LOI0D2bEHxrXElKoAJdDRb",
        statusMessage = "안녕, 난 백설공주",
        profileImage = R.drawable.snow_white_profile2,
        backImage = R.drawable.rectangle_2
    )
    private val OLAF = Character(
        "올라프",
        "asst_pKRMmMds8HVj6cWNmhhjZxGh",
        statusMessage = "겨울이 좋아",
        profileImage = R.drawable.olaf_profile2,
        backImage = R.drawable.rectangle_3
    )
    private val JENIE = Character(
        "지니",
        "asst_6JEfd9K0gVT0fhF9TWLXLKd9",
        statusMessage = "소원을 말해봐",
        profileImage = R.drawable.genie_profile2,
        backImage = R.drawable.rectangle_4,
    )

    fun getAllCharacters(): List<Character> {
        return listOf(IRON_MAN, SNOW_WHITE, OLAF, JENIE)
    }
}