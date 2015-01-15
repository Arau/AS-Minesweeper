package service;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import service.MailRelayStub.SendMail;
import service.MailRelayStub.SendMailResponse;


public class SendMailClient {
	
	public void sendMail(String address, String msg) {
		try {
			MailRelayStub stub = new MailRelayStub();
			SendMail sm = new SendMail();
			sm.setMail(address);
			sm.setMsg(msg);
			SendMailResponse res = stub.sendMail(sm);
			System.out.println(res.get_return());
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
