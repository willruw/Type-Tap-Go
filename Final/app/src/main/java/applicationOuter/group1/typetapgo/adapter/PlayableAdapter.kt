package applicationOuter.group1.typetapgo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import applicationOuter.group1.typetapgo.R

/**
 * The PlayableAdapter class formats the data from wordlist so
 * that it can be displayed within the recycler view object.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 03-15-2022
 *
 */
class PlayableAdapter :
    RecyclerView.Adapter<PlayableAdapter.PlayableViewHolder>() {

    /**
     * Mutable list of Random words from the RandomWord data class.
     */
    companion object {
        var wordList = mutableListOf<String>()
    }

    /**
     * The PlayableViewHolder is used to create an instance of a view
     * holder. Each view holder instance represents a single random word from
     * the wordlist. The view holder is passed to the extended class
     * RecyclerView.Adapter.
     *
     * @param view an instance of a View which is passed to RecyclerView
     * .ViewHolder
     */
    class PlayableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtWord: TextView = view.findViewById(R.id.txt_play_text)
    }

    /**
     * The onCreateViewHolder function is used to create a new view holder
     * for the RecyclerView container when there are no view holders that can
     * be reused.
     *
     * @param parent the view group that the view holder will be attached to
     * @param viewType to pass the view type
     * @return the PlayableViewHolder layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PlayableViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.playable, parent, false)
        return PlayableViewHolder(adapterLayout)
    }

    /**
     * The onBindViewHolder function is used to replace the contents of a
     * view with a different view in the list.
     *
     * @param holder the ItemViewHolder created by onCreateViewHolder
     * @param position the current position within the item list
     */
    override fun onBindViewHolder(holder: PlayableViewHolder, position: Int) {
        holder.txtWord.text = wordList[position]
    }

    /**
     * The getItemCount function returns the size of the wordlist.
     *
     * @return the size of the wordlist
     */
    override fun getItemCount(): Int {
        return wordList.size
    }
}
