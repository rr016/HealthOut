package cosc431.towson.edu.sqlitedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IController {
    override fun addPerson() {
        val p = Person("Person ${list.size}", list.size + 15)
        list = list.plusElement(p)
        recyclerView.adapter.notifyDataSetChanged()
    }

    override lateinit var list: List<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = getPeople()

        recyclerView.adapter = PersonAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addPersonBtn.setOnClickListener { addPerson() }
    }

    private fun getPeople(): List<Person> {
        return (0..10).map { Person("Person ${it}", it + 15) }
    }
}

interface IController {
    val list: List<Person>
    fun addPerson()
}