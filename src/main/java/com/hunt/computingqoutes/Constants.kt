package com.hunt.computingqoutes

object Constants {

    const val DB_NAME = "MY_RECORDS_DB"
    const val DB_VERSION = 1

    const val TABLE_NAME = "MY_RECORDS_TABLE"

    const val C_ID = "ID"
    const val C_NAME = "NAME"
    const val C_IMAGE = "IMAGE"
    const val C_DOB = "DOB"
    const val C_DEATH = "DEATH"
    const val C_DESCRIPTION = "DESCRIPTION"
    const val C_QOUTE = "QOUTE"

    const val CREATE_TABLE = (
            "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_DOB + " TEXT,"
            + C_DEATH + " TEXT,"
            + C_DESCRIPTION + " TEXT,"
            + C_QOUTE + " TEXT"
            +")"
            )



}