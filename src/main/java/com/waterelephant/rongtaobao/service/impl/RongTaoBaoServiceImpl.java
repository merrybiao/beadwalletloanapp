package com.waterelephant.rongtaobao.service.impl;

import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.rongtaobao.entity.TaoBaoEntity;
import com.waterelephant.rongtaobao.entity.TaoBaoReturn;
import com.waterelephant.rongtaobao.entity.TbDeliverAddre;
import com.waterelephant.rongtaobao.entity.TbOrder;
import com.waterelephant.rongtaobao.entity.TbUser;
import com.waterelephant.rongtaobao.entity.TbZhifubaoBinding;
import com.waterelephant.rongtaobao.service.RongTaoBaoService;
import com.waterelephant.rongtaobao.service.TbDeliverAddreService;
import com.waterelephant.rongtaobao.service.TbOrderService;
import com.waterelephant.rongtaobao.service.TbUserService;
import com.waterelephant.rongtaobao.service.TbZhifubaoBindingService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.OrderAndBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/8 9:47
 */
@Service
public class RongTaoBaoServiceImpl implements RongTaoBaoService {

    @Autowired
    private TbDeliverAddreService tbDeliverAddreService;
    @Autowired
    private TbOrderService tbOrderService;
    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private TbZhifubaoBindingService tbZhifubaoBindingService;
    @Autowired
    private BwOrderAuthService bwOrderAuthService;

    @Override
    public void saveTaoBaoData(TaoBaoReturn taoBaoReturn, Long borrowerId, Long orderId, String authChannel) throws Exception {
        BwOrderAuth bwOrderAuthF = bwOrderAuthService.findBwOrderAuth(orderId,8);
        if(bwOrderAuthF == null) {
            BwOrderAuth bwOrderAuth = new BwOrderAuth();
            bwOrderAuth.setOrderId(orderId);
            bwOrderAuth.setAuth_channel(Integer.parseInt(authChannel));
            bwOrderAuth.setAuth_type(8);
            bwOrderAuth.setCreateTime(new Date());
            bwOrderAuth.setUpdateTime(new Date());
            bwOrderAuthService.saveBwOrderAuth(bwOrderAuth);
        }
        List<TaoBaoEntity> taoBaoEntity = taoBaoReturn.getData_list();
        if (taoBaoEntity != null && taoBaoEntity.size() > 0) {
            for (TaoBaoEntity baoEntity : taoBaoEntity) {
                TbUser tbUserDel = new TbUser();
                tbUserDel.setBorrowerId(borrowerId);
                tbUserDel.setOrderId(orderId);
                tbUserService.deleteTbUser(tbUserDel);
                TbUser tbUser = baoEntity.getTb_user();
                tbUser.setBorrowerId(borrowerId);
                tbUser.setOrderId(orderId);
                tbUser.setCreateDate(new Date());
                tbUserService.saveTbUser(tbUser);
                TbZhifubaoBinding tbZhifubaoBindingDel = new TbZhifubaoBinding();
                tbZhifubaoBindingDel.setBorrowerId(borrowerId);
                tbZhifubaoBindingDel.setOrderId(orderId);
                tbZhifubaoBindingService.deleteTbZhifubaoBinding(tbZhifubaoBindingDel);
                TbZhifubaoBinding zhifubaoBinding = baoEntity.getTb_zhifubao_binding();
                zhifubaoBinding.setBorrowerId(borrowerId);
                zhifubaoBinding.setOrderId(orderId);
                zhifubaoBinding.setCreateDate(new Date());
                tbZhifubaoBindingService.saveTbZhifubaoBinding(zhifubaoBinding);
                List<TbOrder> tbOrderList = baoEntity.getTb_orders();
                if (tbOrderList != null && tbOrderList.size() > 0) {
                    TbOrder tbOrderDel = new TbOrder();
                    tbOrderDel.setBorrowerId(borrowerId);
                    tbOrderDel.setBwOrderId(orderId);
                    tbOrderService.deleteTbOrder(tbOrderDel);
                    for (TbOrder tbOrder : tbOrderList) {
                        tbOrder.setBorrowerId(borrowerId);
                        tbOrder.setBwOrderId(orderId);
                        tbOrder.setCreateDate(new Date());
                        // 截取products的长度为180
                        String products = tbOrder.getProducts();
                        if (products.length() >= 181) {
                            products = products.substring(0, 180);
                        }
                        tbOrder.setProducts(products);
                        tbOrderService.saveTbOrder(tbOrder);
                    }
                }
                List<TbDeliverAddre> tbDeliverAddreList = baoEntity.getTb_deliver_addrs();
                if (tbDeliverAddreList != null && tbDeliverAddreList.size() > 0) {
                    TbDeliverAddre tbDeliverAddreDel = new TbDeliverAddre();
                    tbDeliverAddreDel.setBorrowerId(borrowerId);
                    tbDeliverAddreDel.setOrderId(orderId);
                    tbDeliverAddreService.deleteTbDeliverAddre(tbDeliverAddreDel);
                    for (TbDeliverAddre tbDeliverAddre : tbDeliverAddreList) {
                        tbDeliverAddre.setBorrowerId(borrowerId);
                        tbDeliverAddre.setOrderId(orderId);
                        tbDeliverAddre.setCreateDate(new Date());
                        tbDeliverAddreService.saveTbDeliverAddre(tbDeliverAddre);
                    }
                }
            }
        }
    }

    public void saveOrderAuther(String orderId) {
    }

    ;
}
