package com.lam.Controller;

import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.CartMapper;
import com.lam.pojo.Cart;
import com.lam.pojo.Result;
import com.lam.pojo.TokenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartMapper cartMapper;

    //    添加购物车
    @PostMapping("/api/cart/add")
    public Result addCart(@RequestBody Cart cart) {
        LocalDateTime t = LocalDateTime.now();//创建一个时间
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        System.out.println(tokenUserInfo);
        cart.setUid(tokenUserInfo.getId());
        System.out.println(cart);
        cart.setJoin_time(t);
        /*
        * 用户把商品id提交过来，然后判断该商品是否存在购物车，如果不存在那么就加入购物车，并使amount为1，
        * 如果存在，那么就要使数量+1
        * */
//        如果商品不存在购物车中则添加（如果存在则添加数量
        try {
//            如果商品存在那么就增加其数量
            Cart exist = cartMapper.isExist(cart.getPd_id(), cart.getUid());
            if(exist.getAmount() > 0){
                int amount = exist.getAmount() + 1;
                cartMapper.increaseAmount(amount,cart.getUid(), cart.getPd_id());
            }else{
                cartMapper.addCart(1,cart.getPd_id(), tokenUserInfo.getId(), t);
            }
            return Result.success("添加购物车成功！");
        } catch (Exception e) {
            return Result.error("添加购物车失败！");
        }

    }

    //查询自己的购物车有哪些商品
    @RequestMapping("/api/cart/query")
    public Result queryCart() {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        System.out.println(tokenUserInfo);
        //从token中取出用户id
        List<Cart> carts = cartMapper.querySelfCart(tokenUserInfo.getId());
        return Result.success(carts);
    }

    //    在购物车中删除某样商品
    @RequestMapping("/api/cart/del")
    public Result deleteCart(Integer id) {
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        try {
            cartMapper.deleteSelfCart(id, tokenUserInfo.getId());
            return Result.success("移除成功");
        } catch (Exception e) {
            return Result.error("移除失败");
        }
    }
//    修改购物车中商品的数量
    @PostMapping("/api/cart/modify")
    public Result modifyAmount(@RequestBody Cart cart){
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        try {
            Cart exist = cartMapper.isExist(cart.getPd_id(), tokenUserInfo.getId());
            if(exist.getAmount() > 0){
                cartMapper.modifyAmount(cart.getAmount(),tokenUserInfo.getId(), cart.getPd_id());
            }else{
//                走到这个分支就证明从当前的uid和pd_id查不出东西
               return Result.error("未知错误");
            }
            return Result.success("修改成功");
        }catch (Exception e){
            return Result.error("修改失败");
        }
    }

}
