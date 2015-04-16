package networkManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


/**
 * UDP commands to and from the NI CRio chassis. 
 * @author Doug Gillespie
 *
 */
public class NIUDPInterface {
	
	private NetworkManager networkManager;
	
	private static final int RXBUFFLEN = 255;
	
	//private String niAddress = "localhost";

	private int niUDPPort = 8000;

	public NIUDPInterface(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	public String sendCommand(String command) {
		return sendCommand(command, networkManager.getCurrentIPaddress(), niUDPPort);
	}
	
	/**
	 * Send command to the cRio. 
	 * @param command - command to send
	 * @param ipAddress - IP address. 
	 * @param port - UDP port number
	 * @return the address. 
	 */
	public synchronized String sendCommand(String command, String ipAddress, int port) {
		System.out.println(" NIUDPInterface:  Sending command: "+command);
		InetAddress inAddress;
		try {
			inAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			System.out.println("Unknown host address " + ipAddress);
			return null;
		}
		DatagramPacket dp = new DatagramPacket(command.getBytes(), command.length(), inAddress, port);
		try {
			DatagramSocket socket = new DatagramSocket();
			
			
			//try to clear buffer
			try{
				socket.setSoTimeout(5);
				byte[] rxBuffClear = new byte[RXBUFFLEN];
				DatagramPacket clear = new DatagramPacket(rxBuffClear, RXBUFFLEN);			
				while (true){
					socket.receive(clear);
					if (clear.getLength()==0) break; 
				}
			}
			catch(Exception e){
				
			}
			
			//send command 
			socket.send(dp);
			byte[] rxBuff = new byte[RXBUFFLEN];
			DatagramPacket rp = new DatagramPacket(rxBuff, RXBUFFLEN);			
			socket.setSoTimeout(2000);
			socket.receive(rp);
			socket.close();
			return new String(rxBuff, 0, rp.getLength());
		} 
		catch (SocketTimeoutException e) {
			System.out.println("Socket timeout exception for command " + command);
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
	
	