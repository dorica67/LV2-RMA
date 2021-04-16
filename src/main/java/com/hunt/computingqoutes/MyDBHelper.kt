package com.hunt.computingqoutes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class MyDBHelper(context: Context?): SQLiteOpenHelper(
    context,
    Constants.DB_NAME,
    null,
    Constants.DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Constants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME)
        onCreate(db)
    }
    fun insertRecord(
        name:String?,
        image:String?,
        dob:String?,
        death:String?,
        desciption:String?,
        qoute:String?
    ):Long{
        //get writeable database
        val db = this.writableDatabase
        val  values = ContentValues()
        values.put(Constants.C_NAME,name)
        values.put(Constants.C_IMAGE,image)
        values.put(Constants.C_DOB,dob)
        values.put(Constants.C_DEATH,death)
        values.put(Constants.C_DESCRIPTION,desciption)
        values.put(Constants.C_QOUTE,qoute)
        val id = db.insert(Constants.TABLE_NAME,null,values)

        db.close()
        return id
    }
    fun getAllPersons():ArrayList<InspiringPerson>{
        val personList = ArrayList<InspiringPerson>()
            val selectQuery = "SELECT * FROM "+ Constants.TABLE_NAME
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor.moveToFirst())
        {
            do {
                val modelPerson = InspiringPerson(
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_ID)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_DOB)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_DEATH)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_QOUTE))
                )

                personList.add(modelPerson)
            }while (cursor.moveToNext())
        }

        db.close()
        return personList
        }

}