package com.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * 存放字段属性信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanStruct {

    //字段的名字
    private String  fieldName;
    //字段的类型
    private Object  fieldType;
    //字段值读方法
    private Method  readMethod;
    //字段值写方法
    private Method  writeMethod;
    private boolean isDeclared;

}
