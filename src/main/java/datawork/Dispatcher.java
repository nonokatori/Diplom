package datawork;

import org.pcap4j.core.PcapNetworkInterface;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Dispatcher implements ISniffer {

    public Dispatcher(LogicSniffer logicSniffer) {
        this.logicSniffer = logicSniffer;
    }

    private OutData outData = new OutData();
    private InData inData = new InData();
    private LogicSniffer logicSniffer;

    public OutData getOutData() {
        return outData;
    }

    public InData getInData() {
        return inData;
    }

    public void save() {

    }

    @Override
    public void start(String name) {
        start(name);
    }

    @Override
    public void reboot() {

    }

    @Override
    public void stop() {

    }


    public class InData {

        private Map<String, String> filter;
        private Map<String, String> direction;
        private String data;

        public InData() {
            filter = new HashMap<>();
            filter.put("ip", "");
            filter.put("ip.src", "");
            filter.put("ip.dst", "");
            filter.put("port", "");
            filter.put("udp", "");
            filter.put("tcp", "");
            filter.put("icmp", "");
            filter.put("arp", "");

        }

        public Map<String, String> getFilter() {
            return filter;
        }

        public getDirection() {
            return direction;
        }


    }

    public class OutData {

        ConcurrentLinkedQueue<String> packetQueue;

        public ConcurrentLinkedQueue<String> getPacketQueue() {
            return packetQueue;
        }

        public void setPacketQueue(ConcurrentLinkedQueue<String> packetQueue) {
            this.packetQueue = packetQueue;
        }

    }

    @Override
    public PcapNetworkInterface selectInterface(String inData) {

    }

    @Override
    public String[] allInterface() {


        return new String[0];
    }
}
