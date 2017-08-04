package com.jc.constant;

/**
 * 部门枚举
 * Created by jasonzhu on 2017/7/13.
 */
public enum DepartmentEnum {
    其他,
    金融部,
    技术部,
    市场运营部,
    行政人事部,
    财务部,
    总经办,
    品宣部;

    public static DepartmentEnum match(String name){
        for (DepartmentEnum departmentEnum : DepartmentEnum.values()) {
            if (departmentEnum.name().equals(name))
                return departmentEnum;
        }
        return DepartmentEnum.其他;
    }
}
