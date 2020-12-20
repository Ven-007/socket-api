package cn.clouds.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author clouds
 * @version 1.0
 */
@Data
@ToString
public class User {
    private int id;
    private String name;
    private int age;
    private double money;
}
