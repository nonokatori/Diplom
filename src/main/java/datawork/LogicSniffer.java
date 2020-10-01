package datawork;

import com.sun.jna.NativeLong;
import org.pcap4j.core.*;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.GtpV1Packet;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ProtocolFamily;
import org.pcap4j.util.ByteArrays;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class LogicSniffer implements ISniffer {

    private Dispatcher.InData inData;
    private Dispatcher.OutData outData;
    private PcapHandle handle;
    private PcapDumper dumper;
    private PcapNetworkInterface networkInterface;
    private ArrayList<PcapNetworkInterface> interfaceArrayList = new ArrayList<>();

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
        int count = 0;
        try {
            changeFilter();
        } catch (NotOpenException | UnknownHostException e) {
            e.printStackTrace();
        }

        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                for (int j = 0; j < (int) Double.POSITIVE_INFINITY; j++) {

                    System.out.println(j++);
                }

                mainclass.Packet newPacket = new mainclass.Packet(String.valueOf(count),
                        packet.get(IpV4Packet.class).getHeader().getSrcAddr().toString(),
                        packet.get(IpV4Packet.class).getHeader().getDstAddr().toString(),
                        packet.get(EthernetPacket.class).getHeader().getSrcAddr().toString(),
                        packet.get(EthernetPacket.class).getHeader().getDstAddr().toString(),
                        String.valueOf(packet.length()),
                        packet.get(IpV4Packet.class).getHeader().getProtocol().toString(),
                        handle.getTimestamp().toString(),
                        packet.getRawData().toString()
                        );
                // передача пакетов вперед
//                packet.getHeader().getRawData();
//                mainclass.Packet packetNew = new mainclass.Packet(1, handle.getTimestamp(),
//                        packet.get(IpV4Packet.class).getHeader().getSrcAddr(),
//                        packet.get(IpV4Packet.class).getHeader().getDstAddr(),
//                        packet.length());

//                try {
//                    dumper.dump(packet, handle.getTimestamp());
//                } catch (NotOpenException e) {
//                    e.printStackTrace();
//                }
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

        if (!System.getProperty("os.name").toLowerCase().contains("win")) //под виндой не работает
            setDirection(inData.getDirection());

        for (Map.Entry<String, String> entry : inData.getFilterMap().entrySet() ){
            entry.setValue("");
        }
    }

    @Override
    public void stop() {
        dumper.close();
        handle.close();
//        File file = new File("/out.pcap");
    }

    @Override
    public void selectInterface(String interfaceName) {
        interfaceArrayList.stream()
                .filter(packet -> {
                    packet.getName().equals(interfaceName);
                    networkInterface = packet;
                    return false;
                }).findFirst();
    }

    @Override
    public ArrayList<PcapNetworkInterface> allInterface() {
//        if (interfaceArrayList.size() == 0) {
//            System.out.println(" No NIF was found");
//            System.exit(0);
//        }

        try {
            interfaceArrayList = (ArrayList<PcapNetworkInterface>) Pcaps.findAllDevs();
            for (PcapNetworkInterface s: interfaceArrayList)
                System.out.println( "["+ interfaceArrayList.indexOf(s) + "]: "+ s.getAddresses());
        } catch (PcapNativeException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.out.println("No NIF was found");
            System.exit(0);
        }
        return interfaceArrayList;
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


    public void getRawData (String timestamp) {
        PcapHandle readHandle;
        try {
            readHandle = Pcaps.openOffline("dump.pcap", PcapHandle.TimestampPrecision.valueOf(timestamp));
        } catch (PcapNativeException e) {
            try {
                readHandle = Pcaps.openOffline("dump.pcap");
            } catch (PcapNativeException pcapNativeException) {
                pcapNativeException.printStackTrace();
            }
        }

    }

    public void dumpFullPacket (Packet packet, Timestamp timestamp) throws NotOpenException {


    }
}
