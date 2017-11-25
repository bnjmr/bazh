package ir.jahanmirbazh.events;

import android.content.Context;

import ir.jahanmirbazh.enums.EnumMessageKind;

/**
 * Created by FCC on 7/31/2017.
 */

public class EventOnRequestError {

    Context context;
    EnumMessageKind messageKind;

    public EventOnRequestError() {
    }

    public EventOnRequestError(Context context) {
        this.context = context;

    }

    public EnumMessageKind getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(EnumMessageKind messageKind) {
        this.messageKind = messageKind;
    }

    public EventOnRequestError(Context context, EnumMessageKind messageKind) {
        this.context = context;
        this.messageKind = messageKind;

    }
}
