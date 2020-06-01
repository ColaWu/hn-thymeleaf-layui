package com.aaa.entity;

import lombok.Data;

import java.util.List;
@Data
public class LayUiTreeTable {
    private int code;
    private String msg;
    private long count;
    private List<?> data;
}
