package com.example.demo.service.impl;

import com.example.demo.dao.PromoDOMapper;
import com.example.demo.dataobject.PromoDO;
import com.example.demo.service.PromoService;
import com.example.demo.service.model.PromoModel;
import org.apache.logging.log4j.message.ReusableMessage;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀信息
        PromoDO promoDO=promoDOMapper.selectByItemId(itemId);
        //dataobject->model
        PromoModel promoModel=this.convertFromDataObject(promoDO);
        if(promoModel==null){
            return null;
        }
        //判断当前时间是否秒杀活动即将开始或正在进行
        DateTime now=new DateTime();
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    public PromoModel convertFromDataObject(PromoDO promoDO){
        if(promoDO==null){
            return null;
        }
        PromoModel promoModel=new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
