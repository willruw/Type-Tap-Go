package applicationOuter.group1.typetapgo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import applicationOuter.group1.typetapgo.adapter.GlobalLeaderboardAdapter
import applicationOuter.group1.typetapgo.adapter.LeaderboardAdapter
import applicationOuter.group1.typetapgo.data.ProfileManager
import applicationOuter.group1.typetapgo.databinding.FragmentLeaderBoardBinding

/**
 * This "LeaderBoardFragment" class represents an instance of the Leaderboard
 * or "Stats" window. This window is displayed when the user clicks the "Stats"
 * button on the Home window. The life cycle methods within this class
 * instantiate and initialize different aspects of the UI.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 1.16 3/14/2022
 */
class LeaderBoardFragment : Fragment() {

    private var _binding: FragmentLeaderBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

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
        _binding = FragmentLeaderBoardBinding.inflate(inflater, container,
                false)
        return binding.root
    }

    /**
     * The onViewCreated method the sets the current theme within
     * imgLeaderboardBackground. The recycler view is initialized using data
     * from LeaderboardAdapter and an onClickListener is set within a toggle
     * switch. This switch allows the user to re-initialize the recycler view
     * with data from the GlobalLeaderboardAdapter.
     *
     * @param view The view returned by onCreateView
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switch = binding.globalSwitch
        switch.isChecked = false
        recyclerView = binding.recyclerViewLeaderboard
        recyclerView.adapter = LeaderboardAdapter()
        binding.imgLeaderboardBackground.setImageResource(ProfileManager
            .profiles[ProfileManager.currentlySelectedProfile].selectedTheme)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                recyclerView.adapter = GlobalLeaderboardAdapter()
            } else {
                recyclerView.adapter = LeaderboardAdapter()
            }
        }
        binding.btnBackHome.setOnClickListener {
            val action =
                LeaderBoardFragmentDirections
                    .actionLeaderBoardFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
    }
}