package mainclass;

public class Packet {
    private int number;
    private String time;
    private String source;
    private String destination;
//    private String protocol;
    private int length;

    public Packet(int number, String time, String source, String destination, int length) {
        this.number = number;
        this.time = time;
        this.source = source;
        this.destination = destination;
        this.length = length;
    }

    public int getNumber() {
        return number;
    }

    public String getTime() {
        return time;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getLength() {
        return length;
    }
}
