package com.javadi.dictionary.database;

public class DBC {

    public class mainDB{
        public static final String TABLE_NAME="data";
        public static final String ENGLISH_WORD="En";
        public static final String PERSIAN_WORD="Fa";
    }

    public class searchedWords {
        public static final String TABLE_NAME="HISTORY";
        public static final String id="ID";
        public static final String ENGLISH_WORD="WORD";
        public static final String PERSIAN_WORD="MEAN";
        public static final String checkFavorite="CHECK_FAVORITE";
    }
}
