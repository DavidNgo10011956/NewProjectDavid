package com.example.s22023newprojectdavid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.s22023newprojectdavid.databinding.AddeditBinding

class ADDEDIT:Activity(), View.OnClickListener {
    private lateinit var binding: AddeditBinding
    val dbh = DBHandler(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddeditBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)

        val extras = intent.extras
        if (extras != null) {
            // read id and from intent
            val id: Int = extras.getInt("ID")
            val person = dbh.getPerson(id)
            //populate the edit texts
            binding.etId.setText(person.id.toString())
            binding.etName.setText(person.name)
            binding.etImageFile.setText(person.imageFile)
            binding.etAddress.setText(person.address)
            binding.etMobile.setText(person.mobile)
            binding.etEmail.setText(person.email)
            binding.ivImageFile.setImageResource(
                this.resources.getIdentifier(
                    person.imageFile,
                    "drawable", "com.example.s22023newprojectdavid"))

        }
    }
    private fun addPerson() {
        val person = Person()
        person.name = binding.etName.text.toString()
        person.mobile = binding.etMobile.text.toString()
        person.address = binding.etAddress.text.toString()
        person.email = binding.etEmail.text.toString()
        person.imageFile = binding.etImageFile.text.toString()

        //call dbHandler to add
        dbh.addPerson(person)
        // display confirmation
        Toast.makeText(this, "new Person has been added", Toast.LENGTH_SHORT).show()
        // go back to main activity
        goBack()

    }

    private fun editPerson(cid: Int) {
    // create a person object and fill with new values
        val person = dbh.getPerson(cid)
        // read and assign name, mobile, address, email, imagefile
        person.name= binding.etName.text.toString()
        person.mobile= binding.etMobile.text.toString()
        person.address= binding.etAddress.text.toString()
        person.email= binding.etEmail.text.toString()
        person.imageFile= binding.etImageFile.text.toString()
        //call DBhandler update function
        dbh.updatePerson(person)
        Toast.makeText(this,"Person has been updated",Toast.LENGTH_LONG).show()
        // go back to main activity
        goBack()
    }

    private fun goBack() {
        // discard changes and go back to main activity
        val mainAct= Intent(this,MainActivity::class.java)
        // start main activity
        startActivity(mainAct)

    }

    override fun onClick(btn:View) {
        //code to run for button and cancel click
        when (btn.id) {
            R.id.btnSave -> {


                val cid: Int = binding.etId.text.toString().toIntOrNull()?: 0
                // decide add or update
                if (cid == 0) {
                    addPerson()
                } else {
                    editPerson(cid)
                }
            }

            R.id.btnCancel -> {
                // cancel code
                goBack()
            }
        }
    }

}






