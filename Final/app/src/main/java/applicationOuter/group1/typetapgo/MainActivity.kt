package applicationOuter.group1.typetapgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import applicationOuter.group1.typetapgo.data.ProfileManager
import applicationOuter.group1.typetapgo.databinding.ActivityMainBinding


/**
 * This main activity acts as the entry way to the rest of the app. This app
 * challenges users to type as many random words correctly as they can within
 * the one minute time limit.
 *
 * @author William Robertson
 * @author Brendon Schultz
 * @since 1.2 03/01/2022
 */
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private var authListener: FirebaseAuth.AuthStateListener? = null
    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
            this.onSignInResult(res)
        }

    /**
     * The onCreate method initializes the ProfileManager to start listening
     * or reading data from the database when a user is logged in. If a user
     * is not logged in, this method will open a new window in which the user
     * can create a new account.
     *
     * @param savedInstanceState A Bundle object containing the saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                ProfileManager.userId = user.uid
                ProfileManager.startListening()
            } else {
                ProfileManager.userId = null
                ProfileManager.stopListening()
                val signInIntent =
                    AuthUI.getInstance().createSignInIntentBuilder().build()
                signInLauncher.launch(signInIntent)
            }
        }
    }

    /**
     * Starts listening from Firebase when the app is in the onResume state of
     * its lifecycle.
     */
    override fun onResume() {
        super.onResume()
        ProfileManager.startListening()
        FirebaseAuth.getInstance().addAuthStateListener(authListener!!)
    }

    /**
     * Stops listening from database when the app is in the onPause state of
     * its lifecycle.
     */
    override fun onPause() {
        super.onPause()
        ProfileManager.stopListening()
        FirebaseAuth.getInstance().removeAuthStateListener(authListener!!)
    }

    /**
     * Displays toasts to let the user know if login was successful or if an
     * error occurred.
     *
     * @param The callback from FirebaseAuthUIActivityResultContract()
     */
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        when (result.resultCode) {
            RESULT_OK -> {
                Toast.makeText(this, "Signed in!",
                    Toast.LENGTH_SHORT).show();
                findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.loadingFragment)
            }
            RESULT_CANCELED -> {
                Toast.makeText(this, "Start over.",
                    Toast.LENGTH_SHORT).show();
            }
            else -> {
                Toast.makeText(this, "Sign-in error!",
                    Toast.LENGTH_SHORT)
                    .show();
                finish()
            }
        }
    }

    /**
     * The onCreateOptionsMenu method inflates a menu at the top of the
     * display. This menu holds holds a button that the user can click if
     * they would like to sign into a new account.
     *
     * @param menu The options menu in which items will be places
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    /**
     * The onOptionsItemSelected method handles user interaction with the
     * "switch user" button on the options menu. If pressed this method will
     * display an alert, prompting the user to switch to a new account or
     * cancel this action.
     *
     * @param item The item that was selected
     * @return The result of user input
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_user -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.str_action_switch_user)
                    .setMessage(R.string.str_dialog_switch_message)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(R.string.str_dialog_switch_yes) { dialogInterface, which ->
                        findNavController(R.id.nav_host_fragment)
                            .navigate(R.id.loadingFragment)
                        FirebaseAuth.getInstance().signOut()
                    }
                    .setNegativeButton(R.string.str_dialog_switch_no)
                    { dialogInterface, which ->
                        return@setNegativeButton
                    }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * The onBackPressed method disables android's back button. This was
     * helps improve transition animations by requiring the user to use in
     * app buttons.
     */
    override fun onBackPressed() {}
}