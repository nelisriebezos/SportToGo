package hu.IPASS.persistence;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

import java.io.*;

public class PersistenceManager {
    private final static String ENDPOINT = "https://nelisriebezosopslag.blob.core.windows.net/";
    private final static String SASTOKEN = "?sv=2020-02-10&ss=bfqt&srt=sco&sp=rwdlacuptfx&se=2022-05-26T16:43:03Z&st=2021-05-26T08:43:03Z&spr=https&sig=4jxxG0Zcv6IUv68tIbymLwAa41at16D%2Fr6x7vgR%2Feow%3D";
    private final static String CONTAINER = "sporttogocontainer";

    private static BlobContainerClient blobContainer = new BlobContainerClientBuilder()
            .endpoint(ENDPOINT)
            .sasToken(SASTOKEN)
            .containerName(CONTAINER)
            .buildClient();

    public static void sendOefeningTypeToAzure() {
        if (!blobContainer.exists()) {
            blobContainer.create();
        }
        try {
            BlobClient blob = blobContainer.getBlobClient("oefeningtypeblob");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(OefeningTypeData.getOefeningTypeData());

            byte[] bytez = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytez);
            blob.upload(bais, bytez.length, true);

            oos.close();
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadOefeningTypeFromAzure() {
        if (blobContainer.exists()) {
            BlobClient blob = blobContainer.getBlobClient("oefeningtypeblob");
            try {
                if (blob.exists()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    blob.download(baos);

                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bais);

                    OefeningTypeData otd = (OefeningTypeData) ois.readObject();
                    OefeningTypeData.setOefeningTypeData(otd);

                    baos.close();
                    ois.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendUsersToAzure() {
        if (!blobContainer.exists()) {
            blobContainer.create();
        }

        try {
            BlobClient blob = blobContainer.getBlobClient("userblob");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(GebruikerData.getGebruikerData());

            byte[] bytez = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytez);
            blob.upload(bais, bytez.length, true);

            oos.close();
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUserFromAzure() {
        if (blobContainer.exists()) {
            BlobClient blob = blobContainer.getBlobClient("userblob");
            try {
                if (blob.exists()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    blob.download(baos);

                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bais);

                    GebruikerData gebd = (GebruikerData) ois.readObject();
                    GebruikerData.setGebruikerData(gebd);

                    baos.close();
                    ois.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
