/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Forward Thinking Ltd and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Forward Thinking Ltd
 * and its suppliers and may be covered by New Zealand and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Forward Thinking Ltd.
 */
package com.zion.mail.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.common.AppConfig;
import com.zion.mail.SendEmailException;
import com.zion.mail.SesConfiguration;

public class SesServiceImpl implements SesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SesServiceImpl.class);
    
    private final static String MAIL_PROTOCOL = "mail.transport.protocol";
    private final static String MAIL_PROTOCOL_VALUE = "smtp";
    private final static String MAIL_PORT = "mail.smtp.port";
    private final static String MAIL_AUTH = "mail.smtp.auth";
    private final static String MAIL_AUTH_VALUE = "true";
    private final static String MAIL_SSL_ENABLE = "mail.smtp.ssl.enable";
    private final static String MAIL_SSL_ENABLE_VALUE = "true";
    private final static String MAIL_MIME_TYPE = "text/html";

    @Override
    public void sendEmail(String toEmail, String subject, String htmlBody) throws SendEmailException {
        this.sendEmail(null, toEmail, subject, htmlBody);
    }

    @Override
    public void sendEmail(String from, String toEmail, String subject, String htmlBody) throws SendEmailException {
        // TODO Auto-generated method stub
        Transport transport = null;
        try {
            SesConfiguration sesConfig = AppConfig.getInstance().getSesConfiguration();
            // Create a Properties object to contain connection configuration information.
            Properties props = System.getProperties();
            props.put(MAIL_PROTOCOL, MAIL_PROTOCOL_VALUE);
            props.put(MAIL_PORT, sesConfig.getPort());
            // Set properties indicating that we want to use STARTTLS to encrypt the connection.
            // The SMTP session will begin on an unencrypted connection, and then the client
            // will issue a STARTTLS command to upgrade to an encrypted connection.
            props.put(MAIL_AUTH, MAIL_AUTH_VALUE);
            props.put(MAIL_SSL_ENABLE, MAIL_SSL_ENABLE_VALUE);
            // Create a Session object to represent a mail session with the
            // specified properties.
            Session session = Session.getDefaultInstance(props);
            // Create a message with the specified information.
            MimeMessage msg = new MimeMessage(session);
            String form = StringUtils.isNotBlank(from) ? from : sesConfig.getFrom();
            msg.setFrom(new InternetAddress(form));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(subject);

            // HTML version
            final MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, MAIL_MIME_TYPE);
            final Multipart mp = new MimeMultipart("alternative");
            mp.addBodyPart(htmlPart);
            msg.setContent(mp);

            // Create a transport.
            transport = session.getTransport();
            // Connect to Amazon SES using the SMTP username and password you
            // specified above.
            transport.connect(sesConfig.getServer(), sesConfig.getUsername(), sesConfig.getPassword());
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception ex) {
            String msg = String.format("can not send email to %s", toEmail);
            LOGGER.warn(msg);
            throw new SendEmailException(msg);
        } finally {
            // Close and terminate the connection.
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    throw new SendEmailException("fail to close email transport connection");
                }
            }
        }
    }

}

