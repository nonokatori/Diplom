package datawork;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;

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

        private Map<String, String> filterMap;
        private String filter;

        public InData() {
            filterMap = new HashMap<>();
            filterMap.put("ip", "");
            filterMap.put("port", "");
            filterMap.put("udp", "");
            filterMap.put("tcp", "");
            filterMap.put("icmp", "");
            filterMap.put("arp", "");
            filterMap.put("src", "");
            filterMap.put("dst", "");
        }

        public Map<String, String> getFilterMap() {
            return filterMap;
        }

        public PcapHandle.PcapDirection getDirection() {
 return filterMap.get("src") == "true" ? PcapHandle.PcapDirection.OUT :
                    filterMap.get("dst") == "true" ? PcapHandle.PcapDirection.IN : PcapHandle.PcapDirection.INOUT;
        }

        public void setFilterMap(Map<String, String> filterMap) {
            this.filterMap = filterMap;
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


            filter = filterMap.toString().replaceAll("[{},]", "").
                    replaceAll("="," ");
            return filter;
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
