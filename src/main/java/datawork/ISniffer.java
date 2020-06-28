package datawork;

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;

public interface ISniffer {
    void start(String name) throws PcapNativeException;

    void reboot();

    void stop();

    PcapNetworkInterface selectInterface(String inData);

    String[] allInterface();
}
