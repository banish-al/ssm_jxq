package com.zilong.service.salesorderService;

import com.zilong.vo.PageVo;
import com.zilong.vo.salesVo.Salesticket;

import javax.servlet.http.HttpSession;

public interface SalesticketService {
    PageVo<Salesticket> querySalesticket(Salesticket salesticket, int startIndex, int pageSize);

    int addSalesticket(int salesorderId, HttpSession session);

    String queryUserNameBySid(int sid);
    int chuku(int salesorderId);
}
