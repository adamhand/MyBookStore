package com.buaa.controller;

import com.buaa.Service.BusinessService;
import com.buaa.Service.BusinessServiceImpl;
import com.buaa.Utils.PaymentUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponsePayServlet extends HttpServlet {
    private BusinessService bs = new BusinessServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String hmac = request.getParameter("hmac");

        //数据校验
        boolean ok = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt,
                                                r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
        if(!ok){
            out.write("数据有可能被篡改，请联系网站");
        }else {
            if("1".equals(r1_Code)){
                //支付成功，根据订单号更改订单状态。点卡充值时注意表单的重复提交问题。
                if("2".equals(r9_BType)){
                    out.write("success");
                }

                bs.changeOrderStatus(1, r6_Order);
                request.getSession().removeAttribute("cart");
                response.setHeader("Refresh", "2;URL="+request.getContextPath());
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
