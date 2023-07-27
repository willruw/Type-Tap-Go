package applicationOuter.group1.typetapgo.data

import android.util.Log
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import applicationOuter.group1.typetapgo.model.Profile
import applicationOuter.group1.typetapgo.ui.GameFragment

/**
 * The ProfileManager data manager class for managing and connecting the list
 * of profiles in the firebase database to the local lists.
 *
 * @author Brendon Schultz
 * @author William Robertson
 * @since 03-15-2022
 */
object ProfileManager {

    var currentlySelectedProfile = -1
    var userId: String? = null
    val profiles: MutableList<Profile> = mutableListOf()
    private var listReg: ListenerRegistration? = null
    private var globalListReg: ListenerRegistration? = null
    private val listeners: MutableSet<DataListener> = mutableSetOf()
    private val globalProfiles: MutableList<Profile> = mutableListOf()

    /**
     * Neseted DataListener interface for registering listeners throughout
     * the app.
     *
     */
    interface DataListener {
        fun updateData()
    }

    /**
     * Method for registering listeners for when data is changed in the
     * database.
     *
     * @param fragment the fragment registering as a data listener
     */
    fun registerListener(fragment: DataListener) {
        listeners.add(fragment)
    }

    /**
     * Method that initializes the lists in the ProfileManager by calling the
     * appropriate document paths to the database.
     *
     */
    fun startListening() {
        if (userId == null) return
        val db = Firebase.firestore
        val globalRef = db.collection("Leaderboard")
            .document("globalleaderboard").collection("Profiles")
        val ref =
            db.collection("users").document(userId!!).collection("Profiles")
        globalListReg = globalRef.addSnapshotListener { result, e ->
            if (e != null) {
                Log.w("DB_Error", "Error getting documents: ", e)
                return@addSnapshotListener
            }
            globalProfiles.clear()
            for (document in result!!) {
                globalProfiles.add(document.toObject<Profile>())
            }
            for (listener in listeners) {
                listener.updateData()
            }
        }
        listReg = ref.addSnapshotListener { result, e ->
            if (e != null) {
                Log.w("DB_Error", "Error getting documents: ", e)
                return@addSnapshotListener
            }
            profiles.clear()
            for (document in result!!) {
                profiles.add(document.toObject<Profile>())
            }
            for (listener in listeners) {
                listener.updateData()
            }
        }
    }

    /**
     * Stops listening to the database document paths.
     *
     */
    fun stopListening() {
        listReg?.remove()
        globalListReg?.remove()
    }

    /**
     * Creates a ranked local leaderboard based on the user's profiles.
     *
     * @return the local leaderboard
     */
    fun getLeaderBoard(): MutableList<Profile> {
        if (profiles.size >= 10) {
            return profiles.sortedByDescending { it.highestScore }
                .subList(0, 10).toMutableList() // Brendon Added
        } else {
            return profiles.sortedByDescending { it.highestScore }
                .subList(0, profiles.size).toMutableList()
        }
    }

    /**
     * Creates a ranked global leaderboard based on all user's profiles.
     *
     * @return the global leaderboard
     */
    fun getGlobalLeaderBoard(): MutableList<Profile> {
        if (globalProfiles.size >= 10) {
            return globalProfiles.sortedByDescending { it.highestScore }
                .subList(0, 10).toMutableList() // Brendon Added
        } else {
            return globalProfiles.sortedByDescending { it.highestScore }
                .subList(0, globalProfiles.size).toMutableList()
        }
    }

    /**
     * Sets the currently selected profile to be referenced throughout the
     * application.
     *
     * @param index the index of the new currently selected profile
     */
    fun setSelectedProfile(index: Int) {
        profiles[index].currentlySelected = true
        currentlySelectedProfile = index
        GameFragment.currentlySelectedProfile = currentlySelectedProfile
        update(profiles[index])
        for (i in 0 until profiles.size) {
            if (i != index) {
                profiles[i].currentlySelected = false
                update(profiles[i])
            }
        }
    }

    /**
     * Initializes the currently selected profile by going through the list
     * of profiles to find which one is the currently selected profile.
     *
     */
    fun initializeSelectedProfile() {
        for (i in 0 until profiles.size) {
            if (profiles[i].currentlySelected) {
                setSelectedProfile(i)
            }
        }
    }

    /**
     * Updates the given profile by sending the updated profile to the
     * database to replace the old version.
     *
     * @param index the index of the profile
     * @param profile the profile to be updated
     */
    fun update(profile: Profile) {
        val db = Firebase.firestore
        db.collection("users")
            .document(userId!!)
            .collection("Profiles")
            .document(profile.id.toString())
            .set(profile)
        db.collection("Leaderboard")
            .document("globalleaderboard")
            .collection("Profiles")
            .document(profile.id.toString())
            .set(profile)
    }

    /**
     * Adds the given profile to the database.
     *
     * @param profile the profile to be added
     */
    fun add(profile: Profile) {
        val db = Firebase.firestore
        db.collection("users")
            .document(userId!!)
            .collection("Profiles")
            .document(profile.id.toString())
            .set(profile)
        db.collection("Leaderboard")
            .document("globalleaderboard")
            .collection("Profiles")
            .document(profile.id.toString())
            .set(profile)
    }

    /**
     * Deletes the profile at the given index from the database.
     *
     * @param index the index of the profile to be deleted
     */
    fun delete(index: Int) {
        val profile = profiles[index]
        val db = Firebase.firestore
        db.collection("users")
            .document(userId!!)
            .collection("Profiles")
            .document(profile.id.toString())
            .delete()
        db.collection("Leaderboard")
            .document("globalleaderboard")
            .collection("Profiles")
            .document(profile.id.toString())
            .delete()
    }
}