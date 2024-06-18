package com.lam.Controller;

import com.lam.Service.ProductService;
import com.lam.Utils.CheckPower;
import com.lam.Utils.UserTheadLocal;
import com.lam.mapper.HandleMapper;
import com.lam.mapper.ProductMapper;
import com.lam.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ProductController {
    //    自动连线的
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private HandleMapper handleMapper;

    @RequestMapping("/api/product")
    public Result listProduct() {//查询所有商品
        List<Product> products = productMapper.allProduct();
        return new Result("1", "success", products);
    }

    //查询商品的总个数
    @RequestMapping("/api/product/count")
    public Result productCount() { //产品的总数是多少
        int count = productMapper.count();
        return new Result("1", "success", count);
    }

    //分页查询商品
    @RequestMapping("/api/product/page/{start}")
    public Result productDividePage(@PathVariable Integer start) {
        List<Product> products = productMapper.dividePage(start);
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        System.out.println("获取到了数据");
        System.out.println(tokenUserInfo);
        return new Result("1", "success", products);
    }

    //update product information
    @PostMapping("/api/product/update")
    public Result productUpdate(@RequestBody Product product) {
        System.out.println(product);
        LocalDateTime t = LocalDateTime.now();
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
//        判断该账号是否归属管理员
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {
            return Result.error("该账号没有权限");
        }
        //determine what information the admin had updated
        String updateInfo = "";
        if (product.getPrice() != null) {
            updateInfo += "价格;";
        }
        if (product.getState() != null) {
            updateInfo += "状态;";
        }
        if (product.getP_name() != null) {
            updateInfo += "名称;";
        }
        if (product.getP_describe() != null) {
            updateInfo += "描述;";
        }
        if (product.getPd_type() != null) {
            updateInfo += "类型;";
        }
        System.out.println(updateInfo);
        int num = productMapper.updateInformation(product);
        //这里将更新动作的信息写入日志
        handleMapper.insertInfo(tokenUserInfo.getId(), product.getPd_id(), updateInfo, t);
        System.out.println(num);
        return new Result("1", "success", num);
    }

    //add product
    @RequestMapping("/api/product/add")
    public Result productAdd(@RequestBody Product product) {
        //System.out.println("是否能接收file");
        //System.out.println(file);
        TokenUserInfo tokenUserInfo = UserTheadLocal.get();
        //        判断该账号是否归属管理员
        if (!CheckPower.check(tokenUserInfo.getAuthorization())) {
            return Result.error("该账号没有权限");
        }
        product.setTime(LocalDateTime.now());
        System.out.println(product);
        int determiner = productService.determiner(product);
        if (determiner == 0) {
            return new Result("0", "fail", "信息不完整，无法录入！");
        }
        return new Result("1", "success", "录入成功！");
    }

    //查询产品价格
    @RequestMapping("/api/product/price")
    public Result queryPrice(Integer id) {
        try {
            String price = productMapper.productQuery(id);
            return Result.success(price);
        } catch (Exception e) {
            return Result.error("价格查询失败！");
        }

    }

    @GetMapping("/api/search")
    public Result searchProduct(String name) {
        System.out.println("用户在搜索" + name);
        return Result.success("test interface ,please don't use in the productive environment");
    }

    //上传详情页的图片数据
    @PostMapping("/api/uploads/detail")
    public Result uploadPictures(@RequestBody Pictures pictures) {
        try {
            productService.detailProductInsert(pictures.getPictures(), pictures.getPd_id());
            return Result.success("详情图片写入成功");
        } catch (Exception e) {
            return Result.error("详情图片写入失败");
        }
    }

    //获取详情页的图片数据
    @RequestMapping("/api/picture/detail")
    public Result requestPictureDetail(Integer pd_id) {
        try {
            List<PictureDetail> pictureDetails = productMapper.queryPicture(pd_id);
            return Result.success(pictureDetails);
        } catch (Exception e) {
            return Result.error("no relativity data");
        }

    }


    //删除详情页图片的接口 get请求
    @RequestMapping("/api/delete/picture")
    public Result deletePicture(Integer pt_id) {
        try {
            productMapper.deletePictureInfo(pt_id);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("删除失败");
        }
    }
    //该接口新增，接口文档中不存在
    @RequestMapping("/api/product/info")
    public Result queryProduct(Integer pdId) {
        try {
            Product product = productMapper.queryProductInfo(pdId);
            return Result.success(product);
        } catch (Exception e) {
            return Result.error("查询商品信息错误，请确保商品的ID正确");
        }
    }

}
