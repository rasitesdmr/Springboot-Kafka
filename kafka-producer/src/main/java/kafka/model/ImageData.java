package kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {

    private String name;

    private String type;

    private byte[] imageData;

    @Override
    public String toString() {
        return "ImageData{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
