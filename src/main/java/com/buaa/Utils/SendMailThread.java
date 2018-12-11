package com.buaa.Utils;

import com.buaa.domain.Customer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.ProxySelector;
import java.util.Properties;

public class SendMailThread extends Thread{

    private Customer customer;

    public SendMailThread(Customer customer) {
        this.customer = customer;
    }

    public void run(){
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol","smtp");
        props.setProperty("mail.host", "stmp.mxhichina.com");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(props);
        session.setDebug(true);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress("xingtian@tianfang1234.cn"));
            mimeMessage.setRecipients(Message.RecipientType.TO, customer.getEmail());

            mimeMessage.setSubject("来自指令汇书店的注册邮件");
            mimeMessage.setContent("", "text/html;charset=UTF-8");

            mimeMessage.setContent("亲爱的"+customer.getUsername()+":<br/>请猛戳下面激活您的账户<a href='http://localhost:8080/BookStore/servlet/ClientServlet?op=activeCustomer&code="+customer.getCode()+"'>激活</a><br/>", "text/html;charset=UTF-8");
            mimeMessage.saveChanges();

            Transport transport = session.getTransport();
            transport.connect("邮箱账号", "邮箱密码");     //此处需要修改为自己的
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
