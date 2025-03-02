package com.lam.Service;

import com.lam.RequestDTO.CommentDTO;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.CommentMapper;
import com.lam.mapper.ManageMapper;
import com.lam.mapper.ProductMapper;
import com.lam.pojo.*;
import com.lam.responseDTO.CommentViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ManageMapper manageMapper;

    public Result addComment(CommentDTO commentDTO) {
        //评价内容和星数
        if (commentDTO.getComment().isEmpty() || commentDTO.getStars() == 0) {
            return Result.error("评价内容不全，请检查");
        }

        //determine this order is had be reviews
        String flag = commentMapper.determineHasBeen(commentDTO.getOrderId(), commentDTO.getPdId());
        if (flag.equals("1")) {
            return Result.error("该商品已经评价过");
        }

        //step 1.  insert comment
        ProductComment productComment = new ProductComment();
        productComment.setComment(commentDTO.getComment());
//        LocalDateTime.now();
        productComment.setTime(LocalDateTime.now());
        //get user id
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        productComment.setUser_id(tokenUserInfo.getId());
        productComment.setPd_id(commentDTO.getPdId());
        commentMapper.addComment(productComment);
        //step 2. insert images url
        if (!commentDTO.getImages().isEmpty()) {
            commentMapper.addFileOfComment(productComment.getId(), commentDTO.getImages());
        }
        //step 3. modify reviews status
        commentMapper.modifyOrderStatus(commentDTO.getStars(), commentDTO.getOrderId(), commentDTO.getPdId());

        return Result.success("发布成功");
    }

    public Result readComment(Integer pdId, Integer offset) {
        //step 1. read product information
        try {
            Product product = productMapper.queryProductInfo(pdId);
            if (Objects.isNull(product)) {
                return Result.error("该产品不存在");
            }
            List<CommentViewDTO> commentViewDTOList = new ArrayList<>();
            //find product reviews
            List<ProductComment> productComments = commentMapper.readProductReviews(pdId, offset);
            if (productComments.isEmpty()) {
                return Result.error("暂无评论");
            }
            productComments.forEach(item -> {
                CommentViewDTO commentViewDTO = new CommentViewDTO();
                commentViewDTO.setComment(item.getComment());
                commentViewDTO.setPublishDate(item.getTime().toLocalDate());

                //find reviews picture path
                List<ProductCommentFile> productCommentFiles = commentMapper.readProductReviewsFile(item.getId());
                if (!productCommentFiles.isEmpty()) {
                    //defined a list
                    List<String> imgList = new ArrayList<>();
                    productCommentFiles.forEach(k -> {
                        imgList.add(k.getFile_name());
//                        commentViewDTO.getImages().add(k.getFile_name());
                    });
                    commentViewDTO.setImages(imgList);
//                    System.out.println("1123");
//                    commentViewDTO.setGender();
                }
                //find user info
                User userInfo = manageMapper.findUserId(String.valueOf(item.getUser_id()));
                if (!Objects.isNull(userInfo)) {
                    //write user info
                    commentViewDTO.setGender(userInfo.getGender());
                    commentViewDTO.setUserName(userInfo.getUser_name());
                }
                commentViewDTOList.add(commentViewDTO);
            });
            return Result.success(commentViewDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("访问出现错误");
        }
    }
}
