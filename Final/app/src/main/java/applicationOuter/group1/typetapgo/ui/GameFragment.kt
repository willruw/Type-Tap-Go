package applicationOuter.group1.typetapgo.ui

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.adapter.PlayableAdapter
import applicationOuter.group1.typetapgo.data.ProfileManager
import applicationOuter.group1.typetapgo.databinding.FragmentGameBinding

/**
 * This "GameFragment" class represents an instance of the Game window (the
 * window in which the user plays the game). This window is displayed when the
 * user clicks tbe "Play" button on the Home Window. The life cycle methods
 * within this class instantiate and initialize different aspects of the UI.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 1.16 3/14/2022
 */
class GameFragment : Fragment() {

    var numWordsTyped = 0
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var index = 0
    private var running = true

    /**
     * The onCreate method instantiates the Fragment.
     *
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        _binding = FragmentGameBinding.inflate(inflater, container,
            false)
        return binding.root
    }

    /**
     * The onViewCreated method the sets the current theme within
     * imgGameBackground. The recycler view is intialized, the keyboard is
     * forced open and user input is handled within the handleKeyEvent
     * function. This method also defines onClickListeners for each button
     * and a onItemTouchLister was configured for the recyclerview.
     * Reference: https://stackoverflow.com/questions/36808992/
     * disable-touch-events-of-recyclerview-to-prevent-user-touches/55849546
     *
     * @param view The view returned by onCreateView
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgGameBackground.setImageResource(ProfileManager
            .profiles[ProfileManager.currentlySelectedProfile].selectedTheme)
        recyclerView = binding.recyclerViewRandomWord
        recyclerView.adapter = PlayableAdapter()
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,
                false)
        binding.edtGame.requestFocus()
        showSoftKeyboard(binding.edtGame)
        binding.edtGame.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(keyCode)
        }
        binding.btnGameQuit.setOnClickListener {
            numWordsTyped = 0
            countDownTimer.cancel()
            val action =
                GameFragmentDirections.actionGameFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
        binding.btnGameRestart.setOnClickListener {
            numWordsTyped = 0
            countDownTimer.cancel()
            val action =
                GameFragmentDirections.actionGameFragmentToStartGameFragment()
            view.findNavController().navigate(action)
        }
        recyclerView.addOnItemTouchListener(
            object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent)
            : Boolean {
                return true
            }
        })
    }

    /**
     * The handleKeyEvent method matches user input within the edit text
     * object "edtGame" to game data and scrolls the recycler view to a new
     * position if the input is a match.
     *
     * @param view The current fragment
     * @param keyCode The keycode entered by the user
     * @return true
     */
    private fun handleKeyEvent(keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && binding.edtGame.text.toString()
                .equals(PlayableAdapter.wordList[index], true)) {
            binding.edtGame.text.clear()
            recyclerView.smoothScrollToPosition(++index)
            binding.edtGame.requestFocus()
            numWordsTyped++
        }
        return true
    }

    /**
     * The showSoftKeyboard method forces the keyboard open while the game is
     * active.
     *
     * @param view The edit text field "edtGame"
     */
    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    /**
     * The countDownTimer object creates an instance of a count down timer.
     * This timer decrements the play time while the game is in progress.
     *
     * Reference: https://developer.android.com/reference/android/os/
     * CountDownTimer
     */
    val countDownTimer = object : CountDownTimer(61000,
        1000) {

        /**
         * The onTick method sets the current timer value within the text
         * field "txtTimer" each time the timer's onTick method is executed.
         *
         * @param p0 The timer's current value in milliseconds
         */
        override fun onTick(p0: Long) {
            var seconds = (p0 / 1000)
            if (running) {
                binding.txtTimer.setText(seconds.toString())
            }
        }

        /**
         * The onFinish method calls the gameEnd to update the user profile
         * when the timer is finished. This method automatically navigates the
         * user back to the Home fragment.
         */
        override fun onFinish() {
            gameEnd()
            numWordsTyped = 0
            findNavController().navigate(R.id.homeFragment)
        }
    }

    /**
     * The onResume method resets the game if the user leaves and returns.
     * This ensures that the user maintains an accurate score in words per
     * minute.
     */
    override fun onResume() {
        super.onResume()
        running = true
        numWordsTyped = 0
        index = 0
        recyclerView.smoothScrollToPosition(index)
        countDownTimer.start()
    }

    /**
     * The onPause method disables the timer so that a new instance of the
     * timer can be created when the user returns to the GameFragment.
     */
    override fun onPause() {
        super.onPause()
        running = false
        countDownTimer.cancel()
    }

    /**
     * An overriden method for onDestroyView. This sets binding equal to null
     * when the fragment's life cycle ends.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * The gameEnd method adds the most recent score to the user's list of
     * scores. This allows for the creation of user statistics.
     */
    fun gameEnd() {
        ProfileManager.profiles[currentlySelectedProfile].listOfScores
            .add(numWordsTyped)
        ProfileManager.profiles[currentlySelectedProfile].numWordsTyped =
            ProfileManager.profiles[currentlySelectedProfile].numWordsTyped
        + numWordsTyped
        if (numWordsTyped > ProfileManager.profiles[currentlySelectedProfile]
                .highestScore) {
            ProfileManager.profiles[currentlySelectedProfile].highestScore =
                numWordsTyped
        }
        ProfileManager.update(ProfileManager.profiles[currentlySelectedProfile])
    }

    /**
     * The companion object shares the current selected profile with different
     * fragments in the UI.
     */
    companion object {
        var currentlySelectedProfile = ProfileManager.currentlySelectedProfile
    }
}