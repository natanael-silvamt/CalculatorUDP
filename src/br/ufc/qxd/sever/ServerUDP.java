package br.ufc.qxd.sever;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class ServerUDP {

	private static DatagramSocket dsocket;

	public static void main(String[] args) {
		
		try {
			dsocket = new DatagramSocket(3000);
			byte[] buffer = null;
			DatagramPacket packet_receive = null;
			DatagramPacket packet_send = null;
			
			while(true) {
				buffer = new byte[65535];
				packet_receive = new DatagramPacket(buffer, buffer.length);
				
				dsocket.receive(packet_receive);
				String string_receive = new String(buffer, 0, buffer.length);
				string_receive = string_receive.trim();
				System.out.println("Equação recebida: " + string_receive);
				
				if(string_receive.equals("exit")) {
					System.out.println("Servidor encerrado !!!");
					break;
				}
				
				int result = 0;
				
				StringTokenizer tokens = new StringTokenizer(string_receive);
				int first_operand = Integer.parseInt(tokens.nextToken());
				String operation = tokens.nextToken();
				int second_operand = Integer.parseInt(tokens.nextToken());
				
				if(second_operand != 0) {
					if(operation.equals("+"))
						result = first_operand + second_operand;
					else if(operation.equals("-"))
						result = first_operand - second_operand;
					else if(operation.equals("*"))
						result = first_operand * second_operand;
					else if(operation.equals("/"))
						result = first_operand / second_operand;
					else
						result = 0;
				}else {
					System.out.println("Error !!! " + "Servidor não realiza divisão por 0(zero).");
					break;
				}				
				
				System.out.println("Calculando resultado...");
				String res = Integer.toString(result);
				buffer = res.getBytes();
				int port = packet_receive.getPort();
				packet_send = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), port);
				dsocket.send(packet_send);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
