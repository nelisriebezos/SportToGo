package hu.IPASS.persistence;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import hu.IPASS.domeinklassen.Gebruiker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PersistenceManager {
    private final static String ENDPOINT = "https://nelisriebezosopslag.blob.core.windows.net/";
    private final static String SASTOKEN = "?sv=2020-02-10&ss=bfqt&srt=sco&sp=rwdlacuptfx&se=2022-05-26T16:43:03Z&st=2021-05-26T08:43:03Z&spr=https&sig=4jxxG0Zcv6IUv68tIbymLwAa41at16D%2Fr6x7vgR%2Feow%3D";
    private final static String CONTAINER = "sporttogocontainer";

    private static PersistenceManager PM;

    private static BlobContainerClient blobContainer = new BlobContainerClientBuilder()
            .endpoint(ENDPOINT)
            .sasToken(SASTOKEN)
            .containerName(CONTAINER)
            .buildClient();

    private PersistenceManager() {}

    public static PersistenceManager getPM() {
        if (PM == null) PM = new PersistenceManager();
        return PM;
    }

    public void sendUserToAzure(Gebruiker geb) {
        if (!blobContainer.exists()) {
            blobContainer.create();
        }

        try {
            BlobClient blob = blobContainer.getBlobClient("userblob");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(geb);

            byte[] bytez = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytez);
            blob.upload(bais, bytez.length, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
