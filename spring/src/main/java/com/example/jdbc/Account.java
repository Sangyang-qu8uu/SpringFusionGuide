package com.example.jdbc;

public class Account {
    private Integer id;
    private String name;
    private Double money;

    // 无参构造（JdbcTemplate 反射创建对象时必须要有）
    public Account() {}

    // 有参构造
    public Account(Integer id, String name, Double money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    // 省略 Getter 和 Setter 方法，请在 IDEA 中按 Alt + Insert 自动生成
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getMoney() { return money; }
    public void setMoney(Double money) { this.money = money; }

    @Override
    public String toString() {
        return "Account{id=" + id + ", name='" + name + "', money=" + money + '}';
    }
}
