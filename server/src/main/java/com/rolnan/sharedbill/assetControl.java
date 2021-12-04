package com.rolnan.sharedbill;


import com.rolnan.sharedbill.controller.assetsRepository;
import com.rolnan.sharedbill.domain.assetsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@Controller // This means that this class is a Controller
@RequestMapping(path="/api/assets") // This means URL's start with /demo (after Application path)
public class assetControl {
    @Autowired
    private assetsRepository assetsRepository;

    private static final Logger logger = LoggerFactory.getLogger(assetControl.class);

    @PostMapping(path="/create")
    public @ResponseBody
    returnMsg createasset(@RequestParam String name
            , @RequestParam Double amount, HttpServletRequest request) {

        assetsInfo n = new assetsInfo();
        n.setName(name);
        n.setNumber(amount);
        n.setUserid((Integer)(request.getSession().getAttribute("UserId")));
        assetsRepository.save(n);

        logger.info("create asstes {} {} success",n.getId(),name);
        return new returnMsg(200,"asset Saved", null);
    }

    @PostMapping(path="/modify")
    public @ResponseBody
    returnMsg modifyasset(@RequestParam Integer id
            , @RequestParam(required = false) String name
            , @RequestParam(required = false) Double account) {

        List<assetsInfo> res = assetsRepository.findByAssetsId(id);
        if(res==null){
            logger.error("没有该信息");
            return new returnMsg(320,"No object", null);
        }
        assetsInfo n = assetsRepository.findByAssetsId(id).get(0);
        if(name!="" && name!=null){
            n.setName(name);
        }
        if(account!=null){
            n.setNumber(account);
        }
        assetsRepository.save(n);

        return new returnMsg(200,"asset Modified", account);
    }

    @GetMapping(path="/getAssets")
    public @ResponseBody
    returnMsg getAssets(HttpServletRequest request) {
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("assets", assetsRepository.
                getAllAssets((Integer)(request.getSession().getAttribute("UserId"))));

        return new returnMsg(200,"Get User Assets", ret);
    }

    @GetMapping(path="/getAssetsTest")
    public @ResponseBody
    returnMsg getAssetsTest() {
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("assets", assetsRepository.
                getAllAssets(1));

        return new returnMsg(200,"Get User Assets", ret);
    }

    @GetMapping(path="/getAssetsInfo")
    public @ResponseBody
    returnMsg getAssetsInfo(@RequestParam Integer id) {
        assetsInfo res = assetsRepository.findByAssetsId(id).get(0);
        return new returnMsg(200,"Get asset info", res);
    }

    @GetMapping(path="/getRecords")
    public @ResponseBody
    returnMsg getAssetsRecords(@RequestParam Integer id) {
        logger.info("visit /api/assets/getRecords");
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("billDetail", assetsRepository.getRecords(id));
        return new returnMsg(200,"Get asset info", ret);
    }


    @GetMapping(path="/all")
    public @ResponseBody
    returnMsg getAllasset() {
        return new returnMsg(200,"ALL Asstes", assetsRepository.findAll());
    }


}
