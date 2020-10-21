package com.example.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hudongshan
 */
@ApiModel(value = "自定义对象",description = "这个对象的说明")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyObject {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "年龄")
    private Integer age;
}
