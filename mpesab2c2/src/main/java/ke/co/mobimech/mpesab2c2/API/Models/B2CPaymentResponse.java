package ke.co.mobimech.mpesab2c2.API.Models;

public class B2CPaymentResponse {
    public String OriginatorConversationId;
    public String ConversationId;
    public String ResponseDescription;

    public B2CPaymentResponse(String originatorConversationId, String conversationId, String responseDescription) {
        OriginatorConversationId = originatorConversationId;
        ConversationId = conversationId;
        ResponseDescription = responseDescription;
    }

    public String getOriginatorConversationId() {
        return OriginatorConversationId;
    }

    public void setOriginatorConversationId(String originatorConversationId) {
        OriginatorConversationId = originatorConversationId;
    }

    public String getConversationId() {
        return ConversationId;
    }

    public void setConversationId(String conversationId) {
        ConversationId = conversationId;
    }

    public String getResponseDescription() {
        return ResponseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        ResponseDescription = responseDescription;
    }
}
