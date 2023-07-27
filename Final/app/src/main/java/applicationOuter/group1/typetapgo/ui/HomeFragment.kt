package applicationOuter.group1.typetapgo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.adapter.PlayableAdapter
import applicationOuter.group1.typetapgo.data.ProfileManager
import applicationOuter.group1.typetapgo.data.RandomWord
import applicationOuter.group1.typetapgo.databinding.FragmentHomeBinding

/**
 * This "HomeFragment" class represents an instance of the Home window. This
 * window is displayed after the loading screen upon opening the app.
 * The life cycle methods within this class instantiate and initialize
 * different aspects of the UI.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 1.16 3/14/2022
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentHomeBinding.inflate(inflater, container,
            false)
        return binding.root
    }

    /**
     * The onViewCreated method the sets the current theme within
     * imgHomeBackground. The most recent score is set within
     * txtMostRecentScore on the view and each button is given an
     * onClickListener which allows for navigation between fragments.
     *
     * @param view The view returned by onCreateView
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RandomWord.initializeWordList(view.context)
        RandomWord.wordList.shuffle()
        PlayableAdapter.wordList = RandomWord.wordList
        ProfileManager.initializeSelectedProfile()
        binding.imgHomeBackground.setImageResource(ProfileManager
            .profiles[ProfileManager.currentlySelectedProfile].selectedTheme)
        if (ProfileManager.profiles[ProfileManager.currentlySelectedProfile]
                .listOfScores.size > 0) {
            (getString(R.string.str_most_recent_score) + " ${
                ProfileManager.profiles[ProfileManager.currentlySelectedProfile]
                    .listOfScores[ProfileManager.profiles[ProfileManager
                    .currentlySelectedProfile].listOfScores.size - 1]
            } " + getString(R.string.str_wpm)).also { binding.txtMostRecentScore
                .text = it }
        }
        binding.btnHomePlay.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToStartGameFragment()
            view.findNavController().navigate(action)
        }
        binding.btnHomeProfiles.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToProfilesFragment()
            view.findNavController().navigate(action)
        }
        binding.btnHomeStatistics.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToLeaderBoardFragment()
            view.findNavController().navigate(action)

        }
    }
}