package com.lam.Service;

import com.lam.mapper.ProductMapper;
import com.lam.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    //private int relevance[] = new int[10];
    private int index = 0;

    public int determiner(Product product) {
        if (product.getPrice() == null || product.getState() == null || product.getP_describe() == null || product.getPd_type() == null || product.getPicture_name() == null) {
            System.out.println("文本不完整，结束。");
            return 0;
        } else {
            System.out.println("executed finished");
            return productMapper.addProduct(product);
        }

    }

    /*
     * 详情页面的图片逻辑
     * 当点击删除按钮的时候发送一个删除请求把选中的数据（id）发往服务器，再又服务器执行
     * 添加图片则是先把图片全部上传到服务器，再由服务器把所有的图片数据返回（而不是在本地比较返回）
     * */
    public void detailProductInsert(String[] paths, Integer pd_id) {
        for (String path : paths) {
            try {
                //往数据库中插入图片
                productMapper.detailPicture(path, pd_id);
            } catch (Exception e) {
                //异常就结束插入
                return;
            }
        }
        //完成插入返回true
    }

    public boolean check(int low, String str, String keyword) {
        if (str.length() - low < keyword.length()) {
          //  System.out.println("running ??");
            return false;
        }
        for (int i = 0, j = low; i < keyword.length(); i++, j++) {
            if (str.toUpperCase().charAt(j) != keyword.toUpperCase().charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public List<Product> match(List<Product> content, String keyword) {
        int[] relevance = new int[content.size()];
        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).getP_describe().length(); j++) {
                if (check(j, content.get(i).getP_describe(), keyword)) {
                    relevance[i] = content.get(i).getP_describe().length() * (j + 1);
//                    System.out.println("content = "+content.get(i).getP_describe() +"j = " + j);
//                    System.out.println("relevance[" + i + "] = "+relevance[i]);
                    break;
                }
            }
        }
        return sort_list(content, relevance);
    }

    public List<Product> sort_list(List<Product> content, int[] relevance) {
        for (int i = 0; i < relevance.length; i++) {
            for (int j = i + 1; j < relevance.length; j++) {
                if (relevance[i] > relevance[j]) {
                    int temp = relevance[j];
                    Product temp_p = content.get(j);
                    relevance[j] = relevance[i];
                    content.set(j, content.get(i));
                    relevance[i] = temp;
                    content.set(i, temp_p);
                }
            }
        }
//        System.out.println(Arrays.toString(relevance));
        return content;
    }
}
