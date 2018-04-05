package com.ociweb.ibm;

import com.ociweb.gl.api.*;
import com.ociweb.pronghorn.network.TLSCertificates;

public class IBMHTTP implements GreenApp
{
    @Override
    public void declareConfiguration(Builder builder)
    {
        System.out.println("----------------declare config-------------");
        builder.useNetClient(); //secure connection (HTTPS). use InsecureNetClient for HTTP
        System.out.println("----------------declare config-------------");
    }
    @Override
    public void declareBehavior(GreenRuntime runtime)
    {
        System.out.println("----------------declare behavior-------------");

        //publish a topic
        HttpClient httpClient=new HttpClient(runtime);
        runtime.addResponseListener(httpClient);

        System.out.println("----------------declare behavior-------------");
    }
          
}
