package applicationOuter.group1.typetapgo.data

import applicationOuter.group1.typetapgo.R
import android.content.Context
import java.util.*

/**
 * The RandomWord data class creates a list of random words from a text file
 * in the raw resource files.
 *
 * https://developer.android.com/reference/android/content/res/Resources#openRawResource(int)
 * words list source: https://github.com/wordnik/wordlist
 *
 */
object RandomWord {

    var wordList = mutableListOf<String>()

    /**
     * Reads in the random words list from the raw resources and initializes
     * the wordlist that will be referenced throughout the application.
     *
     * @param context the context of the activity from which the function is
     * called
     */
    fun initializeWordList(context: Context) {
        val inputStream = context.resources.openRawResource(R.raw.wordslist);
        val scanIn = Scanner(inputStream)
        while (scanIn.hasNext()) {
            wordList.add(scanIn.next())
        }
    }
}