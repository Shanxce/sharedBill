package com.rolnan.sharedbill;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rolnan.sharedbill.domain.*;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller // This means that this class is a Controller
@RequestMapping(path = "/api/bill") // This means URL's start with /demo (after Application path)
public class billControl {
    @Autowired
    private com.rolnan.sharedbill.controller.billRepository billRepository;
    @Autowired
    private com.rolnan.sharedbill.controller.billUserRepository billUserRepository;
    @Autowired
    private com.rolnan.sharedbill.controller.userRepository userRepository;
    @Autowired
    private com.rolnan.sharedbill.controller.billDetailRepository billDetailRepository;
    @Autowired
    private com.rolnan.sharedbill.controller.assetsRepository assetsRepository;
    @Autowired
    private com.rolnan.sharedbill.controller.recordUserRepository recordUserRepository;


    private static final Logger logger = LoggerFactory.getLogger(billControl.class);

    private HashMap<String, Pair<Integer, LocalDateTime>> InviteCode = new HashMap<>();
    private HashMap<Integer, String> getCodeByBillId = new HashMap<>();


    @PostMapping(path = "/create")
    public @ResponseBody
    MappingJacksonValue createBill(@RequestParam String name
            , @RequestParam Boolean share, HttpServletRequest request) {

        billInfo n = new billInfo();
        n.setName(name);
        n.setIsshare(share);
        billRepository.save(n);

        logger.info("user {} {} create success", n.getId(), name);

        Integer userid = (Integer) (request.getSession().getAttribute("UserId"));
        String username = request.getSession().getAttribute("UserName").toString();

        billUser m = new billUser();
        m.setBillId(n.getId());
        m.setUserid(userid);
        m.setNickname(username);
        m.setLeaveime(null);
        billUserRepository.save(m);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.filterOutAllExcept("id");
        FilterProvider filterProvider = new SimpleFilterProvider().
                addFilter("billFilter", simpleBeanPropertyFilter);
        returnMsg res = new returnMsg(200, "Saved", n);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(res);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping(path = "/getUsers")
    public @ResponseBody
    returnMsg getUsers(@RequestParam Integer id) {
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("users", billRepository.findBillUser(id));
        return new returnMsg(200, "getUsers", billRepository.findBillUser(id));
    }

    @GetMapping(path = "/getInviteCode")
    public @ResponseBody
    returnMsg getInviteCode(@RequestParam Integer billId, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("UserId");
        if (userId == null)
            return new returnMsg(300, "用户未登录", null);
        if (billUserRepository.findByBillIdAndUserId(billId, userId).isEmpty())
            return new returnMsg(301, "权限不足", null);
        String code = getCodeByBillId.get(billId);
        if (code == null || ChronoUnit.MINUTES.between(LocalDateTime.now(), InviteCode.get(code).getSecond()) >= 10) {
            while (true) {
                code = RandomString.make(10);
                Pair<Integer, LocalDateTime> d = InviteCode.get(code);
                if (d == null || ChronoUnit.MINUTES.between(LocalDateTime.now(), d.getSecond()) >= 10) {
                    InviteCode.put(code, Pair.of(billId, LocalDateTime.now()));
                    getCodeByBillId.put(billId, code);
                    break;
                }
            }
        }
        String finalCode = code;
        return new returnMsg(200, "获取邀请码成功", new HashMap<>() {
            {
                put("inviteCode", finalCode);
            }
        });
    }

    @PostMapping(path = "/join")
    public @ResponseBody
    returnMsg userJoin(@RequestParam String code, HttpServletRequest request) {

        Integer userId = (Integer) request.getSession().getAttribute("UserId");
        if (userId == null)
            return new returnMsg(300, "用户未登录", null);
        Pair<Integer, LocalDateTime> d = InviteCode.get(code);
        if (d == null || ChronoUnit.MINUTES.between(LocalDateTime.now(), d.getSecond()) >= 10)
            return new returnMsg(320, "非法邀请码", null);

        Integer billId = d.getFirst();
        String username = (String) request.getSession().getAttribute("UserName");

        List<billUser> data = billUserRepository.findByBillIdAndUserId(billId, userId);
        if (!data.isEmpty()) {
            return new returnMsg(400, "用户已在账本", null);
        }
        billUser m = new billUser();
        m.setBillId(billId);
        m.setUserid(userId);
        m.setNickname(username);
        m.setLeaveime(null);
        billUserRepository.save(m);

        logger.info("user {} {} join bill {} success", userId, username, billId);

        return new returnMsg(200, "user join success", null);
    }

    @PostMapping(path="/createRecord")
    public @ResponseBody returnMsg createRecord(@RequestParam Integer billId,
                                                @RequestParam Integer assetsId,
                                                @RequestParam Double count,
                                                @RequestParam String describe,
                                                @RequestParam String photoAddr,
                                                @RequestParam String joiner,
                                                HttpServletRequest request) {
        Integer userid=(Integer)(request.getSession().getAttribute("UserId"));

        billDetail m = new billDetail();
        m.setBillId(billId);
        m.setUserid(userid);
        m.setAssetsid(assetsId);
        m.setCount(count);
        m.setPhotoaddr(photoAddr);
        m.setBillDescribe(describe);
        billDetailRepository.save(m);

        assetsInfo ai = assetsRepository.findByAssetsId(assetsId).get(0);
        ai.setNumber(ai.getNumber()-count);
        assetsRepository.save(ai);


        for(Integer joinnow : Arrays.stream(joiner.split(" ")).mapToInt(Integer::parseInt).toArray()){
            recordUser n = new recordUser();
            n.setUserid(joinnow);
            n.setBilldetailId(m.getBilldetailId());
            recordUserRepository.save(n);
        }

        return new returnMsg(200,"getRecords",null);
    }

    @GetMapping(path = "/getJoiners")
    public @ResponseBody
    returnMsg getJoiners(@RequestParam Integer recordId
            , HttpServletRequest request) {
        HashMap<String, Object> ret = new HashMap<>();
        ret.put("Users", recordUserRepository.useRecordId(recordId));
        return new returnMsg(200, "getRecords", ret);
    }

    @GetMapping(path = "/getRecords")
    public @ResponseBody
    returnMsg getRecords(@RequestParam Integer id
            , HttpServletRequest request) {
        return new returnMsg(200, "getRecords", new HashMap<>(){{
            put("records", billDetailRepository.findByBillId(id));
        }});
    }

    @GetMapping(path = "/getRecordsInfo")
    public @ResponseBody
    returnMsg getRecordsInfo(@RequestParam Integer id) {
        return new returnMsg(200, "getUsers", billDetailRepository.findRecordsInfo(id).get(0));
    }

    @PostMapping(path="/calculate")
    public @ResponseBody returnMsg calculate(@RequestParam Integer id) {
        List<Integer> userlist = billRepository.findBillUserId(id);
        HashMap<Integer, Double> moneyNeed = new HashMap<>();

        for(Integer idnow : userlist){
            moneyNeed.put(idnow,0.0);
        }

        for(Integer idnow : userlist){
            List<billDetail> data = billDetailRepository.findByUserIdAndBillCleanAndBillId(idnow,false,id);
            for(billDetail bdnow : data){
                List<recordUser>joiner = recordUserRepository.findByBillDetailId(bdnow.getBilldetailId());
                Integer joinernum = joiner.size();
                for(recordUser runow : joiner){
                    Integer usernow = runow.getUserid();
                    moneyNeed.put(usernow,moneyNeed.get(usernow)+bdnow.getCount()/joinernum);
                }
                //moneyTot+=bdnow.getCount()/userNum;
            }
        }
        for(Integer idnow : userlist) {
            List<assetsInfo> trashlist = assetsRepository. findByAssetsUserIdAndAssetsTrash(idnow,true);
            assetsInfo trash = trashlist.get(0);
            Integer trashnum = trash.getId();

            List<billDetail> data = billDetailRepository.findByUserIdAndBillCleanAndBillId(idnow,false,id);
            Double moneyHave=0.0;
            for(billDetail bdnow : data){
                moneyHave+=bdnow.getCount();
                bdnow.setClean(true);
                billDetailRepository.save(bdnow);
            }

            billDetail m = new billDetail();
            m.setBillId(id);
            m.setUserid(idnow);
            m.setAssetsid(trashnum);
            m.setCount(moneyHave-moneyNeed.get(idnow));
            m.setClean(true);
            m.setPhotoaddr(null);
            m.setBillDescribe("calculate result");
            billDetailRepository.save(m);

            recordUser ru = new recordUser();
            ru.setBilldetailId(m.getBilldetailId());
            ru.setUserid(idnow);
            recordUserRepository.save(ru);

            Double trashCount=trash.getNumber();
            trash.setNumber(trashCount+moneyHave-moneyNeed.get(idnow));
            assetsRepository.save(trash);
        }

        return new returnMsg(200,"success",userlist);
    }

    @PostMapping(path="/recordCleanModify")
    public @ResponseBody returnMsg recordCleanModify(@RequestParam Integer recordId,
                                                     @RequestParam Integer assetsId ) {
        billDetail record = billDetailRepository.findByBillDetailId(recordId).get(0);
        Integer preAssetsId = record.getAssetsid();

        assetsInfo preAsset = assetsRepository.findByAssetsId(preAssetsId).get(0);
        preAsset.setNumber(preAsset.getNumber()+record.getCount());
        assetsRepository.save(preAsset);

        record.setAssetsid(assetsId);
        billDetailRepository.save(record);

        assetsInfo newAsset = assetsRepository.findByAssetsId(assetsId).get(0);
        newAsset.setNumber(newAsset.getNumber()-record.getCount());
        assetsRepository.save(newAsset);

        return new returnMsg(200,"success",null);
    }

    @PostMapping(path="/recordNocleanModify")
    public @ResponseBody returnMsg recordNocleanModify(@RequestParam Integer recordId,
                                                        @RequestParam(required = false) Integer assetsId,
                                                       @RequestParam(required = false) String describe,
                                                       @RequestParam(required = false) Double count) {
        billDetail record = billDetailRepository.findByBillDetailId(recordId).get(0);
        Integer preAssetsId = record.getAssetsid();

        if(assetsId!=null && assetsId!=0){
            assetsInfo preAsset = assetsRepository.findByAssetsId(preAssetsId).get(0);
            preAsset.setNumber(preAsset.getNumber()+record.getCount());
            assetsRepository.save(preAsset);

            record.setAssetsid(assetsId);
        }

        if(describe!=null && describe!=""){
            record.setBillDescribe(describe);
        }

        if(count!=null && count!=0){
            record.setCount(count);
        }

        billDetailRepository.save(record);

        if(assetsId!=null && assetsId!=0){
            assetsInfo newAsset = assetsRepository.findByAssetsId(assetsId).get(0);
            newAsset.setNumber(newAsset.getNumber()-record.getCount());
            assetsRepository.save(newAsset);
        }

        return new returnMsg(200,"success",null);
    }


    @GetMapping(path = "/billAll")
    public @ResponseBody
    MappingJacksonValue getAllBills() {
        // This returns a JSON or XML with the users
        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filterProvider = new SimpleFilterProvider().
                addFilter("billFilter", simpleBeanPropertyFilter);
        returnMsg res = new returnMsg(200, "ha", billRepository.findAll());
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(res);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping(path = "/billUserAll")
    public @ResponseBody
    returnMsg getAllBillUser() {
        // This returns a JSON or XML with the users
        return new returnMsg(200, "ha", billUserRepository.findAll());
    }

}
