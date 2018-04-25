package timerapp.jaked.timerapp.database;

public class TimerDbSchema {
    public static final class TimerTable{
        public static final String NAME = "timers";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String MINUTES = "minutes";
            public static final String SECONDS = "seconds";
        }
    }
}
