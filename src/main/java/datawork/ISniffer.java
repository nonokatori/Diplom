package datawork;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;

import java.util.ArrayList;

public interface ISniffer {
    void start() throws PcapNativeException, NotOpenException;

    void stop();

    void selectInterface(String inData);

    ArrayList<String> allInterface();
}
