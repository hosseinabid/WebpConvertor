package com.example.ImageConverter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class ImageConverter {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java WebpConverter <source_directory> <destination_directory>");
            return;
        }

        String sourceDirectory = args[0];
        String destinationDirectory = args[1];

        try {
            Files.walkFileTree(Paths.get(sourceDirectory), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isRegularFile(file)) {
                        // تبدیل فایل به فرمت WebP
                        String fileName = file.getFileName().toString();
                        String outputPath = destinationDirectory + "/" + fileName.replace(".jpg", ".webp");

                        ProcessBuilder processBuilder = new ProcessBuilder("cwebp.exe", file.toString(), "-o", outputPath);
                        processBuilder.redirectErrorStream(true);

                        Process process = processBuilder.start();
                        try {
                            process.waitFor();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println("تصویر " + fileName + " به فرمت WebP تبدیل شد.");
                    }

                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("تمام فایل‌ها به فرمت WebP تبدیل شدند.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}