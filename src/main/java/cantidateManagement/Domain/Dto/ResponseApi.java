package cantidateManagement.Domain.Dto;

import lombok.Data;

@Data
public class ResponseApi<T> {
    public T data;
    public String message;
    public int statusCode;
}
