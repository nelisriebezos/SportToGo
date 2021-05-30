package hu.IPASS.persistence;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import hu.IPASS.domeinklassen.Gebruiker;
import hu.IPASS.domeinklassen.OefeningType;
import hu.IPASS.utils.MakeUser;

import java.io.*;
import java.util.ArrayList;

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

    private Gebruiker currentUser;
    private ArrayList<Gebruiker> gebruikerList = new ArrayList<>();
    private ArrayList<OefeningType> oefeningTypeList = new ArrayList<>();

    private PersistenceManager() {
    }

    public static PersistenceManager getPM() {
        if (PM == null) PM = new PersistenceManager();
        return PM;
    }

    public Gebruiker getUser(String eM, String wW) {
        if (wW.equals("wachtwoord1")) {
            return MakeUser.makeUserData();
        } else {
            return null;
        }
    }

    public void sendUserToAzure(Gebruiker g) {
        if (!blobContainer.exists()) {
            blobContainer.create();
        }

        try {
            BlobClient blob = blobContainer.getBlobClient("userblob");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(g);

            byte[] bytez = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytez);
            blob.upload(bais, bytez.length, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersFromAzure() {
        if (blobContainer.exists()) {
            BlobClient blob = blobContainer.getBlobClient("userblob");
            try {
                if (blob.exists()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    blob.download(baos);

                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Gebruiker gebruiker = (Gebruiker) ois.readObject();

                    System.out.println(gebruiker);
//                    PM.setGebruikerList(gebruikerLijst);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public Gebruiker getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Gebruiker ingelogdGebruiker) {
        this.currentUser = ingelogdGebruiker;
    }

    public ArrayList<Gebruiker> getGebruikerList() {
        return gebruikerList;
    }

    public void setGebruikerList(ArrayList<Gebruiker> gebruikerLijst) {
        this.gebruikerList = gebruikerLijst;
    }

    public ArrayList<OefeningType> getOefeningTypeList() {
        return oefeningTypeList;
    }

    public void setOefeningTypeList(ArrayList<OefeningType> oefeningTypeLijst) {
        this.oefeningTypeList = oefeningTypeLijst;
    }

    public boolean addGebruikerToList(Gebruiker g) {
        for (Gebruiker geb : gebruikerList) {
            if (geb.getEmailAdres().equals(g.getEmailAdres())) {
                return false;
            }
        }
        this.gebruikerList.add(g);
        return true;
    }

    public boolean addOefeningToList(OefeningType ot) {
        if (!oefeningTypeList.contains(ot)) {
            this.oefeningTypeList.add(ot);
            return true;
        }
        return false;
    }
}
