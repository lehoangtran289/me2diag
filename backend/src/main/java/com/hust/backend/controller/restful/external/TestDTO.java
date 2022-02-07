package com.hust.backend.controller.restful.external;

import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

//@Data
@Setter
public class TestDTO implements Serializable {
    private String ok = "ok";

    private String ok2 = "ok2";
}
