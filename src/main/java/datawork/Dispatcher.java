package datawork;

import mainclass.Packet;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;

import java.util.ArrayList;
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
    private ArrayList<String> interfaceList;

    public OutData getOutData() {
        return outData;
    }

    public InData getInData() {
        return inData;
    }

    public void save() {



    }

    @Override
    public void start() {
        try {
            logicSniffer.start();
        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }
    }

    public void reboot() {
        stop();
        allInterface();
        start();
    }

    @Override
    public void stop() {
        logicSniffer.stop();
    }

    @Override
    public void selectInterface(String inData) {
        logicSniffer.selectInterface(inData);
    }

    @Override
    public ArrayList<String> allInterface() {
        interfaceList = logicSniffer.allInterface();
        return interfaceList;
    }

    public class InData {

        private Map<String, String> filterMap;
        private String interfaceName;


        public InData() {
            filterMap = new HashMap<>();
            filterMap.put("-ip", "");
            filterMap.put("-port", "");
            filterMap.put("-udp", "");
            filterMap.put("-tcp", "");
            filterMap.put("-icmp", "");
            filterMap.put("-arp", "");
            filterMap.put("-src", "");
            filterMap.put("-dst", "");

        }

        public Map<String, String> getFilterMap() {
            return filterMap;
        }

        public PcapHandle.PcapDirection getDirection() {
        return filterMap.get("src") == "true" ? PcapHandle.PcapDirection.OUT :
                    filterMap.get("dst") == "true" ? PcapHandle.PcapDirection.IN : PcapHandle.PcapDirection.INOUT;
        }

        public String getFilter() {
            StringBuilder buildFilter = new StringBuilder("");
            for (Map.Entry<String, String> entry: filterMap.entrySet()) {
                if (!"".equals(entry.getValue())) {
                    if (!("true".equals(entry.getValue())))
                        buildFilter.append(entry.getKey()).append(" ");
                    else
                        buildFilter.append(entry.getKey()).append(" ").append(entry.getValue()).append(" ");
                }
            }
            return buildFilter.toString().replaceAll("-", "").
                    replace("src", "").replace("dst", "");
        }

        public String getInterfaceName() {
            return interfaceName;
        }

        public void setInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
        }
    }

    public class OutData {

        ConcurrentLinkedQueue<Packet> packetQueue;

        public ConcurrentLinkedQueue<Packet> getPacketQueue() {
            return packetQueue;
        }

        public void setPacketQueue(ConcurrentLinkedQueue<Packet> packetQueue) {
            this.packetQueue = packetQueue;
        }

    }


}
