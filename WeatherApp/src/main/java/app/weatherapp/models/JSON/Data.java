package app.weatherapp.models.JSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private String address;

    private Days[] days;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Days[] getDays() {
        return days;
    }

    public void setDays(Days[] days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Data{" +
                "address='" + address + '\'' +
                ", days=" + Arrays.toString(days) +
                '}';
    }
}
