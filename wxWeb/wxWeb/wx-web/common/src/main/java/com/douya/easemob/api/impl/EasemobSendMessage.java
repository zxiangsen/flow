package com.douya.easemob.api.impl;

import com.douya.easemob.api.SendMessageAPI;
import com.douya.easemob.comm.EasemobAPI;
import com.douya.easemob.comm.OrgInfo;
import com.douya.easemob.comm.ResponseHandler;
import com.douya.easemob.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
