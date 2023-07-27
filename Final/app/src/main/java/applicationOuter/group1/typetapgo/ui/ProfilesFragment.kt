package applicationOuter.group1.typetapgo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import applicationOuter.group1.typetapgo.adapter.ProfileAdapter
import applicationOuter.group1.typetapgo.data.ProfileManager
import applicationOuter.group1.typetapgo.databinding.FragmentProfilesBinding

/**
 * This "ProfilesFragment" class represents an instance of the Profile list
 * window. This window is displayed when the user clicks the "Profiles"
 * button on the Home window. The life cycle methods within this class
 * instantiate and initialize different aspects of the UI.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 1.16 3/14/2022
 */
class ProfilesFragment : Fragment(), ProfileManager.DataListener {

    private var _binding: FragmentProfilesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    /**
     * The companion object allows the profileRecyclerView to be shared with
     * the ProfileAdapter.
     */
    companion object {
        lateinit var profileRecyclerView: RecyclerView
    }

    /**
     * The onCreate method instantiates the Fragment.
     *
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * The onCreateView method inflates the layout, initializes binding, and
     * registers this fragment as a listener.
     *
     * @param inflater The layout inflater used to inflate view within the
     * fragment.
     * @param container The container for the view (parent)
     * @param savedInstanceState A Bundle object containing the saved state
     * @return The fragments view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentProfilesBinding.inflate(inflater, container,
            false)
        ProfileManager.registerListener(this)
        return binding.root
    }

    /**
     * The onViewCreated method the sets the current theme within
     * imgProfileBackground. The recycler view that displays user profiles is
     * initialized and the fab buttons are given onClickListeners to allow the
     * user to navigate between fragments.
     *
     * @param view The view returned by onCreateView
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgProfileBackground.setImageResource(ProfileManager
            .profiles[ProfileManager.currentlySelectedProfile].selectedTheme)
        recyclerView = binding.recyclerView
        profileRecyclerView = recyclerView
        recyclerView.adapter = ProfileAdapter()
        binding.btnAdd.setOnClickListener {
            val action =
                ProfilesFragmentDirections
                    .actionProfilesFragmentToEditProfileFragment(-1)
            view.findNavController().navigate(action)
        }
        binding.btnBackToHome.setOnClickListener {
            val action =
                ProfilesFragmentDirections.actionProfilesFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
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
     * The updateData method refreshes the data within the recycler view when
     * the data is changed. This method is required when implementing the
     * data listener interface.
     */
    override fun updateData() {
        recyclerView.adapter?.notifyDataSetChanged()
    }
}