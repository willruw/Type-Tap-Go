package applicationOuter.group1.typetapgo.model

/**
 * This class represents a profile object.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 03-15-2022
 *
 * @property id id to be referenced when interacting with the database
 * @property username the username of the profile
 * @property avatar the integer that references the image in the drawable
 * resource folder
 * @property bio a short bio description
 * @property numWordsTyped the total number of words typed
 * @property highestScore the profile's highest score
 * @property listOfScores the profile's list of scores
 * @property selectedTheme the integer that references the image in the
 * drawable resource folder
 * @property currentlySelected boolean that is true if profile is the
 * currently selected profile, else false
 */
data class Profile(
    var id: Long,
    var username: String,
    var avatar: Int,
    var bio: String,
    var numWordsTyped: Int,
    var highestScore: Int,
    var listOfScores: MutableList<Int>,
    var selectedTheme: Int,
    var currentlySelected: Boolean
) {
    /**
     * Instantiates a Profile object.
     */
    constructor(): this(-1, "", 0, "",0,
        0, mutableListOf<Int>(), 0, false)

    /**
     * Instantiates a Profile object.
     */
    constructor(username: String, avatar: Int, bio: String, numWordsTyped: Int,
                highestScore: Int, listOfScores: MutableList<Int>,
                selectedTheme: Int, currentlySelected: Boolean):
            this(System.currentTimeMillis(), username, avatar, bio,
                numWordsTyped, highestScore, listOfScores, selectedTheme,
                currentlySelected)

    /**
     * Calculates the profile's average score in words per minute.
     *
     * @return the profile's average score
     */
    fun getAverageScore(): Int {
        return listOfScores.average().toInt()
    }
}
