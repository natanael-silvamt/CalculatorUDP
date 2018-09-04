package br.ufc.qxd.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientUDP {
	private static DatagramSocket dsocket;
	private static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);		
		try {
			dsocket = new DatagramSocket();
			InetAddress ip_local = InetAddress.getLocalHost();			
			byte buffer[] = null;
			
			while(true) {
				System.out.println("Digite a equação no seguinte formato: 'operando1 operador operando2'");
				String input = scanner.nextLine();
				buffer = new byte[65535];
				
				buffer = input.getBytes();
				DatagramPacket packet_send = new DatagramPacket(buffer, buffer.length, ip_local, 3000);
				dsocket.send(packet_send);
				
				StringTokenizer token = new StringTokenizer(input.trim());
				int i = 0;
				String aux_input = null;
				while(i <= 2) {
					if(i == 2) {
						aux_input = token.nextToken();
						break;
					}
					token.nextToken();
					i++;
				}
				
				if(input.equals("exit") || Integer.parseInt(aux_input) == 0) {
					System.out.println("Serviço encerrado !!!");
					break;
				}
				
				buffer = new byte[65535];
				DatagramPacket packet_receive = new DatagramPacket(buffer, buffer.length);
				dsocket.receive(packet_receive);
				System.out.println("Resultado: " + new String(buffer, 0, buffer.length));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
