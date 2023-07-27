package applicationOuter.group1.typetapgo.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.data.ProfileManager
import applicationOuter.group1.typetapgo.databinding.FragmentLoadingBinding
import applicationOuter.group1.typetapgo.model.Profile

/**
 * This "LoadingFragment" class represents an instance of the loading window.
 * This window is displayed when the user opens the application. The life cycle
 * methods within this class instantiate and initialize different aspects of the
 * UI.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 1.16 3/14/2022
 */
class LoadingFragment : Fragment() {
    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!
    private var flag = false

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
        _binding = FragmentLoadingBinding.inflate(inflater, container,
            false)
        return binding.root
    }

    /**
     * The onViewCreated method initializes the count down timer.
     *
     * @param view The view returned by onCreateView
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        countDownTimer.start()
    }

    /**
     * The onResume method restarts the count down timer if the user
     * leaves and then returns to the application.
     */
    override fun onResume() {
        super.onResume()
        countDownTimer.start()
    }

    /**
     * The onPause method disables the timer so that a new instance of the
     * timer can be created when the user returns to the LoadingFragment.
     */
    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
    }

    /**
     * The onDestroy method disables the timer when this fragment is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    /**
     * The countDownTimer object creates an instance of a count down timer.
     * This timer decrements while keeping this fragment alive. This allows the
     * application to retrieve user data from firebase before the main
     * components of the application (the home page) are displayed.
     *
     * Reference: https://developer.android.com/reference/android/os/
     * CountDownTimer
     */
    val countDownTimer = object : CountDownTimer(5000,
        1000) {

        /**
         * The onTick method controls the text view object "txtLoading" and
         * initializes the user profile to a default profile if the user is
         * logging in for the first time.
         *
         * @param p0 The timer's current value in milliseconds
         */
        override fun onTick(p0: Long) {
            if (p0 < 3000 && flag == false) {
                if (ProfileManager.profiles.isNullOrEmpty()) {
                    val defaultProfile =
                        Profile("Default", R.drawable.ic_avatar_1,
                            "", 0, 0,
                            mutableListOf<Int>(), R.drawable.img_cloud1_bg,
                            true)
                    ProfileManager.add(defaultProfile)
                    flag = true
                }
            }
            if (p0 > 9000) {
                binding.txtLoading.setText(getString(R.string.str_loading_1))
            } else if (p0 > 8000) {
                binding.txtLoading.setText(getString(R.string.str_loading_2))
            } else if (p0 > 7000) {
                binding.txtLoading.setText(getString(R.string.str_loading_3))
            } else if (p0 > 6000) {
                binding.txtLoading.setText(R.string.str_loading_1)
            } else if (p0 > 5000) {
                binding.txtLoading.setText(R.string.str_loading_2)
            } else if (p0 > 4000) {
                binding.txtLoading.setText(R.string.str_loading_3)
            } else if (p0 > 3000) {
                binding.txtLoading.setText(R.string.str_loading_1)
            } else if (p0 > 2000) {
                binding.txtLoading.setText(R.string.str_loading_2)
            } else if (p0 > 1000) {
                binding.txtLoading.setText(R.string.str_loading_3)
            } else {
                binding.txtLoading.setText(R.string.str_loading_1)
            }
        }

        /**
         * The onFinish method automatically navigates the user to the Home
         * fragment.
         */
        override fun onFinish() {
            findNavController().navigate(R.id.homeFragment)
        }
    }
}