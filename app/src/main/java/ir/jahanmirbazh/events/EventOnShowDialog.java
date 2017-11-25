package ir.jahanmirbazh.events;

import android.content.Context;

import ir.jahanmirbazh.enums.EnumDialogKind;

/**
 * Created by FCC on 7/30/2017.
 */

public class EventOnShowDialog {

    String message;
    Context context;
    EnumDialogKind dialogKind;

    public EventOnShowDialog(String message, Context context, EnumDialogKind dialogKind) {
        this.message = message;
        this.dialogKind = dialogKind;
        this.context = context;
    }

    public EnumDialogKind getDialogKind() {
        return dialogKind;
    }

    public void setDialogKind(EnumDialogKind dialogKind) {
        this.dialogKind = dialogKind;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
