package com.lam.Service;

import com.lam.RequestDTO.CommentDTO;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.CommentMapper;
import com.lam.pojo.ProductComment;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public Result addComment(CommentDTO commentDTO) {
        //determine this order is had be reviews
        String flag = commentMapper.determineHasBeen(commentDTO.getOrderId(), commentDTO.getPdId());
        if(flag.equals("1")){
            return Result.error("该商品已经评价过");
        }

        //step 1.  insert comment
        ProductComment productComment = new ProductComment();
        productComment.setComment(commentDTO.getComment());
//        LocalDateTime.now();
        productComment.setTime( LocalDateTime.now());
        //get user id
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        productComment.setUser_id(tokenUserInfo.getId());
        productComment.setPd_id(commentDTO.getPdId());
        commentMapper.addComment(productComment);
        //insert images url
        if(!commentDTO.getImages().isEmpty()){
            commentMapper.addFileOfComment(productComment.getId(),commentDTO.getImages());
        }
        //modify reviews status
        commentMapper.modifyOrderStatus(commentDTO.getStars(),commentDTO.getOrderId(),commentDTO.getPdId());

        return Result.success("发布成功");
    }
}
