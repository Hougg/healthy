package com.houxy.controller;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.houxy.constant.MessageConstant;
import com.houxy.entity.PageResult;
import com.houxy.entity.QueryPageBean;
import com.houxy.entity.Result;
import com.houxy.pojo.CheckGroup;
import com.houxy.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;

    /**
     * 新增检查组
     * @param map
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody  Map<String, Object> map){
        System.out.println(map);
        //从map集合中获取jsonArray
        JSONArray jsonArray = (JSONArray) map.get("checkitemIds");
        //把jsonArray反序列化为整数类型的数组
        Integer[] checkitemIds = jsonArray.toArray(new Integer[]{});
        //从map集合中获取json对象
        JSONObject jsonObject = (JSONObject) map.get("checkGroup");
        //把json对象反序列化为检查组
        CheckGroup checkGroup = jsonObject.toJavaObject(CheckGroup.class);

        try {
            System.out.println("controller"+checkGroup+checkitemIds.toString());
            checkGroupService.add(checkGroup,checkitemIds);
            System.out.println(new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS));
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL));
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.queryPage(queryPageBean);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据CheckGroupId查询关联的CheckItemId
     * @param id
     * @return
     */
    @RequestMapping("/findCheckItemIdsById")
    public Result findCheckItemIdsById(Integer id){
        try {
            List<Integer> checkItemIds = checkGroupService.findCheckItemIdsById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(Integer[] checkitemIds, @RequestBody CheckGroup checkGroup){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("/delById")
    public Result delById(Integer id){
        try {
            checkGroupService.delById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
}
