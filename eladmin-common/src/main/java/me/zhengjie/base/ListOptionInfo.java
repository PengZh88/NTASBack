package me.zhengjie.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ListOptionInfo implements Serializable {
    private String label;
    private String engLabel;
    private String value;
}
