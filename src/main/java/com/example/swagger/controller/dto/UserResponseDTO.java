package com.example.swagger.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

  private String username;
  private String userID;
  private List<BookDTO> books;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
 static class BookDTO {
    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String publishDate;
    private String publisher;
    private int pages;
    private String description;
    private String website;
  }
}
