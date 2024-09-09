package org.example;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Outils {
    ArrayList <IpV4> ip = new ArrayList<>();

    public ArrayList<IpV4> getSystemIp() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isUp() && !networkInterface.isLoopback()
                    && !networkInterface.isVirtual()) {
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress.getHostAddress().contains(".") && !inetAddress.isLoopbackAddress()) {
                        ip.add(new IpV4(networkInterface.getName(), networkInterface.getDisplayName(), inetAddress.getHostAddress()));
                    }
                }
            }
        }
        return ip;
    }
}