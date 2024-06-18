package com.lam.Controller;

import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.CollectionMapper;
import com.lam.pojo.Collection;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CollectionController {
    @Autowired
    private CollectionMapper collectionMapper;

    //    添加收藏
    @GetMapping("/api/collection/add")
    public Result addCollection(Integer id) {
        LocalDateTime t = LocalDateTime.now();
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        try {
            Collection exist = collectionMapper.isExist(tokenUserInfo.getId(), id);  //判断商品是否已经被收藏
            System.out.println(exist);
            if (exist != null) {
                return Result.success("已收藏");
            }
            collectionMapper.addCollection(tokenUserInfo.getId(), id, t);
            return Result.success("收藏成功");
        } catch (Exception e) {
            return Result.error("收藏失败");
        }
    }
    @RequestMapping("/api/collection/query/single")
    public Result queryCollectionSingle(Integer id){
        LocalDateTime t = LocalDateTime.now();
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        try {
            Collection exist = collectionMapper.isExist(tokenUserInfo.getId(), id);  //判断商品是否已经被收藏
            if (exist != null) {
                //存在返回
                return Result.success("exist");
            }else{
                //不存在返回
                return Result.success("absent");
            }
        }catch (Exception e){
            return Result.error("查询失败");
        }
    }

    //    移除收藏
    @RequestMapping("/api/collection/remove")
    public Result removeCollection(Integer id) {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        try {
            collectionMapper.removeCollection(tokenUserInfo.getId(), id);
            return Result.success("移除收藏成功");
        } catch (Exception e) {
            return Result.error("移除收藏失败");
        }
    }

    //查询用户收藏
    @RequestMapping("/api/collection/query")
    public Result queryCollection(){
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        List<Collection> result_query = collectionMapper.query(tokenUserInfo.getId());
        return Result.success(result_query);
    }


}
