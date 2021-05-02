package com.rasyidf.kontakku.database;

public class Teman {

  private int id;
  private String name;
  private String phone;

  public Teman(int id, String name, String address) {
    this.id = id;
    this.name = name;
    this.phone = address;
  }

  public Teman() {
    setId(0);
    setName("");
    setPhone("");
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getId() {
    return id;
  }

  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPhone() {
    return phone;
  }

  public String getName() {
    return name;
  }
}
