package raum.muchbeer.total

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.databinding.ActivityHseBinding
import raum.muchbeer.total.viewmodel.HseViewModel

@AndroidEntryPoint
class HseActivity : AppCompatActivity() {
   private lateinit var binding : ActivityHseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hse)

    //    binding = DataBindingUtil.setContentView(this, R.layout.activity_hse)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        val navController = findNavController(this, R.id.fragment_host)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        setupActionBarWithNavController(this, navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.logout -> {
                loginPhase()
                true
            }
            R.id.help -> {
                loginPhase()
                //  Toast.makeText(this, "Please contact admin", Toast.LENGTH_LONG).show()
                //   val intent = Intent(this, HseActivity::class.java)
                //   startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loginPhase() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}