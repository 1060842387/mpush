package com.shinemo.mpush.core.session;

import com.shinemo.mpush.api.connection.SessionContext;
import com.shinemo.mpush.common.security.AesCipher;
import com.shinemo.mpush.common.security.CipherBox;
import com.shinemo.mpush.common.security.RsaCipher;

/**
 * Created by ohun on 2015/12/25.
 */
public final class ReusableSession {
    public String sessionId;
    public long expireTime;
    public SessionContext context;

    public String encode() {
        StringBuffer sb = new StringBuffer();
        sb.append(context.osName).append(',');
        sb.append(context.osVersion).append(',');
        sb.append(context.clientVersion).append(',');
        sb.append(context.deviceId).append(',');
        sb.append(context.cipher);
        return sb.toString();
    }

    public void decode(String value) throws Exception {
        String[] array = value.split(",");
        if (array.length != 6) throw new RuntimeException("decode session exception");
        SessionContext context = new SessionContext();
        context.osName = array[0];
        context.osVersion = array[1];
        context.clientVersion = array[2];
        context.deviceId = array[3];
        byte[] key = AesCipher.toArray(array[4]);
        byte[] iv = AesCipher.toArray(array[5]);
        context.cipher = new AesCipher(key, iv);
        this.context = context;
    }
}
