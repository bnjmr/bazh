package ir.jahanmirbazh.Gson;

/**
 * Created by FCC on 10/25/2017.
 */

public class getUpdate {

    float Version;
    String Url;
    boolean IsRequired;
    String Description;
    String Message;
    int Result;


    public float getVersion() {
        return Version;
    }

    public void setVersion(float version) {
        Version = version;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public boolean isRequired() {
        return IsRequired;
    }

    public void setRequired(boolean required) {
        IsRequired = required;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }
}
