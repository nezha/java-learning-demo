package com.nezha.learn.demo.constant;

import lombok.Getter;

/**
 * @Description: xxxxx <br>
 * @Date: 2019/4/3 10:27 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Getter
public enum CarType {
    BMW(1, "宝马"),
    AUDI(2, "奥迪"),
    SAIC(3, "上汽");

    private Integer code;
    private String type;

    CarType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public static CarType convert(String type) {

        switch (type) {
            case "宝马":
                return BMW;
            case "奥迪":
                return AUDI;
            case "上汽":
                return SAIC;
        }
        return null;
    }
}
