package edu.towson.cosc431.sqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object PersonContract {
    object PersonEntry : BaseColumns {
        const val TABLE_NAME = "people"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_AGE = "age"
    }
}

interface IDatabase {
    fun addPerson(p: Person)
    fun getPeople(): List<Person>
}

private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${PersonContract.PersonEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${PersonContract.PersonEntry.COLUMN_NAME_NAME} TEXT," +
                "${PersonContract.PersonEntry.COLUMN_NAME_AGE} TEXT)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${PersonContract.PersonEntry.TABLE_NAME}"

class PersonDatabase(ctx: Context) : IDatabase {
    override fun addPerson(p: Person) {
        val values = toContentValues(p)
        db.insert(PersonContract.PersonEntry.TABLE_NAME, null, values)
    }

    override fun getPeople(): List<Person> {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, PersonContract.PersonEntry.COLUMN_NAME_NAME, PersonContract.PersonEntry.COLUMN_NAME_AGE)

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${BaseColumns._ID} DESC"
        val cursor = db.query(
                PersonContract.PersonEntry.TABLE_NAME,
                projection,
                null, // selection
                null, // selectionArgs
                null, // group
                null, // groupBy
                sortOrder
        )
        val result = mutableListOf<Person>()
        with(cursor) {
            while(cursor.moveToNext()) {
                val name = getString(getColumnIndex(PersonContract.PersonEntry.COLUMN_NAME_NAME))
                val age = getInt(getColumnIndex(PersonContract.PersonEntry.COLUMN_NAME_AGE))
                result.add(Person(name, age))
            }
        }
        return result
    }

    private fun toContentValues(p: Person): ContentValues {
        val cv = ContentValues()
        cv.put(PersonContract.PersonEntry.COLUMN_NAME_NAME, p.name)
        cv.put(PersonContract.PersonEntry.COLUMN_NAME_AGE, p.age)
        return cv
    }

    private val db: SQLiteDatabase

    init {
        db = PersonDbHelper(ctx).writableDatabase
    }

    class PersonDbHelper(ctx: Context) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION)
    {
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        companion object {
            val DATABASE_NAME = "person.db"
            val DATABASE_VERSION = 1
        }
    }
}

