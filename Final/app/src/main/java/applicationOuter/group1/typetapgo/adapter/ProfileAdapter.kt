package applicationOuter.group1.typetapgo.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.data.ProfileManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import applicationOuter.group1.typetapgo.ui.ProfilesFragment
import applicationOuter.group1.typetapgo.ui.ProfilesFragmentDirections

/**
 * The ProfileAdapter class formats the data from ProfileManager so
 * that it can be displayed within the recycler view object.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 03-15-2022
 *
 */
class ProfileAdapter :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    val profileList = ProfileManager.profiles

    /**
     * The ProfileViewHolder is used to create an instance of a view
     * holder. Each view holder instance represents a single item within the
     * ProfileManager profile list. The view holder is passed to
     * the extended class RecyclerView.Adapter.
     *
     * @param view an instance of a View which is passed to RecyclerView
     * .ViewHolder
     */
    class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView = ProfilesFragment.profileRecyclerView
        val txtName: TextView = view.findViewById(R.id.txt_profile_name)
        val imgAvatar: ImageView = view.findViewById(R.id.img_avatar)
        val imgCurrentlySelected: ImageView = view.findViewById(
            R.id.img_currently_selected
        )
        init {
            view.setOnLongClickListener {
                val setAsCurrent = AlertDialog.Builder(view.context)
                setAsCurrent.setMessage(
                    R.string.str_set_current_profile
                )
                    .setPositiveButton(R.string.str_yes, DialogInterface
                        .OnClickListener { dialog, id ->
                            ProfileManager.setSelectedProfile(
                                index = adapterPosition
                            )
                            recyclerView.adapter?.notifyDataSetChanged()
                            val action = ProfilesFragmentDirections
                                .actionProfilesFragmentSelf()
                            view.findNavController().navigate(action)
                        })
                    .setNegativeButton(R.string.str_no,
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                    .setTitle(R.string.str_current_profile)
                    .create().show()
                true
            }
        }
        init {
            view.setOnClickListener {
                // Send index of adapter position to EditItemFragment on click
                val action = ProfilesFragmentDirections
                    .actionProfilesFragmentToViewProfileFragment(
                        index = adapterPosition
                    )
                view.findNavController().navigate(action)
            }
        }
    }

    /**
     * The onCreateViewHolder function is used to create a new view holder
     * for the RecyclerView container when there are no view holders that can
     * be reused.
     *
     * @param parent the view group that the view holder will be attached to
     * @param viewType to pass the view type
     * @return the ProfileViewHolder layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProfileViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.profiles, parent, false)
        return ProfileViewHolder(adapterLayout)
    }

    /**
     * The onBindViewHolder function is used to replace the contents of a
     * view with a different view in the list.
     *
     * @param holder the ItemViewHolder created by onCreateViewHolder
     * @param position the current position within the item list
     */
    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.txtName.text = profileList[position].username
        holder.imgAvatar.setImageResource(profileList[position].avatar)
        holder.imgCurrentlySelected.isVisible = profileList[position]
            .currentlySelected
    }

    /**
     * The getItemCount function returns the size of the profile list.
     *
     * @return the size of the profile list
     */
    override fun getItemCount(): Int {
        return profileList.size
    }
}