import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;

public class ConnectionThread implements Runnable {
    DataInputStream dis;
    DataOutputStream dos;
    Socket socket;
    File file;

    ConnectionThread(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setSoTimeout(3000);

    }

    @Override
    public void run() {
        try {
            receive();
        } catch (IOException e) {
            System.out.println("Something goes wrong");
            this.file.delete();
        }

    }

    void receive() throws IOException {
        InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();
        DataInputStream dis = new DataInputStream(sin);
        DataOutputStream dos = new DataOutputStream(sout);
        long nameSize = dis.readLong();

        String name = dis.readUTF();

        if (name.length() != nameSize) System.out.println("wrong name");
        long size = 0;
        try {
            size = dis.readLong();
        } catch (IOException e) {
            socket.close();
        }
        //System.out.println(size);
        String dirName = "uploads";
        File file1 = new File(dirName + "/" + String.valueOf(name));
        String name1 = file1.getName();
        int tochkaInd = name1.lastIndexOf(".");
        if (file1.exists()) {
            int num = 1;
            while (file1.exists()) {
                name1 = (new StringBuilder(name).insert(tochkaInd, num)).toString();
                file1 = new File(dirName + "/" + String.valueOf(name1));
                num++;
            }
            this.file = new File(dirName + "/" + String.valueOf(name1));
        } else file = new File(dirName + "/" + String.valueOf(name));
        this.file.getParentFile().mkdirs();
        this.file.createNewFile();
        FileOutputStream writer = new FileOutputStream(this.file);
        //FileWriter writer = new FileWriter(this.file);
        long startTime = System.currentTimeMillis();
        long curTime = System.currentTimeMillis();
        long instantN = 0;
        long averageN = 0;


        double perc = size / 100;
        System.out.println("*****************************receiving: " + name + " *****************************");
        System.out.println("------------------------------------------------------------------------------");
        byte data[] = new byte[256];
        int nn;
        final String format = "#0.00";
        final String speedFormat = "#0.000";

        String speedR[] = {"Byte", "KB", "MB", "GB", "TB"};

        nn = dis.read(data);
        while (nn > 0) {
            try {

                averageN += nn;
                instantN += nn;
                if ((System.currentTimeMillis() - curTime) >= 3000) {
                    double inSp = instantN * 1000 / (System.currentTimeMillis() - curTime);
                    double avSp = averageN * 1000 / (System.currentTimeMillis() - startTime);
                    int speed = 0;
                    while ((inSp / 1024 > 1) && (speed < speedR.length)) {
                        speed++;
                        inSp /= 1024;
                    }
                    int speedA = 0;
                    while ((avSp / 1024 > 1) && (speedA < speedR.length)) {
                        speedA++;
                        avSp /= 1024;
                    }

                    String formatted = new DecimalFormat(format).format(averageN / perc);
                    String formattedAv = new DecimalFormat(speedFormat).format(avSp);
                    String formattedIn = new DecimalFormat(speedFormat).format(inSp);
                    System.out.println("instant speed:" + formattedIn + " " + speedR[speed] + "  per sec");
                    System.out.println("average speed:" + formattedAv + " " + speedR[speedA] + "  per sec");
                    System.out.println(formatted + "% of file has already received");
                    System.out.println("------------------------------------------------------------------------------");
                    instantN = 0;
                    curTime = System.currentTimeMillis();
                }

                if (nn < 256) {
                    writer.write(data, 0, nn);
                } else {
                    writer.write(data);
                }
                data = new byte[256];
                nn = dis.read(data);
                //if(nn<=0)break;

            } catch (IOException e) {
                break;
            }
        }

        if (instantN > 0) {
            if (System.currentTimeMillis() - curTime == 0) {
                double inSp = instantN;
                double avSp = averageN;
                int speed = 0;
                while ((inSp / 1024 > 1) && (speed < speedR.length)) {
                    speed++;
                    inSp /= 1024;
                }
                int speedA = 0;
                while ((avSp / 1024 > 1) && (speedA < speedR.length)) {
                    speedA++;
                    avSp /= 1024;
                }

                String formattedAv = new DecimalFormat(speedFormat).format(avSp);
                String formattedIn = new DecimalFormat(speedFormat).format(inSp);
                System.out.println("instant speed:" + formattedIn + " " + speedR[speed] + "  per sec");
                System.out.println("average speed:" + formattedAv + " " + speedR[speedA] + "  per sec");

            } else {
                double inSp = instantN * 1000 / (System.currentTimeMillis() - curTime);
                double avSp = averageN * 1000 / (System.currentTimeMillis() - startTime);
                int speed = 0;
                while ((inSp / 1024 > 1) && (speed < speedR.length)) {
                    speed++;
                    inSp /= 1024;
                }
                int speedA = 0;
                while ((avSp / 1024 > 1) && (speedA < speedR.length)) {
                    speedA++;
                    avSp /= 1024;
                }

                String formattedAv = new DecimalFormat(speedFormat).format(avSp);
                String formattedIn = new DecimalFormat(speedFormat).format(inSp);
                System.out.println("instant speed:" + formattedIn + " " + speedR[speed] + "  per sec");
                System.out.println("average speed:" + formattedAv + " " + speedR[speedA] + "  per sec");
            }
            //System.out.println(averageN/perc+"% of file has already received");
            System.out.println("------------------------------------------------------------------------------");

        }
        writer.close();
        if (size == averageN) {
            //System.out.println(averageN);
            dos.writeBoolean(true);
            System.out.println("******************************" + name + " was received*******************************");
        } else {
            dos.writeBoolean(false);
            System.out.println("Something goes wrong");
            this.file.delete();
        }
        socket.close();

    }
}