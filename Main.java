import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;


public class Main {
    public static void main(String[] ags) {

        // создание сохранения
        GameProgress game1 = new GameProgress(80, 4, 2, 55);
        GameProgress game2 = new GameProgress(44, 6, 5, 11);
        GameProgress game3 = new GameProgress(13, 3, 14, 22);
        GameProgress game4 = new GameProgress(10, 2, 22, 33);

        // создание файлов сохранения
        saveGame(game1, "D://games//savegames//save1.dat");
        saveGame(game2, "D://games//savegames//save2.dat");
        saveGame(game3, "D://games//savegames//save3.dat");
        saveGame(game4, "D://games//savegames//save4.dat");

        // список с именами файлов в папке
        List<String> allFiles = new ArrayList<>();
        File dir = new File("D://games//savegames//");
        // заполнение списка
        for (File file : dir.listFiles()) {
            allFiles.add(file.getPath());
        }
        // внесение в архив согласно списка
        zipFiles("D://games//savegames//zip.zip", allFiles);


        // удаление файлов которые находятся в архиве из корневой папки, согласно списка
        for (String file : allFiles) {
            File deleteFile = new File(file);
            if (deleteFile.delete()) {
                System.out.println("Удален " + file);
            }
            ;
        }
    }


    public static void saveGame(GameProgress game, String path) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(game);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void zipFiles(String path, List<String> allFiles) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(path))) {
            for (String file : allFiles) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file);
                    zos.putNextEntry(entry);
                    while (fis.available() != 0) {
                        zos.write(fis.read());
                    }
                    zos.closeEntry();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
