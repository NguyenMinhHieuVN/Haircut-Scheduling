package com.example.haircutscheduling.classes;

public class User
{
    private String Name;
    private String Email;
    private String Password;
    private String Phone;
    public User() {}

    public User(String name, String email, String password, String phone) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
    }

    public User(String phone) {
        setEmail(Email);
        setPhone(phone);
    }

    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        this.Phone = phone;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        this.Password = password;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        this.Email = email;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
}
