
public class FileDuplicationCheck {

}




/*
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import java.math.BigInteger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.security.MessageDigest;
import java.security.DigestInputStream;
import java.security.NoSuchAlgorithmException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePaths {

  Path duplicatePath;
  Path originalPath;

  public FilePaths(Path duplicatePath, Path originalPath) {
      this.duplicatePath = duplicatePath;
      this.originalPath  = originalPath;
  }

  public String toString() {
      return "(original: " + originalPath + ", duplicate: " + duplicatePath + ")";
  }
}


public class FileInfo {

  long timeLastEdited;
  Path path;

  public FileInfo(long timeLastEdited, Path path) {
      this.timeLastEdited = timeLastEdited;
      this.path = path;
  }
}


public List<FilePaths> findDuplicateFiles(Path startingDirectory) {
  Map<String, FileInfo> filesSeenAlready = new HashMap<String, FileInfo>();
  Stack<Path> stack = new Stack<Path>();
  stack.push(startingDirectory);

  List<FilePaths> duplicates = new ArrayList<FilePaths>();

  while (!stack.empty()) {

      Path currentPath = stack.pop();
      File currentFile = new File(currentPath.toString());

      // if it's a directory,
      // put the contents in our stack
      if (currentFile.isDirectory()) {
          for (File file : currentFile.listFiles()) {
              stack.add(file.toPath());
          }

      // if it's a file
      } else {

          // get its hash
          String fileHash = sampleHashFile(currentPath);

          // get its last edited time
          long currentLastEditedTime = currentFile.lastModified();

          // if we've seen it before
          if (filesSeenAlready.containsKey(fileHash)) {

              FileInfo fileInfo = filesSeenAlready.get(fileHash);
              long existingLastEditedTime = fileInfo.timeLastEdited;
              Path existingPath = fileInfo.path;

              if (currentLastEditedTime > existingLastEditedTime) {

                  // current file is the dupe!
                  duplicates.add(new FilePaths(currentPath, existingPath));

              } else {

                  // old file is the dupe!
                  duplicates.add(new FilePaths(existingPath, currentPath));

                  // but also update filesSeenAlready to have the new file's info
                  filesSeenAlready.put(fileHash, new FileInfo(currentLastEditedTime, currentPath));
              }

          // if it's a new file, throw it in filesSeenAlready
          // and record its path and last edited time,
          // so we can tell later if it's a dupe
          } else {
              filesSeenAlready.put(fileHash, new FileInfo(currentLastEditedTime, currentPath));
          }
      }
  }
  return duplicates;
}


public String sampleHashFile(Path path) {

  final int numBytesToReadPerSample = 4000;
  final long totalBytes = new File(path.toString()).length();

  InputStream inputStream = null;
  MessageDigest digest = null;

  try {
      inputStream = new FileInputStream(path.toString());
      digest = MessageDigest.getInstance("SHA-512");
  } catch (FileNotFoundException e) {
      System.out.println(e);
  } catch (NoSuchAlgorithmException e) {
      System.out.println(e);
  }

  DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest);

  try {

      // if the file is too short to take 3 samples, hash the entire file
      if (totalBytes < numBytesToReadPerSample * 3) {
          byte[] bytes = new byte[(int) totalBytes];
          digestInputStream.read(bytes);
      } else {
          byte[] bytes = new byte[numBytesToReadPerSample * 3];
          long numBytesBetweenSamples = (totalBytes - numBytesToReadPerSample * 3) / 2;

          // read first, middle and last bytes
          for (int n = 0; n < 3; n++) {
              digestInputStream.read(bytes, n * numBytesToReadPerSample, numBytesToReadPerSample);
              digestInputStream.skip(numBytesBetweenSamples);
          }
      }
  } catch (IOException e) {
      System.out.println(e);
  }

  return new BigInteger(1, digest.digest()).toString(16).toString();
}*/