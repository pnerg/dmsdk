package org.dmonix.util.zip;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.dmonix.io.IOUtil;

/**
 * Class for writing/deflating entries to a ZIP file.
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
public class ZipWriter {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(ZipWriter.class.getName());

    private ZipOutputStream zoStream = null;
    private ZipEntry currentEntry = null;

    private ZipWriter(String fileName) throws IOException {
        this.zoStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
    }

    /**
     * Add a new file entry to the existing zip file. <br>
     * The method will add the file to zip file with information on the original files source path relative to the supplied directory path. <br>
     * E.g. the directory <i>/etc/foo/</i> and the file <i>/etc/foo/data/file.txt</i> results in an entry called <i>data/file.txt/</i> <br>
     * The method will implicitly perform an <code>addZipEntry</code> and a <code>closeZipEntry</code>.
     * 
     * @param file
     *            A file object
     * @param directory
     *            A parent directory
     * @throws IOException
     * @see org.dmonix.io.IOUtil.getRelativePath(File, File)
     */
    public void addFile(File file, File directory) throws IOException {
        addZipEntry(IOUtil.getRelativePath(directory, file));
        writeDataToZipEntry(new FileInputStream(file), true);
        closeZipEntry();
    }

    /**
     * Add a new file entry to the existing zip file. <br>
     * The method will add the file to zip file with no information on the original files source path. <br>
     * I.e. the file <i>/etc/foo/data/file.txt</i> will be written to the ZIP file simply as <i>file.txt</i>. <br>
     * Note that there is a risk of filename collision this way. <br>
     * The method will implicitly perform an <code>addZipEntry</code> and a <code>closeZipEntry</code>.
     * 
     * @param file
     *            The file
     * @throws IOException
     */
    public void addFile(File file) throws IOException {
        addZipEntry(file.getName());
        writeDataToZipEntry(new FileInputStream(file), true);
        closeZipEntry();
    }

    /**
     * Add a new file entry to the existing zip file <br>
     * The method will add the file to zip file with no information on the original files source path. <br>
     * I.e. the file <i>/etc/foo/data/file.txt</i> will be written to the ZIP file simply as <i>file.txt</i>. <br>
     * Note that there is a risk of filename collision this way. <br>
     * The method will implicitly perform an <code>addZipEntry</code> and a <code>closeZipEntry</code>.
     * 
     * @param fileName
     *            name/path of the file to add
     * @throws IOException
     */
    public void addFile(String fileName) throws IOException {
        addFile(new File(fileName));
    }

    /**
     * Add a new file entry to the existing zip file. <br>
     * The method will add the file to zip file with information on the original files source path relative to the supplied directory path. <br>
     * E.g. the directory <i>/etc/foo/</i> and the file <i>/etc/foo/data/file.txt</i> results in an entry called <i>data/file.txt/</i> <br>
     * The method will implicitly perform an <code>addZipEntry</code> and a <code>closeZipEntry</code>.
     * 
     * @param file
     *            A file object
     * @param directory
     *            A parent directory
     * @param comment
     *            The entry comment
     * @throws IOException
     * @see org.dmonix.io.IOUtil.getRelativePath(File, File)
     */
    public void addFile(File file, File directory, String comment) throws IOException {
        addZipEntry(IOUtil.getRelativePath(directory, file), comment);
        writeDataToZipEntry(new FileInputStream(file), true);
        closeZipEntry();

        if (log.isLoggable(Level.FINER))
            log.log(Level.FINER, "Added zip entry " + file.getAbsolutePath());

    }

    /**
     * Add a new file entry to the existing zip file. <br>
     * The method will add the file to zip file with no information on the original files source path. <br>
     * I.e. the file <i>/etc/foo/data/file.txt</i> will be written to the ZIP file simply as <i>file.txt</i>. <br>
     * Note that there is a risk of filename collision this way. <br>
     * The method will implicitly perform an <code>addZipEntry</code> and a <code>closeZipEntry</code>.
     * 
     * @param file
     *            The file
     * @param comment
     *            The entry comment
     * @throws IOException
     */
    public void addFile(File file, String comment) throws IOException {
        addZipEntry(file.getName(), comment);
        writeDataToZipEntry(new FileInputStream(file), true);
        closeZipEntry();

        if (log.isLoggable(Level.FINER))
            log.log(Level.FINER, "Added zip entry " + file.getAbsolutePath());
    }

    /**
     * Add a new file entry to the existing zip file <br>
     * The method will implicitly perform an <code>addZipEntry</code> and a <code>closeZipEntry</code>.
     * 
     * @param fileName
     *            name/path of the file to add
     * @param comment
     *            The entry comment
     * @throws IOException
     */
    public void addFile(String fileName, String comment) throws IOException {
        addFile(new File(fileName), comment);
    }

    /**
     * Add an array of files to the existing zip file. <br>
     * This is equal to multiple calls to <code>addFile(File)</code>
     * 
     * @param files
     *            The files to add
     * @throws IOException
     * @see addFile(File)
     */
    public void addFiles(File[] files) throws IOException {
        for (int i = 0; i < files.length; i++) {
            this.addFile(files[i]);
        }
    }

    /**
     * Creates a new entry in the ZIP file.
     * 
     * @param name
     *            The name/path of the entry
     * @throws IOException
     */
    public void addZipEntry(String name) throws IOException {
        this.currentEntry = new ZipEntry(name);
        zoStream.putNextEntry(this.currentEntry);
    }

    /**
     * Creates a new entry in the ZIP file.
     * 
     * @param name
     *            The name/path of the entry
     * @param comment
     *            The entry comment
     * @throws IOException
     */
    public void addZipEntry(String name, String comment) throws IOException {
        this.currentEntry = new ZipEntry(name);
        this.currentEntry.setComment(comment);
        zoStream.putNextEntry(this.currentEntry);
    }

    /**
     * Writes data to the current entry. <br>
     * A call to <code>addZipEntry</code> must preceed this operation.
     * 
     * @param data
     *            The data to write to the entry
     * @throws IOException
     * @see addZipEntry(String)
     * @see addZipEntry(String, String)
     */
    public void writeDataToZipEntry(int data) throws IOException {
        zoStream.write(data);
    }

    /**
     * Writes data to the current entry. <br>
     * A call to <code>addZipEntry</code> must preceed this operation.
     * 
     * @param data
     *            The data to write to the entry
     * @throws IOException
     * @see addZipEntry(String)
     * @see addZipEntry(String, String)
     */
    public void writeDataToZipEntry(byte[] data) throws IOException {
        zoStream.write(data);
    }

    /**
     * Writes data to the current entry. <br>
     * A call to <code>addZipEntry</code> must preceed this operation.
     * 
     * @param data
     *            The data to write to the entry
     * @param offset
     *            The offset from where to start read data in the data array
     * @param length
     *            The amount of data to read from the data array
     * @throws IOException
     * @see addZipEntry(String)
     * @see addZipEntry(String, String)
     */
    public void writeDataToZipEntry(byte[] data, int offset, int length) throws IOException {
        zoStream.write(data, offset, length);
    }

    /**
     * Copies data from the provided stream to the current entry <br>
     * A call to <code>addZipEntry</code> must preceed this operation.
     * 
     * @param istream
     *            The inputstrream from where to copy data
     * @param closeInputStream
     *            If the inputstream is to be closed
     * @throws IOException
     * @see addZipEntry(String)
     * @see addZipEntry(String, String)
     */
    public void writeDataToZipEntry(InputStream istream, boolean closeInputStream) throws IOException {
        IOUtil.copyStreams(istream, zoStream, closeInputStream, false);
    }

    /**
     * Closes the current zip entry. <br>
     * A call to <code>addZipEntry</code> must preceed this operation.
     * 
     * @throws IOException
     * @see addZipEntry(String)
     * @see addZipEntry(String, String)
     */
    public void closeZipEntry() throws IOException {
        zoStream.flush();
        zoStream.closeEntry();
        this.currentEntry = null;
    }

    /**
     * Close the outputstream to the zip file. <br>
     * The methods does nothing if the outputstream is already closed.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        if (zoStream != null)
            zoStream.close();

        zoStream = null;
    }

    /**
     * Sets the level of compression.
     * 
     * @param compressionLevel
     *            The compression level [0-9]
     */
    public void setCompressionLevel(int compressionLevel) {
        this.zoStream.setLevel(compressionLevel);
    }

    /**
     * Set a comment for the current ZIP file.
     * 
     * @param comment
     *            The ZIP file comment
     */
    public void setZipfileComment(String comment) {
        this.zoStream.setComment(comment);
    }

    /**
     * Creates a new ZIP file.
     * 
     * @param file
     *            The ZIP file
     * @return A ZIPWriter instance
     * @throws IOException
     */
    public static ZipWriter createZipFile(File file) throws IOException {
        return new ZipWriter(file.getAbsolutePath());
    }

    /**
     * Creates a new ZIP file.
     * 
     * @param file
     *            The ZIP file
     * @param compressionLevel
     *            The compression level [0-9]
     * @return A ZIPWriter instance
     * @throws IOException
     */
    public static ZipWriter createZipFile(File file, int compressionLevel) throws IOException {
        ZipWriter zr = new ZipWriter(file.getAbsolutePath());
        zr.setCompressionLevel(compressionLevel);
        return zr;
    }

    /**
     * Creates a new ZIP file.
     * 
     * @param fileName
     *            Name of the zip file
     * @return A ZIPWriter instance
     * @throws IOException
     */
    public static ZipWriter createZipFile(String fileName) throws IOException {
        return new ZipWriter(fileName);
    }

    /**
     * Creates a new ZIP file.
     * 
     * @param fileName
     *            Name of the zip file
     * @param compressionLevel
     *            The compression level [0-9]
     * @return A ZIPWriter instance
     * @throws IOException
     */
    public static ZipWriter createZipFile(String fileName, int compressionLevel) throws IOException {
        ZipWriter zr = new ZipWriter(fileName);
        zr.setCompressionLevel(compressionLevel);
        return zr;
    }

}