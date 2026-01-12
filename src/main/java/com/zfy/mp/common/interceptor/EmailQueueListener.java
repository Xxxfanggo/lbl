package com.zfy.mp.common.interceptor;

import com.zfy.mp.common.constants.RabbitConst;
import com.zfy.mp.enums.MailboxAlertsEnum;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Objects;

/**
 *
 * @文件名: EmailQueueListener.java
 * @工程名: mp
 * @包名: com.zfy.mp.common.interceptor
 * @描述: 邮件队列监听器
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-12 17:07
 * @版本号: V2.4.0
 */
@Log4j2
@Component
public class EmailQueueListener {
    @Value("${web.index.path}")
    private String webIndexPath;
    @Value("${spring.mail.username}")
    private String username;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private JavaMailSender mailSender;
    @RabbitListener(queues = RabbitConst.MAIL_QUEUE)
    public void receive(Map<String, Object> data) {
        String email = (String) data.get("email");
        String code = (String) data.get("code");
        String type = (String) data.get("type");
        MimeMessage mimeMessage = null;
        if (MailboxAlertsEnum.REGISTER.getCodeStr().equals(type)) {
            mimeMessage = sendHtmlMail(email, MailboxAlertsEnum.REGISTER.getSubject(), MailboxAlertsEnum.REGISTER.getTemplateName(), Map.of(
                    "expirationTime", "5分钟",
                    "code", code,
                    "toUrl", webIndexPath,
                    "openSourceAddress", "https://gitee.com/kuailemao/ruyu-blog"
            ));
        }

        if (Objects.isNull(mimeMessage)) return;
        // 发送邮件
        mailSender.send(mimeMessage);
        log.info("{}邮件发送成功", email);
    }

    MimeMessage sendHtmlMail(String email, String subject, String templateName, Map<String, Object> model) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject(subject);
            Context context = new Context();
            context.setVariables(model);
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            log.error("发送邮件失败：{}", e.getMessage());
        }
        return helper.getMimeMessage();
    }
}
