package org.dmonix.io;

import java.io.*;

import org.dmonix.util.zip.ZipWriter;

/**
 * Utility class for splitting files into smaller parts. <br>
 * The most obvious usage is to split large text files, e.g. log files into smaller more manageable parts.
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class FileSplitter {

    private FileSplitter() {
    }

    public static int getLineCount(File file) throws IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(file));

        int lines = 0;

        while (reader.readLine() != null) {
            lines++;
        }

        reader.close();

        return lines;
    }

    /**
     * Splits a single file into the requested amount of parts. <br>
     * The individual files will have the same name as the original files plus a sequence number appended.
     * 
     * @param file
     *            The file to split
     * @param files
     *            The amount of parts to split the file into
     * @param compress
     *            If the separate parts are to be compressed using ZIP
     * @throws IOException
     */
    public static void splitFile(File file, int files, boolean compress) throws IOException {
        splitFile(file, file.length() / files, -1, compress);
    }

    /**
     * Splits a single file into the requested amount of parts. <br>
     * The individual files will have the same name as the original files plus a sequence number appended.
     * 
     * @param file
     *            The file to split
     * @param rowsPerFile
     *            The amount of per file
     * @param compress
     *            If the separate parts are to be compressed using ZIP
     * @throws IOException
     */
    public static void splitFile(File file, long rowsPerFile, boolean compress) throws IOException {
        splitFile(file, -1, rowsPerFile, compress);
    }

    private static void splitFile(File file, long bytesPerFile, long rowsPerFile, boolean compress) throws IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(file));
        File dir = file.getParentFile();
        String fileName = file.getName();

        String line;
        ZipWriter zipWriter = null;
        BufferedOutputStream bostream = null;

        if (compress) {
            zipWriter = ZipWriter.createZipFile(new File(dir, fileName + "0" + ".zip"));
            zipWriter.addZipEntry(fileName + "0");
        } else
            bostream = new BufferedOutputStream(new FileOutputStream(new File(dir, fileName + "0")));

        long byteCount = 0;
        int fileCount = 0;
        int rowCount = 0;
        while ((line = reader.readLine()) != null) {
            if ((bytesPerFile > 0 && byteCount >= bytesPerFile) || (rowsPerFile > 0 && rowCount == rowsPerFile)) {
                fileCount++;
                byteCount = 0;
                rowCount = 0;

                if (compress) {
                    zipWriter.closeZipEntry();
                    zipWriter.close();

                    zipWriter = ZipWriter.createZipFile(new File(dir, fileName + fileCount + ".zip"));
                    zipWriter.addZipEntry(fileName + fileCount);
                } else {
                    bostream.flush();
                    bostream.close();

                    bostream = new BufferedOutputStream(new FileOutputStream(new File(dir, fileName + fileCount)));
                }
            }

            if (compress) {
                zipWriter.writeDataToZipEntry(line.getBytes());
                zipWriter.writeDataToZipEntry("\r".getBytes());
            } else {
                bostream.write(line.getBytes());
                bostream.write("\r".getBytes());
            }

            rowCount++;
            byteCount += line.getBytes().length + 1;
        }

        if (compress) {
            zipWriter.closeZipEntry();
            zipWriter.close();
        } else {
            bostream.flush();
            bostream.close();
        }

        reader.close();
    }
}
