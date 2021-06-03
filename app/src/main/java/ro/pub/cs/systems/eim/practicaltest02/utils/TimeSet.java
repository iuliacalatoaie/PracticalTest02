package ro.pub.cs.systems.eim.practicaltest02.utils;

import java.util.Objects;

public class TimeSet {
    private Integer hour;
    private Integer minute;

    public TimeSet(Integer hour, Integer minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "TimeSet{" +
                "hour=" + hour +
                ", minute=" + minute +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSet timeSet = (TimeSet) o;
        return Objects.equals(hour, timeSet.hour) &&
                Objects.equals(minute, timeSet.minute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }
}
