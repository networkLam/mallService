package com.lam.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result error(String data){
        return new Result("0","fail",data);
    }

    public static Result success(Object data){
        return new Result("1","success",data);
    }
}
