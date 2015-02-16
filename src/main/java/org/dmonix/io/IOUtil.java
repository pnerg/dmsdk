package org.dmonix.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dmonix.io.filters.AcceptAllFilter;
import org.dmonix.io.filters.DirectoryFilter;

/**
 * I/O related utility methods.
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class IOUtil {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(IOUtil.class.getName());

    private static final DirectoryFilter dirFilter = new DirectoryFilter();

    /**
     * Attempt to close the provided stream. <br>
     * Any exceptions are caught and loggged.
     * 
     * @param istream
     */
    public static void closeNoException(InputStream istream) {
        try {
            istream.close();
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed to close stream : " + ex.getMessage());
        }
    }

    /**
     * Attempt to close the provided stream. <br>
     * Any exceptions are caught and loggged.
     * 
     * @param ostream
     */
    public static void closeException(OutputStream ostream) {
        try {
            ostream.close();
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed to close stream : " + ex.getMessage());
        }
    }

    /**
     * Attempt to close the provided stream. <br>
     * Any exceptions are caught and loggged.
     * 
     * @param reader
     */
    public static void closeException(Reader reader) {
        try {
            reader.close();
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed to close reader : " + ex.getMessage());
        }
    }

    /**
     * Attempt to close the provided stream. <br>
     * Any exceptions are caught and loggged.
     * 
     * @param writer
     */
    public static void closeException(Writer writer) {
        try {
            writer.close();
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed to close writer : " + ex.getMessage());
        }
    }

    /**
     * Compare the two input files. The content of the files will be compared byte by byte.
     * 
     * @param f1
     * @param f2
     * @return
     * @throws IOException
     */
    public static boolean compare(File f1, File f2) throws IOException {
        if (f1 == null || !f1.exists() || f2 == null || !f2.exists() || f1.length() != f2.length())
            return false;

        if (f1.getAbsolutePath().equals(f2.getAbsolutePath()))
            return true;

        BufferedInputStream bostream1 = new BufferedInputStream(new FileInputStream(f1));
        BufferedInputStream bostream2 = new BufferedInputStream(new FileInputStream(f2));

        int b;
        while ((b = bostream1.read()) != -1) {
            if (b != bostream2.read()) {
                bostream1.close();
                bostream2.close();
                return false;
            }
        }

        bostream1.close();
        bostream2.close();
        return true;
    }

    /**
     * Copy a file from a source. <br>
     * The target may not be a directory, in such case an exception will be raised. <br>
     * If the target file does not exist it will be created along with the entire path to the target file.
     * 
     * @param target
     *            The target file
     * @param source
     *            The source
     * @throws IOException
     */
    public static void copyFile(InputStream source, File target) throws IOException {
        if (target == null)
            throw new IOException("The target can not be null");

        if (target.isDirectory())
            throw new IOException("Target can not not be a directory\n" + target.getAbsolutePath());

        if (!target.exists()) {
            if (target.getParentFile() != null)
                target.getParentFile().mkdirs();
        }

        copyStreams(source, new FileOutputStream(target), true, true);
    }

    /**
     * Copy a file from an other file.
     * 
     * @param source
     *            The source file
     * @param target
     *            The target file
     * @throws IOException
     */
    public static void copyFile(File source, File target) throws IOException {
        copyFile(new FileInputStream(source), target);
    }

    /**
     * Copy a file from a URL.
     * 
     * @param url
     *            The source URL
     * @param target
     *            The target file
     * @throws IOException
     */
    public static void copyFile(URL url, File target) throws IOException {
        copyFile(url.openStream(), target);
    }

    /**
     * Copy a file from a source. <br>
     * Any exceptions are ignored and logged.
     * 
     * @param target
     *            The target file
     * @param source
     *            The source
     */
    public static void copyFileNoException(File source, File target) {
        try {
            IOUtil.copyFile(source, target);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Could not copy file from source to target " + target.getAbsolutePath(), ex);
        }
    }

    /**
     * Copy a file from a source. <br>
     * Any exceptions are ignored and logged.
     * 
     * @param target
     *            The target file
     * @param source
     *            The source
     */
    public static void copyFileNoException(InputStream source, File target) {
        try {
            IOUtil.copyFile(source, target);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Could not copy file from source to target " + target.getAbsolutePath(), ex);
        }
    }

    /**
     * Copy a file from a URL. <br>
     * Any exceptions are ignored and logged.
     * 
     * @param target
     *            The target file
     * @param url
     *            The source URL
     */
    public static void copyFileNoException(URL url, File target) {
        try {
            copyFile(url.openStream(), target);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Could not copy file from URL " + url.toString() + " to target " + target.getAbsolutePath(), ex);
        }
    }

    /**
     * Copy data between two streams. <br>
     * This is the same as a call to <code>copyStreams(InputStream, OutputStream, true, true)</code>
     * 
     * @param source
     *            The source stream
     * @param target
     *            The target stream
     * @throws IOException
     */
    public static void copyStreams(InputStream source, OutputStream target) throws IOException {
        copyStreams(source, target, true, true);
    }

    /**
     * Copy data between two streams. <br>
     * The output stream will be wrapped in a <code>BufferedOutputStream</code> in order to optimize the writing.
     * 
     * @param source
     *            The source stream
     * @param target
     *            The target stream
     * @param closeSource
     *            If the source stream is to be closed after the operation
     * @param closeTarget
     *            If the target stream is to be closed after the operation
     * @throws IOException
     */
    public static void copyStreams(InputStream source, OutputStream target, boolean closeSource, boolean closeTarget) throws IOException {
        int read = -1;
        byte[] data = new byte[512];

        BufferedOutputStream bostream = new BufferedOutputStream(target);
        while ((read = source.read(data)) > 0) {
            bostream.write(data, 0, read);
        }
        bostream.flush();

        if (closeSource)
            source.close();

        if (closeTarget)
            bostream.close();
    }

    /**
     * The method deletes a directory. <br>
     * Since <code>File.delete()</code> only works on empty directories this method will recursively find and delete all sub-directories and files to the input
     * directory.<br>
     * If the input is a non-directory file it will simply be deleted.
     * 
     * @param dir
     *            The directory to be deleted
     * @return The number of deleted files/directories
     */
    public static int deleteDirectory(File dir) {
        int deletedFiles = 1;
        if (dir.isDirectory()) {
            File[] fileList = dir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                deletedFiles += deleteDirectory(fileList[i]);
            }
        }

        deleteFile(dir);

        return deletedFiles;
    }

    /**
     * Remove a single file. <br>
     * Note that if the file is a non-empty directory it cannot be deleted. <br>
     * In order to delete a non-empty directory use <code>deleteDirectory(File)</code> <br>
     * The delete result will be logged using <code>Level.FINE</code>
     * 
     * @param file
     *            The file to remove
     * @return The result of the operation
     * @see deleteDirectory(File)
     */
    public static boolean deleteFile(File file) {
        boolean result = file.delete();
        if (log.isLoggable(Level.FINE))
            log.log(Level.FINE, "Deleting file : " + file.getAbsolutePath() + " - " + result);

        return result;
    }

    /**
     * Remove a single file. <br>
     * Note that if the file is a non-empty directory it cannot be deleted. <br>
     * In order to delete a non-empty directory use <code>deleteDirectory(File)</code> <br>
     * The delete result will be logged using <code>Level.FINE</code>
     * 
     * @param file
     *            The file to remove
     * @return The result of the operation
     * @see deleteDirectory(File)
     */
    public static boolean deleteFile(String file) {
        return deleteFile(new File(file));
    }

    /**
     * Recursively deletes all files in the provided path that match the filter. <br>
     * Each delete operation invokes <code>deleteFile(File)</code>
     * 
     * @param dir
     *            The directory path
     * @param filter
     *            The filter
     * @return The number of deleted files
     */
    public static int deleteFiles(File dir, FileFilter filter) {
        ArrayList<File> files = new ArrayList<File>();
        listFiles(dir, files, filter);
        for (File file : files) {
            deleteFile(file);
        }

        return files.size();
    }

    /**
     * Recursively deletes all files in the provided path that match the filter. <br>
     * Each delete operation invokes <code>deleteFile(File)</code>
     * 
     * @param dir
     *            The directory path
     * @param filter
     *            The filter
     * @return The number of deleted files
     */
    public static int deleteFiles(File dir, FilenameFilter filter) {
        ArrayList<File> files = new ArrayList<File>();
        listFiles(dir, files, filter);
        for (File file : files) {
            deleteFile(file);
        }

        return files.size();
    }

    /**
     * Remove an array of files.
     * 
     * @param files
     *            The files to remove
     */
    public static void deleteFiles(File[] files) {
        for (int i = 0; i < files.length; i++) {
            deleteFile(files[i]);
        }
    }

    /**
     * Return the relative path between the directory and the file. <br>
     * The method returns null if:
     * <ul>
     * <li>if the directory file isn't a directory</li>
     * <li>if the directory file isn't a parent directory to the file</li>
     * </ul>
     * E.g. the directory <i>/etc/foo/</i> and the file <i>/etc/foo/data/file.txt</i> results in <i>data/file.txt/</i>
     * 
     * @param directory
     *            The directory
     * @param file
     *            The file
     * @return The relative path
     */
    public static String getRelativePath(File directory, File file) {
        if (directory.isDirectory() == false)
            return null;

        String sdirectory = directory.getAbsolutePath();
        String sfile = file.getAbsolutePath();

        if (sfile.startsWith(sdirectory) == false)
            return null;

        return sfile.substring(sdirectory.length() + 1);
    }

    /**
     * Recursively lists all files for a directory. <br>
     * If the input is a non-directory file this file will be returned in a single item <code>File</code> array.<br>
     * Invoking this method is equal to:<br>
     * <code>listFiles(File, AcceptAllFileFilter)</code>
     * 
     * @param dir
     *            The directory to list items for
     * @return A File array with all files and directories.
     * @see org.dmonix.io.filters.AcceptAllFilter
     */
    public static File[] listFiles(File dir) {
        return listFiles(dir, (FileFilter) new AcceptAllFilter());
    }

    /**
     * Recursively lists all files for a directory. <br>
     * If the input is a non-directory file this file will be returned in a single item <code>File</code> array.<br>
     * Only items matching the filter will be returned
     * 
     * @param dir
     *            The directory to list items for
     * @param filter
     *            The filter
     * @return A File array with all files and directories.
     */
    public static File[] listFiles(File dir, FileFilter filter) {
        if (!dir.isDirectory())
            return new File[] { dir };

        ArrayList<File> files = new ArrayList<File>();
        listFiles(dir, files, filter);
        files.trimToSize();
        File[] f = new File[files.size()];
        files.toArray(f);

        return f;
    }

    /**
     * Recursively lists all files for a directory. <br>
     * If the input is a non-directory file this file will be returned in a single item <code>File</code> array.<br>
     * Only items matching the filter will be returned
     * 
     * @param dir
     *            The directory to list items for
     * @param filter
     *            The filter
     * @return A File array with all files and directories.
     */
    public static File[] listFiles(File dir, FilenameFilter filter) {
        if (!dir.isDirectory())
            return new File[] { dir };

        ArrayList<File> files = new ArrayList<File>();
        listFiles(dir, files, filter);
        files.trimToSize();
        File[] f = new File[files.size()];
        files.toArray(f);

        return f;
    }

    /**
     * Create a backup file of the specified file. The backup will have the same name as the file but with the appended extension <code>.bak</code>.
     * 
     * @param file
     *            the file to backup
     */
    public static void makeBackup(File file) {
        File outputFile = new File(file.getParent(), file.getName() + ".bak");
        copyFileNoException(file, outputFile);
    }

    private static void listFiles(File dir, List<File> files, FileFilter filter) {
        // list and store all files that match the filter
        File[] f = dir.listFiles(filter);
        for (int i = 0; i < f.length; i++) {
            files.add(f[i]);
        }

        // list all directories and traverse these in order
        File[] dirs = dir.listFiles(dirFilter);
        for (int i = 0; i < dirs.length; i++) {
            listFiles(dirs[i], files, filter);
        }
    }

    private static void listFiles(File dir, List<File> files, FilenameFilter filter) {
        // list and store all files that match the filter
        File[] f = dir.listFiles(filter);
        for (int i = 0; i < f.length; i++) {
            files.add(f[i]);
        }

        // list all directories and traverse these in order
        File[] dirs = dir.listFiles(dirFilter);
        for (int i = 0; i < dirs.length; i++) {
            listFiles(dirs[i], files, filter);
        }
    }
}
