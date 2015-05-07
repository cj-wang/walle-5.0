package cn.walle.framework.core.support;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MulticastService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private String groupAddress;
	private int port;
	private int messageBufferSize;
	
	private InetAddress group;
	
	private MulticastSocket senderSocket;
	private MulticastSocket receiverSocket;
	
	private DatagramPacket senderDatagram;
	private DatagramPacket receiverDatagram;
	
	@PostConstruct
	public void init() {
		try {
			group = InetAddress.getByName(groupAddress);
			senderSocket = new MulticastSocket(port);
			receiverSocket = new MulticastSocket(port);
			receiverSocket.joinGroup(group);
			senderDatagram = new DatagramPacket(new byte[messageBufferSize], messageBufferSize, group, port);
			receiverDatagram = new DatagramPacket(new byte[messageBufferSize], messageBufferSize, group, port);
			
			new Thread() {
				public void run() {
					while (true) {
						try {
							receiverSocket.receive(receiverDatagram);
							String message = new String(receiverDatagram.getData(), receiverDatagram.getOffset(), receiverDatagram.getLength());
							messageReceived(message);
						} catch (Exception ex) {
							log.error("Multicast receiver error", ex);
						}
					}
				}
			}.start();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void sendMessage(String message) {
		log.info("Send multicast message: " + message);
		byte[] messageBytes = message.getBytes();
		if (messageBytes.length > messageBufferSize) {
			throw new RuntimeException("messageBufferSize should be set larger");
		}
		try {
			senderDatagram.setData(messageBytes);
			senderSocket.send(senderDatagram);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	protected void messageReceived(String message) {
		log.info("Received multicast message: " + message);
	}
	

	public void setGroupAddress(String groupAddress) {
		this.groupAddress = groupAddress;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setMessageBufferSize(int messageBufferSize) {
		this.messageBufferSize = messageBufferSize;
	}

}
