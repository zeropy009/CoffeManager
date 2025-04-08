/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Enums.Roles;
import Enums.Sex;

/**
 *
 * @author zero
 */
public class User extends BaseModel {
    private String userName;
    private String passWord;
    private Roles role;
    private String fullName;
    private Sex sex;
    private String address;
    private int yearOfBirth;
    private String phone;
    private String email;
    private int salary;

    public User() {
    }

    public User(String userName, String passWord, int role, String fullName, boolean sex, String address, int yearOfBirth, String phone, String email, int salary) {
        this.userName = userName;
        this.passWord = passWord;
        this.role = Roles.fromCode(role);
        this.fullName = fullName;
        this.sex = Sex.fromSex(sex);
        this.address = address;
        this.yearOfBirth = yearOfBirth;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    
    @Override
    public String toString() {
        return userName;
    }
}
