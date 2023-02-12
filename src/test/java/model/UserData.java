package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}