package com.google.developer.bugmaster.data;

import android.provider.BaseColumns;

public class BugsContract {

    private BugsContract(){}

    public static class BugEntry implements BaseColumns{
        public static final String TABLE_NAME="bugs";
        public static final String FRIENDLY_NAME="friendlyName";
        public static final String SCIENTIFIC_NAME="scientificName";
        public static final String CLASSIFICATION="classification";
        public static final String IMAGE_ASSET="imageAsset";
        public static final String DANGER_LEVEL="dangerLevel";
    }
}
