package cosc431.towson.edu.sqlitedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IController {
    /**
     * Adds a person to the list of people
     */
    override fun addPerson() {
        // create a new random person
        val p = Person("Person ${list.size}", list.size + 15)
        // add teh person to the db
        db.addPerson(p)
        // get the new list of people
//        list = db.getPeople()
        list.clear()
        list.addAll(db.getPeople())
        // notify the recyclerview
        recyclerView.adapter.notifyDataSetChanged()
    }

    override val list: MutableList<Person> = mutableListOf()

    private lateinit var db: PersonDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create the database class
        db = PersonDatabase(this)
        // populate the initial list of people
        list.addAll(getPeople())

        recyclerView.adapter = PersonAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addPersonBtn.setOnClickListener { addPerson() }
    }

    private fun getPeople(): List<Person> {
        return db.getPeople()
    }
}

interface IController {
    val list: List<Person>
    fun addPerson()
}