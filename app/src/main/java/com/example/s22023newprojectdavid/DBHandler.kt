package com.example.s22023newprojectdavid

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context:Context,factory :SQLiteDatabase.CursorFactory?)
    :SQLiteOpenHelper(context,DATABASE_NAME,factory,DB_VERSION) {
    companion object {
        const val DATABASE_NAME = "HRManager.db"
        const val DB_VERSION = 1
    }
    private val tableName: String = "PERSON"
    private val keyID: String = "ID"
    private val keyName: String = "NAME"
    private val keyMobile: String = "MOBILE"
    private val keyEmail: String = "EMAIL"
    private val keyAddress: String = "ADDRESS"
    private val keyImageFile: String = "IMAGEFILE"

    override fun onCreate(db: SQLiteDatabase) {
        //create a sql statement
        val createTable="CREATE TABLE $tableName($keyID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$keyName TEXT, $keyImageFile TEXT,$keyAddress TEXT,$keyMobile Text,$keyEmail Text)"
        // execute sql statement to create a table
        db.execSQL(createTable)
        // add a sample record to database using contentValues
        val cv = ContentValues()
        cv.put(keyName,"David Ngo")
        cv.put(keyMobile,"0417694579")
        cv.put(keyEmail,"dengo@studytafensw.edu.au")
        cv.put(keyAddress,"27 Washington st, Bexley ,NSW 2207")
        cv.put(keyImageFile,"first")
        // use db.insert function
        db.insert(tableName,null,cv)
    }


    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        // drop existing table
        db.execSQL("DROP TABLE IF EXISTS $tableName")
        // create table
        onCreate(db)
    }
    fun getAllPerson():ArrayList<Person> {
        // declare an arraylist
        val personList = ArrayList<Person>()
        // create a sql query
        val selectQuery = "SELECT * FROM $tableName"
        // get readable database
        val db = this.readableDatabase
        // run query and put result in cursor
        val cursor = db.rawQuery(selectQuery, null)
        // loop through cursor to read all the records
        if (cursor.moveToFirst()) {
            do {
                // create a person object
                val person = Person()
                person.id = cursor.getInt(0)
                person.name = cursor.getString(1)
                person.imageFile =cursor.getString(2)
                person.address = cursor.getString(3)
                person.mobile = cursor.getString(4)
                person.email = cursor.getString(5)
                // add person object to array list
                personList.add(person)
            } while (cursor.moveToNext())
        }
        //close cursor and database
        cursor.close()
        db.close()
        return personList

    }
    //add new record in the data base
    fun addPerson(person:Person){
        // get writable db
        val db= this.writableDatabase
        // create content values object
        val cv = ContentValues()
        cv.put(keyName,person.name)
        cv.put(keyImageFile,person.imageFile)
        cv.put(keyAddress,person.address)
        cv.put(keyMobile,person.mobile)
        cv.put(keyEmail,person.email)
        db.insert(tableName,null , cv)
        // close the db
        db.close()


    }
    fun updatePerson(person:Person){
        // get writable db
        val db= this.writableDatabase
        // create content values object
        val cv = ContentValues()
        cv.put(keyName,person.name)
        cv.put(keyImageFile,person.imageFile)
        cv.put(keyAddress,person.address)
        cv.put(keyMobile,person.mobile)
        cv.put(keyEmail,person.email)
        // update function of db
        db.update(tableName, cv,"$keyID=?", arrayOf((person.id.toString())))
        // close the db
        db.close()

    }
    fun getPerson(id:Int):Person{
        // get readable db
        val db= this.readableDatabase
        // create contact object to fill data
        val person=Person()
        // create cursor based on query
        val cursor=db.query(tableName, arrayOf(keyID,keyName,keyImageFile,keyAddress,keyMobile,keyEmail),
        "$keyID=?",
            arrayOf(id.toString()),
            null,
            null,
            null)
        // check cursor and read value and put in contact
        if (cursor!=null){
            cursor.moveToFirst()
            person.id= cursor.getInt(0)
            person.name=cursor.getString(1)
            person.imageFile=cursor.getString(2)
            person.address=cursor.getString(3)
            person.mobile=cursor.getString(4)
            person.email=cursor.getString(5)
        }
            cursor.close()
        db.close()
        // return person object
        return person

        }

    fun deletePerson(id: Int) {
        // get writable database
        val db=this.writableDatabase
        // call db delete function
        db.delete(tableName,"$keyID=?", arrayOf(id.toString()))
        // close db
        db.close()

    }

}






