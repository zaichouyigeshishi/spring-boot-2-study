package com.example.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 响应（包含数据）
 *
 * @author hudongshan
 * @version 20181107
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultData<T> extends Result {

    @JsonProperty("data")
    private T data;

    public ResultData() {
        super();
    }

    public ResultData(int code, String message) {
        super(code, message);
    }

    public static ResultData ok() {
        return new ResultData();
    }

    public ResultData data(T data) {
        this.setData(data);
        return this;
    }

}