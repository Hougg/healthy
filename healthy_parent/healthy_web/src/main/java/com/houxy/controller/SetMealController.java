package com.houxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.houxy.constant.MessageConstant;
import com.houxy.constant.RedisConstant;
import com.houxy.entity.PageResult;
import com.houxy.entity.QueryPageBean;
import com.houxy.entity.Result;
import com.houxy.pojo.CheckGroup;
import com.houxy.pojo.Setmeal;
import com.houxy.service.CheckGroupService;
import com.houxy.service.SetMealService;
import com.houxy.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/14
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    CheckGroupService checkGroupService;

    @Reference
    SetMealService setMealService;

    @RequestMapping("/upload")
    public Result upload(@RequestBody MultipartFile imgFile){

        try {
            //uuid
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //获取真实的文件名称
            String originalFilename = imgFile.getOriginalFilename();
            //扩展名
            String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //获取唯一的文件名称
            String fileName = uuid + extendName;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //把上传成功后的图片名称存在redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        if (checkGroupList!=null&&checkGroupList.size()>0){
            Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(checkGroupList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    //添加
    @RequestMapping("/add")
    public Result add(Integer[] checkgroupIds, @RequestBody Setmeal setmeal){
        try {
            setMealService.add(checkgroupIds,setmeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
      PageResult pageResult = setMealService.findByPage(queryPageBean);
      return pageResult;
    }
}
