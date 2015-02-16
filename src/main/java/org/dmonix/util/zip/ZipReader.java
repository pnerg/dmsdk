package org.dmonix.util.zip;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.dmonix.io.IOUtil;

/**
 * Class for reading/inflating entries in a ZIP file.
 * <p>
 * Copyright: Copyright (c) 2001
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class ZipReader {
    private ZipFile zipFile = null;
    private Enumeration<? extends ZipEntry> zipEnum = null;

    private ZipReader(File file) throws IOException {
        zipFile = new ZipFile(file);
        if (zipFile.size() > 0)
            zipEnum = zipFile.entries();
        else
            zipEnum = null;
    }

    /**
     * Close the zip file.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        if (zipFile != null)
            zipFile.close();

        zipFile = null;
    }

    /**
     * Returns the number of entries in the ZIP file.
     * 
     * @return number of entries
     */
    public int size() {
        return zipFile.size();
    }

    /**
     * Returns an enumeration of the ZIP file entries.
     * 
     * @return
     * @since 2.0
     */
    public Enumeration<? extends ZipEntry> entries() {
        return this.zipFile.entries();
    }

    /**
     * Return the ZipEntry with the matching name
     * 
     * @param name
     *            Name of the ZipEntry
     * @return The zip entry, null if not found
     * @throws IOException
     */
    public ZipEntry getEntry(String name) throws IOException {
        return zipFile.getEntry(name);
    }

    /**
     * Check if there are more entries to fetch from the zip file
     * 
     * @return boolean true/false
     */
    public boolean hasMoreElements() {
        if (zipFile == null || zipEnum == null)
            return false;

        return zipEnum.hasMoreElements();
    }

    /**
     * Return the next entry in the opened zip file
     * 
     * @return The next entry, null if none
     * @throws IOException
     */
    public ZipFileEntry nextEntry() throws IOException {
        if (zipFile == null)
            throw new IOException("The ZIP file is not open.");

        if (zipEnum.hasMoreElements())
            return new ZipFileEntry((ZipEntry) zipEnum.nextElement());

        return null;
    }

    /**
     * Open a stream to an existing zip file.
     * 
     * @param file
     *            The zip file object
     * @return An instance of ZipReader
     * @throws IOException
     */
    public static ZipReader openZipFile(File file) throws IOException {
        return new ZipReader(file);
    }

    /**
     * Open a stream to an existing zip file.
     * 
     * @param fileName
     *            The zip file
     * @return An instance of ZipReader
     * @throws IOException
     */
    public static ZipReader openZipFile(String fileName) throws IOException {
        return new ZipReader(new File(fileName));
    }

    /**
     * Unzip a zip entry to the specified path. <br>
     * Any existing files will be overwritten.
     * 
     * @param zipEntry
     *            The zip entry to inflate
     * @param path
     *            The path where to deflate the entry
     * @throws IOException
     */
    public void unzip(ZipEntry zipEntry, File path) throws IOException {
        if (!path.isDirectory())
            throw new IOException("The path must be a directory: " + path);

        File file = new File(path, zipEntry.getName());

        // remove any existing file
        if (file.exists())
            file.delete();

        // if the file has parent directories, create these first
        if (file.getParentFile() != null)
            file.getParentFile().mkdirs();

        // create the file
        file.createNewFile();

        IOUtil.copyStreams(zipFile.getInputStream(zipEntry), new FileOutputStream(file));
    }

    /**
     * Unzip a zip entry to the specified outputstream.
     * 
     * @param zipEntry
     *            The zip entry to inflate
     * @param ostream
     *            The outputstream to write the deflated data to
     * @throws IOException
     */
    public void unzip(ZipEntry zipEntry, OutputStream ostream) throws IOException {
        IOUtil.copyStreams(zipFile.getInputStream(zipEntry), ostream);
    }

    /**
     * Unzip a zip entry to the specified path. <br>
     * Any existing files will be overwritten.
     * 
     * @param zipEntry
     *            The zip entry to inflate
     * @param path
     *            The path where to deflate the entry
     * @throws IOException
     */
    public void unzip(ZipEntry zipEntry, String path) throws IOException {
        unzip(zipEntry, new File(path));
    }

    /**
     * Unzip all entries in the zip file to the specified path. <br>
     * Any existing files will be overwritten.
     * 
     * @param path
     *            The path where to deflate the entries
     * @return the amount of deflated files
     * @throws IOException
     */
    public int unzipAll(File path) throws IOException {
        int count = 0;
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        while (enumeration.hasMoreElements()) {
            unzip(enumeration.nextElement(), path);
            count++;
        }

        return count;
    }

    /**
     * Unzip all entries in the zip file to the specified path. <br>
     * Any existing files will be overwritten.
     * 
     * @param path
     *            The path where to deflated the entries
     * @return the amount of deflated files
     * @throws IOException
     */
    public int unzipAll(String path) throws IOException {
        return unzipAll(new File(path));
    }

    /**
     * Unzip the next entry to the specified path. <br>
     * Any existing files will be overwritten. <br>
     * The method will make an implicit call to <code>nextEntry()</code>
     * 
     * @param path
     *            The path where to deflate the entry
     * @return true if ok, false if no more files
     * @throws IOException
     */
    public boolean unzipNextEntry(File path) throws IOException {
        ZipEntry zipEntry = nextEntry();
        if (zipEntry == null)
            return false;

        unzip(zipEntry, path);
        return true;
    }

    /**
     * Unzip the next zip entry to the specified outputstream.
     * 
     * @param ostream
     *            The outputstream to write the deflated data to
     * @return true if ok, false if no more files
     * @throws IOException
     */
    public boolean unzipNextEntry(OutputStream ostream) throws IOException {
        ZipEntry zipEntry = nextEntry();
        if (zipEntry == null)
            return false;

        IOUtil.copyStreams(zipFile.getInputStream(zipEntry), ostream);
        return true;
    }

    /**
     * Unzip the next entry to the specified path. <br>
     * Any existing files will be overwritten. <br>
     * The method will make an implicit call to <code>nextEntry()</code>
     * 
     * @param path
     *            The path where to deflate the entry
     * @return true if ok, false if no more files
     * @throws IOException
     */
    public boolean unzipNextEntry(String path) throws IOException {
        return unzipNextEntry(new File(path));
    }

}
