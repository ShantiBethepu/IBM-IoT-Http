package com.ociweb.ibm;
import com.ociweb.gl.api.*;
import com.ociweb.pronghorn.network.config.HTTPHeaderDefaults;
import com.ociweb.pronghorn.pipe.ChannelReader;
import com.ociweb.pronghorn.pipe.ChannelWriter;
public class HttpClient implements StartupListener, HTTPResponseListener,HeaderWritable
{
    GreenCommandChannel cmd;
    String host="gysmrx.messaging.internetofthings.ibmcloud.com";
    int port=443;//08 8883
    String topic="/api/v0002/device/types/Httpdevices/devices/httpdevice/events/status";
    HTTPSession httpSession=new HTTPSession(host,port);
    Writable message = writer -> writer.append("{\"d\":{\"status\":\"Hello Watson IoT\", \"sender\":\"This is from GreenLightning :) \"}}");
     public HttpClient(GreenRuntime runtime)
    {
        cmd=runtime.newCommandChannel(NET_REQUESTER|DYNAMIC_MESSAGING);
    }
    @Override
    public void startup() {
        cmd.httpPost(httpSession,topic,this::write,message);
       // cmd.httpGet(httpSession,topic,this);
    }
    @Override
    public boolean responseHTTP(HTTPResponseReader reader) {
        System.out.println("Status: "+reader.statusCode());
        //  System.out.println("type: "+reader.contentType());
        Payloadable response=new Payloadable() {
            @Override
            public void read(ChannelReader reader) {
                System.out.println("Received : "+reader.readUTFOfLength(reader.available()));
            }
        };
        reader.openPayloadData(response);
        return true;
    }

    @Override
    public void write(HeaderWriter headerWriter)
    {
         headerWriter.write(HTTPHeaderDefaults.AUTHORIZATION,new BasicAuthorization("use-token-auth","nopassword"));
         headerWriter.write(HTTPHeaderDefaults.CONTENT_TYPE,"application/json");
    }
}