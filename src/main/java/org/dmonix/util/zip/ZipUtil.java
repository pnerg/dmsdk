package org.dmonix.util.zip;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.dmonix.io.IOUtil;
import org.dmonix.io.filters.*;

/**
 * Utility class for performing ZIP related operations.
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class ZipUtil {
    private ZipUtil() {
    }

    /**
     * Packs all files in the given directory to a ZIP file.
     * 
     * @param dir
     *            The directory
     * @param zipFile
     *            The ZIP file
     * @return The number of files written to the ZIP file
     * @throws IOException
     */
    public static int packDirectory(File dir, File zipFile) throws IOException {
        return packDirectory(dir, new AcceptAllFilter(), zipFile);
    }

    /**
     * Packs all matching files in the given directory to a ZIP file. <br>
     * Only the files that match the filter will be included in the ZIP file.
     * 
     * @param dir
     *            The directory
     * @param filter
     *            The filter
     * @param zipFile
     *            The ZIP file
     * @return The number of files written to the ZIP file
     * @throws IOException
     */
    public static int packDirectory(File dir, FileFilter filter, File zipFile) throws IOException {
        File[] files = IOUtil.listFiles(dir, filter);

        ZipWriter zipWriter = ZipWriter.createZipFile(zipFile);
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile())
                zipWriter.addFile(files[i], dir);
        }

        zipWriter.close();

        return files.length;
    }

    /**
     * Creates a ZIP file and includes the given files.
     * 
     * @param files
     *            The files to include in the ZIP file
     * @param zipFile
     *            The ZIP file
     * @throws IOException
     */
    public static void packFiles(File[] files, File zipFile) throws IOException {
        ZipWriter zipWriter = ZipWriter.createZipFile(zipFile);
        zipWriter.addFiles(files);
        zipWriter.close();
    }

    /**
     * Unzips all entries in a ZIP file to the specified destination.
     * 
     * @param zipFile
     *            The ZIP file
     * @param path
     *            The destination
     * @return The number of deflated files
     * @throws IOException
     */
    public static int unzipFile(File zipFile, File path) throws IOException {
        ZipReader zr = ZipReader.openZipFile(zipFile);
        int count = zr.unzipAll(path);
        zr.close();
        return count;
    }

}