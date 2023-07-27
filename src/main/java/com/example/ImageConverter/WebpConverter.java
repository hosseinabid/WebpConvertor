package com.example.ImageConverter;

import java.io.File;
import java.io.IOException;

public class WebpConverter {
    public static void main(String[] args) {


        String inputDirectoryPath = "E:\\pic\\jpg";
        String outputDirectoryPath = "E:\\pic\\webp2";

        // Create the output directory if it doesn't exist
        File outputDirFile = new File(outputDirectoryPath);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdirs();
        }

        File inputDirectory = new File(inputDirectoryPath);
        File[] jpgFiles = inputDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));

        if (jpgFiles == null) {
            System.out.println("The input folder is empty or the path is incorrect!");
            return;
        }

        for (File jpgFile : jpgFiles) {
            String jpgFileName = jpgFile.getName();
            String webpFileName = jpgFileName.substring(0, jpgFileName.lastIndexOf(".")) + ".webp";
            String outputImagePath = outputDirectoryPath + File.separator + webpFileName;

            try {
                // Running the cwebp command to convert an image to the WebP format
                //Syntax :  cwebp [options] input_file -o output_file.webp
                ProcessBuilder processBuilder = new ProcessBuilder("cwebp", jpgFile.getAbsolutePath(), "-o", outputImagePath);
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All jpg images have been converted to the WebP format!");
    }
}
