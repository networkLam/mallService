package com.lam.Controller;

import com.lam.RequestDTO.CommentDTO;
import com.lam.Service.CommentService;
import com.lam.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//商品评论的CURD
@RestController
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    //添加商品评论
    @PostMapping("/api/addedComment")
    public Result addedComment(@RequestBody CommentDTO commentDTO){
        log.info("receive data is {}",commentDTO);
        return commentService.addComment(commentDTO);
    }
}
