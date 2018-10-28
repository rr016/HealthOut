package cosc431.towson.edu.sqlitedemo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val CHECKBOX_KEY = "somePreference"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // read from shared preferences
        val prefs = getPreferences(Context.MODE_PRIVATE)

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
}
