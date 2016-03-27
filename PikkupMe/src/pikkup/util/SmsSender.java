package pikkup.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;

public class SmsSender {

	/* Find your sid and token at twilio.com/user/account */
	public static final String ACCOUNT_SID = "AC42ce451ab8d4f32348cf77daeea1c40f";
	public static final String AUTH_TOKEN = "c1dc5d60ecb815493d44309c63869839";

	public static void SendSms(String phoneno, String message) throws TwilioRestException{
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		String number = "+1" + phoneno;
		
		Account account = client.getAccount();

		MessageFactory messageFactory = account.getMessageFactory();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", number)); // Replace with a valid phone number for your account.
		params.add(new BasicNameValuePair("From", "+14697895691")); // Replace with a valid phone number for your account.
		params.add(new BasicNameValuePair("Body", message));
		Message sms = messageFactory.create(params);
	}
	
}