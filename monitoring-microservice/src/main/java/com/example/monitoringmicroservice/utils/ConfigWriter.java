package com.example.monitoringmicroservice.utils;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ConfigWriter {

    public void writeUUIDsToFile(ArrayList<UUID> devicesUUIDs) {
        try {
            File file = new File("devices_UUIDs.txt");
            if(file.createNewFile()) {
                System.out.println("Succesfully created file");
            } else {
                System.out.println("Error creating file");
            }


            FileWriter fileWriter = new FileWriter("devices_UUIDs.txt");

            for(UUID uuid : devicesUUIDs) {
                fileWriter.write(uuid.toString() + '\n');
            }
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }


    }

}
