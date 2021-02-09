package com.example.doorman;

public class Weather {
    private Item Item;

    public Item getItem () {
        return Item;
    }

    public static class Item {
        private String area;
        private String min_temp;
        private String cur_temp;
        private String name;
        private String ultra_dust;
        private String id;
        private String max_temp;
        private String dust;

        public String getArea () {
            return area;
        }

        public String getMin_temp () {
            return min_temp;
        }

        public String getCur_temp () {
            return cur_temp;
        }

        public String getName () {
            return name;
        }

        public String getUltra_dust () {
            return ultra_dust;
        }

        public String getId () {
            return id;
        }

        public String getMax_temp () {
            return max_temp;
        }

        public String getDust () {
            return dust;
        }

        @Override
        public String toString() {
            return "ClassPojo [area = "+area+", min_temp = "+min_temp+", cur_temp = "+cur_temp+", name = "+name+", ultra_dust = "+ultra_dust+", id = "+id+", max_temp = "+max_temp+", dust = "+dust+"]";
        }
    }
}
