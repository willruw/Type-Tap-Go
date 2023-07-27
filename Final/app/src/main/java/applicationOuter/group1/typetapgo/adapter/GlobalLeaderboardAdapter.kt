package applicationOuter.group1.typetapgo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import applicationOuter.group1.typetapgo.R
import applicationOuter.group1.typetapgo.data.ProfileManager

/**
 * The GlobalLeaderBoardAdapter class formats the data from ProfileManager so
 * that it can be displayed within the recycler view object.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 03-15-2022
 *
 */
class GlobalLeaderboardAdapter :
    RecyclerView.Adapter<GlobalLeaderboardAdapter.GlobalLeaderViewHolder>() {

    var globalLeaderList = ProfileManager.getGlobalLeaderBoard()

    /**
     * The GlobalLeaderViewHolder is used to create an instance of a view
     * holder. Each view holder instance represents a single item within the
     * ProfileManager global leaderboard list. The view holder is passed to
     * the extended class RecyclerView.Adapter.
     *
     * @param view an instance of a View which is passed to RecyclerView
     * .ViewHolder
     */
    class GlobalLeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
     * @return the GlobalLeaderViewHolder layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            GlobalLeaderViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.leaders, parent, false)
        return GlobalLeaderViewHolder(adapterLayout)
    }

    /**
     * The onBindViewHolder function is used to replace the contents of a
     * view with a different view in the list.
     *
     * @param holder the ItemViewHolder created by onCreateViewHolder
     * @param position the current position within the item list
     */
    override fun onBindViewHolder(
        holder: GlobalLeaderViewHolder, position:
        Int
    ) {
        if (globalLeaderList[position].username.length > 7) {
            "${
                globalLeaderList[position].username
                    .subSequence(0, 7)
            }...".also { holder.txtName.text = it }
        } else {
            holder.txtName.text = globalLeaderList[position].username
        }
        holder.txtHighScore.text = globalLeaderList[position].highestScore
            .toString()
        holder.txtTotal.text = globalLeaderList[position].numWordsTyped
            .toString()
    }

    /**
     * The getItemCount function returns the size of the GlobalLeaderList.
     *
     * @return the size of the GlobalLeaderList
     */
    override fun getItemCount(): Int {
        return globalLeaderList.size
    }
}