package com.framework.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

public class EWSEmailService {

	/**
	 * Below method will Exchange Web Services(EWS)
	 * 
	 */
	public static void sendeMail() {

		String fileName = null;
		String attachNewFileName = null;
		ExchangeService service;
		Base64.Decoder decoder;

		List<String> to_EmailAddress = new ArrayList<String>();
		List<String> cc_EmailAddress = new ArrayList<String>();
		List<String> bcc_EmailAddress = new ArrayList<String>();

		String mailBody = "<p>Hello Team,</p>" + "<p>This email is from the NAUTAutomation Framework.</p>" + "<br>"
				+ "<br>" + "<i><b>Regards,</i></b><br>" + "<b>NAUTAutomation Team</b><br>"
				+ "<p style=\"color:red;\"><i><b>Note:</b> This is auto generated email do not reply.</i></p>";

		decoder = Base64.getDecoder();

		service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		try {
			service.setUrl(new URI(GlobalVariables.configProp.getProperty("URL")));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExchangeCredentials credentials = new WebCredentials(GlobalVariables.configProp.getProperty("from_MailID"),
				new String(decoder.decode(GlobalVariables.configProp.getProperty("mailPassword"))));
		service.setCredentials(credentials);
		try {
			EmailMessage msg = new EmailMessage(service);
			msg.setSubject("Test Automation Mail");
			msg.setBody(new MessageBody(mailBody));

			// Attaching file on mail

			File attachFile = new File(System.getProperty("user.dir") + GlobalVariables.configProp.getProperty("reportPath"));
			FileAttachment fileAttachment = msg.getAttachments().addFileAttachment(attachFile.toString());
			fileName = fileAttachment.getName();
			attachNewFileName = fileName.replaceAll("[/<>+^:,-]", "_");
			fileAttachment.setName(attachNewFileName);

			if (GlobalVariables.configProp.getProperty("to_MailID").length() > 1
					&& !(GlobalVariables.configProp.getProperty("to_MailID").isEmpty()) == true) {
				String[] toemails = GlobalVariables.configProp.getProperty("to_MailID").split(",");
				for (String tomail : toemails) {
					if (tomail != null) {
						// Add element to the list
						to_EmailAddress.add(tomail.trim());
					}
					msg.getToRecipients().add(tomail);
				}

				if (GlobalVariables.configProp.getProperty("cc_MailID").length() > 1) {
					String[] ccemails = GlobalVariables.configProp.getProperty("cc_MailID").split(",");
					for (String ccmail : ccemails) {
						if (ccmail != null) {
							// Add element to the list
							cc_EmailAddress.add(ccmail.trim());
						}
						msg.getCcRecipients().add(ccmail);
					}
				}

				if (GlobalVariables.configProp.getProperty("bcc_MailID").length() > 1) {
					String[] bccemails = GlobalVariables.configProp.getProperty("bcc_MailID").split(",");
					for (String bccmail : bccemails) {
						if (bccmail != null) {
							// Add element to the list
							bcc_EmailAddress.add(bccmail.trim());
						}
						msg.getBccRecipients().add(bccmail);
					}
				}

				// Sending emails
				   msg.send();
				   
			} else {
				System.out.println("Please provide atleast one email recipient");
			}

			//msg.sendAndSaveCopy();
			System.out.println("Email Sent Successfully..!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}