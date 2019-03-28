package edu.towson.cosc431.sqlitedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.person_item.view.*

class PersonAdapter(val controller: IController) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return controller.list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val person = controller.list.get(position)
        holder.itemView.personNameTv.text = person.name
        holder.itemView.personAgeTv.text = person.age.toString()
    }
}

class PersonViewHolder(view: View):RecyclerView.ViewHolder(view)

data class Person(var name: String, var age: Int)