package com.alipay.hulu.common.utils;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public class HttpDns implements Dns {
    private static final Dns SYSTEM = Dns.SYSTEM;
    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        Log.e("HttpDns", "lookup:" + hostname);

        if(hostname.equals("raw.githubusercontent.com")){
            String ip = "151.101.0.133";
            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
            Log.e("HttpDns", "inetAddresses:" + inetAddresses);
            return inetAddresses;
        }
        return SYSTEM.lookup(hostname);
    }
}
