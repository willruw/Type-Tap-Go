package applicationOuter.group1.typetapgo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.data.ProfileManager

/**
 * The LeaderBoardAdapter class formats the data from ProfileManager so
 * that it can be displayed within the recycler view object.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 03-15-2022
 *
 */
class LeaderboardAdapter :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderViewHolder>() {

    var leaderList = ProfileManager.getLeaderBoard()

    /**
     * The LeaderViewHolder is used to create an instance of a view
     * holder. Each view holder instance represents a single item within the
     * ProfileManager leaderboard list. The view holder is passed to
     * the extended class RecyclerView.Adapter.
     *
     * @param view an instance of a View which is passed to RecyclerView
     * .ViewHolder
     */
    class LeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txt_leader_name)
        val txtHighScore: TextView = view.findViewById(
            R.id
                .txt_leader_high_score
        )
        val txtTotal: TextView = view.findViewById(
            R.id
                .txt_leader_total_words
        )
    }

    /**
     * The onCreateViewHolder function is used to create a new view holder
     * for the RecyclerView container when there are no view holders that can
     * be reused.
     *
     * @param parent the view group that the view holder will be attached to
     * @param viewType to pass the view type
     * @return the LeaderViewHolder layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            LeaderViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.leaders, parent, false)
        return LeaderViewHolder(adapterLayout)
    }

    /**
     * The onBindViewHolder function is used to replace the contents of a
     * view with a different view in the list.
     *
     * @param holder the ItemViewHolder created by onCreateViewHolder
     * @param position the current position within the item list
     */
    override fun onBindViewHolder(holder: LeaderViewHolder, position: Int) {
        if (leaderList[position].username.length > 7) {
            "${
                leaderList[position].username
                    .subSequence(0, 7)
            }...".also { holder.txtName.text = it }
        } else {
            holder.txtName.text = leaderList[position].username
        }
        holder.txtHighScore.text = leaderList[position].highestScore.toString()
        holder.txtTotal.text = leaderList[position].numWordsTyped.toString()
    }

    /**
     * The getItemCount function returns the size of the GlobalLeaderList.
     *
     * @return the size of the GlobalLeaderList
     */
    override fun getItemCount(): Int {
        return leaderList.size
    }
}