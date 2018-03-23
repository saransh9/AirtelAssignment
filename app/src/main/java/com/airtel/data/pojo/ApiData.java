package com.airtel.data.pojo;

/**
 * Created by saransh on 23/03/18.
 */

public class ApiData {

        private Wallpapers[] wallpapers;

        private String success;

        public Wallpapers[] getWallpapers ()
        {
            return wallpapers;
        }

        public void setWallpapers (Wallpapers[] wallpapers)
        {
            this.wallpapers = wallpapers;
        }

        public String getSuccess ()
        {
            return success;
        }

        public void setSuccess (String success)
        {
            this.success = success;
        }

        @Override
        public String toString()
        {
            return "ApiData [wallpapers = "+wallpapers+", success = "+success+"]";
        }


}
