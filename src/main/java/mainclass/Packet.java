package mainclass;

public class Packet {

    private String id;
    private String srcIp;
    private String desIp;
    private String srcMac;
    private String desMac;
    private String length;
    private String protocol;
    private String time;
    private String content;


    public Packet(String id, String srcIp, String desIp,
                  String srcMac, String desMac, String length,
                  String protocol, String time, String content) {
        this.id = id;
        this.srcIp = srcIp;
        this.desIp = desIp;
        this.srcMac = srcMac;
        this.desMac = desMac;
        this.length = length;
        this.protocol = protocol;
        this.time = time;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getDesIp() {
        return desIp;
    }

    public void setDesIp(String desIp) {
        this.desIp = desIp;
    }

    public String getSrcMac() {
        return srcMac;
    }

    public void setSrcMac(String srcMac) {
        this.srcMac = srcMac;
    }

    public String getDesMac() {
        return desMac;
    }

    public void setDesMac(String desMac) {
        this.desMac = desMac;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id='" + id + '\'' +
                ", srcIp='" + srcIp + '\'' +
                ", desIp='" + desIp + '\'' +
                ", srcMac='" + srcMac + '\'' +
                ", desMac='" + desMac + '\'' +
                ", length='" + length + '\'' +
                ", protocol='" + protocol + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
