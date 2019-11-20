package com.waterelephant.rongtaobao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.List;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/8 10:54
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TaoBaoEntity {

    private TbUser tb_user;
    private List<TbOrder> tb_orders;
    private List<TbDeliverAddre> tb_deliver_addrs;
    private TbZhifubaoBinding tb_zhifubao_binding;

    public TbUser getTb_user() {
        return tb_user;
    }

    public void setTb_user(TbUser tb_user) {
        this.tb_user = tb_user;
    }

    public List<TbOrder> getTb_orders() {
        return tb_orders;
    }

    public void setTb_orders(List<TbOrder> tb_orders) {
        this.tb_orders = tb_orders;
    }

    public List<TbDeliverAddre> getTb_deliver_addrs() {
        return tb_deliver_addrs;
    }

    public void setTb_deliver_addrs(List<TbDeliverAddre> tb_deliver_addrs) {
        this.tb_deliver_addrs = tb_deliver_addrs;
    }

    public TbZhifubaoBinding getTb_zhifubao_binding() {
        return tb_zhifubao_binding;
    }

    public void setTb_zhifubao_binding(TbZhifubaoBinding tb_zhifubao_binding) {
        this.tb_zhifubao_binding = tb_zhifubao_binding;
    }
}
