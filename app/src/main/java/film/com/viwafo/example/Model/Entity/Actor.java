package film.com.viwafo.example.Model.Entity;

import java.io.Serializable;

/**
 * Created by minhl on 06/08/2017.
 */

public class Actor implements Serializable {
    private String name;
    private String urlImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
