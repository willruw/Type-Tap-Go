package applicationOuter.group1.typetapgo.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.data.RandomWord
import applicationOuter.group1.typetapgo.databinding.FragmentStartGameBinding

/**
 * This "StartGameFragment" class represents an instance of the Start game
 * window. This window is displayed after the user clicks the "Play"
 * button on the Home window, but before the game actually starts. The life
 * cycle methods within this class instantiate and initialize different aspects
 * of the UI.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 1.16 3/14/2022
 */
class StartGameFragment : Fragment() {

    private var _binding: FragmentStartGameBinding? = null
    private val binding get() = _binding!!

    /**
     * The onCreate method instantiates the Fragment and shuffles the list of
     * random words.
     *
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RandomWord.wordList.shuffle()
    }

    /**
     * The onCreateView method inflates the layout and initializes binding.
     *
     * @param inflater The layout inflater used to inflate view within the
     * fragment.
     * @param container The container for the view (parent)
     * @param savedInstanceState A Bundle object containing the saved state
     * @return The fragments view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentStartGameBinding.inflate(inflater, container,
            false)
        return binding.root
    }

    /**
     * The onViewCreated method starts the timer for the StartGameFragment when
     * the this fragment is displayed (inflated).
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countDownTimer.start()
    }

    /**
     * The onPause method disables the timer for the StartGameFragment so that a
     * new instance of the timer can be created if the user closes or
     * minimizes this app and then resumes later.
     */
    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
    }

    /**
     * The onResume method starts the timer for the StartGameFragment when
     * the this fragment is displayed or resumed (inflated) (when the user
     * returns to this app).
     */
    override fun onResume() {
        super.onResume()
        countDownTimer.start()
    }

    /**
     * An onDestroy method disables the timer for the StartGameFragment when
     * the fragment is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    /**
     * The countDownTimer object creates an instance of a count down timer.
     * This timer acts as a delay sequence so that the user can prepare to
     * play the game.
     *
     * Reference: https://developer.android.com/reference/android/os/
     * CountDownTimer
     */
    val countDownTimer = object : CountDownTimer(3000,
        1000) {

        /**
         * The onTick method sets the text within txtCount to "Type...", "Tap
         * ..", "Go!" prior to transitioning to the "GameFragment".
         *
         * @param p0 The timer's current value in milliseconds
         */
        override fun onTick(p0: Long) {
            if (p0 > 2000L) {
                binding.txtCount.setText(getString(R.string.str_type))
            }
            if (p0 <= 2000L && p0 >= 1000L) {
                binding.txtCount.setText(getString(R.string.str_tap))
            }
            if (p0 <= 1000L) {
                binding.txtCount.setText(getString(R.string.str_go))
            }
        }

        /**
         * The onFinish method transitions the user to the gameFragment so
         * that they can play the game.
         */
        override fun onFinish() {
            findNavController().navigate(R.id.gameFragment)
        }
    }
}