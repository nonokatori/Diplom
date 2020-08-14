package datawork;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogicSniffer implements ISniffer {

    private Dispatcher.InData inData;
    private Dispatcher.OutData outData;
    private PcapHandle handle;
    private PcapDumper dumper;
    private PcapNetworkInterface networkInterface;
//    private int max_packet;


    public void setData(Dispatcher.InData inData, Dispatcher.OutData outData) {
        this.inData = inData;
        this.outData = outData;
    }

    @Override
    public void start() throws PcapNativeException, NotOpenException {
        int snapshotLength = 65536;
        int readTimeout = 50;

        selectInterface(inData.getInterfaceName());
        this.handle = networkInterface
                .openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);
        dumper = handle.dumpOpen("out.pcap");
        try {
            changeFilter();
        } catch (NotOpenException | UnknownHostException e) {
            e.printStackTrace();
        }

        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                // передача пакетов вперед

                try {
                    dumper.dump(packet, handle.getTimestamp());
                } catch (NotOpenException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            int maxPackets = (int) Double.POSITIVE_INFINITY; //бесконечное число пакетов
            handle.loop(maxPackets, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void changeFilter () throws PcapNativeException, NotOpenException, UnknownHostException {
        setFilter(inData.getFilter());
        setDirection(inData.getDirection());

        for (Map.Entry<String, String> entry : inData.getFilterMap().entrySet() ){
            entry.setValue("");
        }
    }


    @Override
    public void stop() {
        dumper.close();
        handle.close();
    }

    @Override
    public void selectInterface(String interfaceName) {
        List<PcapNetworkInterface> allDevs = null;
        PcapNetworkInterface[] selectInt = {null};
        try {
            Pcaps.findAllDevs().stream()
                    .filter(packet -> {
                        packet.getName().equals(interfaceName);
                        networkInterface = packet;
                        return false;
                    }).findFirst();
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> allInterface() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Pcaps.findAllDevs()
                    .forEach(name -> list.add(name.getName()));
            System.out.println(list.toString());
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }
        return list;
        // добавить в диспатчер обработку полученного массива для вывода в консоль
        // и данные, которые кушают кнопки
    }

    protected void setDirection(PcapHandle.PcapDirection dir) throws PcapNativeException, NotOpenException {
        handle.setDirection(dir);
    }

    protected void setFilter(String filter) throws UnknownHostException, PcapNativeException, NotOpenException {
        handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE, "".equals(inData.getFilterMap().get("ip"))
                ? PcapHandle.PCAP_NETMASK_UNKNOWN : (Inet4Address) InetAddress.getByName(inData.getFilterMap().get("ip")));
    }
}
