package com.lam.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
//查看商品评论的DTO 响应
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentViewDTO {
    private String userName;
    private String gender;
    private LocalDate publishDate;
    private String comment;
    private List<String> images;
}
