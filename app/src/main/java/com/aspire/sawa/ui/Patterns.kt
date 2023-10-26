package com.aspire.sawa.ui

import java.util.regex.Pattern

object Patterns {

    val ONLY_ENGLISH_LETTER_PATTERN: Pattern = Pattern.compile("^[a-zA-Z]+$")
    val ONLY_ENGLISH_LETTER_PATTERN_WITH_SPACE: Pattern = Pattern.compile("^[a-zA-Z ]+$")
    val JORDANIAN_PHONE_NUMBER: Pattern = Pattern.compile("^(00962|962|\\+962|0)(7)([789])([0-9]{7})\$")
    val FACEBOOK_LINK: Pattern = Pattern.compile("((http|https)://)?(www[.])?facebook.com/.+")
    val SNAPCHAT_LINK: Pattern = Pattern.compile("((http|https)://)?(www[.])?snapchat.com/.+")
    val INSTAGRAM_LINK: Pattern = Pattern.compile("((http|https)://)?(www[.])?instagram.com/.+")
    val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=])" +  //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{8,}" +  //at least 8 characters
                "$"
    )
}