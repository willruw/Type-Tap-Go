package applicationOuter.group1.typetapgo.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.data.ProfileManager
import applicationOuter.group1.typetapgo.databinding.FragmentEditProfileBinding
import applicationOuter.group1.typetapgo.model.Profile

/**
 * This "EditProfileFragment" class represents an instance of the  Edit Profile
 * window.This window is displayed when the user selects the "Edit" button on
 * the Profile view page. The life cycle methods within this class instantiate
 * and initialize different aspects of the UI.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 1.16 3/14/2022
 */
class EditProfileFragment : Fragment() {

    var background: Int = 0
    private val key = "index"
    private var _binding: FragmentEditProfileBinding? = null
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
        _binding = FragmentEditProfileBinding.inflate(inflater, container,
                false)
        return binding.root
    }

    /**
     * The onViewCreated method initializes certain views to profile specific
     * data if the profile index passed is not equal to -1. This method also
     * defines the click listeners for each image view and fab.
     *
     * @param view The view returned by onCreateView
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var selected: Int = 0
        if (index > -1) {
            binding.imgProfileEditBackground.setImageResource(ProfileManager
                .profiles[index].selectedTheme)
            background = ProfileManager.profiles[index].selectedTheme
            selected = ProfileManager.profiles[index].avatar
            binding.edtName.setText(ProfileManager.profiles[index].username)
            if (selected == R.drawable.ic_avatar_1) {
                binding.cardAvatar1.setBackgroundColor(getResources()
                    .getColor(R.color.black))
            } else if (selected == R.drawable.ic_avatar_2) {
                binding.cardAvatar2.setBackgroundColor(getResources()
                    .getColor(R.color.black))
            } else {
                binding.cardAvatar3.setBackgroundColor(getResources()
                    .getColor(R.color.black))
            }
            if (background == R.drawable.img_cloud1_bg) {
                binding.cardTheme1.setBackgroundColor(getResources()
                    .getColor(R.color.black))
            } else if (background == R.drawable.img_cloud2_bg) {
                binding.cardTheme2.setBackgroundColor(getResources()
                    .getColor(R.color.black))
            } else {
                binding.cardTheme3.setBackgroundColor(getResources()
                    .getColor(R.color.black))
            }
            binding.edtBio.setText(ProfileManager.profiles[index].bio)
        } else {
            binding.imgProfileEditBackground.setImageResource(ProfileManager
                .profiles[ProfileManager.currentlySelectedProfile].selectedTheme)
            selected =
                ProfileManager.profiles[ProfileManager
                    .currentlySelectedProfile].avatar
            background =
                ProfileManager.profiles[ProfileManager
                    .currentlySelectedProfile].selectedTheme
        }
        binding.imgAvatar1.setOnClickListener {
            selected = R.drawable.ic_avatar_1
            binding.cardAvatar1.setBackgroundColor(getResources()
                .getColor(R.color.black))
            binding.cardAvatar2.setBackgroundColor(0)
            binding.cardAvatar3.setBackgroundColor(0)
        }
        binding.imgAvatar2.setOnClickListener {
            selected = R.drawable.ic_avatar_2
            binding.cardAvatar1.setBackgroundColor(0)
            binding.cardAvatar2.setBackgroundColor(getResources()
                .getColor(R.color.black))
            binding.cardAvatar3.setBackgroundColor(0)
        }
        binding.imgAvatar3.setOnClickListener {
            selected = R.drawable.ic_avatar_3
            binding.cardAvatar1.setBackgroundColor(0)
            binding.cardAvatar2.setBackgroundColor(0)
            binding.cardAvatar3.setBackgroundColor(getResources()
                .getColor(R.color.black))
        }
        binding.imgTheme1.setOnClickListener {
            background = R.drawable.img_cloud1_bg
            binding.cardTheme1.setBackgroundColor(getResources()
                .getColor(R.color.black))
            binding.cardTheme2.setBackgroundColor(Color.WHITE)
            binding.cardTheme3.setBackgroundColor(Color.WHITE)
        }
        binding.imgTheme2.setOnClickListener {
            background = R.drawable.img_cloud2_bg
            binding.cardTheme1.setBackgroundColor(Color.WHITE)
            binding.cardTheme2.setBackgroundColor(getResources()
                .getColor(R.color.black))
            binding.cardTheme3.setBackgroundColor(Color.WHITE)
        }
        binding.imgTheme3.setOnClickListener {
            background = R.drawable.img_cloud3_bg
            binding.cardTheme1.setBackgroundColor(Color.WHITE)
            binding.cardTheme2.setBackgroundColor(Color.WHITE)
            binding.cardTheme3.setBackgroundColor(getResources()
                .getColor(R.color.black))
        }
        binding.btnBackToList.setOnClickListener {
            if (index > -1) {
                val action =
                    EditProfileFragmentDirections
                        .actionEditProfileFragmentToViewProfileFragment(index)
                view.findNavController().navigate(action)
            } else {
                val action =
                    EditProfileFragmentDirections
                        .actionEditProfileFragmentToProfilesFragment()
                view.findNavController().navigate(action)
            }
        }
        binding.btnSave.setOnClickListener {
            if (binding.edtName.text.isEmpty()) {
                val errorMessage = AlertDialog.Builder(this.context)
                errorMessage.setMessage(R.string.str_name_prompt)
                    .setPositiveButton(R.string.str_ok, DialogInterface.OnClickListener
                    { dialog, id -> dialog.cancel() })
                    .setTitle(R.string.str_name).create().show()
            } else {
                if (index > -1) {
                    ProfileManager.profiles[index].username =
                        binding.edtName.text.toString()
                    ProfileManager.profiles[index].bio =
                        binding.edtBio.text.toString()
                    ProfileManager.profiles[index].numWordsTyped =
                        ProfileManager.profiles[index].numWordsTyped
                    ProfileManager.profiles[index].selectedTheme = background
                    ProfileManager.profiles[index].avatar = selected
                    ProfileManager.update(ProfileManager.profiles[index])
                    val action =
                        EditProfileFragmentDirections
                            .actionEditProfileFragmentToViewProfileFragment(index)
                    view.findNavController().navigate(action)
                } else {
                    ProfileManager.add(Profile(binding.edtName.text.toString(),
                        selected, binding.edtBio.text.toString(), 0,
                        0, mutableListOf<Int>(), background,
                        false))
                    val action =
                        EditProfileFragmentDirections
                            .actionEditProfileFragmentToProfilesFragment()
                    view.findNavController().navigate(action)
                }
            }
        }
    }
}