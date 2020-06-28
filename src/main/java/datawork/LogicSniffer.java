package datawork;

import org.pcap4j.core.*;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class LogicSniffer implements ISniffer {

    private Dispatcher.InData inData;
    private Dispatcher.OutData outData;
    private PcapHandle handle;

    public void setData(Dispatcher.InData inData, Dispatcher.OutData outData) {
        this.inData = inData;
        this.outData = outData;
    }

    @Override
    public void start(String nameInterface) throws PcapNativeException {
        int snapshotLength = 65536;
        int readTimeout = 50;

        this.handle = selectInterface(nameInterface)
                .openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);

        setFilter(inData.getData());
    }

    @Override
    public void reboot() {

    }

    @Override
    public void stop() {


    }

    @Override
    public PcapNetworkInterface selectInterface(String interfaceName) {
        List<PcapNetworkInterface> allDevs = null;
        PcapNetworkInterface[] selectInt = {null};
        try {
            Pcaps.findAllDevs().stream()
                    .filter(packet -> {
                        packet.getName().equals(interfaceName);
                        selectInt[0] = packet;
                        return false;
                    }).findFirst();
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }
        return selectInt[0];
    }

    @Override
    public String[] allInterface() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Pcaps.findAllDevs()
                    .forEach(name -> list.add(name.getName()));
            System.out.println(list.toString());
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }
        return (String[]) list.toArray();
        // добавить в диспатчер обработку полученного массива для вывода в консоль
        // и данные, которые кушают кнопки
    }

    protected void setDirection(PcapHandle.PcapDirection) {

    }

    protected void setFilter(String filter) throws UnknownHostException, PcapNativeException, NotOpenException {
        handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE, "".equals(inData.getFilter().get("ip"))
                ? PcapHandle.PCAP_NETMASK_UNKNOWN : (Inet4Address) InetAddress.getByName(inData.getFilter().get("ip")));
    }
}
