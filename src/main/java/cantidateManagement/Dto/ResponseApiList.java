package cantidateManagement.Dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseApiList<T> {
    public List<T> data;
    public String message;
    public int statusCode;
}
