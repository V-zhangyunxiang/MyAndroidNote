package com.example.apt_processor; // PackageElement

public class User { // TypeElement
  private long id; // VariableElement
  private String username; // VariableElement

  public long getId() { // ExecutableElement
    return id;
  }

  public void setId( // ExecutableElement
      long id) { // VariableElement
    this.id = id;
  }

  public String getUsername() { // ExecutableElement
    return username;
  }

  public void setUsername( // ExecutableElement
      String username) { // VariableElement
    this.username = username;
  }
}
