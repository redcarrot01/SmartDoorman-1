package com.example.doorman;

public class Delivery {
    private Item Item;

    public Item getItem () {
        return Item;
    }

    public static class Item {
        private String progress;
        private String id;
        private TrackingDetails trackingDetails;

        public String getProgress() {
            return progress;
        }

        public String getId() {
            return id;
        }

        public TrackingDetails getTrackingDetails() {
            return trackingDetails;
        }

        public static class TrackingDetails {
            private String kind;
            private String timeString;
            private String where;

            public String getKind() {
                return kind;
            }

            public String getTimeString() {
                return timeString;
            }

            public String getWhere() {
                return where;
            }
        }
    }
}
