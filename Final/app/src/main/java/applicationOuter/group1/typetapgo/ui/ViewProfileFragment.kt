package applicationOuter.group1.typetapgo.ui

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.data.ProfileManager
import applicationOuter.group1.typetapgo.databinding.FragmentViewProfileBinding

/**
 * This "ViewProfileFragment" class represents an instance of the View Profile
 * window. This window is displayed after the user clicks on a "Profile"
 * within the profile list. The life cycle methods within this class instantiate
 * and initialize different aspects of the UI.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 1.16 3/14/2022
 */
class ViewProfileFragment : Fragment() {

    private val key = "index"
    private var _binding: FragmentViewProfileBinding? = null
    private val binding get() = _binding!!
    private var index: Int = -1

    /**
     * The onCreate method instantiates the Fragment and collects the index
     * argument.
     *
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            index = it.getInt(key)
        }
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
        _binding = FragmentViewProfileBinding.inflate(inflater, container,
                false)
        return binding.root
    }

    /**
     * The onViewCreated method initializes certain views to profile specific
     * data if the profile index passed is not equal to -1. This method also
     * defines the click listeners for the back, delete, and edit fabs.
     *
     * @param view The view returned by onCreateView
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgProfileViewBackground.setImageResource(ProfileManager
            .profiles[ProfileManager.currentlySelectedProfile].selectedTheme)
        if (index > -1) {
            binding.txtViewName.setText(ProfileManager.profiles[index].username)
            binding.imgSelectedAvatar.setImageResource(ProfileManager
                .profiles[index].avatar)
            binding.txtViewBio.setText(ProfileManager.profiles[index].bio)
            binding.imgSelectedTheme.setImageResource(ProfileManager
                .profiles[index].selectedTheme)
            binding.txtUserHighScore.setText(String.format(resources.getString(R.string.str_high_score_output),
                ProfileManager.profiles[index].highestScore.toString()))
            binding.txtUserWordCount.setText(String.format(resources.getString(R.string.str_total_words_output),
                ProfileManager.profiles[index].numWordsTyped.toString()))
            binding.txtUserSpeed.setText(String.format(resources.getString(R.string.str_average_speed_output),
                ProfileManager.profiles[index].getAverageScore()
                .toString()))
        }
        binding.btnBackToProfiles.setOnClickListener {
            val action =
                ViewProfileFragmentDirections
                    .actionViewProfileFragmentToProfilesFragment()
            view.findNavController().navigate(action)
        }
        if (ProfileManager.profiles.size == 1) {
            binding.btnDelete.isVisible = false
        }
        binding.btnDelete.setOnClickListener {
            ProfileManager.delete(index)
            val deleteProfile = AlertDialog.Builder(view.context)
            deleteProfile.setMessage(R.string.str_deleting)
                .setTitle(R.string.str_delete_profile).setCancelable(false)
            val dlg = deleteProfile.create()
            dlg.show()
            val countDownTimer = object : CountDownTimer(2000,
                1000) {
                override fun onTick(p0: Long) {
                    if (p0 < 2000) {
                        if (index == ProfileManager.currentlySelectedProfile) {
                            ProfileManager.setSelectedProfile(0)
                        }
                    }
                }
                override fun onFinish() {
                    ProfileManager.initializeSelectedProfile()
                    dlg.cancel()
                    val action =
                        ViewProfileFragmentDirections
                            .actionViewProfileFragmentToProfilesFragment()
                    view.findNavController().navigate(action)
                }
            }.start()
        }
        binding.btnEdit.setOnClickListener {
            val action =
                ViewProfileFragmentDirections
                    .actionViewProfileFragmentToEditProfileFragment(index)
            view.findNavController().navigate(action)
        }
    }
}