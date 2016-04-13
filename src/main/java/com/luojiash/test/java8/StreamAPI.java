package com.luojiash.test.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;

public class StreamAPI {
    
    public static void collectToMap() {
        List<Person> persons=new ArrayList<>();
        Person p1=new Person(1,"F","luo");
        Person p2=new Person(2,"M","john");
        persons.add(p1);
        persons.add(p2);
        
        Map<Long, Person> map=persons.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println(JSON.toJSONString(map));
    }
    public static void main(String[] args) {
        collectToMap();
    }
}

class Person{
    private long id;
    private String sex;
    private String name;
    public Person(int id, String sex, String name) {
        this.id=id;
        this.sex=sex;
        this.name=name;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}