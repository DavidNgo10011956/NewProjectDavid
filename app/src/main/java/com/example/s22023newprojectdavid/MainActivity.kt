package com.example.s22023newprojectdavid

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s22023newprojectdavid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: com.example.s22023newprojectdavid.databinding.ActivityMainBinding
    private lateinit var db:DBHandler

    private val menuAdd= Menu.FIRST+1
    private val menuEdit= Menu.FIRST+2
    private val menuDelete= Menu.FIRST+3

    // create arraylist for person object and ids
    private var personList:MutableList<Person> = arrayListOf()
    private var idList:MutableList<Int> = arrayListOf()
    // create a DBHandler object
    private val dbHandler = DBHandler(this,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // register for context menu
        registerForContextMenu(binding.lstView)
        // initialise the adapter
        initAdapter()

    }
    private fun initAdapter(){
        //use try catch to handle error
        try{
            // clear all values in arraylist
            personList.clear()
            idList.clear()
            // get all persons from DBHandler and populate arraylist with id and details
            for(person:Person in dbHandler.getAllPerson()){
                // read and add to arraylist list
                personList.add(person)
                idList.add(person.id)
            }
            // create array adapter
            val adp = CustomAdapter(this,personList as ArrayList<Person>)
            // assign adapter to list view
            binding.lstView.adapter = adp
        }catch(e:Exception){
            // show error message toast
            Toast.makeText(this,"Problem:${e.message.toString()}",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE,menuAdd,Menu.NONE,"ADD")
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // this will start a new activity
        val goAddEdit = Intent(this, ADDEDIT::class.java)
        startActivity(goAddEdit)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.add(Menu.NONE,menuEdit,Menu.NONE,"EDIT")
        menu.add(Menu.NONE,menuDelete,Menu.NONE,"DELETE")
        return super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        //code for edit and delete
        when (item.itemId) {
            menuEdit -> {
                //create an item and pass id
                val addEdit = Intent(this, ADDEDIT::class.java)
                //add id for next activity
                addEdit.putExtra("ID", idList[info.position])
                startActivity(addEdit)
            }

            menuDelete -> {
                // call delete function from dbhander
                dbHandler.deletePerson(idList[info.position])
                // refresh the view
                initAdapter()

            }

        }

        return super.onContextItemSelected(item)

    }

}

