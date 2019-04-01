package edu.towson.cosc431.sqlitedemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val CHECKBOX_KEY = "somePreference"
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // read from shared preferences
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val shared = PreferenceManager.getDefaultSharedPreferences(this)

        val isChecked = prefs.getBoolean(CHECKBOX_KEY, false)

        preferenceCheckbox.isChecked = isChecked
        if(isChecked) {
            preferenceTextView.text = "Your preference is checked"
        } else {
            preferenceTextView.text = "Your preference is not checked"
        }

        // update the preferences when the checkbox is clicked
        preferenceCheckbox.setOnClickListener {
            prefs.edit().putBoolean(CHECKBOX_KEY, preferenceCheckbox.isChecked).apply()
        }

        val boolSetting = shared.getBoolean("sample_bool", false)
        val editSetting = shared.getString("sample_edit", "")

        Log.d(TAG, "Bool Setting: ${boolSetting}")
        Log.d(TAG, "Input Setting: ${editSetting}")

        // just deomonstrating that you can listen to changes to the preferences
        prefs.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            val isChecked = sharedPreferences.getBoolean(CHECKBOX_KEY, false)

            preferenceCheckbox.isChecked = isChecked
            if(isChecked) {
                preferenceTextView.text = "Your preference is checked"
            } else {
                preferenceTextView.text = "Your preference is not checked"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
